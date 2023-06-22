package com.example.rehlattask1.task1.adapters.amenity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rehlattask1.databinding.AmenityItemBinding
import com.example.rehlattask1.task1.model.decrypted.PopularAmenity

class PopularAmenityAdapter : RecyclerView.Adapter<PopularAmenityAdapter.ViewHolder>() {

    class ViewHolder(private val binding: AmenityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularAmenity) {
            binding.text.text = item.name
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<PopularAmenity>() {
        override fun areItemsTheSame(oldItem: PopularAmenity, newItem: PopularAmenity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PopularAmenity, newItem: PopularAmenity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AmenityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}