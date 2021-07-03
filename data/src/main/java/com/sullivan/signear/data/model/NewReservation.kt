package com.sullivan.signear.data.model

import com.google.gson.annotations.SerializedName

data class NewReservation(
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
    @SerializedName("rsID")
    val id: Int,
)

data class UserInfo(
    @SerializedName("customerID")
    val id: Int
)

data class NewReservationRequest(
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
    @SerializedName("request")
    val request: String,
    @SerializedName("customerUser")
    val userInfo: UserInfo,
    @SerializedName("status")
    val status: Int = 1,
    @SerializedName("type")
    val type: Int = 1,
)

data class NewEmergencyReservationRequest(
    @SerializedName("date")
    val date: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("customerUser")
    val userInfo: UserInfo,
    @SerializedName("end_time")
    val endTime: String = "",
    @SerializedName("area")
    val center: String = "",
    @SerializedName("address")
    val place: String = "긴급통역",
    @SerializedName("method")
    val method: Int = 0,
    @SerializedName("request")
    val request: String = "",
    @SerializedName("status")
    val status: Int = 8,
    @SerializedName("type")
    val type: Int = 8,
)