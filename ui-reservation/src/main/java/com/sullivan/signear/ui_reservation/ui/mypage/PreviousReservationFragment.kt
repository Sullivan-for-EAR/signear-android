package com.sullivan.signear.ui_reservation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentPreviousReservationBinding
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel

class PreviousReservationFragment : BaseFragment<FragmentPreviousReservationBinding>() {

    private val viewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var reservationListAdapter: PreviousReservationListAdapter
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
            ReservationState.Cancel("reason")
        ),
        Reservation(
            2,
            "4월 30일(금)",
            "오전 10시",
            "오전 12시",
            "강남구", "서초좋은병원", "",
            false,
            ReservationState.Served
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
            ReservationState.Served
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
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviousReservationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupView() {

        reservationListAdapter =
            PreviousReservationListAdapter(reservationList.toMutableList())

        viewModel.updatePrevReservationList(reservationList)

        binding.apply {
            rvReservation.apply {
                setHasFixedSize(true)
                adapter = reservationListAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_previousReservationFragment_pop)
            }
        }

        val id = arguments?.getInt(ARGS_KEY)
        if(id != null) {
            reservationListAdapter.remove(id)
        }
    }

    companion object {
        private const val ARGS_KEY = "itemId"
    }
}