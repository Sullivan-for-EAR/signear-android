package com.sullivan.ui_login.ui.login

sealed class LoginState {
    object Init : LoginState()
    object EmailValid : LoginState()
    object EmailNotValid : LoginState()
    data class PasswordNotValid (val errorCode : Int) : LoginState()
    object Sucess : LoginState()
}
