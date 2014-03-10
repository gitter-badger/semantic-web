package org.denigma.semantic.cache

import org.specs2.mutable.Specification
import org.denigma.semantic.test.LoveHater
import play.api.test.WithApplication
import org.denigma.semantic.controllers.{UpdateController, SimpleQueryController}

import org.specs2.mutable._
import play.api.test.WithApplication

import org.denigma.semantic.test.LoveHater
import scala.util.Try
import org.openrdf.query.{BindingSet, TupleQueryResult}
import org.denigma.semantic.reading.selections._
import org.denigma.semantic.controllers.{SimpleQueryController, UpdateController}
import scala.concurrent.Future
import play.api.libs.concurrent.Akka
import org.denigma.semantic.model.IRI
import org.denigma.semantic.sparql._
import org.denigma.semantic.sparql
import scala.collection.JavaConversions._
import org.denigma.semantic.reading.selections._
import org.denigma.semantic.reading._
import org.denigma.semantic.controllers.sync.{SyncSimpleController, SyncUpdateController}
import org.denigma.semantic.vocabulary._
import org.denigma.semantic.model.IRI
import org.denigma.semantic.sparql.InsertQuery
import org.denigma.semantic.sparql.Trip
import org.denigma.semantic.model._

class CacheSpec extends Specification {

  /*
  alias for "this"
   */
  self=>

  class WithTestApp extends WithApplication with SimpleQueryController with UpdateController


  "Accounts" should {


    "Cache inserted triplets" in new WithTestApp
    {

      val context = IRI(USERS.namespace)
      val pasw1 = "password1"
      val pasw2 = "password2"
      val pasw3 = "password3"
      val pasw4 = "password4"

      val anton = USERS.user / "Anton"
      val daniel = USERS.user / "Daniel"
      val liz = USERS.user / "Liz"
      val ilia = USERS.user / "Ilia"
      val pr = USERS.props



      val basic: InsertQuery = InsertQuery {
        INSERT (
          DATA (
          GRAPH(context,
            Trip(
              anton iri,
              pr hasPasswordHash,
              LitStr(pasw1)
            ),
            Trip(
              anton iri,
              pr hasEmail,
              LitStr("anton@gmail.com")
            ),
            Trip(
              daniel iri,
              pr hasPasswordHash,
              LitStr(pasw2)
            ),
            Trip(
              daniel iri,
              pr hasPasswordHash,
              LitStr("daniel@gmail.com")
            )

          )
          )
        )
      }

      val second: InsertQuery = InsertQuery {
        INSERT (
          DATA (
            GRAPH(context,
              Trip(
                USERS.user / liz iri,
                USERS.props hasPasswordHash,
                LitStr(pasw3)
              ),
              Trip(
                USERS.user / liz iri,
                USERS.props hasEmail,
                LitStr("liz@gmail.com")
              ),
              Trip(
                USERS.user / ilia iri,
                USERS.props hasPasswordHash,
                LitStr(pasw4)
              ),
              Trip(
                USERS.user / ilia iri,
                USERS.props hasEmail,
                LitStr("ilia@gmail.com")
              )
            )
          )
        )
      }

      val upq = basic.insert.stringValue


      val u = this.update(upq)
      val ru = this.awaitWrite(u)
      ru.isSuccess should beTrue

      val q = SELECT( ?("p") ) WHERE {
        Pat(
          anton iri, pr hasEmail, ?("p")
        )
      }

      val r = this.awaitRead(this.select(q))
      r.isSuccess should beTrue
      val rl = r.get.toList
      rl.size shouldEqual 1
      val h: BindingSet = rl.head
      h.getBinding("p").getValue.stringValue() shouldEqual "anton@gmail.com"



    }
  }
}