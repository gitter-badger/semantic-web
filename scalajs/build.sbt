scalaJSSettings

name := "scala-js-frontend"

Build.sameSettings

scalacOptions ++= Seq( "-feature", "-language:_" )

version := "0.2"

ScalaJSKeys.relativeSourceMaps := true

libraryDependencies += "org.denigma" %%% "binding" % "0.3.1"
