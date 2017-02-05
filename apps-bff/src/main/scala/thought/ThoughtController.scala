package thought

import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import jarta.thoughtservice.thriftscala.{Thought, ThoughtService}

class ThoughtController(thoughtService: ThoughtService.FutureIface) extends Controller {

  get("/thought/:id") { request: GetThoughtRequest =>
    thoughtService.getThought(request.id)
  }

  put("/thought") { request: PutThoughtRequest =>
    thoughtService.insertThought(Thought(0, request.description))
  }

  delete("/thought/:id") { request: DeleteThoughtRequest =>
    thoughtService.deleteThought(request.id)
  }

}

case class GetThoughtRequest(@QueryParam id: Int)

case class PutThoughtRequest(@QueryParam description: String)

case class DeleteThoughtRequest(@QueryParam id: Int)