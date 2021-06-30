package com.sullivan.signear.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sullivan.common.core.DataState
import com.sullivan.common.ui_common.navigator.LoginNavigator
import com.sullivan.common.ui_common.navigator.ReservationNavigator
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
        checkAccessToken()
        observeViewModel()
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

    private fun checkAccessToken() {
        if (viewModel.checkAccessToken()) {
            viewModel.checkIsAccessTokenValid()
        } else {
            moveToLoginScreen()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        resultCheckAccessToken.observe(this@SplashActivity, { response ->
            if (response.result) {
                moveToMainScreen()
            }
        })
    }
}