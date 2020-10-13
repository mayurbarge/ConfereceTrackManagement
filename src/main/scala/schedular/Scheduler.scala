package schedular

import domain.{Event, Track}

object Scheduler {
  def schedule(tracks: List[Track]) = {
    tracks.map(scheduleTrack)
  }

  val scheduleTrack= (track: Track) => {
    val (morningSlots, _) = elapsedMinutes(track.events)
    morningSlots
  }

  private val elapsedMinutes: List[Event] => (List[EventSchedule], Int) = (events: List[Event]) => {
    events.foldLeft((List.empty[EventSchedule], 0))((acc, e) => {
      val (slots, count) = acc
      val newSlots = {
        slots :+ EventSchedule(e.name, count, Some(count + e.minutes))
      }
      (newSlots, count + e.minutes)
    })
  }
}

case class EventSchedule(eventName: String, beginAtOffset: Int, endAtOffset: Option[Int])
