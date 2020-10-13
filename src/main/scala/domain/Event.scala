package domain

trait Event {
  val name: String
  val minutes: Int
}
case class Talk(name: String, minutes:  Int) extends Event
case class Lunch(minutes: Int) extends Event {
  val name = "Lunch"
}
case class Networking(minutes: Int) extends Event {
  val name = "Networking"
}