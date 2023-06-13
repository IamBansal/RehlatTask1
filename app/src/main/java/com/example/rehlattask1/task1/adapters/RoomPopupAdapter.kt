package com.example.rehlattask1.task1.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rehlattask1.databinding.PopupRoomItemBinding
import com.example.rehlattask1.task1.RoomItem

class RoomPopupAdapter(private val itemAdapter: ItemAdapter) : RecyclerView.Adapter<RoomPopupAdapter.ViewHolder?>() {

    var onItemSelectedListener: OnItemSelectedListener? = null

    private var selectedPosition = itemAdapter.selectedPosition
    private var lastSelectedPosition = itemAdapter.lastSelectedPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PopupRoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface OnItemSelectedListener { fun onItemSelected(position: Int) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            //To show changes in main screen
            itemAdapter.selectedPosition = selectedPosition
            itemAdapter.lastSelectedPosition = lastSelectedPosition
            itemAdapter.notifyItemChanged(lastSelectedPosition)
            itemAdapter.notifyItemChanged(selectedPosition)

            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onItemSelectedListener?.onItemSelected(selectedPosition)

        }
        if (selectedPosition == holder.adapterPosition) holder.binding.divider.visibility = View.VISIBLE
        else holder.binding.divider.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int { return differ.currentList.size }

    private val differCallback = object : DiffUtil.ItemCallback<RoomItem>(){
        override fun areItemsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem.title == newItem.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding: PopupRoomItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RoomItem) { binding.text1.text = item.title }
    }
}
