package com.zg.sanctuary.chat.data.network

import com.zg.sanctuary.core.data.network.BASE_IP
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SOCKET_PORT
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readReason
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive

// Websocket service for chat.
class WebsocketService {
    // TODO: - Change it to message object later
    // Incoming messages
    private val _incomingMessages = MutableSharedFlow<String>()
    val incomingMessages = _incomingMessages.asSharedFlow()

    // Web socket session
    private var currentConnection: WebSocketSession? = null

    // Function to connect to web socket
    suspend fun connect(host: String = BASE_IP, port: Int = SOCKET_PORT) {
        if (currentConnection != null && currentConnection!!.isActive) {
            _incomingMessages.emit("Already connected.")
            return
        }

        try {
            // Listen to messages
            HttpClientProvider.httpClient.webSocket(method = HttpMethod.Get, host = host, port = port, path = "/ws") {
                // store the connection
                currentConnection = this

                // Emit message that connection established
                _incomingMessages.emit("Connection established.")
                println("Ktor client: Connection established.")

                // Continuously receive messages.
                while (isActive) {
                    when (val frame = incoming.receive()) {
                        is Frame.Text -> {
                            val message = frame.readText()
                            _incomingMessages.emit(message)
                            println("Ktor received message : $message")
                        }

                        is Frame.Close -> {
                            _incomingMessages.emit("Connection closed from server: ${frame.readReason()}")
                            println("Ktor connection closed from server : ${frame.readReason()}")
                        }

                        else -> {
                            println("Ktor Client: Received other frame type: ${frame.frameType}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _incomingMessages.emit("Web socket connection failed ${e.message}")
            println("Web socket connection failed ${e.message}")
        } finally {
            currentConnection = null
            _incomingMessages.emit("WebSocket client disconnected.")
            println("Ktor Client: WebSocket client disconnected.")
        }
    }

    // Send message via web socket
    // TODO: - Replace message with message object later
    suspend fun sendMessage(message: String, accessToken: String, receiverId: Int) {
        if (currentConnection?.isActive == true) {
            // Connection is active. Send the message
            try {
                currentConnection?.send(Frame.Text(message))
                _incomingMessages.emit("Sent message $message")
                println("Sent message : $message")
            } catch (e: Exception) {
                _incomingMessages.emit("Error sending message: ${e.message}")
                println("Error sending message : ${e.message}")
            }
        } else {
            // Connection not active
            _incomingMessages.emit("Not connected. Please connect first")
            println("Not connected. Please connect first")
        }
    }

    // Disconnect
    suspend fun disconnect() {
        if (currentConnection?.isActive == true) {
            try {
                currentConnection?.close()
            } catch (e: Exception) {
                _incomingMessages.emit("Error disconnecting. ${e.message}")
                println("Error disconnecting. ${e.message}")
            }
        }
    }
}