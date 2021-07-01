package com.sullivan.signear.data.remote

import com.sullivan.signear.data.model.*
import retrofit2.http.*


interface ApiService {

    companion object {
        //        const val BASE_URL = "http://3.35.204.9:80/"
        const val BASE_URL = "http://10.0.2.2:8088/"
//        const val BASE_URL = "http://192.168.1.9:8088/"
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

    @GET("user/customer/")
    suspend fun getUserInfo(@Query("customer_id") id: Int): UserProfile

    @GET("/reservation/customer/list")
    suspend fun getReservationList(@Query("customer_id") id: Int): List<ReservationData>

    @POST("reservation/customer/create")
    suspend fun applyReservation(
        @Body reservationInfo: HashMap<String, Any>
    ): NewReservation
}