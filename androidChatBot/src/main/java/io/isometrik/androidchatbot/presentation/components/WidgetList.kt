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
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.data.model.Widget

@Composable
fun WidgetList(
    widgetList: List<Widget>,
    uiPreferences: UiPreferences,
    onViewMoreClick: () -> Unit
) {
    val SHOW_WIDGET_LIMIT = 3
    val displayWidgets = if (widgetList.size > SHOW_WIDGET_LIMIT) widgetList.take(SHOW_WIDGET_LIMIT) else widgetList

    LazyRow {
        items(displayWidgets.size) { position ->
            WidgetItem(widget = widgetList[position], uiPreferences = uiPreferences)
        }
        if (widgetList.size > SHOW_WIDGET_LIMIT) {
            item {
                ViewMoreItem(onClick = onViewMoreClick)
            }
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