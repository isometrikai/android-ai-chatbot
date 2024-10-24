package io.isometrik.androidchatbot.data

import io.isometrik.androidchatbot.data.model.UiPreferences

data class ViewState(
    val uiPreferences: UiPreferences = UiPreferences(
        2,
        "#2196f3",
        "#1b1b1b",
        "#2196f3",
        "12px",
        "Arial",
        "#ffffff",
        "#ffffff"
    ),
    val appName : String = "Offline",
    val profileImageUrl : String = "https://admin-media1.isometrik.io/ai_bot/GE3f_Oc3-X.png",
    val loading : Boolean = false
)
