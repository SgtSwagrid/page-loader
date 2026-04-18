package com.alecdorrington.pageloader.tapir

import sttp.capabilities.Streams
import sttp.tapir.*

/**
  * A [Tapir](https://tapir.softwaremill.com/en/latest/) endpoint definition for
  * responding to heartbeat messages using a WebSocket.
  *
  * This is the cross-platform base for [[HeartbearService]], which adds the
  * server-side logic on JVM.
  *
  * @param path
  *   The URL path at which the endpoint is mounted.
  */
class HeartbeatApi(private val path: EndpointInput[Unit] = "heartbeat"):

  /**
    * The public endpoint definition.
    *
    * @param stream
    *   The streams capability to use for the WebSocket body.
    *
    * @return
    *   An endpoint that accepts and produces plain-text WebSocket messages.
    */
  def publicEndpoint[S <: Streams[S]](stream: S) = endpoint
    .get
    .in(path)
    .out(
      webSocketBody[
        String,
        CodecFormat.TextPlain,
        String,
        CodecFormat.TextPlain,
      ](stream),
    )

  val index = endpoint.get.in("").out(htmlBodyUtf8)
