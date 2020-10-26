package domain

import java.time.ZonedDateTime
import datetime.TimeZoneAndDurationProvider
import scalaz.Scalaz._
import scalaz._
import validations.Validator.Result

trait Session {
  val sessionDuration: Int
}
case class MorningSession(events: List[Event]) extends Session {
  override val sessionDuration: Int = events.map(_.minutes.value).sum
}
case class AfternoonSession(events: List[Event]) extends Session {
  override val sessionDuration: Int = events.map(_.minutes.value).sum
}

object Session {
  import TimeZoneAndDurationProvider.initial._

  def validatePair(morningAndAfteroonSession: (MorningSession, AfternoonSession)): Result[(_ <: MorningSession, _ <: AfternoonSession)] = {
    val (morningSession, afternoonSession) = morningAndAfteroonSession
    val validatedMorningSession: Result[MorningSession] = validateMorningSession(morningSession, TimeZoneAndDurationProvider.initial)
    val validatedAfternoonSession: Result[AfternoonSession] = validateAfternoonSession(afternoonSession, TimeZoneAndDurationProvider.initial.plusHours(4))
    (validatedMorningSession |@| validatedAfternoonSession).tupled
  }

  private def validateMorningSession(session: MorningSession, startTime: ZonedDateTime): Result[MorningSession] = {
    if (plusHours(0) == startTime && plusHours(3) == startTime.plusMinutes(session.sessionDuration))
      session.success
    else "Error morning session validation failed.".failureNel
  }

  private def validateAfternoonSession(session: AfternoonSession, startTime: ZonedDateTime): Result[AfternoonSession] = {
    if (plusHours(4) == startTime &&
      plusHours(7) <= startTime.plusMinutes(session.sessionDuration)   &&
      startTime.plusMinutes(session.sessionDuration) <= plusHours(8) ) {
      session.success
    }
    else "Error afternoon session validation failed.".failureNel
  }
}