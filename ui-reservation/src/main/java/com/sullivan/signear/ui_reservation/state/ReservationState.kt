package com.sullivan.signear.ui_reservation.state

sealed class ReservationState {
    object NotRead : ReservationState()
    object NotConfirm : ReservationState()
    object Confirm : ReservationState()
    object Cancel : ReservationState()
    object Urgent: ReservationState()
    class Reject(val reason: String) : ReservationState()
    object Served : ReservationState()
}
