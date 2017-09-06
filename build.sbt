name := """hello_play"""
organization := "com.hello_play"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

resolvers += "Bintary JCenter" at "http://jcenter.bintray.com"

libraryDependencies ++= Seq(
  guice,
  "play-circe" %% "play-circe" % "2608.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  "org.skinny-framework" %% "skinny-orm" % "2.4.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0",
  "mysql" % "mysql-connector-java" % "6.0.6"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.hello_play.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.hello_play.binders._"
