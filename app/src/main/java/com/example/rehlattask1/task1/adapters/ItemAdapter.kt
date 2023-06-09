package com.example.rehlattask1.task1.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rehlattask1.databinding.RoomItemBinding
import com.example.rehlattask1.task1.model.RoomItem
import com.example.rehlattask1.task1.model.decrypted.Rooms

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder?>() {

    var onItemSelectedListener: OnItemSelectedListener? = null
    var selectedPosition = 0
    var lastSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindVideo(differ.currentList[position])
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onItemSelectedListener?.onItemSelected(selectedPosition)
        }
        if (selectedPosition == holder.adapterPosition) {
            holder.binding.apply {
                card.visibility = View.VISIBLE
                textView.visibility = View.GONE
                ivImage.visibility = View.GONE
            }
        } else {
            holder.binding.apply {
                card.visibility = View.GONE
                textView.visibility = View.VISIBLE
                ivImage.visibility = View.VISIBLE
            }
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Rooms>(){
        override fun areItemsTheSame(oldItem: Rooms, newItem: Rooms): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Rooms, newItem: Rooms): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(val binding: RoomItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindVideo(item: Rooms) {
            binding.textView.text = item.name
            binding.headText.text = item.name
            binding.ivImage.setOnClickListener {
                binding.card.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
                binding.ivImage.visibility = View.GONE
            }
        }
    }
}
