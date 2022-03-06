package actors

import akka.actor.{Actor, ActorRef, Props}

class ChatActor(out: ActorRef) extends Actor {
  def receive: Receive = {
    case message: String => println("Got Message: " + message)
    case _ => println("Unhandled message in ChatActor: " + _)
  }
}

object ChatActor {
  def props(out: ActorRef) = Props(new ChatActor(out))
}
