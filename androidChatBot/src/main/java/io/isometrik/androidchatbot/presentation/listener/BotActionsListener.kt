package io.isometrik.androidchatbot.presentation.listener

import io.isometrik.androidchatbot.data.model.Widget

interface BotActionsListener {

    fun onWidgetActionClick(widget: Widget)
}