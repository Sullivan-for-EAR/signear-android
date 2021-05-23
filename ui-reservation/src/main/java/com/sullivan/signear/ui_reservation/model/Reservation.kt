package com.sullivan.signear.ui_reservation.model

import com.sullivan.signear.ui_reservation.state.ReservationState

data class Reservation(
    val id: Int = 0,
    val date: String,
    val startTime: String,
    val endTime: String,
    val center: String,
    val place: String,
    val purpose: String,
    val isContactless: Boolean = false,
    var currentState: ReservationState = ReservationState.NotRead,
    var reject_cancel_reason: String = "",
    val isEmergency: Boolean = false
)
