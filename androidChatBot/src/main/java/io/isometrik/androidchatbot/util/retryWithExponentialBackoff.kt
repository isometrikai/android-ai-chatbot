package io.isometrik.androidchatbot.util

import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

// Retry function with exponential backoff
suspend fun <T> retryWithExponentialBackoff(
    maxRetries: Int = 5,
    initialDelay: Long = 1000, // Start with 1 second delay
    maxDelay: Long = 16000,    // Max delay of 16 seconds
    factor: Double = 2.0,      // Multiplier for exponential backoff
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    var attempt = 0

    while (true) {
        try {
            return block()
        } catch (e: IOException) {
            // Handle network errors (like no connection)
        } catch (e: HttpException) {
            // Handle HTTP exceptions (like 500 errors)
        }

        attempt++
        if (attempt > maxRetries) {
            throw Exception("Max retry limit reached")
        }

        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
}
