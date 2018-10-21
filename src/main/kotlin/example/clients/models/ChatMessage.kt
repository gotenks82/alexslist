package example.clients.models

import java.time.Instant

data class ChatMessage(val from: String, val content: String, val timestamp: Instant)