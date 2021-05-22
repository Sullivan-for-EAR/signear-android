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
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentDialogReservationDeleteBinding
import com.sullivan.signear.common.ex.makeGone
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationConfirmDialogFragment
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationDeleteFragmentDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDialogReservationDeleteBinding
    private val viewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var currentReservationInfo: Reservation

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
    }

    private fun setupView() {
        binding.apply {
            val id = arguments?.getInt(ARGS_KEY)
            if (id != null) {
                currentReservationInfo = id.let { viewModel.findItemWithIdInPrevList(it)!! }
                makeReservationView()

                btnDelete.setOnClickListener {
                    findNavController().navigate(
                        ReservationDeleteFragmentDialogDirections.actionReservationDeleteFragmentDialogToPreviousReservationFragment(
                            id
                        )
                    )
                }
            }
        }
    }

    private fun makeReservationView() {
        binding.apply {
            tvPlace.text = currentReservationInfo.place
            tvCenter.text = currentReservationInfo.center + "수어통역센터"
            tvReservationDate.text = currentReservationInfo.date
            tvReservationStartTime.text = currentReservationInfo.startTime
            tvReservationEndTime.text = currentReservationInfo.endTime

            if (!currentReservationInfo.isContactless) {
                tvReservationTranslation.text = "수어통역"
                tvTranslation.text = "(대면)"
            } else {
                tvReservationTranslation.text = "화상통역"
                tvTranslation.text = "(비대면)"
            }

            tvReservationPurpose.text = currentReservationInfo.purpose

            btnClose.setOnClickListener {
                findNavController().navigate(R.id.action_reservationDeleteFragmentDialog_pop)
            }

            showReservationState(currentReservationInfo.currentState, ivReservationState)
        }
    }

    private fun showReservationState(currentState: ReservationState, ivState: ImageView) {
        when (currentState) {
            is ReservationState.Served -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.served_icon, null
                )
            )
            is ReservationState.Cancel -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.cancel_icon, null
                )
            )
            is ReservationState.Reject -> ivState.setImageDrawable(
                ResourcesCompat.getDrawable(
                    ivState.context.resources,
                    R.drawable.reject_icon, null
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

        private var fragment: ReservationDeleteFragmentDialog? = null

        @JvmStatic
        fun newInstance() =
            fragment ?: synchronized(this) {
                fragment ?: ReservationDeleteFragmentDialog().also { fragment = it }
            }

        @JvmStatic
        fun dismissInstance() {
            fragment = null
        }
    }
}