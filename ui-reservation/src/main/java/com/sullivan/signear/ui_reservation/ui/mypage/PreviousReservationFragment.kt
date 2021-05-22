package com.sullivan.signear.ui_reservation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentPreviousReservationBinding
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.ui_reservation.ui.reservation.ReservationSharedViewModel

class PreviousReservationFragment : BaseFragment<FragmentPreviousReservationBinding>() {

    private val viewModel: ReservationSharedViewModel by activityViewModels()
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
    }

    override fun setupView() {
        reservationListAdapter =
            PreviousReservationListAdapter(
                viewModel.fetchPrevList(),
                viewModel,
                swipeHelperCallback
            )
        val id = arguments?.getInt(ARGS_KEY)
        if (id != null) {
            reservationListAdapter.remove(id)
        }

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

    companion object {
        private const val ARGS_KEY = "itemId"
    }
}