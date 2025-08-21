package dev.maxmeza.cineapp.data.remote

import dev.maxmeza.cineapp.data.model.Message
import dev.maxmeza.cineapp.data.model.WebSocketClient
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class KtorWebSocketClient : WebSocketClient {

    private var client: HttpClient? = null
    private var session: DefaultWebSocketSession? = null

    private val _messages = MutableSharedFlow<Message>()
    private val _connectionState = MutableStateFlow(false)

    // Using a separate scope for WebSocket operations
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun open(url: String) {
        if (_connectionState.value) {
            println("WebSocket already open or connecting.")
            return
        }

        println("Attempting to open WebSocket connection to: $url")
        _connectionState.value = false // Indicate connecting state

        client = HttpClient { // Use the actual engine
            install(WebSockets)
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        scope.launch {
            try {
                client?.webSocket(url) { // This block represents the WebSocket session
                    session = this
                    _connectionState.value = true
                    println("WebSocket session opened!")

                    // Listen for incoming messages
                    for (frame in incoming) {
                        if (frame is Frame.Text) {
                            val messageText = frame.readText()
                            println("Received RAW: $messageText")
                            try {
                                val message = Json.decodeFromString<Message>(messageText)
                                _messages.emit(message)
                            } catch (e: Exception) {
                                println("Error decoding message: ${e.message}")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                println("WebSocket connection failed: ${e.message}")
                _connectionState.value = false
                session = null // Clear session on failure
                // Implement re-connection logic here or higher up in ViewModel
                // For a basic example, we'll let the ViewModel manage re-connection attempts
            } finally {
                println("WebSocket session closed.")
                _connectionState.value = false
                session = null
                client?.close() // Close the HttpClient instance
                client = null
            }
        }
    }

    override fun sendMessage(message: Message) {
        scope.launch {
            if (_connectionState.value && session != null) {
                try {
                    println("Message $message")
                    val messageJson = Json.encodeToString(Message.serializer(), message)
                    session?.send(Frame.Text(messageJson))
                    println("Sent: $messageJson")
                } catch (e: Exception) {
                    println("Error sending message: ${e.message}")
                    _connectionState.value = false // Sending error might mean connection is dead
                }
            } else {
                println("Cannot send message: WebSocket not connected.")
            }
        }
    }

    override fun observeMessages(): Flow<Message> = _messages.asSharedFlow()

    override fun observeConnectionState(): Flow<Boolean> = _connectionState.asStateFlow()

    override fun close() {
        println("Closing WebSocket connection.")
        scope.launch {
            try {
                session?.close()
            } catch (e: Exception) {
                println("Error closing session: ${e.message}")
            } finally {
                _connectionState.value = false
                session = null
                client?.close()
                client = null
            }
        }
    }
}