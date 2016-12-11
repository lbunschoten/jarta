package jarta.converters

import com.twitter.util.{Return, Throw, Future => TwitterFuture, Promise => TwitterPromise, Try => TwitterTry}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future => ScalaFuture, Promise => ScalaPromise}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try => ScalaTry}

object TwitterConverters {

  implicit def scalaToTwitterTry[T](t: ScalaTry[T]): TwitterTry[T] = t match {
    case Success(r) => Return(r)
    case Failure(ex) => Throw(ex)
  }

  implicit def twitterToScalaTry[T](t: TwitterTry[T]): ScalaTry[T] = t match {
    case Return(r) => Success(r)
    case Throw(ex) => Failure(ex)
  }

  implicit def scalaToTwitterFuture[T](f: ScalaFuture[T])(implicit ec: ExecutionContext): TwitterFuture[T] = {
    val promise = TwitterPromise[T]()
    f.onComplete(promise update _)
    promise
  }

  implicit def twitterToScalaFuture[T](f: TwitterFuture[T]): ScalaFuture[T] = {
    val promise = ScalaPromise[T]()
    f.respond(promise complete _)
    promise.future
  }

  implicit class ScalaToTwitterFuture[T](future: ScalaFuture[T]) {
    def toTwitterFuture: TwitterFuture[T] = future
  }

  implicit class TwitterToScalaFuture[T](future: TwitterFuture[T]) {
    def toScalaFuture: ScalaFuture[T] = future
  }

}