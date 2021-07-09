package com.sullivan.signear.data.remote

import com.sullivan.signear.data.model.*
import retrofit2.http.*


interface ApiService {

    companion object {
        const val BASE_URL = "http://49.50.166.181:8088/"
    }

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
    suspend fun createReservation(
        @Body reservationInfo: HashMap<String, Any>
    ): NewReservation

    @GET("reservation/customer/")
    suspend fun getReservationDetailInfo(@Query("reservation_id") id: Int): ReservationDetailInfo

    @POST("reservation/customer/cancel/{reservation_id}")
    suspend fun cancelReservation(@Path("reservation_id") id: Int): ReservationDetailInfo

    @POST("reservation/emergency/create")
    suspend fun createEmergencyReservation(
        @Body reservationInfo: HashMap<String, Any>
    ): NewReservation

    @POST("reservation/emergency/cancel/{reservation_id}")
    suspend fun cancelEmergencyReservation(@Path("reservation_id") id: Int): ReservationDetailInfo

    @GET("management/customer/list")
    suspend fun getPrevReservationList(@Query("customer_id") id: Int): List<ReservationData>

    @POST("management/delete/{reservation_id}")
    suspend fun removePrevReservation(@Path("reservation_id") id: Int)
}