package jarta.thoughtservice.model

import slick.lifted.ProvenShape

object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

trait Tables {
  val profile: slick.driver.JdbcProfile

  import profile.api._

  case class ThoughtRow(id: Int, description: String)

  class Thought(tag: Tag) extends Table[ThoughtRow](tag, "thoughts") {

    def id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)

    def description: Rep[String] = column[String]("description")

    def * : ProvenShape[ThoughtRow] = (id, description) <> (ThoughtRow.tupled, ThoughtRow.unapply)

  }

  lazy val thoughts: TableQuery[Thought] = new TableQuery(tag => new Thought(tag))

}
