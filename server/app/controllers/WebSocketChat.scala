package controllers

import actors.ChatActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import javax.inject._


@Singleton
class WebSocketChat @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, materializer: Materializer)
  extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.chatPage())
  }

  def socket = WebSocket.accept[String, String] { request =>
    println("Getting Socket")
    ActorFlow.actorRef { out =>
      ChatActor.props(out)
    }
  }
}
