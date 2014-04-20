package org.denigma.semantic.reading.questions

import org.denigma.semantic.reading.QueryResultLike
import scala.util.Try
import org.denigma.rdf.model.vocabulary.WI


trait IAsk[T] extends AskReader {
  def question(query:String): Try[T] = this.askQuery[T](query,askHandler)(WI.RESOURCE)
  protected def askHandler:AskHandler[T]
}

trait SimpleAsk extends IAsk[Boolean] {
  override protected def askHandler:AskHandler[Boolean]  = (str,con,q)=>q.evaluate()
}

trait JsonAsk extends IAsk[QueryResultLike] {
  override protected def askHandler:AskHandler[QueryResultLike]  = (str,con,q)=>AskResult(str,q.evaluate())
}