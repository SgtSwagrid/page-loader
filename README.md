<div align="center">
  <h1>🖥️ Page Loader</h1>
  <p>A simple <a href="https://www.scala-js.org/">Scala.js</a> webpage loader for <a href="https://www.scala-lang.org/">Scala</a> web servers.</p>
  <span>
    <a href="https://github.com/SgtSwagrid/page-loader/actions/workflows/build-integrity.yml"><img src="https://github.com/SgtSwagrid/page-loader/actions/workflows/build-integrity.yml/badge.svg" alt="Build status" /></a>
    <a href="https://search.maven.org/artifact/io.github.sgtswagrid/page-loader-tapir_3"><img src="https://img.shields.io/maven-central/v/io.github.sgtswagrid/page-loader-tapir_3.svg" alt="Maven Central" /></a>
  </span>
</div>

## ✔️ Features

This tool is extremely small and minimalistic, with absolutely no bells or whistles.

1. Generates a shared HTML shell template that bootstraps any [Scala.js](https://www.scala-js.org/) view.
2. Selects the correct view at request time by injecting its exported name into the template.
3. Supports automatic hot reload during development via a WebSocket heartbeat.

## ⬇️ Installation

Add the following dependency to your `build.sbt`:

```scala
libraryDependencies += "io.github.sgtswagrid" %% "page-loader-common" % "0.1.1"
```

For the client (Scala.js), add:

```scala
libraryDependencies += "io.github.sgtswagrid" %%% "page-loader-client" % "0.1.1"
```

Compiled with Scala `3.8.3`, with no intention to explicitly support older versions.

## ⚙️ Example

### Server

Define a `ViewData` for each page, then wire it up to a Tapir endpoint using `showView`:

```scala
import io.github.sgtswagrid.pageloader.ViewData
import io.github.sgtswagrid.pageloader.tapir.{HeartbeatService, ViewEndpoint}

val indexView = ViewData(name = "IndexView", pageTitle = "Home")
val aboutView = ViewData(name = "AboutView", pageTitle = "About")

val heartbeat = HeartbeatService[IO]()

val endpoints = List(
  endpoint.get.in("").showView(indexView),
  endpoint.get.in("about").showView(aboutView),
) ++ heartbeat.api
```

### Client

Each view is a Scala.js object exported with `@JSExportTopLevel`. The name passed to the annotation must match the `name` field of the corresponding `ViewData` on the server.

```scala
import io.github.sgtswagrid.pageloader.View
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("IndexView")
object IndexView extends View:

  @JSExport("show")
  override def show(rootName: String = "root"): Unit =
    // Render content into the root element.
    ???
```

## 📡 Server Integration

Currently, a connector exists for only a single web framework: Tapir.
In principle, any future connectors will be published as separate dependencies with the name `page-loader-{web-framework}`.
Contributions are welcome!

### Tapir

[Tapir](https://tapir.softwaremill.com/en/latest/) is a library to describe HTTP APIs and expose them as a server. A separate connector is provided to easily attach a `ViewData` to a Tapir endpoint. Just add the following dependency:

```scala
libraryDependencies += "io.github.sgtswagrid" %% "page-loader-tapir" % "0.1.1"
```

Use the `showView` extension method to convert any Tapir `GET` endpoint into one that serves a page:

```scala
endpoint.get.in("").showView(ViewData(name = "IndexView", pageTitle = "Home"))
```

#### Hot Reload

`HeartbeatService` exposes a WebSocket endpoint that the client connects to. When the server restarts, the disconnection is detected and the page reloads automatically. This is enabled by default when using `showView`.

```scala
val heartbeat = HeartbeatService[IO]()
// Add heartbeat.api to your server alongside your page endpoints.
```

To disable hot reload for a specific page, pass `hotReloadWebsocketPath = None`:

```scala
endpoint.get.in("").showView(view, hotReloadWebsocketPath = None)
```

## 🎨 Client Integration

### Laminar

[Laminar](https://laminar.dev/) is a reactive UI library for Scala.js. A separate connector provides `LaminarView`, a base trait that handles rendering automatically. Just add the following dependency:

```scala
libraryDependencies += "io.github.sgtswagrid" %%% "page-loader-laminar" % "0.1.1"
```

Extend `LaminarView` instead of `View` and implement `content`:

```scala
import com.raquo.laminar.api.L.{*, given}
import io.github.sgtswagrid.pageloader.laminar.LaminarView
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndexView")
object IndexView extends LaminarView:

  override def content = div(
    h1("Welcome to my website!"),
    p("We're still getting set up here... Stay tuned!"),
  )
```

## 🖥️ Client Versions

All server-side dependencies above are exclusively for the JVM.
However, you may wish to access the non-JVM-specific functionality from the client as well.
For this reason, each aforementioned dependency is published with a common part that is cross-compiled.

These can be installed as follows:

```scala
libraryDependencies += "io.github.sgtswagrid" %%% "page-loader-common" % "0.1.1"
```

```scala
libraryDependencies += "io.github.sgtswagrid" %%% "page-loader-tapir-common" % "0.1.1"
```

Note that you don't need to explicitly include the above if you only use this library on the server.

## 👁️ See also

- See [Scala Website Template](https://github.com/SgtSwagrid/scala-website-template) for an example template which uses _Page Loader_ to build a full stack website.
- See [Asset Loader](https://github.com/SgtSwagrid/asset-loader) for a similar library which loads static assets instead of dynamic pages.
- This library was made using [Scala Library Template](https://github.com/SgtSwagrid/scala-library-template).
