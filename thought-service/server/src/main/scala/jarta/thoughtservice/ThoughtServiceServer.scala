package jarta.thoughtservice

import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import jarta.thoughtservice.model.ThoughtRepository
import slick.driver.MySQLDriver
import slick.jdbc.JdbcBackend

object ThoughtServiceServer extends ThriftServer {

  override val name = "thought-service-server"
  override val disableAdminHttpServer = true

  lazy val db = JdbcBackend.Database.forConfig("db")

  lazy val ThoughtRepository = new ThoughtRepository(db)

  override def configureThrift(router: ThriftRouter) {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[AccessLoggingFilter]
      .filter[StatsFilter]
      .add(new ThoughtService(ThoughtRepository))
  }
}