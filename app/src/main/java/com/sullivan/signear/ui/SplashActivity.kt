package com.sullivan.signear.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sullivan.sigenear.common.databinding.ActivitySplashBinding
import com.sullivan.sigenear.common.navigator.LoginNavigator
import com.sullivan.signear.R
import com.sullivan.signear.common.ex.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
        moveToLoginScreen()
    }

    private fun moveToLoginScreen() {
        lifecycleScope.launchWhenCreated {
//            delay(1_500)
            loginNavigator.openLogin(this@SplashActivity)
        }
    }
}