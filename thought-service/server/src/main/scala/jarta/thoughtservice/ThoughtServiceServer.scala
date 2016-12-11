package jarta.thoughtservice

import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter

object ThoughtServiceServer extends FinatraThoughtServiceServer

trait FinatraThoughtServiceServer extends ThriftServer {

  override val name = "thought-service-server"
  override val disableAdminHttpServer = true

  override def configureThrift(router: ThriftRouter) {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[AccessLoggingFilter]
      .filter[StatsFilter]
      .add[ThoughtService]
  }
}