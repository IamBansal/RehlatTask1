package com.example.rehlattask1.task2.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rehlattask1.databinding.ActivityMain2Binding
import com.example.rehlattask1.task2.adapter.HotelOffersAdapter
import com.example.rehlattask1.task2.model.response.LstDealsPromo
import com.example.rehlattask1.task2.repository.Repository
import com.example.rehlattask1.task2.utils.Resource
import com.example.rehlattask1.task2.viewmodel.ViewModel
import com.example.rehlattask1.task2.viewmodel.ViewModelProviderFactory
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelProviderFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[ViewModel::class.java]

        observeResponse()
    }

    private val hotelOffersCallback = object : HotelOffersAdapter.HotelOffersCallback {
        override fun onOfferClick(position: Int, hotelOffersRemoteConfig: LstDealsPromo) {
            //TODO - action on click offer
            Toast.makeText(
                this@MainActivity,
                "Offer with coupon code ${hotelOffersRemoteConfig.couponCode} is tried to be applied",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onOfferRemove(position: Int, hotelOffersRemoteConfig: LstDealsPromo) {
            //TODO - action on remove offer
            Toast.makeText(
                this@MainActivity,
                "Offer with coupon code ${hotelOffersRemoteConfig.couponCode} is tried to be removed.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun observeResponse() {
        viewModel.hotels.observe(this) { response ->
            binding.progressBar.visibility = View.VISIBLE
            when (response) {
                is Resource.Success -> {
                    Log.d("success", response.data?.apiStatus.toString())
                    setUpViewPager(response.data!!.lstDealsPromos)
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("error", response.message.toString())
                    Toast.makeText(
                        this@MainActivity,
                        "Error occurred: ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("loading", "in loading state")
                }
            }
        }
    }

    private fun setUpViewPager(lstDealsPromos: List<LstDealsPromo>?) {
        binding.viewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            setPageTransformer(true) { page, position ->
                val r = 1 - abs(position)
                page.scaleY = (0.80f + r * 0.20f)
            }
            adapter = HotelOffersAdapter(this@MainActivity, filterList(lstDealsPromos!!), hotelOffersCallback)
        }
    }

    private fun filterList(lstDealsPromos: List<LstDealsPromo>): List<LstDealsPromo> {
        return lstDealsPromos.filter { offer ->
            offer.dealFor == "Hotel"
        }
    }
}