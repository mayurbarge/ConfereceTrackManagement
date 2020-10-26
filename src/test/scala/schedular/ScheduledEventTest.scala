package schedular

import org.scalatest.{FunSpec, Matchers}
import ScheduledEvent._

class ScheduledEventTest extends FunSpec with Matchers {
  describe("ScheduledEvent") {
    val event1 = ScheduledEvent("E1", Slot(0, Some(30)))
    val event2 = ScheduledEvent("E2", Slot(0, Some(90)))
    val noDuration = ScheduledEvent("E1", Slot(0, None))
    val result = ScheduledEvent("E2", Slot(30, Some(90)))

    it("should combine events E1 and E2") {
      scheduledEventMonoid.append(event1, event2) shouldBe result
    }

    it("should combine events E1 and E2 with result beginAtOffset E1's duration") {
      scheduledEventMonoid.append(event1, event2).slot.beginAtOffset shouldBe 30
    }

    it("should combine events E1 and E2 when duration for E1 is not defined") {
      scheduledEventMonoid.append(noDuration, event2) shouldBe event2
    }
  }
}
