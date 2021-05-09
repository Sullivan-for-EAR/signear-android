package com.sullivan.signear.ui_reservation.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
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
        }
    }

}