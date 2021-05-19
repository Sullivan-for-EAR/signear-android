package com.sullivan.signear.ui_reservation.model

import com.sullivan.signear.ui_reservation.state.ReservationState

data class Reservation(
//    val id: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val center: String,
    val place: String,
    val purpose: String,
    val currentState: ReservationState = ReservationState.NotRead,
    val isContactless: Boolean = false
)
