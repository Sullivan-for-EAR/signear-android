package com.sullivan.signear.ui_reservation.ui.mypage

import androidx.lifecycle.*
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

    val userInfo: LiveData<UserProfile> = liveData {
        val id = sharedPreferenceManager.getUserId()
        viewModelScope.launch {
            repository.getUserInfo(id).collect {
                emit(it)
            }
        }
    }
}