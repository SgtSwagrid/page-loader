package io.github.sgtswagrid.pageloader

/**
  * Metadata for a [[View]].
  *
  * @param name
  *   The name of the view. Include only the name, with no package prefix and no
  *   parentheses (e.g. `IndexView`). In order to be visible to the client, a
  *   view must be exported using the `@JSExportTopLevel` annotation, and the
  *   name used in that annotation must match the name specified here.
  *
  * @param iconPath
  *   The URL path to the page favicon.
  *
  * @param pageTitle
  *   The title of the page.
  *
  * @param languageCode
  *   The [BCP 47](https://tools.ietf.org/html/bcp47) language code for the
  *   `lang` attribute of the `<HTML>` tag.
  *
  * @param charset
  *   The character encoding declared in the `<META charset>` tag.
  */
case class ViewData
  (
    name: String,
    iconPath: String = "/assets/img/icon.png",
    pageTitle: String = "",
    languageCode: String = "en",
    charset: String = "UTF-8",
  ):

  /**
    * Uses [[PageTemplate]] to produce a HTML string for this view, which can
    * then be served to the client.
    *
    * @param hotReloadWebsocketPath
    *   Whether hot reload is enabled on the client (yes if `Some` ), and if so,
    *   at which URL path can the heartbeat websocket connection be established?
    *
    * @param scalajsPath
    *   The URL path to the compiled Scala.js script.
    */
  def render
    (
      hotReloadWebsocketPath: Option[String] = Some("heartbeat"),
      scalajsPath: String = "/assets/scripts/main.js",
    )
    : String = PageTemplate(
    this,
    hotReloadWebsocketPath,
    scalajsPath,
  )
