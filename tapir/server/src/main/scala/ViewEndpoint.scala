package io.github.sgtswagrid.pageloader.tapir

import io.github.sgtswagrid.pageloader.{PageTemplate, ViewData}
import sttp.tapir.*
import sttp.tapir.server.ServerEndpoint

object ViewEndpoint:

  extension (view: ViewData)

    def toEndpoint[F[_]]
      (
        endpoint: PublicEndpoint[Any, Any, String, Any],
        hotReloadWebsocketPath: Option[String] = Some("heartbeat"),
        scalajsPath: String = "/assets/scripts/main.js",
      )
      : ServerEndpoint[Any, F] = endpoint.serverLogicSuccessPure: _ =>
      PageTemplate(
        view = view,
        hotReloadWebsocketPath = hotReloadWebsocketPath,
        scalajsPath = scalajsPath,
      )

  extension [F[_]](endpoint: PublicEndpoint[Any, Any, String, Any])

    def showView
      (
        view: ViewData,
        hotReloadWebsocketPath: Option[String] = Some("heartbeat"),
        scalajsPath: String = "/assets/scripts/main.js",
      )
      : ServerEndpoint[Any, F] = endpoint.serverLogicSuccessPure: _ =>
      PageTemplate(
        view = view,
        hotReloadWebsocketPath = hotReloadWebsocketPath,
        scalajsPath = scalajsPath,
      )
