package controllers

import models.{TaskListInMemoryModel, UserData}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc._
import play.filters.csrf.CSRF

import javax.inject._


@Singleton
class TaskList3 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def load = Action { implicit request =>
    Ok(views.html.main3())
  }

  implicit val UserDataReads: Reads[UserData] = Json.reads[UserData]

  def withJsonBody[A](f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case error @ JsError(_) => Redirect(routes.TaskList3.load())
      }
    }.getOrElse(Redirect(routes.TaskList3.load()))
  }

  def withSessionUsername(f: String => Result)(implicit request: Request[AnyContent]) = {
    request.session.get("username")
      .map(f)
      .getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

  def validate = Action {implicit request =>
    withJsonBody[UserData] { myUserData =>
      if (TaskListInMemoryModel.validateUser(myUserData.username, myUserData.password)) {
        Ok(Json.toJson(true))
          .withSession(
            "username" -> myUserData.username,
            "csrfToken" -> CSRF.getToken.get.value
          )
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def taskList = Action { implicit request   =>
    withSessionUsername { username =>
      Ok(Json.toJson(TaskListInMemoryModel.getTasks(username)))
    }
  }

  def addTask = Action { implicit request =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      request.body.asJson.map { body =>
        Json.fromJson[String](body) match {
          case JsSuccess(task, path) =>
            TaskListInMemoryModel.addTask(username, task);
            Ok(Json.toJson(true))
          case error@JsError(_) => Redirect(routes.TaskList3.load())
        }
      }.getOrElse(Ok(Json.toJson(false)))
    }.getOrElse(Ok(Json.toJson(false)))
  }

  def deleteTask = Action { implicit request =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      request.body.asJson.map { body =>
        Json.fromJson[Int](body) match {
          case JsSuccess(index, path) =>
            TaskListInMemoryModel.removeTask(username, index);
            Ok(Json.toJson(true))
          case error@JsError(_) => Redirect(routes.TaskList3.load())
        }
      }.getOrElse(Ok(Json.toJson(false)))
    }.getOrElse(Ok(Json.toJson(false)))
  }

  def createUser = Action {implicit request =>
    withJsonBody[UserData] { myUserData =>
      if (TaskListInMemoryModel.createUser(myUserData.username, myUserData.password)) {
        Ok(Json.toJson(true))
          .withSession(
            "username" -> myUserData.username,
            "csrfToken" -> CSRF.getToken.get.value
          )
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def logout = Action {
    Ok(Json.toJson(true)).withNewSession
  }

}
