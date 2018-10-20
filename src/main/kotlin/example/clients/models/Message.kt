package example.clients.models

import java.time.ZonedDateTime

data class Message(val from: String, val content: String, val timestamp: ZonedDateTime)