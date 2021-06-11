package controllers

import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject._


@Singleton
class TaskList2 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def load = Action { implicit request =>
    Ok(views.html.main2())
  }

  def login = Action {
    Ok(views.html.login2())
  }


}
