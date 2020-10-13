package domain

trait TimeOfTheDay
case object Morning extends TimeOfTheDay
case object Afternoon extends TimeOfTheDay

trait Session
case class MorningSession(events: List[Event]) extends Session
case class AfternoonSession(events: List[Event]) extends Session
