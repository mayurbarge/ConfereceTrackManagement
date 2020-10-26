package schedular

import domain._

trait SchedulingStrategy extends {
  def split(events: List[Event]): List[(MorningSession, AfternoonSession)]
}

object SchedulingStrategy {
  val knapsackScheduling = new SchedulingStrategy {
    override def split(events: List[Event]): List[(MorningSession, AfternoonSession)] = {
      def go(remainingEvents: List[Event], done: List[(MorningSession, AfternoonSession)]): List[(MorningSession, AfternoonSession)] = {
        if(remainingEvents.isEmpty)
          return done
        val morningSession =  MorningSession(new Knapsack(remainingEvents, 180).run)
        val afternoonSession =  AfternoonSession(new Knapsack(remainingEvents diff morningSession.events, 240).run)
        go((remainingEvents diff morningSession.events diff afternoonSession.events), done :+ (morningSession, afternoonSession))
      }
      go(events, List.empty[(MorningSession, AfternoonSession)])
    }
  }
}

