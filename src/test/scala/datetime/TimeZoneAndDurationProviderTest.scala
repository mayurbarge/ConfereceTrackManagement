package datetime

import org.scalatest.Matchers

class TimeZoneAndDurationProviderTest extends org.scalatest.FunSpec with Matchers {
  describe("TimeZoneAndDurationProvider") {
    it("should provide day start time at 9:00 AM") {
      TimeZoneAndDurationProvider.initial.getHour shouldBe 9
      TimeZoneAndDurationProvider.initial.getMinute shouldBe 0
    }

    it("should give time after 11:11 AM time when 71 minutes are added to the initial time") {
      val expectedTime = TimeZoneAndDurationProvider.getTimeAt(71)
      expectedTime.toDisjunction.getOrElse(TimeZoneAndDurationProvider.initial).getHour shouldBe 10
      expectedTime.toDisjunction.getOrElse(TimeZoneAndDurationProvider.initial).getMinute shouldBe 11
    }

    it("should give time after 11 are added to the initial time") {
      TimeZoneAndDurationProvider.getTimeAt(Integer.MIN_VALUE).toDisjunction.isLeft shouldBe true
    }
  }
}
