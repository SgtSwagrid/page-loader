import IdeSettings.packagePrefix
import org.scalajs.sbtplugin.ScalaJSPlugin
import sbt._
import sbt.Keys._
import sbtunidoc.BaseUnidocPlugin.autoImport.*
import sbtunidoc.ScalaUnidocPlugin

val scala3 = "3.9.0"

ThisBuild / scalaVersion := scala3

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
    (`page-loader-common`.projectRefs ++
      `page-loader-tapir-common`.projectRefs ++ Seq[ProjectReference](
        `page-loader-client`,
        `page-loader-tapir`,
        `page-loader-laminar`,
      )) *,
  )
  .settings(
    ScalaUnidoc / unidoc / scalacOptions ++= Seq("-project", "Page Loader"),
  )

/** Cross-compiled for JVM and JS. */
lazy val `page-loader-common` = projectMatrix
  .in(file("core/common"))
  .settings(
    name          := "page-loader-common",
    packagePrefix := "com.alecdorrington.pageloader",

    // sbt 2.0.x resolves a matrix's sources against the working directory,
    // so they are pinned to this build's own base:
    sourceDirectory :=
      (ThisBuild / baseDirectory).value / "core" / "common" / "src",
    libraryDependencies +=
      "com.alecdorrington" %% "asset-loader-common" % "0.2.5",
  )
  .jvmPlatform(scalaVersions = Seq(scala3))
  .jsPlatform(scalaVersions = Seq(scala3))

lazy val `page-loader-client` = project
  .in(file("core/client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`page-loader-common`.js(scala3))
  .settings(
    name                                  := "page-loader-client",
    packagePrefix                         := "com.alecdorrington.pageloader",
    libraryDependencies += "org.scala-js" %% "scalajs-dom" % "2.8.1",
  )

lazy val `page-loader-tapir` = project
  .in(file("tapir/server"))
  .dependsOn(`page-loader-tapir-common`.jvm(scala3))
  .settings(
    name          := "page-loader-tapir",
    packagePrefix := "com.alecdorrington.pageloader.tapir",
    libraryDependencies ++= Seq(
      "com.alecdorrington"            %% "asset-loader-tapir"       % "0.2.5",
      "com.softwaremill.sttp.tapir"   %% "tapir-core"               % "1.13.33",
      "com.softwaremill.sttp.tapir"   %% "tapir-prometheus-metrics" % "1.13.32",
      "com.softwaremill.sttp.tapir"   %% "tapir-swagger-ui-bundle"  % "1.13.32",
      "com.softwaremill.sttp.client4" %% "fs2"                      % "4.1.0",
    ),
  )

/** Cross-compiled for JVM and JS. */
lazy val `page-loader-tapir-common` = projectMatrix
  .in(file("tapir/common"))
  .dependsOn(`page-loader-common`)
  .settings(
    name          := "page-loader-tapir-common",
    packagePrefix := "com.alecdorrington.pageloader.tapir",

    // sbt 2.0.x resolves a matrix's sources against the working directory,
    // so they are pinned to this build's own base:
    sourceDirectory :=
      (ThisBuild / baseDirectory).value / "tapir" / "common" / "src",
    libraryDependencies ++= Seq(
      "com.alecdorrington"          %% "asset-loader-tapir-common" % "0.2.5",
      "com.softwaremill.sttp.tapir" %% "tapir-core"                % "1.13.15",
    ),
  )
  .jvmPlatform(scalaVersions = Seq(scala3))
  .jsPlatform(scalaVersions = Seq(scala3))

lazy val `page-loader-laminar` = project
  .in(file("laminar/client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`page-loader-client`)
  .settings(
    name          := "page-loader-laminar",
    packagePrefix := "com.alecdorrington.pageloader.laminar",
    libraryDependencies += "com.raquo" %% "laminar" % "17.2.1",
  )
