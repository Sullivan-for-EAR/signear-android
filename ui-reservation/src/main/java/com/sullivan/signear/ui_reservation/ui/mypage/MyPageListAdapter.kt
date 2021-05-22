package com.sullivan.signear.ui_reservation.ui.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

//        private fun showDialog(context: Context) {
//            val dialog = MaterialAlertDialogBuilder(
//                context, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
//            )
//                .setTitle("거절 사유")
//                .setMessage(currentReservationInfo.reject_cancel_reason)
//                .setPositiveButton("확인") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .setCancelable(false)
//                .create()
//
//            dialog.show()
//        }
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