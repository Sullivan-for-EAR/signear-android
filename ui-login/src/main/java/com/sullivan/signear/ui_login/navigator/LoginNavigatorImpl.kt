package com.sullivan.signear.ui_login.navigator

import android.content.Context
import com.sullivan.signear.common.ex.launchActivity
import com.sullivan.signear.common.navigator.LoginNavigator
import com.sullivan.signear.ui_login.ui.LoginActivity
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor() : LoginNavigator {
    override fun openLogin(context: Context) {
        context.launchActivity<LoginActivity>()
    }
}