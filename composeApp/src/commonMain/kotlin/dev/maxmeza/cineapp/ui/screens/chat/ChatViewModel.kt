package dev.maxmeza.cineapp.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxmeza.cineapp.data.model.Message
import dev.maxmeza.cineapp.data.model.WebSocketClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val webSocketClient: WebSocketClient // Injected via Koin
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _connectionError = MutableStateFlow<String?>(null)
    val connectionError: StateFlow<String?> = _connectionError.asStateFlow()

    private val roomId = "general" // Or dynamically passed
    private val websocketUrl = "ws://192.168.1.36:8080/ws/$roomId" // Replace with your server URL

    // Reconnection logic
    private var reconnectJob: Job? = null
    private var reconnectAttempt = 0
    private val maxReconnectAttempts = 10
    private val reconnectDelayBase: Long = 1000 // 1 second

    init {
        println("ChatViewModel initialized. Starting WebSocket observation.")
        observeWebSocket()
        connectWebSocketWithRetry()
    }

    private fun observeWebSocket() {
        viewModelScope.launch {
            webSocketClient.observeMessages().collect { message ->
                println("VM received message: ${message.message}")
                _messages.update { it + message }
            }
        }
        viewModelScope.launch {
            webSocketClient.observeConnectionState().collect { connected ->
                println("VM connection state changed: $connected")
                _isConnected.value = connected
                if (connected) {
                    reconnectJob?.cancel() // Cancel retry if successfully connected
                    reconnectAttempt = 0 // Reset attempts
                    _connectionError.value = null // Clear error on successful connect
                } else {
                    // Connection lost, initiate retry if not already doing so
                    if (reconnectJob?.isActive != true) {
                        // This is where you trigger reconnect if the connection dropped unexpectedly
                        connectWebSocketWithRetry()
                    }
                }
            }
        }
    }

    private fun connectWebSocketWithRetry() {
        reconnectJob?.cancel() // Cancel any ongoing reconnect job

        reconnectJob = viewModelScope.launch {
            while (reconnectAttempt < maxReconnectAttempts && !_isConnected.value) {
                val delay = reconnectDelayBase * (1L shl reconnectAttempt).coerceAtMost(32000L) // Exponential backoff max 32s
                println("Attempting WebSocket connect (attempt ${reconnectAttempt + 1}/${maxReconnectAttempts}) in ${delay / 1000.0}s...")
                _connectionError.value = "Connecting... (Attempt ${reconnectAttempt + 1})"
                kotlinx.coroutines.delay(delay) // Use kotlinx.coroutines.delay

                reconnectAttempt++
                webSocketClient.open(websocketUrl) // Try to open the connection

                // Give it a moment to establish connection and update _isConnected
                kotlinx.coroutines.delay(100) // Small delay to let connection state update
            }

            if (!_isConnected.value && reconnectAttempt >= maxReconnectAttempts) {
                _connectionError.value = "Failed to connect after $maxReconnectAttempts attempts."
                println("Max reconnect attempts reached. Connection failed.")
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isNotBlank()) {
            val message = Message(user = "ComposeUser", message = text) // User can be dynamic
            webSocketClient.sendMessage(message)
        }
    }

    // Call this when ViewModel is no longer needed (e.g., onCleared for Android ViewModel)
    override fun onCleared() {
        println("ChatViewModel cleared. Closing WebSocket.")
        webSocketClient.close()
        reconnectJob?.cancel()
        super.onCleared()
    }
}