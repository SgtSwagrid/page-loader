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

lazy val `page-loader-common` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core/common"))
  .settings(
    packagePrefix := "io.github.sgtswagrid.pageloader",
    libraryDependencies +=
      "io.github.sgtswagrid" %%% "asset-loader-common" % "0.1.10",
  )

lazy val `page-loader-client` = project
  .in(file("core/client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`page-loader-common`.js)
  .settings(
    packagePrefix                          := "io.github.sgtswagrid.pageloader",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.1",
  )

lazy val `page-loader-tapir` = project
  .in(file("tapir/server"))
  .dependsOn(`page-loader-tapir-common`.jvm)
  .settings(
    packagePrefix := "io.github.sgtswagrid.pageloader.tapir",
    libraryDependencies ++= Seq(
      "io.github.sgtswagrid"          %% "asset-loader-tapir"       % "0.1.10",
      "com.softwaremill.sttp.tapir"   %% "tapir-core"               % "1.13.15",
      "com.softwaremill.sttp.tapir"   %% "tapir-prometheus-metrics" % "1.13.15",
      "com.softwaremill.sttp.tapir"   %% "tapir-swagger-ui-bundle"  % "1.13.15",
      "com.softwaremill.sttp.client4" %% "fs2"                      % "4.0.22",
    ),
  )

lazy val `page-loader-tapir-common` = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("tapir/common"))
  .dependsOn(`page-loader-common`)
  .settings(
    packagePrefix := "io.github.sgtswagrid.pageloader.tapir",
    libraryDependencies ++= Seq(
      "io.github.sgtswagrid"        %%% "asset-loader-tapir-common" % "0.1.10",
      "com.softwaremill.sttp.tapir" %%% "tapir-core"                % "1.13.15",
    ),
  )

lazy val `page-loader-laminar` = project
  .in(file("laminar/client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(`page-loader-client`)
  .settings(
    packagePrefix := "io.github.sgtswagrid.pageloader.laminar",
    libraryDependencies += "com.raquo" %%% "laminar" % "17.0.0",
  )
