package com.sullivan.signear.ui_reservation.state

import androidx.annotation.Keep

@Keep
sealed class ReservationState {
    object NotRead : ReservationState()
    object NotConfirm : ReservationState()
    object Confirm : ReservationState()
    class Cancel(reason: String = "") : ReservationState()
    class Reject(reason: String = "") : ReservationState()
    object Served : ReservationState()
    object EmergencyCancel : ReservationState()
    object None : ReservationState()
}
