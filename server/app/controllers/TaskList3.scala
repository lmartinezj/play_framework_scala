package controllers

import models.{TaskListInMemoryModel, UserData}
import play.api.i18n._
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.filters.csrf.CSRF

import javax.inject._


@Singleton
class TaskList3 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def load = Action { implicit request =>
    Ok(views.html.main3())
  }

  implicit val UserDataReads = Json.reads[UserData]

  def validate = Action {implicit request =>
    request.body.asJson.map { body =>
      Json.fromJson[UserData](body) match {
        case JsSuccess(myUserData, path) =>

          if (TaskListInMemoryModel.validateUser(myUserData.username, myUserData.password)) {
            Ok(Json.toJson(true))
              .withSession(
                "username" -> myUserData.username,
                "csrfToken" -> CSRF.getToken.get.value
              )
          } else {
            Ok(Json.toJson(false))
          }

        case error @ JsError(_) => Redirect(routes.TaskList3.load())
      }
    }.getOrElse(Redirect(routes.TaskList3.load()))
  }

  def taskList = Action { implicit request   =>
    val userNameOption = request.session.get("username")
    userNameOption.map { username =>
      Ok(Json.toJson(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(Json.toJson(Seq.empty[String])))
  }

}
