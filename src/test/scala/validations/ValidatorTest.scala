package validations

import domain.{AfternoonSession, MorningSession, Talk, Track}
import org.scalatest.{FunSpec, Matchers}
import scalaz.Success

class ValidatorTest extends FunSpec with Matchers {
  describe("Validator") {
    it("should apply validations to Track") {
      val track = Track(MorningSession(List(Talk("T1", 90), Talk("T2", 90))), AfternoonSession(List(Talk("T3", 90), Talk("T4", 90), Talk("T5", 60))))
      implicitly[Validator[Track]].validate(track).isSuccess shouldBe true
    }

    it("should return validation failure when morning session exceeds 180 minutes") {
      val track = Track(MorningSession(List(Talk("T1", 120), Talk("T2", 120))), AfternoonSession(List(Talk("T3", 90), Talk("T4", 90), Talk("T5", 60))))
      implicitly[Validator[Track]].validate(track).isSuccess shouldBe false
    }

    it("should return validation failure when afternoon session exceeds 240 minutes") {
      val track = Track(MorningSession(List(Talk("T1", 90), Talk("T2", 90))), AfternoonSession(List(Talk("T3", 120), Talk("T4", 120), Talk("T5", 60))))
      implicitly[Validator[Track]].validate(track).isSuccess shouldBe false
    }
  }

}
