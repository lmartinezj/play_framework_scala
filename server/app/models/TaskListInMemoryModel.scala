package models

import scala.collection.mutable

object TaskListInMemoryModel {

  private val users = mutable.Map[String, String]("luis" -> "123")

  def validateUser(username: String, password: String): Boolean = {
    users.get(username).map(_ == password)getOrElse(false)
  }
  def createUser(username: String, password: String): Boolean = ???
  def getTasks(username: String): Seq[String] = ???
  def addTask(username: String, task: String): Unit = ???
  def removeTask(username: String, index: Int): Boolean = ???

}
