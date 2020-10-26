package schedular

import java.util.spi.TimeZoneNameProvider

import datetime.TimeZoneAndDurationProvider
import scalaz.Monoid

case class Slot(beginAtOffset: Int, duration: Option[Int])
case class ScheduledEvent(eventName: String, slot: Slot)

object ScheduledEvent {
  implicit val scheduledEventMonoid = new Monoid[ScheduledEvent] {
    override def zero: ScheduledEvent = ScheduledEvent("", Slot(0, None))
    override def append(earlierEvent: ScheduledEvent, laterEvent: => ScheduledEvent): ScheduledEvent = {
      ScheduledEvent(laterEvent.eventName, Slot(earlierEvent.slot.duration.getOrElse(0) + earlierEvent.slot.beginAtOffset, laterEvent.slot.duration))
    }
  }

  def toString(event: ScheduledEvent) = {
    s""" ${TimeZoneAndDurationProvider.morning9Time.plusMinutes(event.slot.beginAtOffset)} ${event.eventName}  ${event.slot.duration.getOrElse(0)} min"""
  }
}