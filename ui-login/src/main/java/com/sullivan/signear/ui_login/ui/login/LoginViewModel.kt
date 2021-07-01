package com.sullivan.signear.ui_login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.model.ResponseCheckEmail
import com.sullivan.signear.data.model.ResponseLogin
import com.sullivan.signear.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val repository: SignearRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Init)
    val loginState: LiveData<LoginState> = _loginState

    private val _resultCheckEmail = MutableLiveData<ResponseCheckEmail>()
    val resultCheckEmail: LiveData<ResponseCheckEmail> = _resultCheckEmail

    private val _resultLogin = MutableLiveData<ResponseLogin>()
    val resultLogin: LiveData<ResponseLogin> = _resultLogin

    private val _resultJoin = MutableLiveData<ResponseLogin>()
    val resultJoin: LiveData<ResponseLogin> = _resultJoin

    fun updateLoginState(currentState: LoginState) {
        _loginState.value = currentState
    }

    fun checkCurrentState() = _loginState.value

    fun checkEmail(email: String) {
        viewModelScope.launch {
            repository.checkEmail(email).collect {
                _resultCheckEmail.value = it
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect { response ->
                response.let {
                    with(sharedPreferenceManager) {
                        setAccessToken(response.accessToken)
//                        setUserName(response.userProfile.email)
//                        setUserPHONE(response.userProfile.phone)
                    }
                    _resultLogin.value = response
                }
            }
        }
    }

    fun createUser(email: String, password: String, phone: String) {
        viewModelScope.launch {
            repository.createUser(email, password, phone).collect { response ->
                response.let {
                    with(sharedPreferenceManager) {
                        setAccessToken(response.accessToken)
//                        setUserName(response.userProfile.email)
//                        setUserPHONE(response.userProfile.phone)
                    }
                    _resultJoin.value = response
                }
            }
        }
    }
}