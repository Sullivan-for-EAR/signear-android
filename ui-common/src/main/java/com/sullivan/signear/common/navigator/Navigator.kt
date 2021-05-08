package com.sullivan.signear.common.navigator

import android.content.Context

interface LoginNavigator {
    fun openLogin(context: Context)
}

interface ReservationNavigator {
    fun openReservationHome(context: Context)
}