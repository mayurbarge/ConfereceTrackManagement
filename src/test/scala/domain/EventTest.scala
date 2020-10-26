package domain

import scalaz._
import Scalaz._
import org.scalatest.{FunSpec, Matchers}
import validations.Validator.Result

class EventTest extends FunSpec with Matchers {
  describe("Event") {
    it("should create Talks when valid inputs are given") {
      Talk.validate("ABC", "90").isSuccess shouldBe true
    }

    it("should fail validation when minutes are incorrect") {
      Talk.validate("ABC", "abc").isFailure shouldBe true
    }

    it("should fail validation when minutes are negative") {
      Talk.validate("ABC", "-1").isFailure shouldBe true
    }

    it("should fail validation when minutes are zero") {
      Talk.validate("ABC", "0").isFailure shouldBe true
    }

    it("should show correct message when validation fails for invalid duration") {
      Talk.validate("ABC", "abc") shouldBe Failure(NonEmptyList(s"""Minute duration is invalid. For input string: "abc""""))
    }

    it("should show correct message when validation fails for duration less than zero") {
      Talk.validate("ABC", "-1") shouldBe Failure(NonEmptyList(s"""Duration can not be less than or equal to zero."""))
    }
  }
}
