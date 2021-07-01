package com.sullivan.signear.data.model

import com.google.gson.annotations.SerializedName

data class ResponseApplyReservation(
    @SerializedName("rsID")
    val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("area")
    val center: String,
    @SerializedName("address")
    val place: String,
    @SerializedName("method")
    val method: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("request")
    val request: String,
    @SerializedName("customerUser")
    val userInfo: UserInfo,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("modDate")
    val modDate: String,
)

data class UserInfo(
    @SerializedName("customerID")
    val id: Int
)