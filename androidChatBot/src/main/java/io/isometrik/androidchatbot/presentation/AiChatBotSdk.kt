package io.isometrik.androidchatbot.presentation

import android.app.Application
import android.content.Context
import io.isometrik.androidchatbot.util.UserSession
import kotlin.concurrent.Volatile

/**
 * The IsometrikChatBotSdk singleton to expose sdk functionality to other modules.
 */
class AiChatBotSdk private constructor() {
    private var userSession: UserSession? = null
      var imConfiguration: IMConfiguration? = null
    private var applicationContext: Context? = null

    /**
     * private constructor.
     */
    init {
        //Prevent form the reflection api.

        if (aiChatBotSdk != null) {
            throw RuntimeException(
                "Use getInstance() method to get the single instance of this class."
            )
        }
    }

    /**
     * Sdk initialize.
     *
     * @param applicationContext the application context
     */
    fun sdkInitialize(applicationContext: Context?) {
        if (applicationContext == null) {
            throw RuntimeException(
                "Sdk initialization failed as application context cannot be null."
            )
        } else if (applicationContext !is Application) {
            throw RuntimeException(
                "Sdk initialization failed as context passed in parameter is not application context."
            )
        }

        this.applicationContext = applicationContext
    }

    /**
     * Create configuration.
     *
     * @param appSecret the app secret
     * @param licenseKey the license key
     */
    fun createConfiguration(
        chatBotId: String,
        appSecret: String,
        licenseKey: String,
        userId: String,
        storeCategoryId: String,
        location: String,
        latitude : String,
        longitude : String,
        createIsometrikUser : Boolean = true
    ) {
        if (applicationContext == null) {
            throw RuntimeException("Initialize the sdk before creating configuration.")
        } else if (chatBotId.isEmpty()) {
            throw RuntimeException("Pass a valid chatBotId for AiChatBot sdk initialization.")
        } else if (storeCategoryId.isEmpty()) {
            throw RuntimeException("Pass a valid storeCategoryId for AiChatBot sdk initialization.")
        } else if (appSecret.isEmpty()) {
            throw RuntimeException("Pass a valid appSecret for AiChatBot sdk initialization.")
        } else if (licenseKey.isEmpty()) {
            throw RuntimeException("Pass a valid licenseKey for AiChatBot sdk initialization.")
        } else if (userId.isEmpty()) {
            throw RuntimeException("Pass a valid userId for AiChatBot sdk initialization.")
        } else if (location.isEmpty()) {
            throw RuntimeException("Pass a valid location for AiChatBot sdk initialization.")
        }

         imConfiguration =
            IMConfiguration(
                applicationContext!!, licenseKey, appSecret,userId,createIsometrikUser,chatBotId.toInt(),storeCategoryId,location)

        userSession = UserSession(applicationContext)
    }


    /**
     * Gets user session.
     *
     * @return the user session
     */
    fun getUserSession(): UserSession {
        if (userSession == null) {
            throw RuntimeException(
                "Create configuration before trying to access user session object."
            )
        }

        return userSession as UserSession
    }

    val context: Context?
        /**
         * Gets context.
         *
         * @return the context
         */
        get() {
            if (userSession == null) {
                throw RuntimeException("Create configuration before trying to access context object.")
            }
            return applicationContext
        }

    /**
     * On terminate.
     */
    fun onTerminate() {
        if (userSession == null) {
            throw RuntimeException("Create configuration before trying to access isometrik object.")
        }
    }


    companion object {
        @Volatile
        private var aiChatBotSdk: AiChatBotSdk? = null
        @JvmStatic
        val instance: AiChatBotSdk?
            /**
             * Gets instance.
             *
             * @return the IsometrikChatBotSdk instance
             */
            get() {
                if (aiChatBotSdk == null) {
                    synchronized(AiChatBotSdk::class.java) {
                        if (aiChatBotSdk == null) {
                            aiChatBotSdk = AiChatBotSdk()
                        }
                    }
                }
                return aiChatBotSdk
            }
    }
}
