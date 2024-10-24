package io.isometrik.androidchatbot.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.extensions.toColor
import io.isometrik.androidchatbot.presentation.extensions.toFont

@Composable
fun WidgetResponseFlowItem(
    widget: Widget,
    modifier: Modifier = Modifier,
    uiPreferences: UiPreferences,
    onWidgetClick : (Widget) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = uiPreferences.primary_color.toColor(),
                shape = RoundedCornerShape(20.dp)
            ).clickable { onWidgetClick(widget) }
    ) {
        Text(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = widget.actionText.orEmpty(),
            style = TextStyle(
                color =  uiPreferences.primary_color.toColor(),
                fontSize = 14.sp,
                fontFamily = uiPreferences.font_style.toFont()
            )
        )
    }


}

@Preview
@Composable
fun PreviewResponseFlowWidget(){
    WidgetResponseFlowItem(widget = previewWidget, uiPreferences = previewUiPreferences, onWidgetClick = {})
}