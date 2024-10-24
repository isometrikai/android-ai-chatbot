package io.isometrik.androidchatbot.data

import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.enums.MessageType

data class Message(
    val message: String,
    val type: MessageType,
    val listOfWidget : List<Widget> = arrayListOf()
)