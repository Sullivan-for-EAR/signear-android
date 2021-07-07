package com.sullivan.signear.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ReservationData(
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
)
