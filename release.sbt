ThisBuild / name           := "Scala Library Template"
ThisBuild / normalizedName := "scala-library-template"
ThisBuild / description    := "A reusable template for Scala libraries."

ThisBuild / homepage :=
  Some(url("https://github.com/SgtSwagrid/scala-library-template"))

ThisBuild / organization         := "io.github.sgtswagrid"
ThisBuild / organizationName     := "SgtSwagrid"
ThisBuild / organizationHomepage := Some(url("https://github.com/SgtSwagrid"))

ThisBuild / versionScheme := Some("strict")

ThisBuild / licenses :=
  List("MIT" -> url("https://opensource.org/licenses/MIT"))

ThisBuild / scmInfo := Some(ScmInfo(
  url("https://github.com/SgtSwagrid/scala-library-template"),
  "scm:git@github.com:SgtSwagrid/scala-library-template.git",
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
