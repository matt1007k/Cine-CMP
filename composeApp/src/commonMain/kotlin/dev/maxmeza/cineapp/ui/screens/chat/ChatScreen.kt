package dev.maxmeza.cineapp.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.maxmeza.cineapp.Greeting
import dev.maxmeza.cineapp.data.model.Message
import dev.maxmeza.cineapp.util.DateUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class,  @OptIn(FormatStringsInDatetimeFormats::class)
    FormatStringsInDatetimeFormats::class
)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val connectionError by viewModel.connectionError.collectAsState()

    var messageInput by remember { mutableStateOf("") }
    val iOName = Greeting().greet()
    val scope = rememberCoroutineScope()
    var date by remember { mutableStateOf(DateUtil().getDateToJSONString()) }

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                delay(1000L)
                date = DateUtil().getDateToJSONString()
            }

        }
    }

    var newDate by remember { mutableStateOf("no date") }

    LaunchedEffect(date) {
        scope.launch {
            val dateTimeNow = Clock.System.now()
            val instantDateTimeNow = Instant.parse(dateTimeNow.toString())
            val dateTimeParse = Instant.parse("2025-08-02T03:58:04.279280Z")
            val duration: Duration = instantDateTimeNow - dateTimeParse
            val localDateTime = instantDateTimeNow.toLocalDateTime(TimeZone.currentSystemDefault())
            val formatter = LocalDateTime.Format {
                byUnicodePattern("dd-MM-yyyy HH:mm:ss")
            }
            val formattedString = localDateTime.format(formatter)
            newDate = formattedString
            val duration2: Duration = instantDateTimeNow.minus(dateTimeParse)

            println("Duration: $duration2")
            println("Duration in seconds: ${duration2.inWholeSeconds}")
            println("Duration in minutes: ${duration2.inWholeMinutes}")
            println("format::: ${dateTimeNow.toString()} / $formattedString / ${formatTimeAgo(duration)}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ktor Chat $iOName $date") })
        },
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        label = { Text("Enter message") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.sendMessage(messageInput)
                            messageInput = ""
                        },
                        enabled = isConnected, // Enable send button only when connected
                        modifier = Modifier.height(56.dp) // Match TextField height
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Send Message")
                    }
                }
                // Connection status display
                if (connectionError != null) {
                    Text(
                        text = connectionError ?: "Unknown Error",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                } else {
                    Text(
                        text = if (isConnected) "Connected" else "Disconnected",
                        color = if (isConnected) Color.Green else Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (isConnected) Color.Green.copy(alpha = 0.1f) else Color.Red.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            item {
                Text("Date JSON ${newDate}")
            }
            items(messages) { message ->
                MessageBubble(message, iOName)
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message, iOName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (message.user == iOName) Alignment.End else Alignment.Start // Align based on sender
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
        ) {
            Card (
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (message.user == iOName) MaterialTheme.colorScheme.primary else Color.LightGray
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    AsyncImage(
                        model = message.avatar,
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(top= 8.dp, start = 8.dp)
                            .clip(CircleShape)
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = message.user,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (message.user == iOName) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer
                        )
                        Text(
                            text = message.message,
                            color = if (message.user == iOName) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondaryContainer
                        )

                    }
                }
            }
            Text(
                text = DateUtil().formatDate(message.timestamp), // Use platform-specific date formatting
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End)
                ,
                color = if (message.user == iOName) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) else MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
            )
        }

    }
}

fun formatTimeAgo(duration: Duration): String {
    return when {
        duration.inWholeMinutes < 1 -> "${duration.inWholeSeconds} seconds ago"
        duration.inWholeHours < 1 -> "${duration.inWholeMinutes} minutes ago"
        duration.inWholeDays < 1 -> "${duration.inWholeHours} hours ago"
        duration.inWholeDays < 30 -> "${duration.inWholeDays} days ago"
        duration.inWholeDays < 365 -> "${duration.inWholeDays / 30} months ago" // Approximation
        else -> "${duration.inWholeDays / 365} years ago" // Approximation
    }
}