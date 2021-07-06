package com.sullivan.signear.ui_reservation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.common.ui_common.base.BaseFragment
import com.sullivan.common.ui_common.ex.makeGone
import com.sullivan.common.ui_common.ex.makeVisible
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentPreviousReservationBinding
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviousReservationFragment : BaseFragment<FragmentPreviousReservationBinding>() {

    private val viewModel: PreviousReservationListViewModel by viewModels()
    private val sharedViewModel: ReservationSharedViewModel by activityViewModels()
    private lateinit var reservationListAdapter: PreviousReservationListAdapter
    private val swipeHelperCallback = SwipeHelperCallback().apply {
        setClamp(220f)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviousReservationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackKeyEvent()
        observeViewModel()
    }

    override fun setupView() {
        reservationListAdapter =
            PreviousReservationListAdapter(
                mutableListOf(),
                viewModel,
                swipeHelperCallback
            )

        binding.apply {
            val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
            itemTouchHelper.attachToRecyclerView(rvReservation)

            rvReservation.apply {
                setHasFixedSize(true)
                adapter = reservationListAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_previousReservationFragment_to_myPageFragment)
            }
        }
    }

    private fun setupBackKeyEvent() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_previousReservationFragment_to_myPageFragment)
                }
            })
    }

    private fun observeViewModel() {
        viewModel.myPrevReservationList.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                with(binding) {
                    rvReservation.makeVisible()
                    tvEmptyList.makeGone()
                    ivPrevReservation.makeGone()
                }

                reservationListAdapter.addAll(list.reversed())

            } else {
                with(binding) {
                    rvReservation.makeGone()
                    tvEmptyList.makeVisible()
                    ivPrevReservation.makeVisible()
                }
            }
        })

        sharedViewModel.refreshList.observe(viewLifecycleOwner, { status ->
            if (status) {
                viewModel.getPrevReservationList()
                sharedViewModel.clearRefreshList()
            }
        })
    }
}