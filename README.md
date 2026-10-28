<div align="center">
  <h1>🖥️ Page Loader</h1>
  <p>A simple loader for <a href="https://www.scala-js.org/">Scala.js</a> webpages with client-side rendering.</p>
  <span>
    <a href="https://github.com/SgtSwagrid/page-loader/actions/workflows/build-integrity.yml"><img src="https://github.com/SgtSwagrid/page-loader/actions/workflows/build-integrity.yml/badge.svg" alt="Build status" /></a>
    <a href="https://search.maven.org/artifact/com.alecdorrington/page-loader_3"><img src="https://img.shields.io/maven-central/v/com.alecdorrington/page-loader_3.svg" alt="Maven Central" /></a>
    <a href="https://alecdorrington.com/page-loader"><img src="https://img.shields.io/badge/docs-latest-blue.svg" alt="Documentation" /></a>
  </span>
</div>

> [!WARNING]
> Page Loader is still a prototype. Anything could be broken or change between minor versions.

<br/><br/>

> "There is nothing new under the sun." — Ecclesiastes.

<br/>

## ✔️ Features

This tool is extremely small and minimalistic, with absolutely no bells or whistles.

1. Generates a shared HTML shell template that bootstraps any [Scala.js](https://www.scala-js.org/) view.
2. Selects the correct view at request time by injecting its exported name into the template.
3. Supports automatic hot reload during development via a WebSocket heartbeat.

## ⬇️ Installation

There are several components which can be installed separately, but the same version number (e.g. [latest](../../releases/latest)) must be used across all of them.
The following applies for [Scala 3](https://www.scala-lang.org/) projects built with [sbt 2](https://www.scala-sbt.org/).
Add one **server integration** and one **client integration** to your `build.sbt` file:

```scala
// Tapir server integration
libraryDependencies += "com.alecdorrington" %% "page-loader-tapir" % "{version}"

// Laminar client integration
libraryDependencies += "com.alecdorrington" %% "page-loader-laminar" % "{version}"
```

## ⚙️ Example

### Server

Define a `ViewData` for each page, then wire it up to a Tapir endpoint using `showView`:

```scala
import com.alecdorrington.pageloader.ViewData
import com.alecdorrington.pageloader.tapir.{HeartbeatService, ViewEndpoint}

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
import com.raquo.laminar.api.L.{*, given}
import com.alecdorrington.pageloader.laminar.LaminarView
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndexView")
object IndexView extends LaminarView:

  override def content = div(
    h1("Welcome to my website!"),
    p("We're still getting set up here... Stay tuned!"),
  )
```

## 📡 Server Integration

Currently, a connector exists for only a single web framework: Tapir.
In principle, any future connectors will be published as separate dependencies with the name `page-loader-{web-framework}`.
Contributions are welcome!

### Tapir

[Tapir](https://tapir.softwaremill.com/en/latest/) is a library to describe HTTP APIs and expose them as a server. A separate connector, `page-loader-tapir`, is provided to easily attach a `ViewData` to a Tapir endpoint.

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

Currently, a connector exists for only a single UI framework: Laminar.
In principle, any future connectors will be published as separate dependencies with the name `page-loader-{ui-framework}`.
Contributions are welcome!

### Laminar

[Laminar](https://laminar.dev/) is a reactive UI library for Scala.js. A separate connector, `page-loader-laminar`, provides `LaminarView`, a base trait that handles rendering automatically.

Extend `LaminarView` and implement `content`:

```scala
import com.raquo.laminar.api.L.{*, given}
import com.alecdorrington.pageloader.laminar.LaminarView
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndexView")
object IndexView extends LaminarView:

  override def content = div(
    h1("Welcome to my website!"),
    p("We're still getting set up here... Stay tuned!"),
  )
```

## 👁️ See also

- See [Scala Website Template](https://github.com/SgtSwagrid/scala-website-template) for an example template which uses _Page Loader_ to build a full stack website.
- See [Asset Loader](https://github.com/SgtSwagrid/asset-loader) for a similar library which loads static assets instead of dynamic pages.
- This library was made using [Scala Library Template](https://github.com/SgtSwagrid/scala-library-template).
