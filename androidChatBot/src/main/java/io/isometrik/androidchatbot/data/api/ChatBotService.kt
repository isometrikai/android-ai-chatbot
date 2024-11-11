package io.isometrik.androidchatbot.data.api

import io.isometrik.androidchatbot.data.model.ClientMsgRequest
import io.isometrik.androidchatbot.data.model.ClientMsgResponse
import io.isometrik.androidchatbot.data.model.GuestAuthRequest
import io.isometrik.androidchatbot.data.model.GuestAuthResponse
import io.isometrik.androidchatbot.data.model.MySessionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ChatBotService {

    @POST("v2/guestAuth")
    suspend fun guestAuth(@Body request : GuestAuthRequest): GuestAuthResponse

    @GET("v1/guest/mygpts")
    suspend fun getMyGPTs(@Header("Authorization") authorization : String, @Query("id") id: Int): MySessionResponse

    @POST("v1/gptClientMsg")
    suspend fun getClientMsg(@Header("Content-Type") contentType : String, @Header("Authorization") authorization : String,@Body request : ClientMsgRequest): ClientMsgResponse
}
