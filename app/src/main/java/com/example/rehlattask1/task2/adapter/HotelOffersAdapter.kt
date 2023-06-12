package com.example.rehlattask1.task2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.example.rehlattask1.R
import com.example.rehlattask1.databinding.HotelItemVouchersApplyBinding
import com.example.rehlattask1.task2.model.response.LstDealsPromo

class HotelOffersAdapter(
    private val mContext: Context,
    private val offersList: List<LstDealsPromo>,
    private val callback: HotelOffersCallback
) : PagerAdapter() {

    override fun isViewFromObject(view: View, mobject: Any): Boolean {
        return view === mobject as View
    }

    override fun getCount(): Int {
        return offersList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = LayoutInflater.from(mContext)
        val binding = HotelItemVouchersApplyBinding.inflate(inflater, container, false)
        container.addView(binding.root)

        val offer = offersList[position]

        binding.couponTxt.text = offer.couponCode
        binding.couponDescTxt.text = offer.dealsHubName

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

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, mobject: Any) {
        container.removeView(mobject as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 1f
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    private fun offerApply(binding: HotelItemVouchersApplyBinding) {
//        binding.hotelOfferLayout.setBackgroundResource(R.drawable.hotel_offer_viewpager_remove_background)
        binding.offerRemoveImg.visibility = View.VISIBLE
        binding.couponApplyTxt.visibility = View.GONE
        binding.couponTxt.setTextColor(ContextCompat.getColor(mContext, R.color.hotel_offer_apply_green))
        binding.couponDescTxt.setTextColor(ContextCompat.getColor(mContext, R.color.darkGreyText))
    }

    private fun offerRemove(binding: HotelItemVouchersApplyBinding) {
//        binding.hotelOfferLayout.setBackgroundResource(R.drawable.hotel_offer_viewpager_remove_background)
        binding.offerRemoveImg.visibility = View.GONE
        binding.couponApplyTxt.visibility = View.VISIBLE
        binding.couponTxt.setTextColor(ContextCompat.getColor(mContext, R.color.black))
        binding.couponDescTxt.setTextColor(ContextCompat.getColor(mContext, R.color.darkGreyText))
    }

    interface HotelOffersCallback {
        fun onOfferClick(position: Int, hotelOffersRemoteConfig: LstDealsPromo)
        fun onOfferRemove(position: Int, hotelOffersRemoteConfig: LstDealsPromo)
    }

}