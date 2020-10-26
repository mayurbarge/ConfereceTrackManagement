package schedular

import domain.Track
import scalaz.Monoid
import scalaz.syntax.semigroup._
import scalaz.Semigroup._
import scalaz._, Scalaz._
import scalaz.Monoid._
import scalaz.syntax.monoid._


object Scheduler {

  def schedule(tracks: List[Track]) = {
    val scheduleTrack = (track: Track) => {
      track.events.foldLeft(List.empty[ScheduledEvent])((acc, element) => {
        acc match {
          case Nil => {
            import scalaz._, Scalaz._
            import Schedule._
            implicitly(scheduledEventMonoid.zero) |+| implicitly(ScheduledEvent(element.title.name, slotMonoidInstance.zero.copy(duration = Some(element.minutes.value))))

            List(scheduledEventMonoid.append(scheduledEventMonoid.zero, ScheduledEvent(element.title.name, slotMonoidInstance.zero.copy(duration = Some(element.minutes.value)))))
          }
          case _=> acc :+ (scheduledEventMonoid.append(acc.last, ScheduledEvent(element.title.name, slotMonoidInstance.zero.copy(duration = Some(element.minutes.value)))))
        }
      })
    }

    tracks.map(scheduleTrack)
  }
}

case class ScheduledEvent(eventName: String, slot: Slot)
object Schedule {
  implicit val scheduledEventMonoid = new Monoid[ScheduledEvent] {
    override def zero: ScheduledEvent = ScheduledEvent("", Slot.slotMonoidInstance.zero)
    override def append(f1: ScheduledEvent, f2: => ScheduledEvent): ScheduledEvent = {
      ScheduledEvent(f2.eventName, Slot(f1.slot.duration.getOrElse(0) + f1.slot.beginAtOffset, f2.slot.duration))
    }
  }
}

case class Slot(beginAtOffset: Int, duration: Option[Int])
object Slot {
  implicit val slotMonoidInstance = new Monoid[Slot] {
    override def zero: Slot = Slot(0, Some(0))
    override def append(f1: Slot, f2: => Slot): Slot = Slot(f1.beginAtOffset, f2.duration)
  }
}

/*
object App extends App {
  import Slot._
  import Schedule._
  import Slot.slotMonoidInstance._
  import Schedule.scheduledEventMonoid._
  val x = List(Slot(0,Some(50)), Slot(50,Some(100))).suml
  val track = Track(MorningSession(List(Talk(Title("T1"), Minutes(90)), Talk(Title("T2"), Minutes(90)))),
    AfternoonSession(List(Talk(Title("T3"), Minutes(90)), Talk(Title("T4"), Minutes(90)), Talk(Title("T5"), Minutes(90)))))

  val y = track.events.foldLeft(List.empty[ScheduledEvent])((acc, element) => {
    acc match {
      case Nil => List(scheduledEventMonoid.append(scheduledEventMonoid.zero, ScheduledEvent(element.title.name, slotMonoidInstance.zero.copy(endAtOffset = Some(element.minutes.value)))))
      case _=> acc :+ (scheduledEventMonoid.append(acc.last, ScheduledEvent(element.title.name, slotMonoidInstance.zero.copy(endAtOffset = Some(element.minutes.value)))))
    }
  })

  println(y)
  /*val y: ScheduledEvent = track.events.foldLeft(Schedule.scheduledEventMonoid.zero)((acc, event) => {
    ScheduledEvent(event.title.name, Slot.slotMonoidInstance.append(acc.slot,Slot(0, Some(event.minutes.value))))
  })*/

  //val y = track.events.map(event => ScheduledEvent(event.title.name, Slot.slotMonoidInstance.zero.copy(endAtOffset = Some(event.minutes.value))))



  //track.events.foldLeft(slotMonoidInstance.zero)(slotMonoidInstance.append)
}*/
