package com.sullivan.signear.ui_reservation.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ItemMypageBinding

class MyPageListAdapter(private val itemList: List<MyPageItem>) :
    RecyclerView.Adapter<MyPageListAdapter.MyPageListViewHolder>() {

    private lateinit var binding: ItemMypageBinding

    inner class MyPageListViewHolder(private val binding: ItemMypageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(item: MyPageItem) {
            binding.apply {
                tvTitle.text = item.title
                rlMypage.setOnClickListener {
                    when (item.title) {
                        "지난 예약" -> it.findNavController()
                            .navigate(R.id.action_myPageFragment_to_previousReservationFragment)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemMypageBinding.inflate(layoutInflater)
        return MyPageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageListViewHolder, position: Int) {
        holder.binding(itemList[position])
    }

    override fun getItemCount() = itemList.size
}