ThisBuild / description := "A simple loader for ScalaJS webpages with client-side rendering. "

ThisBuild / homepage :=
  Some(url("https://github.com/SgtSwagrid/page-loader"))

ThisBuild / organization         := "io.github.sgtswagrid"
ThisBuild / organizationName     := "SgtSwagrid"
ThisBuild / organizationHomepage := Some(url("https://github.com/SgtSwagrid"))

ThisBuild / versionScheme := Some("strict")

ThisBuild / licenses :=
  List("MIT" -> url("https://opensource.org/licenses/MIT"))

ThisBuild / scmInfo := Some(ScmInfo(
  url("https://github.com/SgtSwagrid/page-loader"),
  "scm:git@github.com:SgtSwagrid/page-loader.git",
))

ThisBuild / developers := List(Developer(
  id = "SgtSwagrid",
  name = "Alec Dorrington",
  email = "alecdorrington@gmail.com",
  url = url("https://github.com/SgtSwagrid"),
))

// Target the Sonatype Central Portal (https://central.sonatype.com).
// Requires SONATYPE_USERNAME and SONATYPE_PASSWORD as GitHub secrets or environment variables.
ThisBuild / sonatypeCredentialHost := "central.sonatype.com"
ThisBuild / publishMavenStyle      := true
Global / excludeLintKeys ++= Set(publishMavenStyle)
