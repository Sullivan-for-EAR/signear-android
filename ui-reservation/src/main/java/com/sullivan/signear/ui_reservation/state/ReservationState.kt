package com.sullivan.signear.ui_reservation.state

sealed class ReservationState {
    object NotRead : ReservationState()
    object NotConfirm : ReservationState()
    object Confirm : ReservationState()
    object Cancel : ReservationState()
    object Urgent : ReservationState()
    object Reject : ReservationState()
    object Served : ReservationState()
}
