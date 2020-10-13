package validations

import domain.Track
import scalaz.{Failure, Success, Validation}

trait Validator[T] {
  def validate(t: T): Validation[String, T]
}
object Validator {
  implicit def trackValidator = new Validator[Track] {
    def validate(track: Track): Validation[String, Track] = {
      if (track.morningSession.events.map(_.minutes).sum <= 180 &&
        (track.afternoonSession.events.map(_.minutes).sum >= 180 && track.afternoonSession.events.map(_.minutes).sum <= 240)) {
        Success(track)
      }
      else {
        Failure("Session invalid")
      }
    }

    implicit def validateTrack(track: Track)(implicit validator: Validator[Track]) = {
      validator.validate(track)
    }
  }
}

