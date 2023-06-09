package com.example.rehlattask1.task2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rehlattask1.R
import com.example.rehlattask1.databinding.HotelItemVouchersApplyBinding
import com.example.rehlattask1.task2.model.response.LstDealsPromo

class CarouselRVAdapter(
    private val offersList: List<LstDealsPromo>,
    private val callback: HotelOffersAdapter.HotelOffersCallback
) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    @Suppress("DEPRECATION")
    inner class CarouselItemViewHolder(private val binding: HotelItemVouchersApplyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: LstDealsPromo) {

            binding.couponTxt.text = offer.couponCode
            binding.couponDescTxt.text = offer.couponCode

            if (offer.isActive!!) offerApply(binding)
            else offerRemove(binding)

            binding.couponApplyTxt.setOnClickListener {
                offer.isActive = true
                offerApply(binding)
                callback.onOfferClick(position, offer)
            }

            binding.offerRemoveImg.setOnClickListener {
                offer.isActive = false
                offerRemove(binding)
                callback.onOfferRemove(position, offer)
            }
        }

        private fun offerApply(binding: HotelItemVouchersApplyBinding) {
            binding.hotelOfferLayout.setBackgroundResource(R.drawable.pic)
            binding.offerRemoveImg.visibility = View.VISIBLE
            binding.couponApplyTxt.visibility = View.GONE
            binding.couponTxt.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.green
                )
            )
            binding.couponDescTxt.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.grey
                )
            )
        }

        private fun offerRemove(binding: HotelItemVouchersApplyBinding) {
            binding.hotelOfferLayout.setBackgroundResource(R.drawable.pic)
            binding.offerRemoveImg.visibility = View.GONE
            binding.couponApplyTxt.visibility = View.VISIBLE
            binding.couponTxt.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.black
                )
            )
            binding.couponDescTxt.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.grey
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val binding = HotelItemVouchersApplyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarouselItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.bind(offersList[position])
    }

    override fun getItemCount(): Int {
        return offersList.size
    }

}