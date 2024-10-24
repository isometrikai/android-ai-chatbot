package io.isometrik.androidchatbot.presentation.screens

import UserInput
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.isometrik.androidchatbot.ChatBotActivity
import io.isometrik.androidchatbot.presentation.components.MessageBox
import io.isometrik.androidchatbot.presentation.components.TopBarWithProfile
import io.isometrik.androidchatbot.presentation.enums.MessageType
import io.isometrik.androidchatbot.presentation.extensions.background
import io.isometrik.androidchatbot.viewmodel.ChatScreenViewModel

@Composable
fun ChatScreen(
    viewModel: ChatScreenViewModel
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val listOfMessages by viewModel.listOfMessages.collectAsStateWithLifecycle()
    val tempMessages by viewModel.tempMessages.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewState.uiPreferences.mode_theme.background())
    ) {

        val context = LocalContext.current
        // Top bar
        TopBarWithProfile(
            uiPreferences = viewState.uiPreferences,
            profileImageUrl = viewState.profileImageUrl,
            username = "AI Assistant",
            appTitle = viewState.appName,  // Dynamic app name
            onBackArrowClick = {
                (context as? ChatBotActivity)?.finish()
            },
            onMenuClick = { /* Handle Menu Action */ },
        )

        // Divider to separate TopBar from the rest of the content
        HorizontalDivider(
            thickness = 0.5.dp,      // Thin line
            color = Color.Gray  // Gray color for the line
        )

        // Chat messages or content area

        if (viewState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp),
            reverseLayout = true,
        ) {

            // For temporary messages
            items(tempMessages.size) { position ->
                val reversedPosition = tempMessages.size - 1 - position
                MessageBox(
                    message = tempMessages[reversedPosition],
                    uiPreferences = viewState.uiPreferences,
                    profileImageUrl = viewState.profileImageUrl
                ) { messageType, message ->
                    if (messageType == MessageType.BOT_SUGGESTION) {
                        viewModel.onBotSuggestionSelected(message)
                    }
                }
            }

            // For permanent messages
            items(listOfMessages.size) { position ->
                val reversedPosition = listOfMessages.size - 1 - position

                MessageBox(
                    message = listOfMessages[reversedPosition],
                    uiPreferences = viewState.uiPreferences,
                    profileImageUrl = viewState.profileImageUrl
                ) { messageType, message ->

                }
            }
        }

        // User input section
        UserInput(
            onSendClick = { message ->
                // Handle sending message here
                if (message.isNotBlank()) {
                    viewModel.onUserSentMessage(message)
                }
            },
            uiPreferences = viewState.uiPreferences
        )

    }


}