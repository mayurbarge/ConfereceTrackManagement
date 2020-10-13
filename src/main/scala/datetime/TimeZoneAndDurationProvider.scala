package datetime

import java.time.{Duration, ZoneId, ZonedDateTime}
import java.time.temporal.ChronoField
import java.util.Date

import scalaz.{Failure, Success, Validation}

object TimeZoneAndDurationProvider {
  private val date = new Date()
  private val inst = date.toInstant()
  val initial: ZonedDateTime = inst.atZone(ZoneId.systemDefault())
    .`with`(ChronoField.HOUR_OF_DAY, 0)
    .`with`(ChronoField.MINUTE_OF_DAY, 0)
    .`with`(ChronoField.SECOND_OF_DAY, 0)
    .`with`(ChronoField.MILLI_OF_SECOND, 0)
    .plus(Duration.ofHours(9))

  def getTimeAt(minutesSinceStart: Int): Validation[String, ZonedDateTime] = {
    if(0 <= minutesSinceStart)
      Success(initial.plus(Duration.ofMinutes(minutesSinceStart)))
    else
      Failure("Invalid minutes !")
  }
}

