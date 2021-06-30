package com.sullivan.signear.data.remote

import com.sullivan.signear.data.model.RankingInfo
import com.sullivan.signear.data.model.ResponseCheckEmail
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    companion object {
        const val BASE_URL = "http://3.35.204.9:80/"
    }

    @GET("nrise_data.json")
    suspend fun fetchRankInfo(): RankingInfo

    @GET("customer/check")
    suspend fun checkEmail(@Query("email") email: String): ResponseCheckEmail
}