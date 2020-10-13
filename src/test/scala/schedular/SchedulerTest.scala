package schedular

import domain.{AfternoonSession, Lunch, MorningSession, Talk, Track}
import org.scalatest.{FunSpec, Matchers}

class SchedulerTest extends FunSpec with Matchers {
  describe("Scheduler") {
    val track = Track(MorningSession(List(Talk("T1", 90), Talk("T2", 90))), AfternoonSession(List(Talk("T3", 90), Talk("T4", 90), Talk("T5", 90))))
    it("should return the schedule for events when track is provided") {
      val expectedEventSchedule = List(EventSchedule("T1",0,Some(90)), EventSchedule("T2",90,Some(180)),
        EventSchedule("Lunch",180,Some(240)), EventSchedule("T3",240,Some(330)), EventSchedule("T4",330,Some(420)),
        EventSchedule("T5",420,Some(510)), EventSchedule("Networking",510,Some(510)))

      Scheduler.scheduleTrack(track) should contain theSameElementsAs expectedEventSchedule
    }
  }

}
