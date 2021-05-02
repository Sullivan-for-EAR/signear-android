package com.sullivan.ui_login.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.common.ex.*
import com.sullivan.ui_login.R
import com.sullivan.ui_login.databinding.FragmentLoginBinding
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    private val validEmailRegex =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    private val valPhoneRegex = Pattern.compile(
        "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$",
        Pattern.CASE_INSENSITIVE
    )

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
                        val email = etEmailInput.text.toString().trim()
                        if (email.isNotEmpty()) {
                            viewModel.updateLoginState(LoginState.EmailValid)
                            etEmailInput.clearFocus()
                            etPasswordInput.apply {
                                requestFocus()
                                showKeyboard()
                            }
                        } else {
                            viewModel.updateLoginState(LoginState.JoinMember)

                            tvTitle.text = "회원가입"
                            ivPhone.makeVisible()
                            etPhoneInput.makeVisible()
                            btnNext.makeGone()
                            btnFindAccount.makeGone()
                            tvRule.makeVisible()
                            btnJoin.makeVisible()
                        }

                        ivPassword.makeVisible()
                        etPasswordInput.makeVisible()
                    }
                    is LoginState.EmailValid -> {
                        val password = etPasswordInput.text.toString().trim()
                        if (password.isNotEmpty()) {
                            viewModel.updateLoginState(LoginState.Success)
                            etPasswordInput.apply {
                                clearFocus()
                                hideKeyboard()
                            }
                        } else {

                        }
                    }
                    is LoginState.Success -> {

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
                                ivPassword.makeVisible()
                                etPasswordInput.makeVisible()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setTextWatcher() {

        var email: String
        binding.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                email = binding.etEmailInput.text.toString().trim()
                if (email.isNotEmpty() && checkEmailValidation(email)) {
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

        var phone: String
        binding.etPhoneInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                phone = binding.etPhoneInput.text.toString().trim()
                if (phone.isNotEmpty() && checkPhoneValidation(phone)) {
                    ViewCompat.setBackgroundTintList(
                        binding.btnJoin,
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.black
                        )
                    )
                } else {
                    ViewCompat.setBackgroundTintList(
                        binding.btnJoin,
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.btn_next_disable
                        )
                    )
                }
            }
        })

        var password: String
        binding.etPasswordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                password = binding.etPasswordInput.text.toString().trim()
                if(password.isEmpty()){
                    ViewCompat.setBackgroundTintList(
                        binding.btnJoin,
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.btn_next_disable
                        )
                    )
                } else {
                    ViewCompat.setBackgroundTintList(
                        binding.btnJoin,
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.black
                        )
                    )
                }
            }

        })

    }


    private fun checkEmailValidation(input: String) = validEmailRegex.matcher(input).matches()

    private fun checkPhoneValidation(input: String) = valPhoneRegex.matcher(input).matches()
}