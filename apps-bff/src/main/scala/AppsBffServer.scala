import java.net.InetSocketAddress

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import jarta.thoughtservice.thriftscala.ThoughtService
import thought.ThoughtController

object AppsBffServer extends HttpServer {

  override protected def disableAdminHttpServer = true
  override val defaultFinatraHttpPort = ":8891"

  private val thoughtServiceHost = flag(name = "thought.service.host", default = "localhost", help = "Host for thought-service")
  private val thoughtServicePort = flag(name = "thought.service.port", default = 9997, help = "Host for thought-service")

  private def thriftClientBuilder(host: String, port: Int) = ClientBuilder()
    .hosts(Seq(new InetSocketAddress(host, port)))
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .failFast(false)
    .build()

  def thoughtService: ThoughtService.FutureIface = new ThoughtService.FinagledClient(thriftClientBuilder(host = thoughtServiceHost(), port = thoughtServicePort()))

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add(new ThoughtController(thoughtService))
  }

}