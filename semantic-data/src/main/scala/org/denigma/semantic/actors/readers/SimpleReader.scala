package org.denigma.semantic.actors.readers

import org.denigma.semantic.actors.NamedActor
import akka.actor.Actor
import org.denigma.semantic.reading.queries.SimpleQueryManager
import org.denigma.semantic.reading.CanReadBigData
import org.denigma.semantic.actors.readers.protocols.SimpleRead
import org.scalax.semweb.sparql._

/**
 * Handles simple quries (with native results)
 */
trait SimpleReader {
  reader:NamedActor with CanReadBigData =>


  def simpleQuery: Actor.Receive = {

    case sel:SelectQuery => sender ! qsm.select(sel.stringValue)

    case sel @ SimpleRead.Select(query,offset,limit,rewrite) =>
      if(sel.isPaginated) sender ! qsm.select(query,offset,limit,rewrite) else sender ! qsm.select(query)


    case SimpleRead.Question(query)=>sender ! qsm.question(query)

    case SimpleRead.Construct(query)=>sender ! qsm.construct(query)

    case q @ SimpleRead.Bind(query,binds,offset,limit) => if(q.isPaginated) sender ! qsm.bindedQuery(query,binds,offset,limit) else sender ! qsm.bindedQuery(query,binds)


  }


  /**
  Simple query manager (returns internal query results)
   */
  object qsm extends SimpleQueryManager{
    val lg = reader.lg
    def readConnection = reader.readConnection
  }
}
