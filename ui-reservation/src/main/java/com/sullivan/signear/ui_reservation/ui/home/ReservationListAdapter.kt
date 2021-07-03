package com.sullivan.signear.ui_reservation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sullivan.common.ui_common.ex.convertDate
import com.sullivan.common.ui_common.ex.getTimeInfo
import com.sullivan.common.ui_common.ex.makeGone
import com.sullivan.common.ui_common.ex.makeVisible
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ItemReservationBinding
import com.sullivan.signear.ui_reservation.model.MyReservation
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState

class ReservationListAdapter(private val reservationList: MutableList<MyReservation>) :
    RecyclerView.Adapter<ReservationListAdapter.ReservationListViewHolder>() {
    private lateinit var bindingItem: ItemReservationBinding

    inner class ReservationListViewHolder(private val binding: ItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyReservation) {
            convertStatus(item)
            binding.apply {
                if (item.isEmergency) {
                    tvUrgent.makeVisible()
                    tvPlace.makeGone()
                    btnCancel.makeVisible()
                    btnNavigation.makeGone()
                } else {
                    tvPlace.apply {
                        makeVisible()
                        text = item.place
                    }
                    tvUrgent.makeGone()
                    btnCancel.makeGone()
                    btnNavigation.makeVisible()
                }

                "${item.date.convertDate()} ${item.startTime.getTimeInfo()}".also {
                    tvDate.text = it
                }

                if (!item.isEmergency) {
                    showReservationState(item.currentState, ivState)
                }

                rvReservation.setOnClickListener {
                    if (!item.isEmergency) {
                        it.findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToReservationInfoFragment(item.id)
                        )
                    }
                }

                btnNavigation.setOnClickListener {
                    if (!item.isEmergency) {
                        it.findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToReservationInfoFragment(item.id)
                        )
                    }
                }

                btnCancel.setOnClickListener {
                    showDialog(it.context)
                }
            }
        }

        private fun showReservationState(currentState: ReservationState, ivState: ImageView) {
            when (currentState) {
                is ReservationState.NotRead -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.not_read_icon, null
                    )
                )
                is ReservationState.Cancel -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.cancel_icon, null
                    )
                )
                is ReservationState.NotConfirm -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.not_confirm_icon, null
                    )
                )
                is ReservationState.Confirm -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.confirm_icon, null
                    )
                )
                is ReservationState.Reject -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.reject_icon, null
                    )
                )
                else -> ivState.makeGone()
            }
        }

        private fun showDialog(context: Context) {
            val dialog = MaterialAlertDialogBuilder(
                context, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
            )
                .setTitle(R.string.fragment_reservation_dialog_reservation_cancel_title)
                .setMessage(R.string.fragment_reservation_dialog_reservation_cancel_body)
                .setPositiveButton(R.string.fragment_reservation_dialog_reservation_cancel_positive_btn_title) { dialog, _ ->
                    //todo 예약 취소 로직 추가 예정
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.fragment_reservation_dialog_reservation_cancel_negative_btn_title) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()

            dialog.show()
        }

        private fun convertStatus(item: MyReservation) {
            when (item.status) {
                1 -> item.currentState = ReservationState.NotRead
                2 -> item.currentState = ReservationState.NotConfirm
                3 -> item.currentState = ReservationState.Confirm
                4 -> item.currentState = ReservationState.Cancel()
                5 -> item.currentState = ReservationState.Reject()
                7 -> item.isEmergency = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = ItemReservationBinding.inflate(layoutInflater)
        return ReservationListViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: ReservationListViewHolder, position: Int) {
        val item = reservationList[position]
        holder.bind(item)
    }

    override fun getItemCount() = reservationList.size

    fun addAll(newList: List<MyReservation>) {
        reservationList.clear()
        reservationList.addAll(newList)
        notifyDataSetChanged()
    }
}