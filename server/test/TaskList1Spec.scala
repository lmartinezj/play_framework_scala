import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task List 1" must {
    "login and access functions" in {
      go to s"http://localhost:$port/login1"
      pageTitle mustBe "Login"
      find(cssSelector("h2")).isEmpty mustBe false
      find(cssSelector("h2")).foreach(_.text mustBe "Login")
      click on "username-login"
      textField("username-login").value = "luis"
      click on "password-login"
      pwdField(id("password-login")).value = "123"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        find(cssSelector("h2")).isEmpty mustBe(false)
        find(cssSelector("h2")).foreach(_.text mustBe "Task List")
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("Make videos", "Eat", "Sleep", "Code")
      }
    }

    "add task" in {
      pageTitle mustBe "Task List"
      click on "newTask"
      textField("newTask").value = "test"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test", "Make videos", "Eat", "Sleep", "Code")
      }
    }

    "delete task" in {
      pageTitle mustBe "Task List"
      click on "delete-3"
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test", "Make videos", "Eat", "Code")
      }
    }
  }
}
