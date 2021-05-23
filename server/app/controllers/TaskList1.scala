package controllers

import javax.inject._
import models.TaskListInMemoryModel
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

  def validateLoginPost = Action { request =>
    val postValues = request.body.asFormUrlEncoded
    postValues.map {args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskList1.taskList())
      } else {
        Redirect(routes.TaskList1.login())
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def createUser = TODO

  def taskList = Action {
    val  username = "luis"
    val tasks = TaskListInMemoryModel.getTasks(username)
    Ok(views.html.taskList1(tasks))
  }
}
