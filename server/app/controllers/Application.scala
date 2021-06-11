package controllers

import play.api.mvc._
import shared.SharedMessages

import javax.inject._
import scala.collection.immutable.List

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

  def randomString(length: Int) = Action {
    Ok(makeRandomString(length))
  }

  def makeRandomString(length: Int) = {
    List.fill(length)((util.Random.nextInt(0x05F) + 0x020).toChar).mkString
  }
  
}
