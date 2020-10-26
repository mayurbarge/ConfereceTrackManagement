package schedular

import domain.Track
import scalaz._, Scalaz._

object Scheduler {
  import ScheduledEvent._, Slot._

  def schedule(tracks: List[Track]) = {
    val scheduleTrack = (track: Track) => {
      track.events.foldLeft(List.empty[ScheduledEvent])((acc, element) => {
        acc match {
          case Nil => List(ToSemigroupOps(scheduledEventMonoid.zero)(scheduledEventMonoid) |+| ScheduledEvent(element.title.name, Slot(0, Some(element.minutes.value))))
          case _=> acc :+ (ToSemigroupOps(acc.last)(scheduledEventMonoid) |+| ScheduledEvent(element.title.name, Slot(0, Some(element.minutes.value))))
        }
      })
    }
    tracks.map(scheduleTrack)
  }
}