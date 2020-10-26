package schedular

import domain.Event

class Knapsack(allEvents: List[Event], capacity: Int) {
  def run() = {
    val weights = allEvents.map(_.minutes.value)
    val N = weights.length
    var dp: Array[Array[Int]] = Array.ofDim[Int](N + 1, capacity + 1)
    for (i <- (1 to N)) {
      for (j <- (1 to capacity)) {
        if (weights(i - 1) <= j) {
          dp(i)(j) = Math.max(
            weights(i - 1) + dp(i - 1)(j - weights(i - 1)),
            dp(i - 1)(j))
        } else {
          dp(i)(j) = dp(i - 1)(j)
        }
      }
    }

    var result = dp(N)(capacity)
    var currentCapacity = capacity
    var selectedEvents = scala.collection.mutable.ListBuffer[Event]()
    for( i <- N to -1 by -1 if i > 0 && result > 0 ) {
      if(result != dp(i-1)(currentCapacity)) {
        selectedEvents += allEvents(i-1)
        result = result - weights(i-1)
        currentCapacity = currentCapacity - weights(i-1)
      }
    }
    selectedEvents.toList
  }
}