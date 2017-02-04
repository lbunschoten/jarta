package jarta.thoughtservice

import com.twitter.finatra.thrift.Controller
import jarta.converters.TwitterConverters._
import jarta.thoughtservice.model.ThoughtRepository
import jarta.thoughtservice.thriftscala.ThoughtService.{BaseServiceIface, DeleteThought, GetThought, InsertThought}

class ThoughtService(repository: ThoughtRepository) extends Controller with BaseServiceIface {

  override val insertThought = handle(InsertThought) { args: InsertThought.Args =>
    repository.insert(args.thought).toTwitterFuture
  }

  override val getThought = handle(GetThought) { args: GetThought.Args =>
    repository.select(args.id).toTwitterFuture.map(_.orNull)
  }

  override val deleteThought = handle(DeleteThought) { args: DeleteThought.Args =>
    repository.delete(args.id).toTwitterFuture.map(_ => Unit)
  }

}
