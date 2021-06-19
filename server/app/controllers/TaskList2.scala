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
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
    //Ok(s"username: $username is logged in with password: $password")
  }

  def createUser(username: String, password: String) = Action {

    if (TaskListInMemoryModel.createUser(username, password)) {
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username))).withSession("username" -> username)
    } else {
      Ok(views.html.login2())
    }
  }

  def deleteTask(index: Int) = Action { implicit request =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      TaskListInMemoryModel.removeTask(username, index)
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def addTask(task: String) =  Action { implicit request =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      TaskListInMemoryModel.addTask(username, task)
      Ok(views.html.taskList2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def logout = Action {
    Redirect(routes.TaskList2.load()).withNewSession
  }

  def generatedJS = Action {
    Ok(views.js.generatedJS())
  }
}
