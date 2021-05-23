package com.sullivan.signear.ui_reservation.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sullivan.signear.domain.SignearRepository
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationConfirmDialogState
import com.sullivan.signear.ui_reservation.state.ReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
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
    private val reservationTranslationInfo = MutableStateFlow(false)
    private val reservationPurpose = MutableStateFlow("")

    private val _confirmDialogState = MutableLiveData<ReservationConfirmDialogState>()
    val confirmDialogState: LiveData<ReservationConfirmDialogState> = _confirmDialogState

    private val _reservationTotalInfo = MutableLiveData<Reservation?>()
    val reservationTotalInfo: LiveData<Reservation?> = _reservationTotalInfo

    private var reservationList = emptyList<Reservation>()
    private var prevreservationList = mutableListOf(
        Reservation(
            1,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원",
            "",
            false,
            ReservationState.Cancel("reason")
        ),
        Reservation(
            2,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구", "서초좋은병원", "",
            false,
            ReservationState.Cancel("reason"),
            "reason",
            true
        ),
        Reservation(
            3,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구", "서초좋은병원", "",
            false,
            ReservationState.Reject("reason")
        ),
        Reservation(
            4,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원",
            "",
            false,
            ReservationState.Served
        ),
        Reservation(
            5,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원",
            "",
            false,
            ReservationState.Cancel("reason")
        ),
    )

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

    fun updateTranslationInfo(isContactless: Boolean) {
        reservationTranslationInfo.value = isContactless
    }

    fun updatePurpose(purposeInfo: String) {
        reservationPurpose.value = purposeInfo
    }

    fun updateDialogStatus(status: ReservationConfirmDialogState) {
        _confirmDialogState.value = status
    }

    fun fetchReservationTime(): String {
        reservationTime.value = "${reservationStartTime.value}부터 ${reservationEndTime.value}까지"
        return reservationTime.value
    }

    fun assembleReservationInfo() {
        val calendar = _reservationDate.value
        val currentDate =
            "${calendar.get(Calendar.MONTH) + 1}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일 ${
                getCurrentDayOfName(calendar)
            }"

        val currentReservation = Reservation(
            0,
            currentDate,
            reservationStartTime.value,
            reservationEndTime.value,
            reservationCenter.value,
            reservationPlace.value,
            reservationPurpose.value,
            reservationTranslationInfo.value
        )

        _reservationTotalInfo.value = currentReservation
        Timber.d("${reservationTotalInfo.value}")
    }


    fun getCurrentDayOfName(calendar: Calendar): String {
        val date = calendar.time
        val simpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    fun clearPrevData() {
        _reservationTotalInfo.value = null
        reservationStartTime.value = ""
        reservationEndTime.value = ""
        reservationTime.value = ""
        reservationCenter.value = ""
        reservationPlace.value = ""
        reservationTranslationInfo.value = false
        reservationPurpose.value = ""
    }

    fun updateReservationList(list: List<Reservation>) {
        reservationList = list
    }

    fun findItemWithId(id: Int) = reservationList.find { it.id == id }

    fun updatePrevReservationList(list: MutableList<Reservation>) {
        prevreservationList = list
    }

    fun findItemWithIdInPrevList(id: Int) = prevreservationList.find { it.id == id }

    fun fetchPrevList() = prevreservationList
}