package com.sullivan.signear.ui_reservation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.FragmentMyPageBinding
import com.sullivan.signear.common.base.BaseFragment
import com.sullivan.signear.common.navigator.LoginNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    @Inject
    lateinit var loginNavigator: LoginNavigator

    private val itemList = listOf(MyPageItem("지난 예약"), MyPageItem("의견 남기기"), MyPageItem("로그아웃"))
    private lateinit var myPageListAdapter: MyPageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupView() {
        binding.apply {
            btnEmergency.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_emergencyReservationFragment)
            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_pop)
            }

            myPageListAdapter = MyPageListAdapter(itemList, loginNavigator)
            rvMypage.apply {
                adapter = myPageListAdapter
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            }
        }
    }
}