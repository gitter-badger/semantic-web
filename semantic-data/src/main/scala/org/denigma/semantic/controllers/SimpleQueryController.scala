package org.denigma.semantic.controllers

import org.denigma.semantic.sparql._
import akka.util.Timeout
import scala.concurrent.{Await, Future}
import scala.util.Try

import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Try
import org.denigma.semantic.actors.readers.{SimpleRead, Read}
import org.denigma.semantic.reading.QueryResultLike
import akka.pattern.ask
import org.openrdf.query.TupleQueryResult
import org.denigma.semantic.sparql

/**
 * Simple query controller
 */
trait SimpleQueryController extends WithSemanticReader
{


  implicit val readTimeout:Timeout = Timeout(5 seconds)

  def defLimit:Long = 50
  def defOffset:Long = 0

  /*
  for test purposes only
   */
  def awaitRead[T](fut:Future[T]):T = Await.result(fut,readTimeout.duration)

  def select(query:sparql.SelectQuery):Future[Try[TupleQueryResult]] = reader.ask(query)(readTimeout).mapTo[Try[TupleQueryResult]]
  def select(query:String,offset:Long = defOffset, limit:Long = defLimit):Future[Try[TupleQueryResult]] = reader.ask(SimpleRead.Select(query,offset,limit))(readTimeout).mapTo[Try[TupleQueryResult]]


  def rd[T](message:T):Future[Try[T]] =    reader.ask(message)(readTimeout).mapTo[Try[T]]
}
