package com.sullivan.signear.ui_reservation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.model.UserProfile
import com.sullivan.signear.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject
constructor(
    private val repository: SignearRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _userInfo = MutableLiveData<UserProfile>()
    val userInfo: LiveData<UserProfile> = _userInfo

    init {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {
        val id = sharedPreferenceManager.getUserId()
        viewModelScope.launch {
            repository.getUserInfo(id).collect {
                _userInfo.value = it
            }
        }
    }
}