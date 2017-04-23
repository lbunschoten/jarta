package jarta.frontend

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class IndexController() extends Controller {

  get("/") { _: Request =>
    response.ok.view("index.mustache", MainData(Seq(Thought("a"), Thought("b"))))
  }

  get("/assets/:*") { request: Request =>
    response.ok.file(request.params("*"))
  }

  case class Thought(label: String)
  case class MainData(thoughts: Seq[Thought])

}

