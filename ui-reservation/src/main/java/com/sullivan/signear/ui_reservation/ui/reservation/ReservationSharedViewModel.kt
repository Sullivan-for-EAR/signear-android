package com.sullivan.signear.ui_reservation.ui.reservation

import androidx.lifecycle.ViewModel
import com.sullivan.signear.domain.SignearRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReservationSharedViewModel @Inject
constructor(private val repository: SignearRepository) : ViewModel() {

    private val _reservationDate = MutableStateFlow<Calendar>(Calendar.getInstance())
    val reservationDate: StateFlow<Calendar> = _reservationDate

    private val reservationStartTime = MutableStateFlow("")
    private val reservationEndTime = MutableStateFlow("")
    private val reservationTime = MutableStateFlow("")
    private val reservationCenter = MutableStateFlow("")
    private val reservationPlace = MutableStateFlow("")
    private val reservationTranslationInfo = MutableStateFlow("")
    private val reservationPurpose = MutableStateFlow("")

    fun updateDate(current: Calendar) {
        _reservationDate.value = current
    }

    fun updateStartTime(startTime: String) {
        reservationStartTime.value = startTime
    }

    fun updateEndTime(endTime: String) {
        reservationEndTime.value = endTime
    }

    fun updateCenterInfo(centerInfo: String) {
        reservationCenter.value = centerInfo
    }

    fun updatePlaceInfo(placeInfo: String) {
        reservationPlace.value = placeInfo
    }

    fun updateTranslationInfo(translationInfo: String) {
        reservationTranslationInfo.value = translationInfo
    }

    fun updatePurpose(purposeInfo: String) {
        reservationPurpose.value = purposeInfo
    }

    fun fetchReservationTime(): String {
        reservationTime.value = "${reservationStartTime.value}부터 ${reservationEndTime.value}까지"
        return reservationTime.value
    }

    fun getCurrentDayOfName(calendar: Calendar): String {
        val date = calendar.time
        val simpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
}