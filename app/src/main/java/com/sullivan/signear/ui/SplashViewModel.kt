package com.sullivan.signear.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.bentley.core.DataState
import com.bentley.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SplashViewModel @Inject
constructor(private val repository: SignearRepository) : ViewModel() {

    val rankList = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(DataState.Loading)
        try {
            repository.fetchRankInfo().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}