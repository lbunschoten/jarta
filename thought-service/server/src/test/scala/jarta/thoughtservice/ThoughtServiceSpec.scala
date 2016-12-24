package jarta.thoughtservice

import com.twitter.util.{Await, Future => TFuture}
import jarta.thoughtservice.model.ThoughtRepository
import jarta.thoughtservice.thriftscala.Thought
import jarta.thoughtservice.thriftscala.ThoughtService.{DeleteThought, GetThought, InsertThought}
import org.mockito.Mockito.{verify, when}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future => SFuture}

class ThoughtServiceSpec extends FlatSpec with MockitoSugar with Matchers with OneInstancePerTest {

  private val repository = mock[ThoughtRepository]
  private val service = new ThoughtService(repository)
  private val newThought = Thought(0, "title")

  behavior of classOf[ThoughtService].getSimpleName

  "getThought" should "call the repository with the given id" in {
    when(repository.select(1)).thenReturn(SFuture(None))
    service.getThought(GetThought.Args(1))
    verify(repository).select(1)
  }

  it should "convert a None result to null" in {
    when(repository.select(1)).thenReturn(SFuture(None))
    Await.result(service.getThought(GetThought.Args(1))).success.get shouldBe Await.result(TFuture(null))
  }

  "insertThought" should "call the repository with the given thought" in {
    when(repository.insert(newThought)).thenReturn(SFuture(Thought(1, "title")))
    service.insertThought(InsertThought.Args(newThought))
    verify(repository).insert(newThought)
  }

  "deleteThought" should "call the repository with the given id" in {
    when(repository.delete(1)).thenReturn(SFuture(1))
    service.deleteThought(DeleteThought.Args(1))
    verify(repository).delete(1)
  }
}
