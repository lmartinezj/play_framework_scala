import controllers.Application
import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout}
import play.api.test.{FakeRequest, Helpers}

class ControllerSpec extends PlaySpec {
  "Application#index" must {
    val controller = new Application(Helpers.stubControllerComponents())
    "give back expected page" in {
      val result = controller.index.apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include ("Play and Scala.js")
      bodyText must include ("Play shouts out:")
      bodyText must include ("Scala.js shouts out:")
    }

    "give back a product" in {
      val result = controller.product("test", 42).apply(FakeRequest())
      val bodyText = contentAsString(result)
      bodyText must include ("Product Type is: test, product number is: 42")
    }
  }

}
