package com.sullivan.signear.ui_reservation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.HomeFragmentBinding
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var reservationListAdapter: ReservationListAdapter

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
                ReservationListAdapter(
                    listOf(
                        Reservation(
                            "4월 30일(금)",
                            "오전 10시",
                            "오전 12시",
                            "강남구",
                            "서초좋은병원",
                            "",
                            ReservationState.Urgent
                        ),
                        Reservation(
                            "4월 30일(금)",
                            "오전 10시",
                            "오전 12시",
                            "강남구", "서초좋은병원", "",
                            ReservationState.NotConfirm
                        ),
                        Reservation(
                            "4월 30일(금)",
                            "오전 10시",
                            "오전 12시",
                            "강남구", "서초좋은병원", "",
                            ReservationState.Reject("reason")
                        ),
                        Reservation(
                            "4월 30일(금)",
                            "오전 10시",
                            "오전 12시",
                            "강남구",
                            "서초좋은병원",
                            "",
                            ReservationState.Confirm
                        ),
                        Reservation(
                            "4월 30일(금)",
                            "오전 10시",
                            "오전 12시",
                            "강남구",
                            "서초좋은병원",
                            "",
                            ReservationState.Cancel
                        ),
                        Reservation(
                            "4월 30일(금)", "오전 10시",
                            "오전 12시", "강남구", "서초좋은병원", ""
                        ),
                        Reservation(
                            "4월 30일(금)", "오전 10시",
                            "오전 12시", "강남구", "서초좋은병원", ""
                        ),
                        Reservation(
                            "4월 30일(금)", "오전 10시",
                            "오전 12시", "강남구", "서초좋은병원", ""
                        )
                    )
                )

            rvReservation.apply {
                setHasFixedSize(true)
                adapter = reservationListAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            }

            btnReservation.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_reservationFragment)
            }
        }
    }
}