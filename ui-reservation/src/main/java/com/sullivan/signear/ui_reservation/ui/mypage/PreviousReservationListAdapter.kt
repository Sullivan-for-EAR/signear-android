package com.sullivan.signear.ui_reservation.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ItemReservationBinding
import com.sullivan.signear.common.ex.makeGone
import com.sullivan.signear.common.ex.makeVisible
import com.sullivan.signear.ui_reservation.model.Reservation
import com.sullivan.signear.ui_reservation.state.ReservationState

class PreviousReservationListAdapter(private val reservationList: List<Reservation>) :
    RecyclerView.Adapter<PreviousReservationListAdapter.ReservationListViewHolder>() {
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

                "${item.date} ${item.startTime}".also { tvDate.text = it }
                showReservationState(item.currentState, ivState)

                rvReservation.setOnClickListener {
                    it.findNavController()
                        .navigate(
                            PreviousReservationFragmentDirections.actionPreviousReservationFragmentToReservationDeleteFragmentDialog(
                                item.id
                            )
                        )
                }
            }
        }

        private fun showReservationState(currentState: ReservationState, ivState: ImageView) {
            when (currentState) {
                is ReservationState.Served -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.served_icon, null
                    )
                )
                is ReservationState.Cancel -> ivState.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        ivState.context.resources,
                        R.drawable.cancel_icon, null
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreviousReservationListAdapter.ReservationListViewHolder {
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