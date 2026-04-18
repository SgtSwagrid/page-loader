package com.alecdorrington.pageloader

import org.scalajs.dom
import org.scalajs.dom.WebSocket
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("HotReload")
object HotReload:

  /**
    * Enables automatic reloading. When the server is restarted (detected
    * through WebSocket disconnection), the client will repeatedly attempt to
    * reconnect until it is successful.
    *
    * @param websocketPath
    *   The URL of the heartbeat websocket connection to detect when the server
    *   goes down. Specified relative to the current domain (i.e. include the
    *   full path without the domain name).
    *
    * @note
    *   Intended for use during development.
    */
  @JSExport("enable")
  def enable(websocketPath: String = "heartbeat"): Unit =

    val loc      = dom.window.location
    val protocol = if loc.protocol == "https:" then "wss" else "ws"
    val url      =
      s"$protocol://${ loc.host }/${ websocketPath.dropWhile(_ == '/') }"

    def listen(): Unit = WebSocket(url).onclose = _ =>
      dom.window.setTimeout(() => listen(), 2000)
      loc.reload()

    listen()
