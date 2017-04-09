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

  private def thriftClientBuilder(port: Int) = ClientBuilder()
    .hosts(Seq(new InetSocketAddress(port)))
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .failFast(false)
    .build()

  def thoughtService: ThoughtService.FutureIface = new ThoughtService.FinagledClient(thriftClientBuilder(port = 9997))

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add(new ThoughtController(thoughtService))
  }

}