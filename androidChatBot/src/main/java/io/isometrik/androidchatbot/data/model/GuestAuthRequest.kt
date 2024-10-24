package io.isometrik.androidchatbot.data.model

data class GuestAuthRequest(val appSecret : String,val licensekey : String, val fingerprintId : String, val createIsometrikUser : Boolean)