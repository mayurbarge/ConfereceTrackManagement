package schedular

import domain.{AfternoonSession, Minutes, MorningSession, Session, Talk, Title}
import org.scalatest.{FunSpec, Matchers}
import scalaz._, Scalaz._

class SchedulingStrategyTest extends FunSpec with Matchers {
  describe("Default Scheduling Strategy") {
    val talks = List(
      Talk(Title("1"), Minutes(60)),
      Talk(Title("2"), Minutes(45)),
      Talk(Title("3"), Minutes(30)),
      Talk(Title("4"), Minutes(45)),
      Talk(Title("5"), Minutes(45)),
      Talk(Title("6"), Minutes(60)),
      Talk(Title("7"), Minutes(45)),
      Talk(Title("8"), Minutes(30)),
      Talk(Title("9"), Minutes(30)),
      Talk(Title("10"), Minutes(45)),
      Talk(Title("11"), Minutes(60)),
      Talk(Title("12"), Minutes(60)),
      Talk(Title("13"), Minutes(45)),
      Talk(Title("14"), Minutes(30)),
      Talk(Title("15"), Minutes(30)),
      Talk(Title("16"), Minutes(60)),
      Talk(Title("17"),Minutes(30)),
      Talk(Title("18"), Minutes(30)))

    it("should split events into morning and afternoon sessions") {
      val result: Seq[(MorningSession, AfternoonSession)] = SchedulingStrategy.knapsackScheduling.split(talks)
      val validationResult = result.toList.map(Session.validatePair).sequence
      validationResult.isSuccess shouldBe true
    }

    it("should fail validation when events do not fit within the session boundries") {
      val result: Seq[(MorningSession, AfternoonSession)] = SchedulingStrategy.knapsackScheduling.split(Talk(Title("19"), Minutes(130)) :: talks)
      val validationResult = result.toList.map(Session.validatePair).sequence
      validationResult.isFailure shouldBe true
    }
  }

}
