package controllers

import models.TaskListInMemoryModel
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

  def validateLoginGet(username: String, password: String) = Action {

    if (TaskListInMemoryModel.validateUser(username, password)) {
      println("If true")
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      println("If false")
      Ok(views.html.login2())
    }
    //Ok(s"username: $username is logged in with password: $password")
  }

}
