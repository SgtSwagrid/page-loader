import IdeSettings.packagePrefix
import sbt._
import sbt.Keys._
import sbtunidoc.BaseUnidocPlugin.autoImport.*
import sbtunidoc.ScalaUnidocPlugin

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

lazy val `page-loader` = project
  .in(file("."))
  .enablePlugins(ScalaUnidocPlugin)
  .aggregate(
    `page-loader-common`.jvm,
    `page-loader-common`.js,
    `page-loader-client`,
    `page-loader-tapir`,
    `page-loader-tapir-common`.jvm,
    `page-loader-tapir-common`.js,
    `page-loader-laminar`,
  )
  .settings(
    ScalaUnidoc / unidoc / scalacOptions ++= Seq("-project", "Page Loader"),
  )

lazy val `page-loader-common` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core/common"))
  .settings(
    packagePrefix := "com.alecdorrington.pageloader",
    libraryDependencies +=
      "io.github.sgtswagrid" %%% "asset-loader-common" % "0.1.11",
  )

lazy val `page-loader-client` = project
  .in(file("core/client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`page-loader-common`.js)
  .settings(
    packagePrefix                          := "com.alecdorrington.pageloader",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.1",
  )

lazy val `page-loader-tapir` = project
  .in(file("tapir/server"))
  .dependsOn(`page-loader-tapir-common`.jvm)
  .settings(
    packagePrefix := "com.alecdorrington.pageloader.tapir",
    libraryDependencies ++= Seq(
      "io.github.sgtswagrid"          %% "asset-loader-tapir"       % "0.1.12",
      "com.softwaremill.sttp.tapir"   %% "tapir-core"               % "1.13.16",
      "com.softwaremill.sttp.tapir"   %% "tapir-prometheus-metrics" % "1.13.16",
      "com.softwaremill.sttp.tapir"   %% "tapir-swagger-ui-bundle"  % "1.13.16",
      "com.softwaremill.sttp.client4" %% "fs2"                      % "4.0.22",
    ),
  )

lazy val `page-loader-tapir-common` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("tapir/common"))
  .dependsOn(`page-loader-common`)
  .settings(
    packagePrefix := "com.alecdorrington.pageloader.tapir",
    libraryDependencies ++= Seq(
      "io.github.sgtswagrid"        %%% "asset-loader-tapir-common" % "0.1.11",
      "com.softwaremill.sttp.tapir" %%% "tapir-core"                % "1.13.15",
    ),
  )

lazy val `page-loader-laminar` = project
  .in(file("laminar/client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`page-loader-client`)
  .settings(
    packagePrefix := "com.alecdorrington.pageloader.laminar",
    libraryDependencies += "com.raquo" %%% "laminar" % "17.0.0",
  )
