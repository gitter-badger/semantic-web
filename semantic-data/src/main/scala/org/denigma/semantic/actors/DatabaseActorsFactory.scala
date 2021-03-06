package org.denigma.semantic.actors

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.routing.{DefaultResizer, SmallestMailboxRouter}
import org.denigma.semantic.controllers.{SemanticWriter, SemanticReader}
import org.denigma.semantic.actors.writers.DatabaseWriter
import org.denigma.semantic.actors.readers.DatabaseReader
import org.denigma.semantic.reading.CanReadBigData
import org.denigma.semantic.writing.CanWriteBigData
import org.denigma.semantic.actors.cache.{BusChangeWatcher, CacheActor}
import com.bigdata.rdf.store.AbstractTripleStore
import akka.event.EventStream

/**
* @constructor create a new person with a name and age.
* @param canRead anybody who has a method that provides readonly connection to the database [[org.denigma.semantic.reading.CanReadBigData]]
* @param canWrite anybody who has a method that provides write connection to the database [[org.denigma.semantic.writing.CanWriteBigData]]
* @param sys Actor system that will be used
* @param readers Configuration for reader resizer (min,def,max) number of reader actors that shoud be created by resizer
*/
class DatabaseActorsFactory(db:AbstractTripleStore,canRead:CanReadBigData,canWrite:CanWriteBigData, val sys:ActorSystem,readers:(Int,Int,Int)) {

  //canRead.lg.debug("ACTOR FACTORY STARTS!")

  /**
  router for reader actor, for details see http://doc.akka.io/docs/akka/snapshot/scala/routing.html#SmallestMailboxPool
  @see [[http://doc.akka.io/docs/akka/snapshot/scala/routing.html#SmallestMailboxPool]]
   */
  val router = SmallestMailboxRouter(readers._2)


  val resizer = DefaultResizer(lowerBound = readers._1, upperBound = readers._3)

  protected val readerProps = Props(classOf[DatabaseReader],canRead).withDispatcher("akka.actor.reader-dispatcher").withRouter(router.withResizer(resizer))

  val reader = sys.actorOf(readerProps)

  var cache: ActorRef = sys.actorOf(Props(classOf[CacheActor]),"cache")

  //val cacheWatcher = new CacheWatcher(db,cache)

  val busWatcher = new BusChangeWatcher(db,bus)

  protected val writerProps = Props(classOf[DatabaseWriter],canWrite,  busWatcher).withDispatcher("akka.actor.writer-dispatcher")
  val writer = sys.actorOf(writerProps)

  /*
  actors that handles reades and resize
   */
  SemanticReader.reader = reader
  SemanticReader.cache = cache

  SemanticWriter.writer = writer

  def bus: EventStream = this.sys.eventStream

}