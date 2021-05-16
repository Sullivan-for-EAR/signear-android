package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sullivan.sigenear.ui_reservation.databinding.ReservationConfirmDialogBinding

class ReservationConfirmDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ReservationConfirmDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReservationConfirmDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }

    private fun setupView() {
        binding.apply {

        }
    }

    companion object {
        private var fragment: ReservationConfirmDialogFragment? = null

        @JvmStatic
        fun newInstance() =
            fragment ?: synchronized(this) {
                fragment ?: ReservationConfirmDialogFragment().also { fragment = it }
            }

        fun dismissInstance() {
            fragment = null
        }
    }
}