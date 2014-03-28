package org.denigma.semantic.actors.cache

import akka.actor.ActorRef
import org.denigma.semantic.commons.{LogLike, ChangeWatcher}
import com.bigdata.rdf.store.AbstractTripleStore
import com.bigdata.rdf.changesets.IChangeLog

class CacheWatcher(db: AbstractTripleStore, cache:ActorRef) extends ChangeWatcher{
  override def apply(transaction: String, lg: LogLike): IChangeLog = new ActorChangeObserver(db,transaction,lg,cache)
}