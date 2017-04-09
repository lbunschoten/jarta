package jarta.thoughtservice

import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import jarta.thoughtservice.model.ThoughtRepository
import slick.jdbc.JdbcBackend

object ThoughtServiceServer extends ThriftServer {

  override val name = "thought-service-server"
  override val disableAdminHttpServer = true
  override val defaultFinatraThriftPort = ":9997"
  override val defaultHttpPort = 9992

  private val ThoughtRepository = new ThoughtRepository(JdbcBackend.Database.forConfig("db"))

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