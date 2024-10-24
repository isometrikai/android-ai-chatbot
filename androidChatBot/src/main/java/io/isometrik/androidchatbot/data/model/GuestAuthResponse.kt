package io.isometrik.androidchatbot.data.model

class GuestAuthResponse(
    val message: String,
    val data: Data?,
)

data class Data(
    val accessExpireAt: Long,
    val accessToken: String,
    val refreshToken: String,
    val isometrikToken: String,
    val isometrikUserId: String,
    val licenseKey: String,
    val projectId: String,
    val accountId: String,
    val keySetId: String,
    val appSecret: String,
    val metaData: Any?,
)