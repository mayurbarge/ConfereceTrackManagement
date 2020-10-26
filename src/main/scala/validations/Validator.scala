package validations

import scalaz.ValidationNel

object Validator {
  type Result[A] = ValidationNel[String, A]
}
