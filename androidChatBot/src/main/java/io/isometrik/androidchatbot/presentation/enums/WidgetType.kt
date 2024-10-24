package io.isometrik.androidchatbot.presentation.enums

enum class WidgetType(val value : String) {
    CARD_VIEW("Card View"),
    RESPONSE_FLOW("Response Flow");

    companion object {
        fun fromValue(value: String): WidgetType? {
            return entries.find { it.value == value }
        }
    }
}