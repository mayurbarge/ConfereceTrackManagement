package domain

case class Track(morningSession: MorningSession, afternoonSession: AfternoonSession) {
  def events: List[Event] = {
    val tillNetworking: List[Event] = (morningSession.events :+ Lunch(Minutes(60))) ++ afternoonSession.events
    tillNetworking :+ Networking(Minutes(0))
  }
}
