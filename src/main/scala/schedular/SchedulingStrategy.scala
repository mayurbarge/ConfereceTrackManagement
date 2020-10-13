package schedular

import domain._

trait SchedulingStrategy extends {
  def split(events: List[Event]): List[(MorningSession, AfternoonSession)]
}

object SchedulingStrategy {
  val defaultEventsScheduling = new SchedulingStrategy {
    def filterEvents(allEvents: List[Event], duration: Int): List[Event] = {
      val (_, events) =
        allEvents.foldLeft((0, List.empty[Event]))((acc, e) => {
          if (acc._1 + e.minutes <= duration) {
            val (sum, sessionEvents): (Int, List[Event]) = acc
            (sum + e.minutes, sessionEvents :+ e)
          } else {
            acc
          }
        })
      events
    }

    override def split(events: List[Event]): List[(MorningSession, AfternoonSession)] = {
      def go(remainingEvents: List[Event], done: List[(MorningSession, AfternoonSession)]): List[(MorningSession, AfternoonSession)] = {
        if(remainingEvents.isEmpty)
          return done
        val morningSession =  MorningSession(filterEvents(remainingEvents, 180))
        val afternoonSession =  AfternoonSession(filterEvents(remainingEvents diff morningSession.events, 240))
        go((remainingEvents diff morningSession.events diff afternoonSession.events), done :+ (morningSession, afternoonSession))
      }
      go(events.sortBy(_.minutes).reverse, List.empty[(MorningSession, AfternoonSession)])
    }
  }
}

