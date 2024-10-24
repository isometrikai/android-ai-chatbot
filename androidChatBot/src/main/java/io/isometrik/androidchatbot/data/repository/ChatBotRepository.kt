package io.isometrik.androidchatbot.data.repository

import io.isometrik.androidchatbot.data.api.ChatBotService
import io.isometrik.androidchatbot.data.model.ClientMsgRequest
import io.isometrik.androidchatbot.data.model.ClientMsgResponse
import io.isometrik.androidchatbot.data.model.GuestAuthRequest
import io.isometrik.androidchatbot.data.model.GuestAuthResponse
import io.isometrik.androidchatbot.data.model.MySessionResponse
import io.isometrik.androidchatbot.presentation.AiChatBotSdk

class ChatBotRepository(private val chatBotService: ChatBotService) {

    suspend fun authenticateGuest(): GuestAuthResponse {

        AiChatBotSdk.instance?.imConfiguration?.let {
            if (it.appSecret.isEmpty()) {
                return GuestAuthResponse("appSecret not configured", null)
            } else if (it.licenseKey.isEmpty()) {
                return GuestAuthResponse("licensekey not configured", null)
            } else if (it.fingerprintId.isEmpty()) {
                return GuestAuthResponse("fingerprintId not configured", null)
            } else {
                return chatBotService.guestAuth(
                    GuestAuthRequest(
                        it.appSecret,
                        it.licenseKey,
                        it.fingerprintId,
                        it.createIsometrikUser
                    )
                )
            }
        } ?: let {
            return GuestAuthResponse("AiChatBotSdk not configured", null)
        }

    }

    suspend fun getMySession(): MySessionResponse {
        AiChatBotSdk.instance?.getUserSession()?.userToken?.let {
            return chatBotService.getMyGPTs(it, AiChatBotSdk.instance?.imConfiguration?.chatBotId!!)
        } ?: let {
            return MySessionResponse("userToken not configured", null, 0)
        }
    }

    suspend fun getClientMsg(askedMsg: String, sessionId: String): ClientMsgResponse? {
        AiChatBotSdk.instance?.getUserSession()?.userToken?.let {
            return chatBotService.getClientMsg("application/json",
                it,
                ClientMsgRequest(
                    AiChatBotSdk.instance?.imConfiguration?.chatBotId.toString(),
                    askedMsg,
                    sessionId,
                    AiChatBotSdk.instance?.imConfiguration?.storeCategoryId!!,
                    AiChatBotSdk.instance?.imConfiguration?.fingerprintId!!,
                    AiChatBotSdk.instance?.imConfiguration?.location!!
                )
            )
        } ?: let {
            return null
        }
    }
}