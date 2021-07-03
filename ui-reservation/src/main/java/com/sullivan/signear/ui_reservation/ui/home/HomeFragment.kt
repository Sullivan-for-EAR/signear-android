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
import com.sullivan.common.ui_common.base.BaseFragment
import com.sullivan.common.ui_common.ex.makeGone
import com.sullivan.common.ui_common.ex.makeVisible
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.HomeFragmentBinding
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var reservationListAdapter : ReservationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)


        viewModel.getReservationList()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        observeViewModel()
    }

    override fun setupView() {
        binding.apply {
            btnReservation.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_reservationFragment)
            }

            ivProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_myPageFragment)
            }

            reservationListAdapter = ReservationListAdapter(mutableListOf(), sharedViewModel)
            rvReservation.apply {
                setHasFixedSize(true)
                adapter = reservationListAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            myReservationList.observe(viewLifecycleOwner, { list ->
                if (list.isNotEmpty()) {
                    reservationListAdapter.addAll(list.reversed())
                    with(binding) {
                        emptyReservationLayout.rootView.makeGone()
                        rvReservation.makeVisible()
                    }
                } else {
                    with(binding) {
                        rvReservation.makeGone()
                        emptyReservationLayout.rootView.makeVisible()
                    }
                }
            })
        }
    }
}