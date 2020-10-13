package runner

import datetime.TimeZoneAndDurationProvider
import domain._
import schedular.{EventSchedule, Scheduler, SchedulingStrategy}
import validations.Validator

object MainApp extends App {

  val talks = List(
    Talk("Writing Fast Tests Against Enterprise Rails", 60),
    Talk("Overdoing it in Python", 45),
    Talk("Lua for the Masses", 30),
    Talk("Ruby Errors from Mismatched Gem Versions", 45),
    Talk("Common Ruby Errors", 45),
    Talk("Rails for Python Developers lightning Communicating Over Distance", 60),
    Talk("Accounting-Driven Development", 45),
    Talk("Woah", 30),
    Talk("Sit Down and Write", 30),
    Talk("Pair Programming vs Noise", 45),
    Talk("Rails Magic", 60),
    Talk("Ruby on Rails: Why We Should Move On", 60),
    Talk("Clojure Ate Scala (on my project)", 45),
    Talk("Programming in the Boondocks of Seattle", 30),
    Talk("Ruby vs. Clojure for Back-End Development", 30),
    Talk("Ruby on Rails Legacy App Maintenance", 60),
    Talk("A World Without HackerNews", 30),
    Talk("User Interface CSS in Rails Apps", 30))

  val tracks: List[Track] =
    for {
      (morningSession, afternoonSession) <- SchedulingStrategy.defaultEventsScheduling.split(talks)
    } yield {
      Track(morningSession, afternoonSession)
    }

  val validatedTracks =
    for {
      track <- tracks
    } yield {
      implicitly[Validator[Track]].validate(track).toDisjunction
    }

  val schedule: List[List[EventSchedule]] = Scheduler.schedule(tracks)

  for {
    eventSchedules <- schedule
    _ = println(" Track ")
    eventSchedule <- eventSchedules
  } yield {
    TimeZoneAndDurationProvider.getTimeAt(eventSchedule.beginAtOffset).toDisjunction.map(e =>
      println(e + " -- " + eventSchedule.eventName + " " + (eventSchedule.endAtOffset.getOrElse(0) - eventSchedule.beginAtOffset) + " min"))
  }
}


