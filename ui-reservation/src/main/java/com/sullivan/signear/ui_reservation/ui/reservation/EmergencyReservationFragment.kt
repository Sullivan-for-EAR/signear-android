package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sullivan.common.ui_common.base.BaseFragment
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentEmergencyReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmergencyReservationFragment : BaseFragment<FragmentEmergencyReservationBinding>() {

    private val viewModel: ReservationSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmergencyReservationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupView() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_emergencyReservationFragment_pop)
            }

            btnNormal.setOnClickListener {
                findNavController().navigate(R.id.action_emergencyReservationFragment_to_reservationFragment)
            }

            btnEmergency.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.createEmergencyReservation()
                    delay(1_000)
                    findNavController().navigate(R.id.action_emergencyReservationFragment_to_homeFragment)
                }
            }
        }
    }

    override fun getProgressbarView(): ContentLoadingProgressBar = binding.progressbar
}