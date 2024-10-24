package io.isometrik.androidchatbot.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.isometrik.androidchatbot.data.Message
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.enums.MessageType

@Composable
fun WidgetList(
    message: Message,
    uiPreferences: UiPreferences,
    onViewMoreClick: () -> Unit,
    onWidgetClick: (Widget) -> Unit,
    onActionClick: (Widget) -> Unit
) {
    val widgetList = message.listOfWidget
    if(message.type == MessageType.BOT_WIDGET){
        val SHOW_WIDGET_LIMIT = 3
        val displayWidgets = if (widgetList.size > SHOW_WIDGET_LIMIT) widgetList.take(SHOW_WIDGET_LIMIT) else widgetList

        LazyRow {
            items(displayWidgets.size) { position ->
                WidgetCardItem(widget = widgetList[position], uiPreferences = uiPreferences, onActionClick = onActionClick)
            }
            if (widgetList.size > SHOW_WIDGET_LIMIT) {
                item {
                    ViewMoreItem(onClick = onViewMoreClick)
                }
            }
        }
    }else if(message.type == MessageType.BOT_RESPONSE_FLOW){
        val SHOW_WIDGET_LIMIT = 4
        val displayWidgets = if (widgetList.size > SHOW_WIDGET_LIMIT) widgetList.take(SHOW_WIDGET_LIMIT) else widgetList
        LazyRow {
            items(displayWidgets.size) { position ->
                WidgetResponseFlowItem(widget = widgetList[position], uiPreferences = uiPreferences, onWidgetClick = onWidgetClick)
            }
//            if (widgetList.size > SHOW_WIDGET_LIMIT) {
//                item {
//                    ViewMoreItem(onClick = onViewMoreClick)
//                }
//            }
        }
    }



}

@Composable
fun ViewMoreItem(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(150.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "View More", style = MaterialTheme.typography.headlineSmall)
    }
}