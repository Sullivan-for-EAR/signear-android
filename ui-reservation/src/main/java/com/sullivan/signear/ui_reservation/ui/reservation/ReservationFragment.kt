package com.sullivan.signear.ui_reservation.ui.reservation

import android.app.DatePickerDialog
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ReservationFragmentBinding
import com.sullivan.signear.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class ReservationFragment : BaseFragment<ReservationFragmentBinding>() {

    private val viewModel: ReservationViewModel by viewModels()
    private val centerArray = arrayOf("강남구", "마포구")
    private lateinit var centerAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReservationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setupView() {
        binding.apply {
            spCenter.setItems(centerArray.toList())

            rlSignTranslation.isSelected = true
            rlOnlineTranslation.isSelected = !rlSignTranslation.isSelected

            rlSignTranslation.setOnClickListener {
                rlSignTranslation.isSelected = !rlSignTranslation.isSelected
                rlOnlineTranslation.isSelected = !rlSignTranslation.isSelected
                if (it.isSelected) {
                    tvSignTranslation.typeface = Typeface.DEFAULT_BOLD
                    tvOnlineTranslation.typeface = Typeface.DEFAULT
                }
            }

            rlOnlineTranslation.setOnClickListener {
                rlOnlineTranslation.isSelected = !rlOnlineTranslation.isSelected
                rlSignTranslation.isSelected = !rlOnlineTranslation.isSelected
                if (it.isSelected) {
                    tvOnlineTranslation.typeface = Typeface.DEFAULT_BOLD
                    tvSignTranslation.typeface = Typeface.DEFAULT
                }
            }

            btnCalendar.setOnClickListener {
//                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//                CalendarDialogFragment.newInstance().show(ft, "calendar")
                openDatePickerSpinner()
            }
        }
    }

    private fun openDatePickerSpinner() {
        val calendar = Calendar.getInstance()
        var date: LocalDate

//        val selectedDate = viewModel.getSelectedDate()
//        if (selectedDate != null) {
//            calendar.set(selectedDate.year, selectedDate.month - 1, 1)
//        }

        val monthArray = arrayOf("1월, 2월, 3월, 4월, 5월, 6월, 7월, 8월, 9월, 10월, 11월, 12월")

        val dialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, month, day ->
                date = LocalDate.of(year, month + 1, day)
//                binding?.calendarView?.setCurrentDate(date)
                binding.btnCalendar.text = "${month + 1}월 ${day}일"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialog.datePicker.apply {
            calendarViewShown = false
            findViewById<View>(
                Resources.getSystem().getIdentifier("year", "id", "android")
            ).isVisible = false
        }

        dialog.show()
    }

}