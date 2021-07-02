package com.sullivan.signear.ui_reservation.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.model.NewReservationRequest
import com.sullivan.signear.data.model.UserInfo
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
constructor(
    private val repository: SignearRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _reservationDate = MutableStateFlow<Calendar>(Calendar.getInstance())
    val reservationDate: StateFlow<Calendar> = _reservationDate

    private val reservationStartTime = MutableStateFlow("")
    private val reservationEndTime = MutableStateFlow("")
    private val reservationTime = MutableStateFlow("")
    private val reservationCenter = MutableStateFlow("")
    private val reservationPlace = MutableStateFlow("")
    private val reservationTranslationInfo = MutableStateFlow(1)
    private val reservationPurpose = MutableStateFlow("")

    private val _confirmDialogState = MutableLiveData<ReservationConfirmDialogState>()
    val confirmDialogState: LiveData<ReservationConfirmDialogState> = _confirmDialogState

    private val _reservationTotalInfo = MutableLiveData<NewReservationRequest?>()
    val reservationTotalInfo: LiveData<NewReservationRequest?> = _reservationTotalInfo

    var startHour = "00"
    var startMinute = "00"
    var endHour = "00"
    var endMinute = "00"

    private var reservationList = emptyList<Reservation>()
    private var prevreservationList = mutableListOf(
        Reservation(
            1,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원서초좋은병원서초좋은병원서초좋은병원",
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
        reservationTranslationInfo.value = if (isContactless) 2 else 1
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

        val currentReservation = NewReservationRequest(
            currentDate,
            startHour + startMinute,
            endHour + endMinute,
            reservationCenter.value,
            reservationPlace.value,
            reservationTranslationInfo.value,
            reservationPurpose.value,
            UserInfo(sharedPreferenceManager.getUserId())
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
        reservationTranslationInfo.value = 1
        reservationPurpose.value = ""

        startHour = "00"
        startMinute = "00"
        endHour = "00"
        endMinute = "00"
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

    fun updateStartT(hour: Int, minute: Int) {

        startHour = if (hour < 9) {
            "0$hour"
        } else {
            "$hour"
        }

        startMinute = if (minute < 9) {
            "0$minute"
        } else {
            "$minute"
        }
    }

    fun updateEndT(hour: Int, minute: Int) {
        endHour = if (hour < 9) {
            "0$hour"
        } else {
            "$hour"
        }

        endMinute = if (minute < 9) {
            "0$minute"
        } else {
            "$minute"
        }
    }
}