
import org.scalatestplus.play._
import org.scalatestplus._
/* import org.scalatest._*/

import  models._

class TaskListInMemoryModelSpec extends PlaySpec {
    "TaskListInMemoryModel" must {
        "do valid login for default user" in {
            TaskListInMemoryModel.validateUser("luis", "123") mustBe (true)
        }

        "reject login with wrong password" in {
            TaskListInMemoryModel.validateUser("luis", "1234") mustBe (false)
        }

        "reject login with wrong user" in {
            TaskListInMemoryModel.validateUser("luuis", "123") mustBe (false)
        }

        "get correct default tasks" in {
            TaskListInMemoryModel.getTasks("luis") mustBe (List("Make videos", "Eat", "Sleep", "Code"))
        }
    }

}
