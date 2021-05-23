package com.sullivan.signear.ui_reservation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.HomeFragmentBinding
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var reservationListAdapter: ReservationListAdapter
    private val reservationList = listOf(
        Reservation(
            1,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원",
            "",
            false,
            ReservationState.NotConfirm,
            "",
            true
        ),
        Reservation(
            2,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구", "서초좋은병원", "",
            false,
            ReservationState.NotConfirm
        ),
        Reservation(
            3,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구", "서초좋은병원", "",
            false,
            ReservationState.Reject("reason")
        ),
        Reservation(
            4,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원",
            "",
            false,
            ReservationState.Confirm
        ),
        Reservation(
            5,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구",
            "서초좋은병원",
            "",
            false,
            ReservationState.Cancel("reason")
        ),
        Reservation(
            6,
            "4월 30일(금)", "오전 10시",
            "오전 12시", "강남구", "서초좋은병원", ""
        ),
        Reservation(
            7,
            "4월 30일(금)", "오전 10시",
            "오전 12시", "강남구", "서초좋은병원", ""
        ),
        Reservation(
            8,
            "4월 30일(금)", "오전 10시",
            "오전 12시", "강남구", "서초좋은병원", ""
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupView() {
        binding.apply {
            reservationListAdapter =
                ReservationListAdapter(reservationList)
            sharedViewModel.updateReservationList(reservationList)

            rvReservation.apply {
                setHasFixedSize(true)
                adapter = reservationListAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            }

            btnReservation.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_reservationFragment)
            }

            ivProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_myPageFragment)
            }
        }
    }
}