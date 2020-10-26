package schedular

import domain.{AfternoonSession, Lunch, Minutes, MorningSession, Talk, Title, Track}
import org.scalatest.{FunSpec, Matchers}

class SchedulerTest extends FunSpec with Matchers {
  describe("Scheduler") {
    val track = Track(MorningSession(List(Talk(Title("T1"), Minutes(90)),
          Talk(Title("T2"), Minutes(90)))),
      AfternoonSession(List(Talk(Title("T3"), Minutes(90)), Talk(Title("T4"), Minutes(90)), Talk(Title("T5"), Minutes(90)))))

    it("should return the schedule for events when track is provided") {
      val expectedEventSchedule = List(List(
        ScheduledEvent("T1",Slot(0,Some(90))),
        ScheduledEvent("T2",Slot(90,Some(90))),
        ScheduledEvent("Lunch",Slot(180,Some(60))),
        ScheduledEvent("T3",Slot(240,Some(90))),
        ScheduledEvent("T4",Slot(330,Some(90))),
        ScheduledEvent("T5",Slot(420,Some(90))),
        ScheduledEvent("Networking",Slot(510,Some(0)))))

      Scheduler.schedule(List(track)) should contain theSameElementsAs expectedEventSchedule
    }
  }

}
