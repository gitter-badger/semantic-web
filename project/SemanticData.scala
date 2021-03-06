import sbt._
import sbt.Keys._
import bintray.Plugin.bintraySettings

import play._

import play.Play.autoImport._

import PlayKeys._

trait SemanticData extends Publish {

  //lazy val banana =  RootProject(uri("git://github.com/antonkulaga/banana-rdf.git#master"))

  val semanticDataAppName         = "semantic-data"

  def semanticDataAppPath = "."


  val src = "src"

//  lazy val semanticData = play.Project(semanticDataAppName, semanticDataAppVersion, semanticDataAppDependencies, path = file(this.semanticDataAppPath))

  lazy val semanticData  = (project in file(this.semanticDataAppPath)).enablePlugins(PlayScala).settings(bintraySettings:_*).settings(
    // Add your own project settings here

    parallelExecution in Test := false,

    //compiler options
    scalacOptions ++= Seq( "-feature", "-language:_" ),

    //unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "scala-semantic" / "src" / "main" / "scala",

    sourceDirectory in Compile := baseDirectory.value / (src+"/main/scala"),
    sourceDirectory in Test := baseDirectory.value / (src+"/test/scala"),

    scalaSource in Compile := baseDirectory.value/ (src+"/main/scala"),
    scalaSource in Test := baseDirectory.value / (src+"/test/scala"),

    javaSource in Compile := baseDirectory.value / (src+"/main/java"),
    javaSource in Test:= baseDirectory.value / (src+"/test/java"),


    parallelExecution in Test := false,

    organization := "org.denigma"

  ).settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
}