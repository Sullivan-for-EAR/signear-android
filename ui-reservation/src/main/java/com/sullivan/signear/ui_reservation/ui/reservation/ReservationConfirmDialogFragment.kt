package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sullivan.sigenear.ui_reservation.databinding.ReservationConfirmDialogFragmentBinding
import com.sullivan.signear.ui_reservation.state.ReservationConfirmDialogState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class ReservationConfirmDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ReservationConfirmDialogFragmentBinding
    private val viewModel: ReservationSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReservationConfirmDialogFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserve()
    }

    override fun onResume() {
        super.onResume()

        dialog?.setOnKeyListener { _, _, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
            }
            false
        }
    }


    private fun setupView() {
        binding.apply {
            tvTime.text = viewModel.fetchReservationTime()

            btnView.setOnClickListener {
                viewModel.updateDialogStatus(ReservationConfirmDialogState.MoveToDetail)
                dismiss()
            }

            btnClose.setOnClickListener {
                viewModel.updateDialogStatus(ReservationConfirmDialogState.Dismiss)
                dismiss()
            }
        }
    }

    private fun setupObserve() {
        viewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                reservationDate.collect { calendar ->
                    "${calendar.get(Calendar.MONTH) + 1}월 ${
                        calendar.get(
                            Calendar.DAY_OF_MONTH
                        )
                    }일 ${
                        viewModel.getCurrentDayOfName(
                            calendar
                        )
                    }".also { binding.tvDate.text = it }
                }
            }
        }
    }

    override fun dismiss() {
        super.dismiss()

        if (dialog != null)
            dialog!!.dismiss()

        dismissInstance()

    }

    companion object {
        private var fragment: ReservationConfirmDialogFragment? = null

        @JvmStatic
        fun newInstance() =
            fragment ?: synchronized(this) {
                fragment ?: ReservationConfirmDialogFragment().also { fragment = it }
            }

        @JvmStatic
        fun dismissInstance() {
            fragment = null
        }
    }
}