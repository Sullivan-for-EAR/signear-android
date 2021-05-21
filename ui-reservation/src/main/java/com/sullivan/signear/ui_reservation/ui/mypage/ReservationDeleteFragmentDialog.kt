package com.sullivan.signear.ui_reservation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentDialogReservationDeleteBinding
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationInfoFragment
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
        }
    }

    override fun onResume() {
        super.onResume()
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    companion object {
        private var fragment: ReservationDeleteFragmentDialog? = null
        private const val ARGS_KEY = "itemId"

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