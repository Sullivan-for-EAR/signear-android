package com.sullivan.signear.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sullivan.signear.common.navigator.LoginNavigator
import com.sullivan.signear.common.navigator.ReservationNavigator
import com.sullivan.signear.core.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var loginNavigator: LoginNavigator
    @Inject
    lateinit var reservationNavigator: ReservationNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        moveToLoginScreen()
        checkApi()

        moveToMainScreen()
    }

    private fun moveToLoginScreen() {
        lifecycleScope.launchWhenCreated {
            delay(1_000)
            loginNavigator.openLogin(this@SplashActivity)
        }
    }

    private fun moveToMainScreen() {
        lifecycleScope.launchWhenCreated {
            delay(1_000)
            reservationNavigator.openReservationHome(this@SplashActivity)
        }
    }

    private fun checkApi() {
        viewModel.rankList.observe(this, { result ->

            when (result) {
                is DataState.Success -> {
                    Timber.d("${result.data}")
                }
            }
        })
    }
}