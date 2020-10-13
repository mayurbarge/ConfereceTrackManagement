package schedular

import domain.{AfternoonSession, MorningSession, Talk}
import org.scalatest.{FunSpec, Matchers}

class SchedulingStrategyTest extends FunSpec with Matchers {
  describe("Default Scheduling Strategy") {
    val talks = List(
      Talk("1", 60),
      Talk("2", 45),
      Talk("3", 30),
      Talk("4", 45),
      Talk("5", 45),
      Talk("6", 60),
      Talk("7", 45),
      Talk("8", 30),
      Talk("9", 30),
      Talk("10", 45),
      Talk("11", 60),
      Talk("12", 60),
      Talk("13", 45),
      Talk("14", 30),
      Talk("15", 30),
      Talk("16", 60),
      Talk("17",30),
      Talk("18", 30))

    it("should split events into morning and afternoon sessions by sorting in descending order") {
      val expected = List(
        (MorningSession(List(Talk("16",60), Talk("12",60), Talk("11",60))),
        AfternoonSession(List(Talk("6",60), Talk("1",60), Talk("13",45), Talk("10",45), Talk("18",30)))),
        (MorningSession(List(Talk("7",45), Talk("5",45), Talk("4",45), Talk("2",45))),
        AfternoonSession(List(Talk("17",30), Talk("15",30), Talk("14",30), Talk("9",30), Talk("8",30), Talk("3",30)))))

      SchedulingStrategy.defaultEventsScheduling.split(talks) should contain theSameElementsAs  expected
    }
  }

}
