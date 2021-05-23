package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentEmergencyReservationBinding
import com.sullivan.signear.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmergencyReservationFragment : BaseFragment<FragmentEmergencyReservationBinding>() {

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
                //todo 긴급 예약 신청 작업 예정
                findNavController().navigate(R.id.action_emergencyReservationFragment_to_homeFragment)
            }
        }
    }
}