package io.isometrik.sample.chatbot

import android.app.Application
import io.isometrik.androidchatbot.presentation.AiChatBotSdk

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AiChatBotSdk.instance?.sdkInitialize(this)
        AiChatBotSdk.instance?.createConfiguration(
            getString(R.string.chat_bot_id),
            getString(R.string.app_secret),
            getString(R.string.license_key),
            getString(R.string.user_id),
            getString(R.string.store_category_id),
            getString(R.string.location),
            getString(R.string.latitude),
            getString(R.string.longitude)
        )
    }

    override fun onTerminate() {
        super.onTerminate()
        AiChatBotSdk.instance?.onTerminate()
    }
}