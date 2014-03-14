package controllers
import play.api.mvc._
import org.openrdf.model.impl.URIImpl
import scala.collection.JavaConversions._
import play.api.libs.json.JsNull
import play.api.Play.current
import org.denigma.semantic._
import org.denigma.semantic.reading.selections.SelectResult


/*
Should show ontological info
 */
object Ontologies extends PJaxPlatformWith("ontology") {

  def resource(uri:String) = UserAction {
    implicit request=>
      Ok(pj("resource",views.html.ontologies.resource()))
  }

}
