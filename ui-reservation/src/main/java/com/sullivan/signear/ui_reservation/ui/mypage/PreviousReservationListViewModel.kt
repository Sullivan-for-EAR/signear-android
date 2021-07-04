package com.sullivan.signear.ui_reservation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.model.ReservationData
import com.sullivan.signear.domain.SignearRepository
import com.sullivan.signear.ui_reservation.model.MyReservation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviousReservationListViewModel @Inject
constructor(
    private val repository: SignearRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _myPrevReservationList = MutableLiveData<List<MyReservation>>()
    val myPrevReservationList: LiveData<List<MyReservation>> = _myPrevReservationList

    init {
        getPrevReservationList()
    }

    fun getPrevReservationList() {
        val id = sharedPreferenceManager.getUserId()
        viewModelScope.launch {
            repository.getPrevReservationList(id).collect { response ->
                if (response.isNotEmpty()) {
                    _myPrevReservationList.value = convertData(response)
                } else {
                    _myPrevReservationList.value = emptyList()
                }
            }
        }
    }

    private fun convertData(reservationList: List<ReservationData>): List<MyReservation> {
        val myList = mutableListOf<MyReservation>()

        reservationList.forEach { data ->
            myList.add(
                MyReservation(
                    data.id,
                    data.date,
                    data.startTime,
                    data.endTime,
                    data.center,
                    data.place,
                    data.status,
                    data.method != 1
                )
            )
        }
        return myList
    }

    fun removePrevReservation(id: Int) {
        viewModelScope.launch {
            repository.removePrevReservation(id)
            delay(1_000)
            getPrevReservationList()
        }
    }
}