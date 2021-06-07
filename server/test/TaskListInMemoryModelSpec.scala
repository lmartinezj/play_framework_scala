
import models._
import org.scalatestplus.play.PlaySpec

class TaskListInMemoryModelSpec extends PlaySpec {
    "TaskListInMemoryModel" must {
        "do valid login for default user" in {
            TaskListInMemoryModel.validateUser("luis", "123") mustBe true
        }

        "reject login with wrong password" in {
            TaskListInMemoryModel.validateUser("luis", "1234") mustBe false
        }

        "reject login with wrong user" in {
            TaskListInMemoryModel.validateUser("luuis", "123") mustBe false
        }

        "reject login with wrong user and wrong password" in {
            TaskListInMemoryModel.validateUser("luuis", "1234") mustBe false
        }

        "get correct default tasks" in {
            TaskListInMemoryModel.getTasks("luis") mustBe (List("Make videos", "Eat", "Sleep", "Code"))
        }

        "create new user with no tasks" in {
            TaskListInMemoryModel.createUser("Thiago", "1234") mustBe(true)
            TaskListInMemoryModel.getTasks("Thiago") mustBe(Nil)
        }

        "create new user with existing name" in {
            TaskListInMemoryModel.createUser("luis", "12345") mustBe(false)
        }

        "add new task for default user" in {
            TaskListInMemoryModel.addTask("luis", "Test task")
            TaskListInMemoryModel.getTasks("luis") must contain ("Test task")
        }

        "add new task for new user" in {
            TaskListInMemoryModel.addTask("Thiago", "New test task")
            TaskListInMemoryModel.getTasks("Thiago") must contain ("New test task")
        }

        "remove task from default user" in {
            TaskListInMemoryModel.removeTask("luis", TaskListInMemoryModel.getTasks("luis").indexOf("Eat"))
            TaskListInMemoryModel.getTasks("luis") must not contain ("Eat")
        }
    }

}
