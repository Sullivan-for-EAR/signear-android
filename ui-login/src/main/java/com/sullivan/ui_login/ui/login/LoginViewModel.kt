package com.sullivan.ui_login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bentley.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(private val repository: SignearRepository) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Init)
    val loginState: LiveData<LoginState> = _loginState


    fun updateLoginState(currentState: LoginState) {
        _loginState.value = currentState
    }

    fun checkCurrentState() = _loginState.value
}