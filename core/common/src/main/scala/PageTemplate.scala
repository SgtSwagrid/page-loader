package io.github.sgtswagrid.pageloader

/**
  * A simple HTML template common to every page of an application.
  *
  * @note
  *   Different pages differ only in that a different view initialisation
  *   function is called. This is injected by name as a string.
  */
object PageTemplate:

  /**
    * @param view
    *   The view to embed in the template.
    *
    * @param hotReloadWebsocketPath
    *   Whether hot reload is enabled on the client (yes if `Some`), and if so,
    *   at which URL path can the heartbeat websocket connection be established?
    *
    * @param scalajsPath
    *   The URL path to the compiled Scala.js script.
    */
  def apply
    (
      view: ViewData,
      hotReloadWebsocketPath: Option[String] = Some("heartbeat"),
      scalajsPath: String = "/assets/scripts/main.js",
    )
    : String =

    s"""
<!DOCTYPE HTML>

<HTML lang="${ view.languageCode }">
  <HEAD>
    <TITLE>${ view.pageTitle }</TITLE>
    <META charset="${ view.charset }">
    <META name="viewport" content="width=device-width, initial-scale=1.0">
    <SCRIPT type="text/javascript" src="$scalajsPath"></SCRIPT>
    <LINK rel="icon" type="image/x-icon" href="${ view.iconPath }">
  </HEAD>
  <BODY style="height: 100vh">
    <DIV id="root" style="width: 100%; height: 100%; position: absolute;"></DIV>
    <SCRIPT type="text/javascript">
      ${ view.name }.show("root");
      ${ hotReloadWebsocketPath
        .map(path => s"HotReload.enable($path);")
        .getOrElse("") }
    </SCRIPT>
  </BODY>
</HTML>
    """
