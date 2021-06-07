import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task List 1" must {
    "login and access functions" in {
      go to s"http://localhost:$port/login1"
      eventually {
        find("h2").isEmpty mustBe false
        find("h2").foreach(_.text mustBe "Login")
        click on "username-login"
        textField("username-login").value = "luis"
        click on "password-login"
        pwdField("password-login").value = "123"
        submit()
      }
    }
  }
}
