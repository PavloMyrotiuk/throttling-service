name := "throttling-service"

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
)