package com.sullivan.signear.ui_reservation.navigator

import android.content.Context
import com.sullivan.signear.common.ex.launchActivity
import com.sullivan.signear.common.navigator.ReservationNavigator
import com.sullivan.signear.ui_reservation.ui.ReservationActivity
import javax.inject.Inject

class ReservationNavigatorImpl @Inject constructor() : ReservationNavigator {
    override fun openReservationHome(context: Context) {
        context.launchActivity<ReservationActivity>()
    }
}