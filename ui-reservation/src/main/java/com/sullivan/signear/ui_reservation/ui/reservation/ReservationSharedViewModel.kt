package com.sullivan.signear.ui_reservation.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.common.ui_common.ex.day
import com.sullivan.common.ui_common.ex.month
import com.sullivan.common.ui_common.ex.year
import com.sullivan.common.ui_common.utils.SharedPreferenceManager
import com.sullivan.signear.data.model.NewEmergencyReservationRequest
import com.sullivan.signear.data.model.NewReservationRequest
import com.sullivan.signear.data.model.ReservationDetailInfo
import com.sullivan.signear.data.model.UserInfo
import com.sullivan.signear.domain.SignearRepository
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationConfirmDialogState
import com.sullivan.signear.ui_reservation.state.ReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Year
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
    private val reservationId = MutableStateFlow(0)
    private val emergencyReservationId = MutableStateFlow(0)

    private val _confirmDialogState = MutableLiveData<ReservationConfirmDialogState>()
    val confirmDialogState: LiveData<ReservationConfirmDialogState> = _confirmDialogState

    private val reservationTotalInfo = MutableLiveData<NewReservationRequest?>()
    private val emergencyReservationInfo = MutableLiveData<NewReservationRequest?>()

    private val _reservationDetailInfo = MutableLiveData<ReservationDetailInfo>()
    val reservationDetailInfo: LiveData<ReservationDetailInfo> = _reservationDetailInfo

    private val _reservationCanCelResponse = MutableLiveData<ReservationDetailInfo?>()
    val reservationCanCelResponse: LiveData<ReservationDetailInfo?> = _reservationCanCelResponse

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    var date = ""
    var startHour = "09"
    var startMinute = "00"
    var endHour = "10"
    var endMinute = "00"


    private fun createNewReservation() {
        viewModelScope.launch {
            repository.createNewReservation(reservationTotalInfo.value!!)
                .catch { exception ->
                    _errorMsg.value = "예약 취소가 실패했습니다."
                    Timber.e(exception)
                }
                .collect { response ->
                reservationId.value = response.id
            }
        }
    }

    fun fetchReservationDetail() {
        viewModelScope.launch {
            repository.getReservationDetailInfo(reservationId.value).collect { response ->
                _reservationDetailInfo.value = response
            }
        }
    }

    fun cancelReservation() {
        viewModelScope.launch {
            repository.cancelReservation(reservationId.value)
                .catch { exception ->
                    _errorMsg.value = "예약 취소가 실패했습니다."
                    Timber.e(exception)
                }
                .collect { response ->
                    _reservationCanCelResponse.value = response
                }
        }
    }

    fun createEmergencyReservation() {

        val calendar = Calendar.getInstance()
        val newEmergencyReservation = NewEmergencyReservationRequest(
            convertDate(calendar),
            convertStartTime(calendar),
            UserInfo(sharedPreferenceManager.getUserId())
        )

        viewModelScope.launch {
            repository.createEmergencyReservation(newEmergencyReservation)
                .collect { response ->
                    sharedPreferenceManager.setEmergencyReservationID(response.id)
                }
        }
    }

    fun cancelEmergencyReservation() {
        val id = sharedPreferenceManager.getEmergencyReservationID()
        viewModelScope.launch {
            repository.cancelEmergencyReservation(id)
                .catch { exception ->
                    _errorMsg.value = "예약 취소가 실패했습니다."
                    Timber.e(exception)
                }
                .collect { response ->
//                    _reservationCanCelResponse.value = response
                }
        }
    }


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

    fun updateReservationID(id: Int) {
        reservationId.value = id
    }

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

        val currentReservation = NewReservationRequest(
            convertDate(reservationDate.value),
            startHour + startMinute,
            endHour + endMinute,
            reservationCenter.value,
            reservationPlace.value,
            reservationTranslationInfo.value,
            reservationPurpose.value,
            UserInfo(sharedPreferenceManager.getUserId())
        )

        reservationTotalInfo.value = currentReservation

        createNewReservation()
    }


    fun getCurrentDayOfName(calendar: Calendar): String {
        val date = calendar.time
        val simpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    fun clearPrevData() {
        reservationTotalInfo.value = null
        reservationStartTime.value = ""
        reservationEndTime.value = ""
        reservationTime.value = ""
        reservationCenter.value = ""
        reservationPlace.value = ""
        reservationTranslationInfo.value = 1
        reservationPurpose.value = ""
        _reservationCanCelResponse.value = null

        startHour = "00"
        startMinute = "00"
        endHour = "00"
        endMinute = "00"
    }

    fun updatePrevReservationList(list: MutableList<Reservation>) {
        prevreservationList = list
    }

    fun findItemWithIdInPrevList(id: Int) = prevreservationList.find { it.id == id }

    fun fetchPrevList() = prevreservationList

    fun updateStartT(hour: Int, minute: Int) {

        startHour = if (hour <= 9) {
            "0$hour"
        } else {
            "$hour"
        }

        startMinute = if (minute <= 9) {
            "0$minute"
        } else {
            "$minute"
        }
    }

    fun updateEndT(hour: Int, minute: Int) {
        endHour = if (hour <= 9) {
            "0$hour"
        } else {
            "$hour"
        }

        endMinute = if (minute <= 9) {
            "0$minute"
        } else {
            "$minute"
        }
    }

    private fun convertDate(input: Calendar): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date(input.timeInMillis))
    }

    private fun convertStartTime(input: Calendar): String {
        val sdf = SimpleDateFormat("HHmm", Locale.getDefault())
        return sdf.format(Date(input.timeInMillis))
    }
}