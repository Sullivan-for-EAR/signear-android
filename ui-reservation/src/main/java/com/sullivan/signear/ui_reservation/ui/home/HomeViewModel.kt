package com.sullivan.signear.ui_reservation.ui.home

import androidx.lifecycle.*
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.model.ReservationData
import com.sullivan.signear.domain.SignearRepository
import com.sullivan.signear.ui_reservation.model.MyReservation
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationConfirmDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val repository: SignearRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _myReservationList = MutableLiveData<List<MyReservation>>()
    val myReservationList: LiveData<List<MyReservation>> =  _myReservationList

    fun getReservationList() {
        val id = sharedPreferenceManager.getUserId()
        viewModelScope.launch {
            repository.getReservationList(id).collect { response ->
                if (response.isNotEmpty()) {
                    _myReservationList.value = convertData(response)
                } else {
                    _myReservationList.value = emptyList()
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
}