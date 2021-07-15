package com.sullivan.signear.ui_reservation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sullivan.sigenear.ui_reservation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
    }
}