package jarta.thoughtservice

import com.twitter.finatra.thrift.Controller
import jarta.thoughtservice.thriftscala.ThoughtService.{BaseServiceIface, DeleteThought, GetThought, InsertThought}

class ThoughtService extends Controller with BaseServiceIface {

  override def deleteThought = handle(DeleteThought) { args: DeleteThought.Args =>
    ???
  }

  override def getThought = handle(GetThought) { args: GetThought.Args =>
    ???
  }

  override def insertThought = handle(InsertThought) { args: InsertThought.Args =>
    ???
  }
}
