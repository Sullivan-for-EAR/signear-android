package com.sullivan.signear.ui_login.ui.login

sealed class LoginState {
    object Init : LoginState()
    object EmailValid : LoginState()
    object JoinMember : LoginState()
    object FindAccount: LoginState()
    object Success : LoginState()
    object JoinSuccess : LoginState()
}
