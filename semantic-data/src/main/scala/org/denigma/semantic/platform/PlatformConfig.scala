package org.denigma.semantic.platform

//import org.apache.log4j.Logger
import play.api.Configuration
import scala.collection.immutable._
import scala.collection.JavaConversions._
import org.scalax.semweb.rdf.vocabulary.WI


/*
Config of SemanticPlatform object
 */
class PlatformConfig(conf:Configuration)
{



  val filesConf: List[Configuration] = conf.getConfigList("files").map(_.toList).getOrElse(Nil)
  //lazy val semanticConf = conf.getConfig("semantic")

  val CONFIG_CONTEXT: String = conf.getString("config_context").getOrElse(WI.CONFIG.stringValue)
  val MAIN_CONTEXT: String =  conf.getString("main_context").getOrElse(WI.RESOURCE)
  val POLICY_CONTEXT: String =  conf.getString("main_context").getOrElse(WI.POLICY.stringValue)
  val loadInitial: Boolean = conf.getBoolean("load_initial").getOrElse(true)

  val readers = conf.getConfig("readers").get
  val minReaders = readers.getInt("min").getOrElse(3)
  val defReaders = readers.getInt("def").getOrElse(3)
  val maxReaders = readers.getInt("max").getOrElse(3)




}