package com.sullivan.ui_login.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.common.ex.hideKeyboard
import com.sullivan.signear.common.ex.showKeyboard
import com.sullivan.ui_login.R
import com.sullivan.ui_login.databinding.FragmentLoginBinding
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()
    private val VALID_EMAIL_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

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
        setTextWatcher()
    }

    private fun setupView() {
        binding.apply {
            btnNext.setOnClickListener {
                when (viewModel.checkCurrentState()) {
                    is LoginState.Init -> {
                        viewModel.updateLoginState(LoginState.EmailValid)
                        etEmailInput.clearFocus()
                        etPasswordInput.apply {
                            requestFocus()
                            showKeyboard()
                        }
                    }
                    is LoginState.EmailValid -> {
                        viewModel.updateLoginState(LoginState.Sucess)
                        etPasswordInput.apply {
                            clearFocus()
                            hideKeyboard()
                        }
                    }
                    is LoginState.Sucess -> {

                    }
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
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setTextWatcher() {
        var input: String
        binding.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                input = binding.etEmailInput.text.toString().trim()
                if (input.isNotEmpty() && checkValidation(input)) {
                    ViewCompat.setBackgroundTintList(
                        binding.btnNext,
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.black
                        )
                    )
                } else {
                    ViewCompat.setBackgroundTintList(
                        binding.btnNext,
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.btn_next_disable
                        )
                    )
                }
            }
        })
    }

    private fun checkValidation(input: String) = VALID_EMAIL_REGEX.matcher(input).matches()
}