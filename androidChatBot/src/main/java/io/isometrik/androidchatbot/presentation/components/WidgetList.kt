package io.isometrik.androidchatbot.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.isometrik.androidchatbot.data.Message
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.enums.MessageType
import io.isometrik.androidchatbot.presentation.extensions.toColor
import io.isometrik.androidchatbot.presentation.extensions.toFont

@Composable
fun WidgetList(
    message: Message,
    uiPreferences: UiPreferences,
    onViewMoreClick: () -> Unit,
    onWidgetClick: (Widget) -> Unit,
    onActionClick: (Widget) -> Unit
) {
    val widgetList = message.listOfWidget
    if (message.type == MessageType.BOT_WIDGET) {
        val SHOW_WIDGET_LIMIT = 3
        val displayWidgets =
            if (widgetList.size > SHOW_WIDGET_LIMIT) widgetList.take(SHOW_WIDGET_LIMIT) else widgetList

        LazyRow(verticalAlignment = Alignment.CenterVertically) {
            items(displayWidgets.size) { position ->
                WidgetCardItem(
                    widget = widgetList[position],
                    uiPreferences = uiPreferences,
                    onActionClick = onActionClick
                )
            }
            if (widgetList.size > SHOW_WIDGET_LIMIT) {
                item {
                    ViewMoreCardItem(onClick = onViewMoreClick, uiPreferences)
                }
            }
        }
    } else if (message.type == MessageType.BOT_RESPONSE_FLOW) {
        val SHOW_WIDGET_LIMIT = 3
        val displayWidgets =
            if (widgetList.size > SHOW_WIDGET_LIMIT) widgetList.take(SHOW_WIDGET_LIMIT) else widgetList
        LazyRow {
            items(displayWidgets.size) { position ->
                WidgetResponseFlowItem(
                    widget = widgetList[position],
                    uiPreferences = uiPreferences,
                    onWidgetClick = onWidgetClick
                )
            }
            if (widgetList.size > SHOW_WIDGET_LIMIT) {
                item {
                    WidgetResponseFlowItem(
                        widget = Widget(actionText = "View All"),
                        uiPreferences = uiPreferences,
                        onWidgetClick = {
                            onViewMoreClick()
                        }
                    )
                }
            }
        }
    }


}

@Composable
fun ViewMoreCardItem(onClick: () -> Unit, uiPreferences: UiPreferences) {
    val buttonBackgroundColor =
        uiPreferences.primary_color.toColor().copy(alpha = 0.1f)

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .height(244.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = buttonBackgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "View More",
                style = TextStyle(
                    color = uiPreferences.primary_color.toColor(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    fontFamily = uiPreferences.font_style.toFont()
                )
            )
        }
    }
}