package io.isometrik.androidchatbot.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.isometrik.androidchatbot.R
import io.isometrik.androidchatbot.data.Message
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.enums.MessageType
import io.isometrik.androidchatbot.presentation.extensions.toColor
import io.isometrik.androidchatbot.presentation.extensions.toFont

@Composable
fun MessageBox(
    message: Message,
    uiPreferences: UiPreferences,
    profileImageUrl : String,
    onMessageClick: (MessageType, String) -> Unit
) {
    val modifier = if (
        message.type == MessageType.WELCOME) {
        Modifier
            .padding(start = 10.dp, end = 16.dp)
    } else if( message.type == MessageType.BOT_WIDGET){
        Modifier
            .padding(start = 10.dp, end = 16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        uiPreferences.bot_bubble_color.toColor(),
                        uiPreferences.bot_bubble_color.toColor()
                    )
                )
            )
    }else if (message.type == MessageType.USER) {
        Modifier
            .padding(start = 16.dp, end = 8.dp)
            .clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp, bottomStart = 15.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                       uiPreferences.user_bubble_color.toColor(),
                       uiPreferences.user_bubble_color.toColor()
                    )
                )
            )
    } else if (message.type == MessageType.BOT_SUGGESTION) {
        Modifier
            .padding(start = 10.dp, end = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = uiPreferences.primary_color.toColor(),
                shape = RoundedCornerShape(20.dp)
            )

    } else if(message.type == MessageType.BOT_REPLY || message.type == MessageType.BOT_PROCESSING) {
        Modifier
            .padding(start = 10.dp, end = 16.dp)
            .clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp, bottomEnd = 15.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        uiPreferences.bot_bubble_color.toColor(),
                        uiPreferences.bot_bubble_color.toColor()
                    )
                )
            )
    } else {
        Modifier
            .padding(start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp, bottomEnd = 15.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        uiPreferences.user_bubble_color.toColor(),
                        uiPreferences.user_bubble_color.toColor()
                    )
                )
            )
    }


    val boxArrangement =
        if (message.type == MessageType.USER) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier.padding(vertical = if (message.type == MessageType.BOT_SUGGESTION) 4.dp else 8.dp)
            .fillMaxWidth().clickable { onMessageClick(message.type, message.message)  },
        contentAlignment = boxArrangement
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            if (message.type == MessageType.BOT_REPLY || message.type == MessageType.BOT_PROCESSING) {
                ProfileImageWithPlaceholder(modifier = Modifier.padding(start = 10.dp),profileImageUrl = profileImageUrl,
                    placeholderColor = uiPreferences.user_bubble_color.toColor())
            }
            if(message.type == MessageType.BOT_WIDGET){
                Spacer(modifier = Modifier.width(50.dp))
            }

            Box {
                if (message.type == MessageType.WELCOME) {
                    Column {
                        Image(
                            modifier = Modifier.padding(start = 30.dp).height(100.dp).graphicsLayer(scaleX = -1f),
                            painter = painterResource(id = R.drawable.chat_initiator_avtar),
                            contentDescription = "chat initiator avtar",
                            contentScale = ContentScale.FillHeight
                        )

                        Column(
                            modifier
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 15.dp,
                                        topStart = 15.dp,
                                        bottomEnd = 15.dp
                                    )
                                )
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            uiPreferences.primary_color.toColor(),
                                            Color(0xFF93EDAB),
                                        )
                                    )
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    bottom = 5.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                                text = "Meet ${message.message}",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = uiPreferences.font_style.toFont(),
                                )
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = 5.dp,
                                    bottom = 10.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                                text = "Hey, I am your private butler.",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontFamily = uiPreferences.font_style.toFont(),
                                )
                            )
                        }

                    }
                } else if(message.type == MessageType.BOT_PROCESSING){
                    Box(modifier = modifier.padding(12.dp)) {
                        Text(
                            text = message.message,
                            style = TextStyle(
                                color =  uiPreferences.bot_bubble_font_color.toColor(),
                                fontSize = 14.sp,
                                fontFamily = uiPreferences.font_style.toFont()
                            )
                        )
                    }
                } else if(message.type == MessageType.BOT_REPLY){
                    Box(modifier = modifier.padding(12.dp)) {
                        Text(
                            text = message.message,
                            style = TextStyle(
                                color =  uiPreferences.bot_bubble_font_color.toColor(),
                                fontSize = 14.sp,
                                fontFamily = uiPreferences.font_style.toFont()
                            )
                        )
                    }
                } else if(message.type == MessageType.BOT_WIDGET){
                    WidgetList(message.listOfWidget,uiPreferences){
                        // on View More click
                    }

                }else {
                    Box(modifier = modifier.padding(12.dp)) {
                        Text(
                            text = message.message,
                            style = TextStyle(
                                color = if (message.type == MessageType.BOT_SUGGESTION) uiPreferences.primary_color.toColor() else Color.White,
                                fontSize = 14.sp,
                                fontFamily = uiPreferences.font_style.toFont()
                            )
                        )
                    }
                }
            }
        }
    }
}