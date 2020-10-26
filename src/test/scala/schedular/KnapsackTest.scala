package schedular

import domain.{Minutes, Talk, Title}
import org.scalatest.{FunSpec, Matchers}

import scala.collection.immutable

class KnapsackTest extends FunSpec with Matchers {
  describe("Knapsack algorithm") {
    it("should select talks T1, T2, T3 and T6 when bucket size is 13") {
      val talks = List(
        Talk(Title("T1"), Minutes(5)),
        Talk(Title("T2"), Minutes(5)),
        Talk(Title("T3"), Minutes(1)),
        Talk(Title("T4"), Minutes(10)),
        Talk(Title("T5"), Minutes(4)),
        Talk(Title("T6"), Minutes(2))
      )
      new Knapsack(talks, 13).run().map(_.title.name) should contain theSameElementsAs(List("T1","T2","T3","T6"))
    }

    it("should not select any talk when talk lengths are more than bucket size") {
      val talks = List(
        Talk(Title("T1"), Minutes(5)),
        Talk(Title("T2"), Minutes(5)),
        Talk(Title("T3"), Minutes(10)),
        Talk(Title("T4"), Minutes(10)),
        Talk(Title("T5"), Minutes(4)),
        Talk(Title("T6"), Minutes(20))
      )
      new Knapsack(talks, 3).run().map(_.title.name) shouldBe immutable.List.empty[Talk]
    }

    it("should not select any talk when talk list is empty") {
      val talks = List.empty[Talk]
      new Knapsack(talks, 10).run().map(_.title.name) shouldBe immutable.List.empty[Talk]
    }
  }

}
