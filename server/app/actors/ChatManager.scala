package actors


import akka.actor.{Actor, ActorRef}

class ChatManager extends Actor {

  import actors.ChatManager.{Message, NewChatter}

  private var chatters = List.empty[ActorRef]

  override def receive: Receive = {
    case NewChatter(chatter) => chatters ::= chatter
    case Message(message) => chatters.foreach(_ ! ChatActor.SendMessage(message))
    case _ => println("Unhandled message in ChatManager: " + _)
  }
}

object ChatManager {
  case class NewChatter(chatter: ActorRef)
  case class Message(message: String)
}
