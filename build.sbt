import Dependencies._

scalaVersion := "2.12.4"
scalacOptions += "-Ypartial-unification"
addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full)
libraryDependencies += "io.aecor" %% "akka-persistence-runtime" % "0.17.1"

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
