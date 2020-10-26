package domain

import scalaz._, Scalaz._
import validations.Validator.Result
import scala.util.{Failure, Success, Try}

final case class Title(name: String) extends AnyVal
final case class Minutes(value: Int) extends AnyVal

trait Event {
  val title: Title
  val minutes: Minutes
}
case class Talk(title: Title, minutes:  Minutes) extends Event
case class Lunch(minutes: Minutes) extends Event {
  val title = Title("Lunch")
}
case class Networking(minutes: Minutes) extends Event {
  val title = Title("Networking")
}

object Talk {
  def toInt(token: String): Result[Minutes] = {
    Try {token.toInt} match {
      case Success(value) if value <= 0 => "Duration can not be less than or equal to zero.".failureNel
      case Success(value) => Minutes(value).success
      case Failure(ex) => s"""Minute duration is invalid. ${ex.getLocalizedMessage}""".failureNel
    }
  }

  def toTitle(in: String): Result[Title] = Title(in).success

  def validate(title: String, minutes: String) = (toTitle(title) |@| toInt(minutes))(Talk.apply)
}