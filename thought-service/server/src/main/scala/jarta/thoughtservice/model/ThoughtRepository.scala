package jarta.thoughtservice.model

import jarta.thoughtservice.thriftscala.{Thought => TThought}
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ThoughtRepository(db: Database) {

  import Tables._

  def insert(thought: TThought): Future[TThought] = db.run {
    (thoughts returning thoughts.map(_.id) into ((_, id) => thought.copy(id = id))) += ThoughtRow(0, thought.title)
  }

  def select(id: Int): Future[Option[TThought]] = db.run {
    thoughts
      .filter(_.id === id)
      .result
      .map(_.headOption.map(t => new TThought.Immutable(t.id, t.description)))
  }

  def delete(id: Int): Future[Int] = db.run {
    thoughts.filter(_.id === id).delete
  }

}