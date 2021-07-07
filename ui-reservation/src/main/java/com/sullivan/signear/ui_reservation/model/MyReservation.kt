package com.sullivan.signear.ui_reservation.model

import androidx.annotation.Keep
import com.sullivan.signear.ui_reservation.state.ReservationState

@Keep
data class MyReservation(
    val id: Int = 0,
    val date: String,
    val startTime: String,
    val endTime: String,
    val center: String,
    val place: String,
    val status: Int,
    var isContactless: Boolean = false,
    var currentState: ReservationState = ReservationState.NotRead,
    var isEmergency: Boolean = false
)
