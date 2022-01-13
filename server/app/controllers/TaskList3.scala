package controllers

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject._


@Singleton
class TaskList3 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def load = TODO
  def data = Action {
    Ok(Json.toJson(Seq("a", "b", "c")))
  }
}
