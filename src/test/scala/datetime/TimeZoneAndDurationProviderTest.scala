package datetime

import org.scalatest.Matchers

class TimeZoneAndDurationProviderTest extends org.scalatest.FunSpec with Matchers {
  describe("TimeZoneAndDurationProvider") {
    it("should provide day start time at 9:00 AM") {
      TimeZoneAndDurationProvider.morning9Time.getHour shouldBe 9
      TimeZoneAndDurationProvider.morning9Time.getMinute shouldBe 0
    }
  }
}
