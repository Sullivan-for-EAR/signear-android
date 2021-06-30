package com.sullivan.signear.data.remote

import com.sullivan.signear.data.model.RankingInfo
import com.sullivan.signear.data.model.ResponseCheckAccessToken
import com.sullivan.signear.data.model.ResponseCheckEmail
import com.sullivan.signear.data.model.ResponseLogin
import retrofit2.http.*


interface ApiService {

    companion object {
//        const val BASE_URL = "http://3.35.204.9:80/"
        const val BASE_URL = "http://10.0.2.2:8088/"
    }

    @GET("nrise_data.json")
    suspend fun fetchRankInfo(): RankingInfo

    @GET("customer/check")
    suspend fun checkEmail(@Query("email") email: String): ResponseCheckEmail

    @POST("customer/login")
    suspend fun login(
        @Body input: HashMap<String, Any>
    ): ResponseLogin

    @GET("customer/home")
    suspend fun checkAccessToken(): ResponseCheckAccessToken

    @POST("user/customer/create")
    suspend fun createUser(
        @Body input: HashMap<String, Any>
    ): ResponseLogin
}