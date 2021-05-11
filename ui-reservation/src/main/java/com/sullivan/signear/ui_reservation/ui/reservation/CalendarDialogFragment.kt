package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.DialogFragment
import com.applandeo.materialcalendarview.utils.setSelectedDayColors
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentCalendarDialogBinding

class CalendarDialogFragment : DialogFragment(R.layout.fragment_calendar_dialog) {

    private var binding: FragmentCalendarDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarDialogBinding.bind(view)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupView() {
        binding?.apply {
            calendarView.apply {
                setSelectionBackground(R.drawable.selection_circle)
            }
        }
    }

    companion object {
        private var fragment: CalendarDialogFragment? = null

        @JvmStatic
        fun newInstance() =
            fragment ?: synchronized(this) {
                fragment ?: CalendarDialogFragment().also { fragment = it }
            }

        fun dismissInstance() {
            fragment = null
        }
    }

}