package com.sullivan.signear.ui_reservation.state

sealed class ReservationState {
    object NotRead : ReservationState()
    object NotConfirm : ReservationState()
    object Confirm : ReservationState()
    class Cancel(reason: String) : ReservationState()
//    object Urgent : ReservationState()
    class Reject(reason: String) : ReservationState()
    object Served : ReservationState()
}
