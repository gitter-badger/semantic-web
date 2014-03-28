package org.denigma.semantic.actors.cache

import org.denigma.semantic.sparql.Pat
import org.denigma.semantic.controllers.QueryController
import org.denigma.semantic.actors.WatchProtocol.PatternResult
import org.denigma.semantic.actors.WatchProtocol
import scala.collection.mutable
import org.openrdf.model._
import scala.concurrent.Future
import scala.util.Try
import org.denigma.semantic.commons.Logged
import play.api.libs.concurrent.Execution.Implicits._
import org.denigma.semantic.model.Quad
import akka.pattern.ask
/**
 * Makes decision based on union of patterns that it has
 */
abstract class PatternCache extends Consumer with QueryController[PatternResult] with Logged
{

  val cacheName:String

  var patterns: Set[Pat]

  var active:Boolean = false


  /**
   * Registeres pattern in patterns set
   * @param pat
   * @return
   */
  def pattern(pat:Pat) = {
    this.patterns = this.patterns + pat
    pat
  }


//  case class TuplePatterns(patterns:Set[Pat])
//
//  case class PatternRequest(patterns:Set[Pat])
//
//  case class PatternResult(results:Map[Pat,Set[Statement]])


  def onResult(p:PatternResult)

  /**
   * Activates cache
   */
  def activate(): Future[Try[PatternResult]] =
  {
    this.lastActivation = this.fill()

    for {
      res <- this.fill()
      sub <-this.cache ? Cache.Subscribe(this)
    }
    {
      res.foreach(this.onResult)
      this.active = true
    }
    this.lastActivation.onFailure{
      case f: Throwable =>
        this.lg.error(s"cache request $cacheName failed with: $f")
        this.active = false
    }

    this.lastActivation
  }

  //TODO: fix bad code
  var lastActivation:Future[Try[PatternResult]] = null


  def reset() = {
    this.active = false
    this.activate()

  }

  /**
   *sends pattern request
   * @return
   */
  def fill(): Future[Try[PatternResult]] = this.rd(WatchProtocol.PatternRequest(cacheName,this.patterns))


  def groupByPattern(sts:Set[Quad]): mutable.MultiMap[Pat, Quad] = {

    val quads:mutable.MultiMap[Pat, Quad] = new mutable.HashMap[Pat, mutable.Set[Quad]] with mutable.MultiMap[Pat,Quad]

    for {
      p <-patterns
      st <- sts
      if p.canBind(st)
    } quads.addBinding(p,st)
    quads
  }

  def groupBySubject(sts:Set[Quad]):  mutable.MultiMap[Resource, (Pat, Quad)] = {

    val quads= new mutable.HashMap[Resource, mutable.Set[(Pat,Quad)]] with mutable.MultiMap[Resource,(Pat,Quad)]

    for {
      p <-patterns
      st <- sts
      if p.canBind(st)
    } quads.addBinding(st.getSubject,(p,st))
    quads
  }



}
