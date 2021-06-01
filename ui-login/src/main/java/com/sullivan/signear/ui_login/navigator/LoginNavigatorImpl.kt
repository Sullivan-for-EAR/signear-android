package com.sullivan.signear.ui_login.navigator

import android.app.Activity
import android.content.Context
import com.sullivan.common.ui_common.ex.launchActivity
import com.sullivan.common.ui_common.navigator.LoginNavigator
import com.sullivan.signear.ui_login.ui.LoginActivity
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor() : LoginNavigator {
    override fun openLogin(activity: Activity) {
        activity.launchActivity<LoginActivity>()
    }
}