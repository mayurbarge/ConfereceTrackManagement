package runner

import domain._
import io.Reader
import scalaz.Scalaz._
import scalaz._
import schedular.{ScheduledEvent, Scheduler, SchedulingStrategy}
import validations.Validator.Result


object MainApp extends App {
  val i = System.getProperty("user.dir")
  val inputFilePath = "./input.txt"
  val validatedTalks: Result[List[Talk]] = Reader.getTokensFromFile(inputFilePath).map(e => Talk.validate(e._1,e._2)).sequence

  for {
    validTalk <- validatedTalks
    validatedSessions = SchedulingStrategy.knapsackScheduling.split(validTalk).map(Session.validatePair).sequence
  } yield {
    val tracks = validatedSessions.map(_.map(e => Track(e._1, e._2)))
    val schedules: Validation[NonEmptyList[String], List[List[ScheduledEvent]]] = tracks.map(Scheduler.schedule)

    schedules.map(allTracks => {
      println(allTracks.flatten)
      allTracks.flatten.map(e => println(ScheduledEvent.toString(e)))
    })
  }
}


