package com.sullivan.signear.ui_login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.ui_login.R
import com.sullivan.signear.ui_login.databinding.FragmentLoginStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginStartFragment : BaseFragment<FragmentLoginStartBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToLoginScreen()
    }

    private fun moveToLoginScreen() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginStartFragment_to_loginFragment)
        }
    }

    override fun setupView() {
    }
}