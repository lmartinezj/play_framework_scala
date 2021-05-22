package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._

@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.login1())
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"username: $username is logged in with password: $password")
  }

  def taskList = Action {
    val tasks = List("task1", "task3", "task3", "eat", "sleep")
    Ok(views.html.taskList1(tasks))
  }
}
