package io.isometrik.androidchatbot.presentation.enums

enum class OrderType(val value: Int) {
    SELF_PICKUP(1),
    DELIVERY(2),
    BOTH(3);

    companion object {
        fun fromValue(value: Int): OrderType? {
            return values().find { it.value == value }
        }
    }

}