package domain

import org.scalatest.{FunSpec, Matchers}

class TrackTest extends FunSpec with Matchers {
  describe("Track") {
    val invalidTrack = Track(MorningSession(List(Talk("T1",20),Talk("T2",30))), AfternoonSession(List(Talk("T1",20),Talk("T2",20))))
    val track = Track(MorningSession(List(Talk("T1",180))), AfternoonSession(List(Talk("T2",180))))

    it("should return events with lunch and networking events in a track") {
      track.events.size shouldBe 4
      track.events.count(_.isInstanceOf[Lunch]) shouldBe 1
      track.events.count(_.isInstanceOf[Networking]) shouldBe 1
    }

    it("should add networking event in the end of the end") {
      track.events.last.isInstanceOf[Networking] shouldBe true
    }
  }
}
