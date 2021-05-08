package com.sullivan.ui_login.navigator

import android.content.Context
import com.sullivan.sigenear.common.navigator.LoginNavigator
import com.sullivan.signear.common.ex.launchActivity
import com.sullivan.ui_login.ui.LoginActivity
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor() : LoginNavigator {
    override fun openLogin(context: Context) {
        context.launchActivity<LoginActivity>()
    }
}