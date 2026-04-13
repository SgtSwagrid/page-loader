package io.github.sgtswagrid.pageloader.tapir

import io.github.sgtswagrid.assetloader.tapir.Service
import sttp.capabilities.WebSockets
import sttp.capabilities.fs2.Fs2Streams
import sttp.tapir.*

/**
  * A [Tapir](https://tapir.softwaremill.com/en/latest/) service for responding
  * to heartbeat messages using a WebSocket.
  *
  * Responds to every incoming WebSocket message with `"ok"`.
  *
  * @param path
  *   The URL path at which the endpoint is mounted.
  *
  * @tparam F
  *   The effect type (e.g. `Future` or `IO`).
  */
class HeartbeatService[F[_]]
  (private val path: EndpointInput[Unit] = "heartbeat")
  extends HeartbeatApi(path),
          Service[WebSockets & Fs2Streams[F], F]("Heartbeat Service", "1.0"):

  /**
    * A server endpoint that replies `"ok"` to every incoming WebSocket message.
    *
    * @return
    *   A server endpoint requiring [[WebSockets]] and [[Fs2Streams]]
    *   capabilities.
    */
  final def serverEndpoint: Endpoint = publicEndpoint(Fs2Streams[F])
    .serverLogicSuccessPure: _ =>
      in => in.map(_ => "ok")

  override final def api: List[Endpoint] = List(serverEndpoint)
