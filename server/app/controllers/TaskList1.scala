package controllers

import javax.inject._
import models.TaskListInMemoryModel
import play.api.mvc._
import play.api.i18n._

@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action { implicit request =>
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
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login())
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def createUser = Action { request =>
    val postValues = request.body.asFormUrlEncoded
    postValues.map {args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login())
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def taskList = Action { request =>
    val  usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def logout = Action {
    Redirect(routes.TaskList1.login()).withNewSession
  }
}
