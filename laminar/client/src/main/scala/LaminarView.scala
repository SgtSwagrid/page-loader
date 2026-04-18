package com.alecdorrington.pageloader.laminar

import com.raquo.laminar.api.L.render
import com.raquo.laminar.nodes.ReactiveElement
import com.alecdorrington.pageloader.View
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExport

/**
  * The base trait for all views that use
  * [Laminar](https://laminar.dev/Laminar). Each view corresponds to a distinct
  * page (i.e. at a unique URL) of the website.
  *
  * @note
  *   Every [[View]] must be explicitly exported with `@JSExportTopLevel`.
  *
  * @note
  *   The appropriate view for each page is selected based on the name injected
  *   into the[[PageTemplate]] by the server.
  *
  * @example
  *   ```scala
  *   import scala.scalajs.js.annotation.JSExportTopLevel
  *
  *   @JSExportTopLevel("IndexView")
  *   object IndexView extends LaminarView:
  *     ???
  *   ```
  */
trait LaminarView extends View:

  @JSExport("show")
  override final def show(rootName: String = "root"): Unit =

    val root = document.getElementById("root")
    render(root, content)

  /**
    * The content of this page, constructed with Reactive DOM elements from
    * [Laminar](https://laminar.dev/Laminar).
    *
    * @note
    *   Reactive DOM elements can be imported with:
    *   ```scala
    *   import com.raquo.laminar.api.L.{*, given}
    *   ```
    *
    * @example
    *   ```scala
    *   override def content = div(
    *     h1("Welcome to my website!"),
    *     p("We're still getting set up here... Stay tuned!"),
    *   )
    *   ```
    */
  protected def content: ReactiveElement.Base
