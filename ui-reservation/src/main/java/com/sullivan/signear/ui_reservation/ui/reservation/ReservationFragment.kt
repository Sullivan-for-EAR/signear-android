package com.sullivan.signear.ui_reservation.ui.reservation

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ReservationFragmentBinding
import com.sullivan.signear.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                CalendarDialogFragment.newInstance().show(ft, "calendar")
            }
        }
    }

}