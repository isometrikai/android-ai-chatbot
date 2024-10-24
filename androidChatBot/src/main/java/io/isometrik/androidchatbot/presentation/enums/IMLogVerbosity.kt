package io.isometrik.androidchatbot.presentation.enums

/**
 * The enum defining the log verbosity level of the isometrik remote api calls.
 */
enum class IMLogVerbosity {
    /**
     * None, isometrik log verbosity level, when response of the remote api calls is not to be
     * printed.
     */
    NONE,

    /**
     * Body, isometrik log verbosity level, when response of the remote api calls is to be printed.
     */
    BODY,
}
