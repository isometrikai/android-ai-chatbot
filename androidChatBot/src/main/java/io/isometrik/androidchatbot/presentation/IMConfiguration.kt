package io.isometrik.androidchatbot.presentation

import android.content.Context
import io.isometrik.androidchatbot.presentation.enums.IMLogVerbosity

/**
 * The configuration class for Isometrik sdk initialization.
 */
class IMConfiguration(
    /**
     * Gets context.
     *
     * @return the context
     */
    val context: Context,
    /**
     * License Key provided by Isometrik.
     */
    val licenseKey: String,
    /**
     * Gets app secret.
     *
     * @return the app secret
     */
    val appSecret: String,

    /**
     * Gets fingerprintId.
     *
     * @return fingerprintId
     */

    val fingerprintId : String,

    val createIsometrikUser : Boolean,
    /**
     * Gets user secret.
     *
     * @return the user secret
     */
    val chatBotId: Int,
    /**
     * Gets chatBotId.
     *
     * @return chatBotId
     */
    val storeCategoryId: String,
    /**
     * Gets projectId.
     *
     * @return projectId
     */
    val location: String,
//    /**
//     * Gets keysetId.
//     *
//     * @return keysetId
//     */
//    val keysetId: String,

) {
    /**
     * Gets license key.
     *
     * @return the license key
     */
    /**
     * Gets client id.
     *
     * @return the client id
     */
    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    var clientId: String? = null
    /**
     * Gets username.
     *
     * @return the username
     */
    /**
     * Gets password.
     *
     * @return the password
     */
    /**
     * Is cache busting boolean.
     *
     * @return the cache busting
     */
    /**
     * Sets cache busting.
     *
     * @param cacheBusting the cache busting
     */
    /**
     * If proxies are forcefully caching requests, set to true to allow the client to randomize the
     * subdomain.
     * This configuration is not supported if custom origin is enabled.
     */
    var isCacheBusting: Boolean = false

    /**
     * Is secure boolean.
     *
     * @return the secure
     */
    /**
     * set to true to switch the client to HTTPS:// based communications.
     */
    val isSecure: Boolean = true
    /**
     * Gets log verbosity.
     *
     * @return the log verbosity
     */
    /**
     * Sets log verbosity.
     *
     * @param logVerbosity the log verbosity
     */
    /**
     * toggle to enable verbose logging for api request.
     */
    var logVerbosity: IMLogVerbosity


    /**
     * Gets connect timeout.
     *
     * @return the connect timeout
     */
    /**
     * Sets connect timeout.
     *
     * @param connectTimeout the connect timeout
     */
    /**
     * Stores the maximum number of seconds which the client should wait for connection before timing
     * out.
     */
    var connectTimeout: Int

    /**
     * Gets request timeout.
     *
     * @return the request timeout
     */
    /**
     * Sets request timeout.
     *
     * @param requestTimeout the request timeout
     */
    /**
     * Reference on number of seconds which is used by client during operations to
     * check whether response potentially failed with 'timeout' or not.
     */
    var requestTimeout: Int

    /**
     * Gets user token.
     *
     * @return the user token
     */
    /**
     * Sets user token.
     *
     * @param userToken the user token
     */
    var userToken: String? = null

    /**
     * Instantiates a new Im configuration.
     *
     * @param context the context
     * @param licenseKey the license key
     * @param appSecret the app secret
     * @param userSecret the user secret
     * @param accountId the accountId
     * @param projectId the projectId
     * @param keysetId the keysetId
     * @param userName the userName
     * @param password the password
     * @param googlePlacesApiKey the google places api key
     * @param giphyApiKey the giphy api key
     */
    init {
        requestTimeout = REQUEST_TIMEOUT

        connectTimeout = CONNECT_TIMEOUT

        logVerbosity = IMLogVerbosity.NONE
    }


    companion object {
        private const val REQUEST_TIMEOUT = 10
        private const val CONNECT_TIMEOUT = 10
    }
}
