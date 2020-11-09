package runner

import domain._
import io.{Reader, Writer}
import scalaz.Scalaz._
import scalaz.{Validation, _}
import schedular.{ScheduledEvent, Scheduler, SchedulingStrategy}
import validations.Validator.Result


object MainApp extends App {
  override def main(args: Array[String]): Unit = {
    val inputFilePath = "./input.txt"
    val validatedTalks = readTalksFromFile(inputFilePath)
    val validatedSessions = scheduleAndValidateSessions(validatedTalks)
    printResults(getSchedule(validatedSessions))
  }

  def readTalksFromFile(inputFilePath: String) = {
    Reader.getTokensFromFile(inputFilePath).map(e => Talk.validate(e._1, e._2)).sequence
  }

  def scheduleAndValidateSessions(validatedTalks: Result[List[Talk]]) = {
    for {
      validTalk <- validatedTalks
      validatedSessions = SchedulingStrategy.knapsackScheduling.split(validTalk).map(Session.validatePair).sequence
    } yield {
      validatedSessions
    }
  }

  def getSchedule(validatedSessions: Validation[NonEmptyList[String], Result[List[Tuple2[_ <: MorningSession, _ <: AfternoonSession]]]]) = {
    validatedSessions.map(_ match {
      case Success(validSessions) => processSessions(validSessions)
      case Failure(message) => message.toList.mkString("\n")
    })
  }

  def processSessions(validSessions: List[(_ <: MorningSession, _ <: AfternoonSession)]): String = {
    val schedules = validSessions.map(e => Track(e._1, e._2)).map(Scheduler.schedule)
    ScheduledEvent.printSchedules(schedules)
  }

  def printResults(result: Validation[NonEmptyList[String], String]) = {
    result match {
      case Success(output) => Writer.write(output).unsafePerformIO()
      case Failure(message) => Writer.write(message.toList.mkString("\n"))
    }
  }
}


