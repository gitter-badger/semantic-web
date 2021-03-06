Build.sameSettings

version := "0.3.4"

resolvers += "Bigdata releases" at "http://systap.com/maven/releases/"

resolvers += "nxparser-repo" at "http://nxparser.googlecode.com/svn/repository/"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "apache-repo-releases" at "http://repository.apache.org/content/repositories/releases/"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "2.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" %  "2.3.3"

libraryDependencies += "com.bigdata" % "bigdata" % "1.4.0"

libraryDependencies += "org.scalax" %% "semweb-sesame" % Versions.semWeb

libraryDependencies += "com.assembla.scala-incubator" %% "graph-core" % "1.9.0"


