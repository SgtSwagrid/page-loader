<div align="center">

  <h1>🖥️ Page Loader</h1>
  <p>A simple loader for ScalaJS webpages with client-side rendering. </p>
  
  <span>
    <a href="https://github.com/SgtSwagrid/page-loader/actions/workflows/build-integrity.yml"><img src="https://github.com/SgtSwagrid/page-loader/actions/workflows/build-integrity.yml/badge.svg" alt="Build status" /></a>
    <a href="https://search.maven.org/artifact/io.github.sgtswagrid/page-loader_3"><img src="https://img.shields.io/maven-central/v/io.github.sgtswagrid/page-loader_3.svg" alt="Maven Central" /></a>
  </span>
  
</div>

## ⬇️ Installation

Add the following dependency to your `build.sbt`:

```scala
libraryDependencies += "io.github.sgtswagrid" %% "page-loader" % "0.1.0"
```

## 📢 Publishing workflow

GitHub releases are automatically published to [Maven Central](https://central.sonatype.com/) upon creation, using [`sbt-dynver`](https://github.com/sbt/sbt-dynver).

### Example

To release version `1.2.3`, go to **Releases → Draft a new release**, create the tag `v1.2.3`, and click **Publish release**.
Note the inclusion of `v` in the GitHub release name but not the resulting Maven version.
