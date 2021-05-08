package com.sullivan.signear.ui_reservation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ItemReservationBinding
import com.sullivan.signear.common.ex.makeGone
import com.sullivan.signear.common.ex.makeVisible
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState

class ReservationListAdapter(private val reservationList: List<Reservation>) :
    RecyclerView.Adapter<ReservationListAdapter.ReservationListViewHolder>() {
    private lateinit var bindingItem: ItemReservationBinding

    inner class ReservationListViewHolder(private val binding: ItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Reservation) {
            binding.apply {
                if (item.currentState == ReservationState.Urgent) {
                    tvUrgent.makeVisible()
                    tvPlace.makeGone()
                } else {
                    tvPlace.apply {
                        makeVisible()
                        text = item.place
                    }
                    tvUrgent.makeGone()
                }

                tvDate.text = item.date
                showReservationState(item.currentState, ivState)
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
}