package domain

import scalaz.{Failure, Success, Validation}


case class Track(morningSession: MorningSession, afternoonSession: AfternoonSession) {
  def events = {
    val tillNetworking: List[Event] = (morningSession.events :+ Lunch(60)) ++ afternoonSession.events
    tillNetworking :+ Networking(0)
  }
}
