package actors

import akka.actor.{Actor, ActorRef, Props}

class ChatActor(out: ActorRef, manager: ActorRef) extends Actor {
  import actors.ChatActor.SendMessage

  manager ! ChatManager.NewChatter(self)

  def receive: Receive = {
    case message: String => println("Got Message: " + message)
    case SendMessage(message) => out ! message
    case _ => println("Unhandled message in ChatActor: " + _)
  }
}

object ChatActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new ChatActor(out, manager))

  case class SendMessage(message: String)
}
