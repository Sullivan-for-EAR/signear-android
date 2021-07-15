package com.sullivan.signear.ui_reservation.state

import androidx.annotation.Keep

@Keep
sealed class ReservationConfirmDialogState {
    object Init : ReservationConfirmDialogState()
    object Dismiss : ReservationConfirmDialogState()
    object MoveToDetail : ReservationConfirmDialogState()
}
