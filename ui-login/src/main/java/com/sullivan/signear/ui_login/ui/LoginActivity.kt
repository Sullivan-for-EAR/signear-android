package com.sullivan.signear.ui_login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sullivan.signear.ui_login.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}