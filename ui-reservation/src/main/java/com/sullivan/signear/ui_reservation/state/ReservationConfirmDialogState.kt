package com.sullivan.signear.ui_reservation.state

sealed class ReservationConfirmDialogState {
    object Init : ReservationConfirmDialogState()
    object Dismiss : ReservationConfirmDialogState()
    object MoveToDetail : ReservationConfirmDialogState()
}
