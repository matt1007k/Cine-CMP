package dev.maxmeza.cineapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val instant = Clock.System.now()
@OptIn(ExperimentalTime::class)
val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
val isoString = localDateTime.toString()

@Serializable
data class Message(val user: String, val message: String, val timestamp: String, val avatar: String)

interface WebSocketClient {
    fun open(url: String)
    fun sendMessage(message: Message)
    fun observeMessages(): Flow<Message>
    fun observeConnectionState(): Flow<Boolean> // true if connected, false otherwise
    fun close()
}