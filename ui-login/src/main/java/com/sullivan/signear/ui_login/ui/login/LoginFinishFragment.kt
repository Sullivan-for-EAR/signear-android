package com.sullivan.signear.ui_login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.common.navigator.ReservationNavigator
import com.sullivan.signear.ui_login.databinding.FragmentLoginFinishBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class LoginFinishFragment : BaseFragment<FragmentLoginFinishBinding>() {

    @Inject
    lateinit var reservationNavigator: ReservationNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginFinishBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupView() {
        binding.btnLogin.setOnClickListener {
            moveToMainScreen()
        }
    }

    private fun moveToMainScreen() {
        lifecycleScope.launchWhenCreated {
            delay(1_000)
            reservationNavigator.openReservationHome(requireContext())
        }
    }
}