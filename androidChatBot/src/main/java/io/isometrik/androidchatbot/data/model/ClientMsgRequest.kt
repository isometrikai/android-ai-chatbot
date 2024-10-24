package io.isometrik.androidchatbot.data.model

class ClientMsgRequest (
    val chat_bot_id: String,
    val message: String,
    val sessionId: String,
    val storeCategoryId: String,
    val userId: String,
    val location: String,
)