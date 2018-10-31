import Dependencies._

scalaVersion := "2.12.4"
scalacOptions += "-Ypartial-unification"

lazy val scalemetaParadiseVersion = "3.0.0-M10"
lazy val kindProjectorVersion = "0.9.4"
lazy val circeVersion = "0.9.0"
lazy val monixVersion = "3.0.0-M3"
lazy val aecorVersion = "0.17.1"

addCompilerPlugin("org.scalameta" % "paradise" % scalemetaParadiseVersion cross CrossVersion.full)
addCompilerPlugin("org.spire-math" %% "kind-projector" % kindProjectorVersion)

libraryDependencies += "io.monix" %% "monix-reactive" % monixVersion

libraryDependencies += "io.aecor" %% "akka-persistence-runtime" % aecorVersion
libraryDependencies += "io.aecor" %% "boopickle-wire-protocol" % aecorVersion

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-java8" % circeVersion
)

libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.5.4"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

fork in run := true

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "aecor-first-contact",
    libraryDependencies += scalaTest % Test
  )
