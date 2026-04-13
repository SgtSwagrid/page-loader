import IdeSettings.packagePrefix
import sbt._
import sbt.Keys._

ThisBuild / scalaVersion := "3.8.3"

scalacOptions ++= Seq(
  "-explain",
  "-explain-types",
  "-explain-cyclic",
  "-language:experimental.subCases",
  "-language:experimental.relaxedLambdaSyntax",
  "-language:experimental.multiSpreads",
  "-language:experimental.strictEqualityPatternMatching",
  "-language:experimental.erasedDefinitions",
)

lazy val `scala-library-template` = (project in file(".")).settings(
  packagePrefix       := "io.github.sgtswagrid",
  libraryDependencies += "org.scalameta" %% "munit" % "1.2.4" % Test,
)
