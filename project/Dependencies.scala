import sbt._
import sbt.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
object Dependencies {

  val authDepth = Seq(


    "com.github.t3hnar" % "scala-bcrypt_2.10" % "2.3"

  )

  val playModules = Seq(
    "org.scaldi" %% "scaldi-play" % "0.3.1",

    "org.scaldi" %% "scaldi-akka" % "0.3.1",

    "com.typesafe" %% "play-plugins-mailer" % "2.2.0" //mailer for email confirmations
  )


  val jsDeps: Seq[ModuleID] = Seq(
    "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion,
    "org.scala-lang.modules.scalajs" %% "scalajs-jquery" % "0.3",
    "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.3",
    "org.scalajs" %% "scalajs-pickling" % "0.2",
    "com.scalatags" % "scalatags_2.10" % "0.2.4-JS",
    "com.scalarx" % "scalarx_2.10" % "0.2.3-JS"
  )

  val diDeps = Seq{
     "org.scaldi" %% "scaldi" % "0.3"
  }



  val testDeps = Seq(
    ///ScalaTest for testing
    "org.scalatest" % "scalatest_2.10" % "2.1.0",

    "com.typesafe.akka" %% "akka-testkit" %  "2.2.0"
  )

  val webjars = Seq(
    "org.webjars" %% "webjars-play" % "2.2.2-1",  //webjars support for play2

    "org.webjars" % "codemirror" % "3.22", //codemirror

    "org.webjars" % "d3js" % "3.4.3", //visualization lib

    "org.webjars" % "d3-plugins" % "da342b6",

    "org.webjars" % "jquery" % "2.1.0-2",

    "org.webjars" % "jquery-ui" % "1.10.3",

    "org.webjars" % "jquery-ui-themes" % "1.10.3",

    "org.webjars" % "Semantic-UI" % "0.15.1", //less/css framework

    //"org.webjars" % "jquery-file-upload" % "9.5.4",

    //"org.webjars" % "select2" % "3.4.5", //autocompletion

    "org.webjars" % "pdf-js" % "0.8.1170", //PDFS

    //"org.webjars" % "famfamfam-flags" % "0.0"

    "org.webjars" % "requirejs" % "2.1.11-1",

    "org.webjars" % "ckeditor" % "4.1.2"
  )




  val reflectDep =     scalaVersion("org.scala-lang" % "scala-reflect" % _)

  val compilerDep =   scalaVersion("org.scala-lang" % "scala-compiler" % _)



  val rdfDeps = Seq(
    "com.bigdata" % "bigdata" % "1.3.0",

    "org.openrdf.sesame" % "sesame-model" % "2.7.10"

    //    "org.openrdf.sesame" % "sesame" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-query" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-rio-api" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-rio-turtle" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-repository-api" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-repository" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-queryalgebra-model" % sesameVersion,
    //    "org.openrdf.sesame" % "sesame-queryalgebra-evaluation" % sesameVersion,

    //  "org.topbraid" % "spin" % "1.3.1"

  )

  val graphDeps = Seq(


    "com.assembla.scala-incubator" % "graph-core_2.10" % "1.8.0",

    "com.assembla.scala-incubator" % "graph-json_2.10" % "1.8.0"


  )

  val miscDeps = Seq(

    "com.github.nscala-time" %% "nscala-time" % "0.8.0"

  )

  val scalaVer = "2.10.3"

  val scalajsResolver: URLRepository = Resolver.url("scala-js-releases",
    url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(
      Resolver.ivyStylePatterns)

}

//object LibVersions {
//
//
//  def src = "src"
//
//
//
//
//  val apacheCommonsVersion = "1.3.2"
//
//
//
//
//  val jenaVersion = "2.11.1"
//
//  val nScalaTimeVersion = "0.8.0"
//
//  val parboiledVersion = "2.0-M2"
//
//
//  val scalaVer = "2.10.3"
//
//  val scalaCheckVersion = "1.11.0"
//
//  //val bigDataVersion = "1.3.0" //BIGDATA doesnot support latest Sesame version
//
//
//  val scalaGraphVersion =
//
//  val scalaGraphJsonVersion = "1.7.3"
//
//  val scalaTestVersion ="2.1.0-RC2"
//
//  val scalaTimeVersion = "0.6.0"
//
//  val scalaUriVersion = "0.4.0"
//  //val sesameVersion = "2.6.10" //BIGDATA doesnot support latest Sesame version
//
//  //val scalaZVersion ="7.0.5"
//
//  //val bananaVersion ="0.5-SNAPSHOT"
//
//}
//
//
