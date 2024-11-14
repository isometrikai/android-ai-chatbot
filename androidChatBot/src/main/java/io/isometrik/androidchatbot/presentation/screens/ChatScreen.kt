package io.isometrik.androidchatbot.presentation.screens

import UserInput
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.isometrik.androidchatbot.ChatBotActivity
import io.isometrik.androidchatbot.R
import io.isometrik.androidchatbot.data.Message
import io.isometrik.androidchatbot.data.ViewState
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.presentation.AiChatBotSdk
import io.isometrik.androidchatbot.presentation.components.AlertDialogApp
import io.isometrik.androidchatbot.presentation.components.MessageBox
import io.isometrik.androidchatbot.presentation.components.TopBarWithProfile
import io.isometrik.androidchatbot.presentation.components.WidgetCardItem
import io.isometrik.androidchatbot.presentation.enums.MessageType
import io.isometrik.androidchatbot.presentation.extensions.background
import io.isometrik.androidchatbot.presentation.extensions.toColor
import io.isometrik.androidchatbot.presentation.extensions.toFont
import io.isometrik.androidchatbot.viewmodel.ChatScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: ChatScreenViewModel
) {
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val listOfMessages by viewModel.listOfMessages.collectAsStateWithLifecycle()
    val tempMessages by viewModel.tempMessages.collectAsStateWithLifecycle()

    var showBackDialog by remember { mutableStateOf(false) }
    var showResetSessionDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        // Subscribe to the event flow
        viewModel.event.collect { eventMessage ->
            // Show a toast whenever the event is triggered
            Toast.makeText(context, eventMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Box (modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(viewState.uiPreferences.mode_theme.background()).systemBarsPadding()
        ) {

            val context = LocalContext.current
            // Top bar
            TopBarWithProfile(
                uiPreferences = viewState.uiPreferences,
                profileImageUrl = viewState.profileImageUrl,
                username = "AI Assistant",
                appTitle = viewState.appName,  // Dynamic app name
                onBackArrowClick = {
                    showBackDialog = true
                },
                onMenuClick = {
                    showResetSessionDialog = true
                },
            )

            // Chat messages or content area
            ContentAreaWithBottomsheet(viewModel, viewState, listOfMessages, tempMessages)

        }

        if(showBackDialog){
            AlertDialogApp(
                title = context.getString(R.string.bot_exit_screen),
                message = context.getString(R.string.bot_do_you_want_to_leave_this_screen),
                confirmButtonText = context.getString(R.string.bot_exit),
                dismissButtonText = context.getString(R.string.bot_cancel),
                onConfirm = {
                    showBackDialog = false
                    (context as? ChatBotActivity)?.finish()
                },
                onDismiss = {
                    showBackDialog = false
                    // Do nothing, dialog will close
                }
            )
        }

        if(showResetSessionDialog){
            AlertDialogApp(
                title = context.getString(R.string.bot_session_restart),
                message = context.getString(R.string.bot_do_you_want_to_reset_current_session),
                confirmButtonText = context.getString(R.string.bot_yes),
                dismissButtonText = context.getString(R.string.bot_cancel),
                onConfirm = {
                    showResetSessionDialog = false
                    viewModel.resetSession()
                },
                onDismiss = {
                    showResetSessionDialog = false
                    // Do nothing, dialog will close
                }
            )
        }
    }

}

@Composable
fun manageMessageBox(
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    message: Message,
    uiPreferences: UiPreferences,
    profileImageUrl: String,
    viewModel: ChatScreenViewModel
) {
    MessageBox(message = message,
        uiPreferences = uiPreferences,
        profileImageUrl = profileImageUrl,
        onActionClick = { widget ->
            AiChatBotSdk.instance?.getBotActionsListener()?.let { listener ->
                listener.onWidgetActionClick(widget)
            }
        },
        onMessageClick = { messageType, message ->
            if (messageType == MessageType.BOT_SUGGESTION || messageType == MessageType.BOT_RESPONSE_FLOW) {
                viewModel.onBotSuggestionSelected(message)
            }
        },
        onViewMoreClick = { message ->
            coroutineScope.launch {
                bottomSheetState.show()
            }
        })
}

@Composable
fun ColumnScope.ContentAreaWithBottomsheet(
    viewModel: ChatScreenViewModel,
    viewState: ViewState,
    listOfMessages: List<Message>,
    tempMessages: List<Message>
) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(sheetState = bottomSheetState,
        modifier = Modifier.fillMaxWidth().weight(1f),
        sheetShape = RoundedCornerShape(8.dp),
        sheetBackgroundColor = viewState.uiPreferences.mode_theme.background(),
        sheetContent = {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (tempMessages.lastOrNull()?.type == MessageType.BOT_RESPONSE_FLOW) {
                    // BOT RESPONSE FLOW
                    val widgetList = tempMessages.last().listOfWidget
                    item {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = tempMessages.last().message,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = viewState.uiPreferences.bot_bubble_font_color.toColor(),
                                fontSize = 16.sp,
                                fontFamily = viewState.uiPreferences.font_style.toFont()
                            )
                        )
                    }

                    items(widgetList.size) { position ->
                        Box(
                            modifier = Modifier.padding(start = 10.dp, end = 16.dp)
                                .clip(RoundedCornerShape(10.dp)).background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            viewState.uiPreferences.bot_bubble_color.toColor(),
                                            viewState.uiPreferences.bot_bubble_color.toColor()
                                        )
                                    )
                                ).fillMaxWidth().clickable {
                                    viewModel.onBotSuggestionSelected(widgetList[position].actionText.orEmpty())
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }
                                }
                        ) {
                            Text(
                                text = widgetList[position].actionText.orEmpty(),
                                modifier = Modifier.align(Alignment.CenterStart).padding( 15.dp,),
                                style = TextStyle(
                                    color = viewState.uiPreferences.bot_bubble_font_color.toColor(),
                                    fontSize = 14.sp,
                                    fontFamily = viewState.uiPreferences.font_style.toFont()
                                )
                            )
                        }
                    }

                } else if (listOfMessages.lastOrNull()?.type == MessageType.BOT_WIDGET) {
                    // BOT WIDGET
                    val widgetList = listOfMessages.last().listOfWidget

                    item {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = listOfMessages.last().message,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = viewState.uiPreferences.bot_bubble_font_color.toColor(),
                                fontSize = 16.sp,
                                fontFamily = viewState.uiPreferences.font_style.toFont()
                            )
                        )
                    }

                    items(widgetList.size) { position ->
                        WidgetCardItem(widget = widgetList[position],
                            uiPreferences = viewState.uiPreferences,
                            fromViewMore = true,
                            onActionClick = {
                                AiChatBotSdk.instance?.getBotActionsListener()?.let { listener ->
                                    listener.onWidgetActionClick(widgetList[position])
                                }
                            })
                    }
                }

            }
        }) {
        Column {
            if (viewState.loading) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(bottom = 16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(bottom = 16.dp),
                reverseLayout = true,
            ) {
                // For temporary messages
                items(tempMessages.size) { position ->
                    val reversedPosition = tempMessages.size - 1 - position
                    manageMessageBox(
                        coroutineScope,
                        bottomSheetState,
                        tempMessages[reversedPosition],
                        viewState.uiPreferences,
                        viewState.profileImageUrl,
                        viewModel
                    )
                }

                // For permanent messages
                items(listOfMessages.size) { position ->
                    val reversedPosition = listOfMessages.size - 1 - position
                    manageMessageBox(
                        coroutineScope,
                        bottomSheetState,
                        listOfMessages[reversedPosition],
                        viewState.uiPreferences,
                        viewState.profileImageUrl,
                        viewModel
                    )
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
                isEnabled = tempMessages.lastOrNull()?.type != MessageType.BOT_PROCESSING,
                uiPreferences = viewState.uiPreferences
            )
        }
    }
}
