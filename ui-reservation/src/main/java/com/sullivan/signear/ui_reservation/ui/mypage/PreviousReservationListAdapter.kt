package com.sullivan.signear.ui_reservation.ui.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sullivan.common.ui_common.ex.convertDate
import com.sullivan.common.ui_common.ex.getTimeInfo
import com.sullivan.common.ui_common.ex.makeGone
import com.sullivan.sigenear.ui_reservation.R
import com.sullivan.sigenear.ui_reservation.databinding.ItemReservationBinding
import com.sullivan.signear.ui_reservation.model.MyReservation
import com.sullivan.signear.ui_reservation.state.ReservationState

class PreviousReservationListAdapter(
    private val reservationList: MutableList<MyReservation>,
    private val viewModel: PreviousReservationListViewModel,
    swipeHelperCallback: SwipeHelperCallback
) :
    RecyclerView.Adapter<PreviousReservationListAdapter.ReservationListViewHolder>() {
    private lateinit var bindingItem: ItemReservationBinding

    inner class ReservationListViewHolder(private val binding: ItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyReservation) {
            convertStatus(item)
            binding.apply {
                if (item.isEmergency) {
                    tvPlace.text =
                        tvPlace.context.getString(R.string.fragment_emergency_reservation_title)
                } else {
                    tvPlace.text = item.place
                }

                "${item.date.convertDate()} ${item.startTime.getTimeInfo()}".also {
                    tvDate.text = it
                }

                showReservationState(item.currentState, ivState)

                rvReservation.setOnClickListener {
                    it.findNavController()
                        .navigate(
                            PreviousReservationFragmentDirections.actionPreviousReservationFragmentToReservationDeleteFragmentDialog(
                                item.id
                            )
                        )
                }

                btnDelete.setOnClickListener {
                    remove(item.id)
                    viewModel.removePrevReservation(item.id)
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

        fun getBtnDelete(): View = binding.btnDelete

        fun getSwipeView(): View = binding.rvReservation

        private fun convertStatus(item: MyReservation) {
            when (item.status) {
                6 -> item.currentState = ReservationState.Cancel()
                7 -> item.currentState = ReservationState.Served
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

    override fun getItemId(position: Int) = reservationList[position].id.toLong()

    fun remove(id: Int) {
        val position = reservationList.indexOf(reservationList.find { it.id == id })
        if (position != -1) {
            reservationList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, reservationList.size)
        }
    }

    fun addAll(newList: List<MyReservation>) {
        reservationList.clear()
        reservationList.addAll(newList)
        notifyDataSetChanged()
    }
}