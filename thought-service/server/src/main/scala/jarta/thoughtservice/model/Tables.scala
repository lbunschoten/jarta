package jarta.thoughtservice.model

object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

trait Tables {
  val profile: slick.driver.JdbcProfile

  import profile.api._

  case class ThoughtRow(id: Int, description: String)

  class Thought(tag: Tag) extends Table[ThoughtRow](tag, "thoughts") {

    def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

    def description = column[String]("description")

    def * = (id, description) <> (ThoughtRow.tupled, ThoughtRow.unapply)

  }

  lazy val thoughts: TableQuery[Thought] = new TableQuery(tag => new Thought(tag))

}
