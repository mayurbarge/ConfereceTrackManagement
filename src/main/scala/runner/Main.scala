package runner

import domain._
import io.Reader
import io.Writer
import runner.MainApp.validatedSessions
import scalaz.Scalaz._
import scalaz._
import schedular.{ScheduledEvent, Scheduler, SchedulingStrategy}
import validations.Validator.Result


object MainApp extends App {
  val i = System.getProperty("user.dir")
  val inputFilePath = "./input.txt"
  val validatedTalks: Result[List[Talk]] = readTalksFromFile
  val validatedSessions = scheduleAndValidateSessions

  def readTalksFromFile = {
    Reader.getTokensFromFile(inputFilePath).map(e => Talk.validate(e._1, e._2)).sequence
  }

  def scheduleAndValidateSessions = {
    for {
      validTalk <- validatedTalks
      validatedSessions = SchedulingStrategy.knapsackScheduling.split(validTalk).map(Session.validatePair).sequence
    } yield {
      validatedSessions
    }
  }

  def processSessions(validSessions: List[(_ <: MorningSession, _ <: AfternoonSession)]): String = {
    val schedules = validSessions.map(e => Track(e._1, e._2)).map(Scheduler.schedule)
    ScheduledEvent.printSchedules(schedules)
  }

  def getSchedule: Validation[NonEmptyList[String], String] = {
      validatedSessions.map(_ match {
        case Success(validSessions) => processSessions(validSessions)
        case Failure(message) => message.toList.mkString("\n")
      })
  }


  getSchedule match {
    case Success(output) => Writer.write(output).unsafePerformIO()
    case Failure(message) => Writer.write(message.toList.mkString("\n"))
  }
}


