package com.sullivan.signear.ui_reservation.ui.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ReservationFragmentBinding
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.common.ex.openDialog
import com.sullivan.signear.ui_reservation.state.ReservationConfirmDialogState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReservationFragment : BaseFragment<ReservationFragmentBinding>() {

    private val viewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var centerArray: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReservationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupObserve()
    }

    override fun setupView() {
        binding.apply {
            centerArray = resources.getStringArray(R.array.center_array)
            spCenter.setItems(centerArray.toList())

            rlSignTranslation.isSelected = true
            rlOnlineTranslation.isSelected = !rlSignTranslation.isSelected

            rlSignTranslation.setOnClickListener {
                rlSignTranslation.isSelected = !rlSignTranslation.isSelected
                rlOnlineTranslation.isSelected = !rlSignTranslation.isSelected
                if (it.isSelected) {
                    tvSignTranslation.typeface = Typeface.DEFAULT_BOLD
                    tvOnlineTranslation.typeface = Typeface.DEFAULT
                    viewModel.updateTranslationInfo(false)
                }
            }

            rlOnlineTranslation.setOnClickListener {
                rlOnlineTranslation.isSelected = !rlOnlineTranslation.isSelected
                rlSignTranslation.isSelected = !rlOnlineTranslation.isSelected
                if (it.isSelected) {
                    tvOnlineTranslation.typeface = Typeface.DEFAULT_BOLD
                    tvSignTranslation.typeface = Typeface.DEFAULT
                    viewModel.updateTranslationInfo(true)
                }
            }

            btnCalendar.setOnClickListener {
                openDatePickerSpinner()
            }

            btnCalendar.apply {
                val calendar = Calendar.getInstance()

                "${calendar.get(Calendar.MONTH) + 1}월 ${
                    calendar.get(
                        Calendar.DAY_OF_MONTH
                    )
                }일 ${
                    viewModel.getCurrentDayOfName(
                        calendar
                    )
                }".also { text = it }
            }

            btnStartTime.apply {
                setOnClickListener {
                    openStartTimePicker()
                }
                viewModel.updateStartTime(text.toString())
            }

            btnEndTime.apply {
                setOnClickListener {
                    openEndTimePicker()
                }
                viewModel.updateEndTime(text.toString())
            }

            btnCenter.setOnClickListener {
                spCenter.expand()
            }

            btnReservation.setOnClickListener {
                showDialog()
            }

            spCenter.apply {
                viewModel.updateCenterInfo(this.text.toString())
                setOnItemSelectedListener { _, _, _, item ->
                    viewModel.updateCenterInfo(item.toString())
                }
            }

            btnBack.setOnClickListener {
                moveToHome()
            }
        }
    }

    private fun setupObserve() {
        viewModel.apply {
            confirmDialogState.observe(viewLifecycleOwner, { status ->
                when (status) {
                    is ReservationConfirmDialogState.MoveToDetail -> {
                        moveToConfirmInfo()
                        viewModel.updateDialogStatus(ReservationConfirmDialogState.Init)
                    }
                    is ReservationConfirmDialogState.Dismiss -> {
                        moveToHome()
                        viewModel.updateDialogStatus(ReservationConfirmDialogState.Init)
                    }
                    else -> {
                    }
                }
            })
        }
    }

    private fun openDatePickerSpinner() {
        val calendar = Calendar.getInstance()

        val dialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, monthOfYear, day ->

                val month = monthOfYear + 1
                calendar.set(year, monthOfYear, day)
                viewModel.updateDate(calendar)
                "${month}월 ${day}일 ${viewModel.getCurrentDayOfName(calendar)}".also {
                    binding.btnCalendar.text = it
                }
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
            minDate = System.currentTimeMillis() - 1000
        }

        dialog.show()
    }

    private fun openStartTimePicker() {
        val dialog = TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialog,
            { _, hourOfDay, minute ->
                getTimeInfo(binding.btnStartTime, hourOfDay, minute)
                viewModel.updateStartTime(binding.btnStartTime.text.toString())
            },
            0,
            0,
            false
        )
        dialog.show()
    }

    private fun openEndTimePicker() {
        val dialog = TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialog,
            { _, hourOfDay, minute ->
                getTimeInfo(binding.btnEndTime, hourOfDay, minute)
                viewModel.updateEndTime(binding.btnEndTime.text.toString())
            },
            0,
            0,
            false
        )
        dialog.apply {

        }
        dialog.show()
    }


    private fun getTimeInfo(view: TextView, hour: Int, minute: Int) {
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

    private fun setupTextWatcher() {
        binding.apply {
            etPlace.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val input = s.toString().trim()
                    if (input.isNotEmpty()) {
                        viewModel.updatePlaceInfo(input)
                    }
                }
            })

            etPurpose.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val input = s.toString().trim()
                    if (input.isNotEmpty()) {
                        viewModel.updatePurpose(input)
                    }
                }
            })
        }
    }

    private fun showDialog() {
        viewModel.assembleReservationInfo()
        openDialog(ReservationConfirmDialogFragment.newInstance(), "")
    }

    private fun moveToConfirmInfo() {
        findNavController().navigate(R.id.action_reservationFragment_to_reservationInfoFragment)
    }

    private fun moveToHome() {
        findNavController().navigate(R.id.action_reservationFragment_pop)
    }
}