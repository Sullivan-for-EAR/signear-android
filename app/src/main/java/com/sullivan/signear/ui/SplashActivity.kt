package com.sullivan.signear.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bentley.core.DataState
import com.sullivan.sigenear.common.databinding.ActivitySplashBinding
import com.sullivan.sigenear.common.navigator.LoginNavigator
import com.sullivan.signear.R
import com.sullivan.signear.common.ex.viewBinding
import com.sullivan.ui_login.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        moveToLoginScreen()
        checkApi()
    }

    private fun moveToLoginScreen() {
        lifecycleScope.launchWhenCreated {
            delay(1_000)
            loginNavigator.openLogin(this@SplashActivity)
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