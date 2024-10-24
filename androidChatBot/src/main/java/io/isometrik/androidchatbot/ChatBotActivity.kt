package io.isometrik.androidchatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.isometrik.androidchatbot.presentation.screens.ChatScreen
import io.isometrik.androidchatbot.viewmodel.ChatScreenViewModel

class ChatBotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
                ChatScreen(ChatScreenViewModel())
        }
    }
}