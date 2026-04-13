package io.github.sgtswagrid.pageloader

/**
  * The base trait for all views. Each view corresponds to a distinct page (i.e.
  * at a unique URL) of the website.
  *
  * @note
  *   Every [[View]] must be explicitly exported with `@JSExportTopLevel`.
  *
  * @note
  *   The appropriate view for each page is selected based on the name injected
  *   into the [[PageTemplate]] by the server.
  *
  * @example
  *   ```scala
  *   import scala.scalajs.js.annotation.JSExportTopLevel
  *
  *   @JSExportTopLevel("IndexView")
  *   object IndexView extends View:
  *     ???
  *   ```
  */
trait View:

  /**
    * Called only once upon initialisation when it is time to render this view.
    *
    * @param rootName
    *   The name (`id`) of the root HTML tag (default = `"root"`). The content
    *   of this view will be inserted there.
    *
    * @note
    *   Every implementation of [[show]] must be explicitly exported with
    *   `@JSExport`.
    *
    * @example
    *   ```scala
    *   import scala.scalajs.js.annotation.JSExport
    *
    *   @JSExport("show")
    *   override def show(rootName: String): Unit = ???
    *   ```
    */
  def show(rootName: String = "root"): Unit
