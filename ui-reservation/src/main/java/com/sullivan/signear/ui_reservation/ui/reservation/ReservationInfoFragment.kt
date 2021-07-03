package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sullivan.common.ui_common.base.BaseFragment
import com.sullivan.common.ui_common.ex.makeGone
import com.sullivan.common.ui_common.ex.makeVisible
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentReservationInfoBinding
import com.sullivan.signear.data.model.ReservationDetailInfo
import com.sullivan.signear.ui_reservation.model.MyReservation
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

@AndroidEntryPoint
class ReservationInfoFragment : BaseFragment<FragmentReservationInfoBinding>() {

    private val viewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var currentReservationInfo: ReservationDetailInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    override fun setupView() {
//        viewModel.fetchReservationDetail()
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearPrevData()
    }

    private fun setupObserve() {
        viewModel.reservationDetailInfo.observe(viewLifecycleOwner, { detailInfo ->
            currentReservationInfo = detailInfo
            makeReservationView()
            makeReservationStatusView()
        })
    }

    private fun makeReservationView() {
        binding.apply {
            tvPlace.text = currentReservationInfo.place
            tvCenter.text =
                "${currentReservationInfo.center} ${context?.getString(R.string.tv_center_title)}"
            tvReservationDate.text = convertDate(currentReservationInfo.date)
            getTimeInfo(tvReservationStartTime, currentReservationInfo.startTime.substring(0,2).toInt(), currentReservationInfo.startTime.substring(2).toInt())
            getTimeInfo(tvReservationEndTime, currentReservationInfo.endTime.substring(0,2).toInt(), currentReservationInfo.endTime.substring(2).toInt())
//            tvReservationStartTime.text = currentReservationInfo.startTime
//            tvReservationEndTime.text = currentReservationInfo.endTime

            if (currentReservationInfo.method == 1) {
                tvReservationTranslation.text =
                    context?.getString(R.string.fragment_reservation_tv_sign_translation_title)
                tvTranslation.text =
                    "(${context?.getString(R.string.fragment_reservation_tv_contact_title)})"
            } else {
                tvReservationTranslation.text =
                    context?.getString(R.string.fragment_reservation_tv_online_translation_title)
                tvTranslation.text =
                    "(${context?.getString(R.string.fragment_reservation_tv_online_title)})"
            }

            tvReservationPurpose.text = currentReservationInfo.request

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_reservationInfoFragment_pop)
            }

            btnCancel.setOnClickListener {
                showCancelDialog()
            }
        }
    }

    private fun makeReservationStatusView() {
        binding.apply {
            when (convertStatus()) {
                is ReservationState.NotRead -> {
                    statusNotRead.isSelected = true
                    statusNotConfirm.isSelected = false
                    statusConfirm.isSelected = false
                    ivFlag.isSelected = false

                    makeBlue(tvStatusNotRead)
                    makeGray(tvStatusNotConfirm)
                    makeGray(tvStatusConfirm)

                    makeUsualStatusView()
                }

                is ReservationState.NotConfirm -> {
                    statusNotRead.isSelected = false
                    statusNotConfirm.isSelected = true
                    statusConfirm.isSelected = false
                    ivFlag.isSelected = false

                    makeGray(tvStatusNotRead)
                    makeBlue(tvStatusNotConfirm)
                    makeGray(tvStatusConfirm)

                    makeUsualStatusView()
                }

                is ReservationState.Confirm -> {
                    statusNotRead.isSelected = false
                    statusNotConfirm.isSelected = false
                    statusConfirm.isSelected = true
                    ivFlag.isSelected = true

                    makeGray(tvStatusNotRead)
                    makeGray(tvStatusNotConfirm)
                    makeBlue(tvStatusConfirm)

                    makeUsualStatusView()
                }

                is ReservationState.Cancel -> {
                    makeCancelStatusView()
                }

                is ReservationState.Reject -> {
                    makeRejectStatusView()
                }
            }
        }
    }

    private fun makeGray(view: TextView) {
        view.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.reservation_status_disable_color
            )
        )
    }

    private fun makeBlue(view: TextView) {
        view.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.reservation_status_enable_color
            )
        )
    }

    private fun makeRed(view: TextView) {
        view.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.btn_reservation_cancel_color
            )
        )
    }

    private fun makeCancelStatusView() {
        binding.apply {
            tvStatusNotRead.makeGone()
            tvStatusNotConfirm.makeGone()
            tvStatusConfirm.makeGone()
            tvStatusCancelReject.apply {
                makeVisible()
                text = "에약 취소"
                makeGray(this)
            }

            statusNotRead.makeGone()
            statusNotConfirm.makeGone()
            statusConfirm.makeGone()
            statusCancelReject.apply {
                makeVisible()
                isSelected = false
            }

            btnCancel.makeGone()
        }
    }

    private fun makeRejectStatusView() {
        binding.apply {
            tvStatusNotRead.makeGone()
            tvStatusNotConfirm.makeGone()
            tvStatusConfirm.makeGone()
            tvStatusCancelReject.apply {
                makeVisible()
                text = "에약 거절"
                makeRed(this)
            }

            statusNotRead.makeGone()
            statusNotConfirm.makeGone()
            statusConfirm.makeGone()
            statusCancelReject.apply {
                makeVisible()
                isSelected = true
            }

            btnCancel.makeGone()
            showDialog()
        }
    }

    private fun makeUsualStatusView() {
        binding.apply {
            tvStatusNotRead.makeVisible()
            tvStatusNotConfirm.makeVisible()
            tvStatusConfirm.makeVisible()
            tvStatusCancelReject.makeGone()

            statusNotRead.makeVisible()
            statusNotConfirm.makeVisible()
            statusConfirm.makeVisible()
            statusCancelReject.makeGone()

            btnCancel.makeVisible()
        }
    }

    private fun showDialog() {
        val dialog = MaterialAlertDialogBuilder(
            requireContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setTitle("거절 사유")
            .setMessage(currentReservationInfo.reject)
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun showCancelDialog() {
        val dialog = MaterialAlertDialogBuilder(
            requireContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setTitle(R.string.fragment_reservation_info_dialog_reservation_cancel_title)
            .setMessage(R.string.fragment_reservation_info_dialog_reservation_cancel_body)
            .setPositiveButton(R.string.fragment_reservation_info_dialog_reservation_cancel_positive_btn_title) { dialog, _ ->
                dialog.dismiss()
                //todo 예약취소 작업 예정
                findNavController().navigate(R.id.action_reservationInfoFragment_pop)
            }
            .setNegativeButton(R.string.fragment_reservation_info_dialog_reservation_cancel_negative_btn_title) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun convertStatus(): ReservationState {
        return when (currentReservationInfo.status) {
            1 -> ReservationState.NotRead
            2 -> ReservationState.NotConfirm
            3 -> ReservationState.Confirm
            4 -> ReservationState.Cancel()
            5 -> ReservationState.Reject()
            else -> ReservationState.None
        }
    }

    private fun convertDate(date: String): String {
        val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val calendar = Calendar.getInstance()
        try {
            calendar.time = format.parse(date)!!

        } catch (e: ParseException) {
            Timber.e(e)
        }
        return "${calendar.get(Calendar.MONTH) + 1}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일 ${viewModel.getCurrentDayOfName(calendar)}"
    }

    private fun getTimeInfo(view: TextView, hour: Int, minute: Int) {
        Timber.d("hour: $hour")
        if (hour <= 12) {
            if (hour <= 9) {
                if (minute <= 9) {
                    "오전 0$hour:0$minute".also { view.text = it }
                } else {
                    "오전 0$hour:$minute".also { view.text = it }
                }
            } else {
                if (minute <= 9) {
                    "오전 $hour:0$minute".also { view.text = it }
                } else {
                    "오전 $hour:$minute".also { view.text = it }
                }
            }
        } else {
            if (hour <= 9) {
                if (minute <= 9) {
                    "오후 0${hour - 12}:0$minute".also { view.text = it }
                } else {
                    "오후 0${hour - 12}:$minute".also { view.text = it }
                }
            } else {
                if (minute <= 9) {
                    "오후 ${hour - 12}:0$minute".also { view.text = it }
                } else {
                    "오후 ${hour - 12}:$minute".also { view.text = it }
                }
            }
        }
    }

    companion object {
        const val ARGS_KEY = "itemId"
    }
}