package io.isometrik.androidchatbot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.isometrik.androidchatbot.data.Message
import io.isometrik.androidchatbot.data.ViewState
import io.isometrik.androidchatbot.data.api.RetrofitProvider
import io.isometrik.androidchatbot.data.repository.ChatBotRepository
import io.isometrik.androidchatbot.presentation.AiChatBotSdk
import io.isometrik.androidchatbot.presentation.enums.MessageType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

class ChatScreenViewModel : ViewModel() {

    private val chatBotRepository = ChatBotRepository(RetrofitProvider.guestAuthService)
    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState>
        get() = _viewState

    private val _listOfMessages = MutableStateFlow<List<Message>>(listOf())
    val listOfMessages: StateFlow<List<Message>>
        get() = _listOfMessages

    private val _tempMessages = MutableStateFlow<List<Message>>(listOf())
    val tempMessages: StateFlow<List<Message>>
        get() = _tempMessages

    var retryCont = 0
    var currentSessionId = ""

    init {
        currentSessionId = (System.currentTimeMillis()/1000).toString()
//        currentSessionId = getHashedSessionId((System.currentTimeMillis()/1000).toString())
        if (AiChatBotSdk.instance?.getUserSession()?.userToken.isNullOrEmpty()) {
            authenticateGuest()
        } else {
            getMySession()
        }
    }

    private fun authenticateGuest() {
        _viewState.value = ViewState(loading = true)
        viewModelScope.launch {
            try {
                val authResponse = chatBotRepository.authenticateGuest()
                authResponse.data?.let {
                    AiChatBotSdk.instance?.getUserSession()?.userToken = it.accessToken
                    getMySession()
                } ?: let {
                    Log.e("Error: ", "authenticateGuest: ${authResponse.message}")
                }

                // Handle success or failure
            } catch (e: Exception) {
                // Handle error
                Log.e("Error: ", "Exception: ${e.message}")
            }
        }
    }

    private fun getMySession() {
        _viewState.value = ViewState(loading = true)
        viewModelScope.launch {
            try {
                val authResponse = chatBotRepository.getMySession()
                authResponse.data?.let {
                    val mdata = it[0]
                    _viewState.value = ViewState(
                        mdata.ui_preferences,
                        mdata.name,
                        mdata.profile_image,
                        loading = false
                    )
                    addMessage(Message(mdata.name, MessageType.WELCOME))
                    mdata.suggested_replies.forEach { reply ->
                        val message = Message(reply, MessageType.BOT_SUGGESTION)
                        addTempMessage(message)
                    }
                } ?:let {
                    if(retryCont < 2){
                        retryCont++
                        authenticateGuest()
                    }
                    Log.e("Error: ", "getMySession: ${authResponse.message}")
                }
                // Handle success or failure
            } catch (e: Exception) {
                // Handle error
                if(retryCont < 2){
                    retryCont++
                    authenticateGuest()
                }
            }
        }
    }

    private fun getClientMsg(askedMsg : String) {
        addTempMessage(Message("Processing...", MessageType.BOT_PROCESSING))

        viewModelScope.launch {
            try {
                val clientMsgResponse = chatBotRepository.getClientMsg(askedMsg, currentSessionId)
                clientMsgResponse?.let {
                    _tempMessages.value = arrayListOf()
                    addMessage(Message(it.text, MessageType.BOT_REPLY))
                    if(it.widgetData.isNotEmpty()){
                        it.widgetData[0].let { widgetData ->
                            val listOfWidget = widgetData.widget

                            if(widgetData.type.equals("Card View")){
                                if(listOfWidget.isNotEmpty()){
                                    addMessage(Message(it.text, MessageType.BOT_WIDGET,listOfWidget = listOfWidget))
                                }
                            }
                            if(widgetData.type.equals("Response Flow")){
                                if(listOfWidget.isNotEmpty()){

                                }
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("Error: ", "Exception: ${e.message}")
            }
        }



    }

    fun getHashedSessionId(id: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(id.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun getSessionId(): String {
        // Step 1: Get the current timestamp in milliseconds
        val currentTimeMillis = System.currentTimeMillis().toString()

        // Step 2: Convert the string to SHA-256 hash
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(currentTimeMillis.toByteArray())

        // Step 3: Convert the hash bytes to a hexadecimal string
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun addMessage(newMessage: Message) {
        _listOfMessages.value += newMessage
    }
    private fun addTempMessage(newMessage: Message) {
        _tempMessages.value += newMessage
    }

    fun onBotSuggestionSelected(message: String) {
        processingMessage(message)
    }

    fun onUserSentMessage(message: String) {
        processingMessage(message)

    }

     fun processingMessage(askedMsg: String){
         viewModelScope.launch {
             _tempMessages.value = arrayListOf()
             addMessage(Message(askedMsg, MessageType.USER))

             getClientMsg(askedMsg)

         }

    }
}