package com.sullivan.signear.ui_reservation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sullivan.common.ui_common.ex.convertDate
import com.sullivan.common.ui_common.ex.getTimeInfo
import com.sullivan.common.ui_common.ex.makeGone
import com.sullivan.common.ui_common.ex.makeVisible
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentDialogReservationDeleteBinding
import com.sullivan.signear.data.model.ReservationDetailInfo
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationConfirmDialogFragment
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationInfoFragment.Companion.ARGS_KEY
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReservationDeleteFragmentDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDialogReservationDeleteBinding
    private val viewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var currentReservationInfo: ReservationDetailInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogReservationDeleteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupBackKeyEvent()
        observeViewModel()
    }

    private fun setupView() {
        val id = arguments?.getInt(ARGS_KEY)
        if (id != null) {
            viewModel.updateReservationID(id)
            viewModel.fetchReservationDetail()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        reservationDetailInfo.observe(viewLifecycleOwner, { detailInfo ->
            currentReservationInfo = detailInfo
            makeReservationView()
        })
    }

    private fun makeReservationView() {
        binding.apply {
            if (currentReservationInfo.status >= 8) {
                tvPlace.text =
                    tvPlace.context.getString(R.string.fragment_emergency_reservation_title)

                ivTranslation.makeGone()
                tvTranslationGuideMsg.makeGone()
                border2.makeGone()
                border3.makeGone()
                ivPurpose.makeGone()
                tvPurpose.makeGone()
                tvReservationPurpose.makeGone()
            } else {
                tvPlace.text = currentReservationInfo.place
                ivTranslation.makeVisible()
                tvTranslationGuideMsg.makeVisible()
                border2.makeVisible()
                border3.makeVisible()
                ivPurpose.makeVisible()
                tvPurpose.makeVisible()
                tvReservationPurpose.makeVisible()

                if (currentReservationInfo.method == 1) {
                    tvReservationTranslation.text =
                        getString(R.string.fragment_reservation_tv_sign_translation_title)
                    tvTranslation.text = "(${R.string.fragment_reservation_tv_contact_title})"
                } else {
                    tvReservationTranslation.text =
                        getString(R.string.fragment_reservation_tv_online_translation_title)
                    tvTranslation.text = "(${R.string.fragment_reservation_tv_online_title})"
                }

                tvReservationPurpose.text = currentReservationInfo.request
            }

            tvCenter.text =
                "${currentReservationInfo.center} ${context?.getString(R.string.tv_center_title)}"
            tvReservationDate.text = currentReservationInfo.date.convertDate()
            tvReservationStartTime.text = currentReservationInfo.startTime.getTimeInfo()
            tvReservationEndTime.text = currentReservationInfo.endTime.getTimeInfo()

            btnClose.setOnClickListener {
                findNavController().navigate(R.id.action_reservationDeleteFragmentDialog_pop)
            }

            btnDelete.setOnClickListener {
                viewModel.removePrevReservation(currentReservationInfo.id)
                findNavController().navigate(R.id.action_reservationDeleteFragmentDialog_to_previousReservationFragment)
            }

            showReservationState(currentReservationInfo.status, ivReservationState)
        }
    }

    private fun showReservationState(status: Int, ivState: ImageView) {
        when (status) {
            7 -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.served_icon, null
                )
            )
            4 -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.cancel_icon, null
                )
            )
            5 -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.reject_icon, null
                )
            )
            9 -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.cancel_icon, null
                )
            )
            10 -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.served_icon, null
                )
            )
            else -> ivState.makeGone()
        }
    }

    override fun onResume() {
        super.onResume()
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupBackKeyEvent() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_reservationDeleteFragmentDialog_pop)
                }
            })
    }

    companion object {
        private const val ARGS_KEY = "itemId"
    }
}