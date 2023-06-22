package com.example.rehlattask1.task1.adapters.amenity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rehlattask1.databinding.AmenityItemBinding
import com.example.rehlattask1.task1.model.decrypted.GeneralAmenity

class GeneralAmenityAdapter : RecyclerView.Adapter<GeneralAmenityAdapter.ViewHolder>() {

    class ViewHolder(private val binding: AmenityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GeneralAmenity) {
            binding.text.text = item.name
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GeneralAmenity>() {
        override fun areItemsTheSame(oldItem: GeneralAmenity, newItem: GeneralAmenity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: GeneralAmenity, newItem: GeneralAmenity): Boolean {
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