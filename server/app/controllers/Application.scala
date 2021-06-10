package controllers

import play.api.mvc._
import shared.SharedMessages

import javax.inject._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"Product Type is: $prodType, product number is: $prodNum")
  }

  def randomNumber = Action {
    Ok(util.Random.nextInt(100).toString)
  }
  
}
