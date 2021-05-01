package com.sullivan.ui_login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.ui_login.R
import com.sullivan.ui_login.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserve()
    }

    private fun setupView() {
        binding.apply {
            btnNext.setOnClickListener {
                when (viewModel.checkCurrentState()) {
                    is LoginState.Init -> viewModel.updateLoginState(LoginState.EmailValid)
                    is LoginState.EmailValid -> viewModel.updateLoginState(LoginState.Sucess)
                    is LoginState.Sucess -> viewModel.updateLoginState(LoginState.Sucess)
                }
            }
        }
    }

    private fun setupObserve() {
        viewModel.apply {
            loginState.observe(viewLifecycleOwner, { loginState ->
                run {
                    when (loginState) {
                        is LoginState.EmailValid -> {
                            binding.apply {
                                ivPassword.isVisible = true
                                etPasswordInput.isVisible = true
                                ViewCompat.setBackgroundTintList(
                                    btnNext,
                                    ContextCompat.getColorStateList(
                                        requireContext(),
                                        R.color.black
                                    )
                                )
                            }
                        }
                    }
                }
            })
        }
    }
}