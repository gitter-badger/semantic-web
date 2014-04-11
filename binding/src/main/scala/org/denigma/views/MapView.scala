package org.denigma.views

import org.scalajs.dom._
import org.scalajs.dom
import org.denigma.views.OrdinaryView
import rx.{Var, Rx}
import scalatags.HtmlTag
import dom.extensions._
import org.denigma.binding.{EventBinding, HtmlBinding, PropertyBinding, CollectionBinding}
import scala.collection.immutable._
import scala.collection.mutable
import scala.Some
import org.denigma.extensions._


/**
 * View that binds to map, usually is used as items of ListViews
 * @param name name of the view
 * @param element htmlelement to bind to
 * @param props properties to bind to
 */
abstract class MapView(name:String,element:HTMLElement,props:Map[String,Any]) extends OrdinaryView(name,element)
{
  val reactiveMap:Map[String,Var[String]] = props.map(kv=>(kv._1,Var(kv._2.toString)))

  //TODO: rewrite props
  override def bindProperties(el:HTMLElement,ats:mutable.Map[String, dom.Attr]) = for {
    (key, value) <- ats
  }{
    key.toString match {

      case "showif" => this.showIf(el,value.value,el.style.display)
      case "hideif" => this.hideIf(el,value.value,el.style.display)
      case str if str.startsWith("class-")=> str.replace("class-","") match {
        case cl if cl.endsWith("-if")=>
          this.classIf(el,cl.replace("-if",""),value.value)
        case cl if cl.endsWith("-unless")=>
          this.classUnless(el,cl.replace("-unless",""),value.value)
        case _ =>
          dom.console.error(s"other class bindings are not implemented yet for $str")

      }
      case bname if bname.startsWith("bind-")=>this.bindAttribute(el,key.replace("bind-",""),value.value,this.strings)
      case "bind" => this.bindProperty(el,key,value)
      case "item-bind"=>this.bindItemProperty(el,key,value)
      case bname if bname.startsWith("item-bind-")=>this.bindAttribute(el,key.replace("item-bind-",""),value.value,this.reactiveMap)
      case _ => //some other thing to do
    }
  }

  override def bindAttributes(el:HTMLElement,ats:mutable.Map[String,Attr]) ={
    super.bindAttributes(el,ats)

  }

  /**
   * Binds property value to attribute
   * @param el Element
   * @param key name of the binding key
   * @param att binding attribute
   */
  def bindItemProperty(el:HTMLElement,key:String,att:dom.Attr) = (key.toString.replace("item-",""),el.tagName.toLowerCase().toString) match {
    case ("bind","input")=>
      el.attributes.get("type").map(_.value.toString) match {
        case Some("checkbox") => //skip
        case _ => this.reactiveMap.get(att.value).foreach{str=>
          el.onkeyup =this.makePropHandler[KeyboardEvent](el,str,"value")
          this.bindInput(el,key,str)
        }
      }

    case ("bind","textarea")=>
      this.reactiveMap.get(att.value.toString).foreach{str=>
        el.onkeyup = this.makePropHandler(el,str,"value")
        this.bindText(el,key,str)
      }

    case ("bind",other)=> this.reactiveMap.get(att.value.toString).foreach{str=>
      el.onkeyup = this.makePropHandler(el,str,"value")
      this.bindText(el,key,str)
    }




    case _=> dom.console.error(s"unknown binding")

  }

}