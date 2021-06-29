package com.sullivan.signear.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sullivan.common.core.DataState
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SplashViewModel @Inject
constructor(
    private val repository: SignearRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    val rankList = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(DataState.Loading)
        try {
            repository.fetchRankInfo().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
            Timber.e(DataState.Error(e).toString())
        }
    }

    fun checkAccessToken() = !sharedPreferenceManager.getAccessToken().isNullOrEmpty()
}