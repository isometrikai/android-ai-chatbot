package io.isometrik.androidchatbot.data.model

class ClientMsgRequest (
    val chat_bot_id: Int,
    val message: String,
    val sessionId: String,
    val storeCategoryId: String,
    val userId: String,
    val location: String,
)