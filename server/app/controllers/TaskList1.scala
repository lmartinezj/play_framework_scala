package controllers

import javax.inject._
import models.TaskListInMemoryModel
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.mvc._

case class LoginData(username: String, password: String)

@Singleton
class TaskList1 @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  val loginForm = Form(mapping(
    "Username" -> text(4, 10),
    "Password" -> text(3)
  )(LoginData.apply)(LoginData.unapply))

  def login = Action { implicit request =>
    Ok(views.html.login1(loginForm))
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"username: $username is logged in with password: $password")
  }

  def validateLoginPost = Action { implicit request =>
    val postValues = request.body.asFormUrlEncoded
    postValues.map {args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username/password combination")
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def validateLoginForm = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithError => BadRequest(views.html.login1(formWithError)),
      loginData => {
        if (TaskListInMemoryModel.validateUser(loginData.username, loginData.password)) {
          Redirect(routes.TaskList1.taskList()).withSession("username" -> loginData.username)
        } else {
          Redirect(routes.TaskList1.login()).flashing("error" -> "Invalid username/password combination")
        }
      }
    )
  }

  def createUser = Action { implicit request =>
    val postValues = request.body.asFormUrlEncoded
    postValues.map {args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login()).flashing("error" -> "User creation failed")
      }
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def createUserForm = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithError => BadRequest(views.html.login1(formWithError)),
      loginData => {
        if (TaskListInMemoryModel.createUser(loginData.username, loginData.password)) {
          Redirect(routes.TaskList1.taskList()).withSession("username" -> loginData.username)
        } else {
          Redirect(routes.TaskList1.login()).flashing("error" -> "User creation failed")
        }
      }
    )
  }

  def taskList = Action { implicit request =>
    val  usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def addTask = Action { implicit request =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      val postValues = request.body.asFormUrlEncoded
      postValues.map { args =>
        val newTask = args("newTask").head
        TaskListInMemoryModel.addTask(username, newTask)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def deleteTask = Action { implicit request =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      val postValues = request.body.asFormUrlEncoded
      postValues.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskList1.taskList())
      }.getOrElse(Redirect(routes.TaskList1.taskList()))
    }.getOrElse(Redirect(routes.TaskList1.login()))
  }

  def logout = Action {
    Redirect(routes.TaskList1.login()).withNewSession
  }
}
