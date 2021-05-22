package com.sullivan.signear.ui_reservation.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jaredrummler.materialspinner.BuildConfig.VERSION_NAME
import com.sullivan.sigenear.ui_reservation.BuildConfig
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ItemMypageBinding
import com.sullivan.signear.common.navigator.LoginNavigator

class MyPageListAdapter(
    private val itemList: List<MyPageItem>,
    private val loginNavigator: LoginNavigator
) :
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
                        "로그아웃" -> showDialog(it.context)
                        "의견 남기기" -> sendEmail(it.context)
                    }
                }
            }
        }

        private fun sendEmail(context: Context) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf("sullivan_developer@signear.com"))
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Signear에게 의견 보내기"
            )
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "앱 버전 (AppVersion): ${BuildConfig.VERSION_NAME}\n기기명 (Device):\n안드로이드 OS (Android OS): ${Build.VERSION.RELEASE + ".0"}\n내용 (Content):\n"
            )
            context.startActivity(intent)
        }

        private fun showDialog(context: Context) {
            val dialog = MaterialAlertDialogBuilder(
                context, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
            )
                .setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시나요?")
                .setPositiveButton("확인") { dialog, _ ->
                    loginNavigator.openLogin(context)
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()

            dialog.show()
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