package com.sullivan.signear.ui_login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.ui_login.databinding.FragmentLoginFinishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFinishFragment : BaseFragment<FragmentLoginFinishBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginFinishBinding.inflate(layoutInflater)
        return binding.root
    }
}