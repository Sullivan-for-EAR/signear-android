package com.sullivan.signear.ui_reservation.state

sealed class ReservationState {
    object NotRead : ReservationState()
    object NotCofirmed : ReservationState()
    object Confirmed : ReservationState()
    class Rejected(val reason: String) : ReservationState()
    object Canceld: ReservationState()
}
