import java.net.InetSocketAddress

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import jarta.thoughtservice.thriftscala.ThoughtService
import thought.ThoughtController


class AppsBffServer extends HttpServer {

  import AppsBffServerMain.thoughtService

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add(new ThoughtController(thoughtService))
  }

}

object AppsBffServerMain extends AppsBffServer {

  private val thoughtServiceBuilder = ClientBuilder()
    .hosts(Seq(new InetSocketAddress(9999)))
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .build()

  lazy val thoughtService: ThoughtService.FutureIface = new ThoughtService.FinagledClient(thoughtServiceBuilder)

}