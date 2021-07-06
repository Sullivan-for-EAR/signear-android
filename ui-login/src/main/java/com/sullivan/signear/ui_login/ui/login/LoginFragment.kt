package com.sullivan.signear.ui_login.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sullivan.common.ui_common.base.BaseFragment
import com.sullivan.common.ui_common.ex.*
import com.sullivan.common.ui_common.navigator.ReservationNavigator
import com.sullivan.signear.ui_login.R
import com.sullivan.signear.ui_login.R.*
import com.sullivan.signear.ui_login.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    private val validEmailRegex =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    private val valPhoneRegex = Pattern.compile(
        "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$",
        Pattern.CASE_INSENSITIVE
    )

    @Inject
    lateinit var reservationNavigator: ReservationNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
        setTextWatcher()
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateLoginState(LoginState.Init)
    }

    override fun setupView() {
        with(binding) {
            loginLayout.apply {
                btnNext.setOnClickListener {
                    when (viewModel.checkCurrentState()) {
                        is LoginState.Init -> {
                            val email = etEmailInput.text.toString().trim()
                            if (email.isNotEmpty()) {
                                viewModel.checkEmail(email)
                            }
                        }
                        is LoginState.EmailValid -> {
                            val email = etEmailInput.text.toString().trim()
                            val password = etPasswordInput.text.toString().trim()
                            if (password.isNotEmpty()) {
                                etPasswordInput.apply {
                                    clearFocus()
                                    hideKeyboard()
                                }
                            }
                            viewModel.login(email, password)
                        }
                    }
                }

                btnBack.setOnClickListener {
                    when (viewModel.checkCurrentState()) {
                        is LoginState.Init -> {
                            findNavController().navigate(R.id.action_loginFragment_pop)
                        }
                        else -> {
                            showLoginView()
                        }
                    }
                }

                btnJoin.setOnClickListener {
                    val email = etEmailInput.text.toString().trim()
                    val password = etPasswordInput.text.toString().trim()
                    val phone = etPhoneInput.text.toString().trim()
                    if (email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty()) {
                        etPhoneInput.apply {
                            clearFocus()
                            hideKeyboard()
                        }
                        viewModel.createUser(email, phone, password)
                    }
                }

                btnFindAccount.setOnClickListener {
//                    viewModel.updateLoginState(LoginState.FindAccount)
                    requireContext().showDialog(
                        getString(string.dialog_title),
                        getString(string.future_develop),
                        getString(string.future_develop_positive_btn_title)
                    )
                }
            }

            findAccountLayout.apply {
                btnNext.setOnClickListener {
                    val email = etEmailInput.text.toString().trim()
                    if (email.isNotEmpty()) {
                        viewModel.checkEmail(email)
                    }
                }

                btnLogin.setOnClickListener {
                    showLoginView()
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (viewModel.checkCurrentState()) {
                        is LoginState.Init -> {
                            findNavController().navigate(R.id.action_loginFragment_pop)
                        }
                        else -> {
                            showLoginView()
                        }
                    }
                }
            })
    }

    private fun setupObserve() {
        with(viewModel) {
            loginState.observe(viewLifecycleOwner, { loginState ->
                run {
                    when (loginState) {
                        is LoginState.Init -> {
                            showLoginView()
                        }
                        is LoginState.EmailValid -> {
                            showPasswordInputView()
                        }
                        is LoginState.FindAccount -> {
                            showFindAccountView()
                        }
                        is LoginState.Success -> {
                            moveToMainScreen()
                        }
                        is LoginState.JoinMember -> {
                            showMemberJoinView()
                        }
                        is LoginState.JoinSuccess -> {
                            findNavController().navigate(R.id.action_loginFragment_to_loginFinishFragment)
                        }
                    }
                }
            })

            resultCheckEmail.observe(viewLifecycleOwner, { response ->
                if (response.result) {
                    if (viewModel.checkCurrentState() == LoginState.Init) {
                        viewModel.updateLoginState(LoginState.EmailValid)
                    } else {
                        showNewPasswordInputRequestView()
                    }
                } else {
                    if (viewModel.checkCurrentState() == LoginState.Init) {
                        viewModel.updateLoginState(LoginState.JoinMember)
                    }
                }
            })

            resultLogin.observe(viewLifecycleOwner, { response ->
                if (response.accessToken.isNotEmpty()) {
                    viewModel.updateLoginState(LoginState.Success)
                }
            })

            resultJoin.observe(viewLifecycleOwner, { response ->
                if (response.accessToken.isNotEmpty()) {
                    viewModel.updateLoginState(LoginState.JoinSuccess)
                } else {
                    makeToast("회원 가입 실패!")
                }
            })

            errorMsg.observe(viewLifecycleOwner, { msg ->
                makeToast(msg)
            })
        }
    }

    private fun setTextWatcher() {
        var email: String
        var password: String
        var phone: String

        binding.loginLayout.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.loginLayout.apply {
                    email = etEmailInput.text.toString().trim()
                    if (email.isNotEmpty() && checkEmailValidation(email)) {
                        ViewCompat.setBackgroundTintList(
                            binding.loginLayout.btnNext,
                            ContextCompat.getColorStateList(
                                requireContext(),
                                color.black
                            )
                        )
                    } else {
                        ViewCompat.setBackgroundTintList(
                            binding.loginLayout.btnNext,
                            ContextCompat.getColorStateList(
                                requireContext(),
                                color.btn_next_disable
                            )
                        )
                    }
                }
            }
        })

        binding.findAccountLayout.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.findAccountLayout.apply {
                    email = etEmailInput.text.toString().trim()
                    if (email.isNotEmpty() && checkEmailValidation(email)) {
                        makeBtnEnable(btnNext)
                    } else {
                        makeBtnDisable(btnNext)
                    }
                }
            }
        })

        binding.loginLayout.etPhoneInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.loginLayout.apply {
                    email = etEmailInput.text.toString().trim()
                    password = etPasswordInput.text.toString().trim()
                    phone = etPhoneInput.text.toString().trim()
                    if (phone.isNotEmpty() && checkPhoneValidation(phone) && password.isNotEmpty() && email.isNotEmpty()) {
                        makeBtnEnable(btnJoin)
                    } else {
                        makeBtnDisable(btnJoin)
                    }
                }
            }
        })

        binding.loginLayout.etPasswordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.loginLayout.apply {
                    email = etEmailInput.text.toString().trim()
                    password = etPasswordInput.text.toString().trim()
                    phone = etPhoneInput.text.toString().trim()
                    if (password.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                        makeBtnEnable(btnJoin)
                    } else {
                        makeBtnDisable(btnJoin)
                    }
                }
            }
        })
    }

    private fun moveToMainScreen() {
        lifecycleScope.launchWhenCreated {
            delay(1_000)
            reservationNavigator.openReservationHome(requireActivity())
        }
    }

    private fun checkEmailValidation(input: String) = validEmailRegex.matcher(input).matches()

    private fun checkPhoneValidation(input: String) = valPhoneRegex.matcher(input).matches()

    private fun updateTitle(title: String) {
        binding.tvTitle.text = title
    }

    private fun showMemberJoinView() {
        binding.loginLayout.apply {
            updateTitle("회원가입")
            ivPassword.makeVisible()
            etEmailInput.apply {
                hideKeyboard()
                clearFocus()
            }
            etPasswordInput.makeVisible()
            ivPhone.makeVisible()
            etPhoneInput.makeVisible()
            btnNext.makeGone()
            btnFindAccount.makeGone()
            btnJoin.makeVisible()

            val guideMsg =
                "이어의 <a href='https://www.notion.so/Noticeme-a04e2dceff10453dbeb37926bee03e41'>개인정보 취급방침</a> 과 이용약관에 따라 개인정보를\n수집 및 사용하고, 제 3자에게 제공한다는 점에 동의합니다."
            with(tvRule) {
                makeVisible()
                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(guideMsg, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(guideMsg)
                }

                handleUrlClicks {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(it)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showPasswordInputView() {
        binding.loginLayout.apply {
            ivPassword.makeVisible()
            etPasswordInput.makeVisible()
            etEmailInput.clearFocus()
            etPasswordInput.apply {
                requestFocus()
                showKeyboard()
            }
        }
    }

    private fun showFindAccountView() {
        binding.apply {
            loginLayout.loginLayout.apply {
                makeGone()
                hideKeyboard()
            }

            findAccountLayout.apply {
                updateTitle("계정 찾기")
                findAccountLayout.makeVisible()
            }
        }
    }

    private fun showNewPasswordInputRequestView() {
        binding.findAccountLayout.apply {
            tvFindAccountGuideMsg.makeGone()
            ivHuman.makeGone()
            etEmailInput.apply {
                clearFocus()
                hideKeyboard()
                makeGone()
            }
            btnNext.makeGone()

            ivVoyage.makeVisible()
            guideMsg.makeVisible()
            guideMsg2.makeVisible()
            btnLogin.makeVisible()
        }
    }

    private fun showLoginView() {
        binding.loginLayout.apply {
            loginLayout.makeVisible()
            etEmailInput.apply {
                text = null
                hideKeyboard()
            }
            btnNext.makeVisible()
            btnFindAccount.makeVisible()

            etPasswordInput.apply {
                text = null
                makeGone()
            }
            ivPassword.makeGone()
            ivPhone.makeGone()
            etPhoneInput.apply {
                text = null
                makeGone()
            }
            tvRule.makeGone()
            btnJoin.makeGone()

        }
        binding.findAccountLayout.apply {
            tvFindAccountGuideMsg.makeVisible()
            ivHuman.makeVisible()
            etEmailInput.apply {
                makeVisible()
                text = null
            }
            btnNext.makeVisible()

            ivVoyage.makeGone()
            guideMsg.makeGone()
            guideMsg2.makeGone()
            btnLogin.makeGone()

            findAccountLayout.makeGone()
            updateTitle("로그인")
        }
    }

    private fun makeBtnEnable(btn: Button) {
        ViewCompat.setBackgroundTintList(
            btn,
            ContextCompat.getColorStateList(
                requireContext(),
                color.black
            )
        )
        btn.makeEnable()
    }

    private fun makeBtnDisable(btn: Button) {
        ViewCompat.setBackgroundTintList(
            btn,
            ContextCompat.getColorStateList(
                requireContext(),
                color.btn_next_disable
            )
        )
        btn.makeDisable()
    }
}