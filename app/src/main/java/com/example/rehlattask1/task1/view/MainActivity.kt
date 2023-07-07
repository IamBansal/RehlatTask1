package com.example.rehlattask1.task1.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.rehlattask1.databinding.ActivityMainBinding
import com.example.rehlattask1.task1.adapters.ItemAdapter
import com.example.rehlattask1.task1.adapters.RoomPopupAdapter
import com.example.rehlattask1.task1.model.decrypted.DecryptionResposne
import com.example.rehlattask1.task1.model.decrypted.Rooms
import com.example.rehlattask1.task1.repository.Repository
import com.example.rehlattask1.task1.utils.Constants
import com.example.rehlattask1.task1.utils.Resource
import com.example.rehlattask1.task1.view.fragment.MyDialogFragment
import com.example.rehlattask1.task1.viewmodel.ViewModel
import com.example.rehlattask1.task1.viewmodel.ViewModelProviderFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import tgio.rncryptor.RNCryptorNative
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var popupAdapter: RoomPopupAdapter
    lateinit var viewModel: ViewModel
    private lateinit var resultMapped: DecryptionResposne

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelProviderFactory(Repository())
        viewModel = ViewModelProvider(this, factory)[ViewModel::class.java]
        observeResponse()

        setCustomToolbarAfterCollapsing()
//        setUpRecyclerView(resultMapped.roomsList)

//        binding.roomButton.apply {
//            "${list.size} Room Options".also { text = it }
//            setOnClickListener{
//                setUpPopupRecyclerView(resultMapped.roomsList)
//                if (binding.roomPopupLayout.root.visibility == View.GONE){
//                    showPopup()
//                } else {
//                    hidePopup()
//                }
//            }
//        }

        binding.headCard.card.setOnClickListener {
            val dialogFragment = MyDialogFragment()
            dialogFragment.show(supportFragmentManager, "MyDialogFragment")
        }
    }

    private fun setUpUI() {
        binding.headCard.apply {
            tvHotelName.text = resultMapped.hotelName
            tvHotelAddress.text = resultMapped.address
            "${resultMapped.starRating} star hotel".also { tvStars.text = it }
            tvReviews.text = resultMapped.ta.NumOfReviews.toString()
            btnRating.text = resultMapped.ta.Rating.toString()

            Glide.with(this@MainActivity)
                .load(resultMapped.imageUrl)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        relativeLayoutHead.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Optional implementation
                    }
                })
        }
        setUpRecyclerView(resultMapped.roomsList)
        binding.roomButton.apply {
            "${resultMapped.roomsList.size} Room Options".also { text = it }
            setOnClickListener {
                setUpPopupRecyclerView(resultMapped.roomsList)
                if (binding.roomPopupLayout.root.visibility == View.GONE) {
                    showPopup()
                } else {
                    hidePopup(resultMapped.roomsList.size)
                }
            }
        }
    }

    private fun hidePopup(size: Int) {
        binding.roomPopupLayout.root.visibility = View.GONE
        "$size Room Options".also { binding.roomButton.text = it }
    }

    private fun showPopup() {
        binding.roomPopupLayout.root.visibility = View.VISIBLE
        "Close Options".also { binding.roomButton.text = it }
    }

    private fun setUpPopupRecyclerView(roomsList: List<Rooms>) {
        binding.roomPopupLayout.rvPopupRooms.apply {
            popupAdapter = RoomPopupAdapter(itemAdapter)
            popupAdapter.differ.submitList(roomsList)
            adapter = popupAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            popupAdapter.onItemSelectedListener = object : RoomPopupAdapter.OnItemSelectedListener {
                override fun onItemSelected(position: Int) {
                    scrollToPosition(position)
                    hidePopup(resultMapped.roomsList.size)
                }
            }
        }
    }

//    private val list = listOf(
//        RoomItem("Standard Single Room"),
//        RoomItem("Deluxe Double Room"),
//        RoomItem("Two Bedroom Family Suite"),
//        RoomItem("Family Suite"),
//    )

    private fun setUpRecyclerView(roomsList: List<Rooms>) {
        Log.d("rooms list", roomsList.toString())
        binding.rvContent.apply {
            itemAdapter = ItemAdapter()
            itemAdapter.differ.submitList(roomsList)
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAdapter.onItemSelectedListener = object : ItemAdapter.OnItemSelectedListener {
                override fun onItemSelected(position: Int) {
                    scrollToPosition(position)
                }
            }
        }
    }

    private fun setCustomToolbarAfterCollapsing() {
        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            var isCustomToolbarVisible = false

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                val maxScroll = appBarLayout.totalScrollRange
                val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()

                // Show/hide custom toolbar based on the collapse percentage
                if (percentage >= 0.8f && !isCustomToolbarVisible) {
                    binding.customToolbar.visibility = View.VISIBLE
                    binding.collapsingToolbar.visibility = View.INVISIBLE
                    isCustomToolbarVisible = true
                } else if (percentage < 0.8f && isCustomToolbarVisible) {
                    binding.customToolbar.visibility = View.GONE
                    binding.collapsingToolbar.visibility = View.VISIBLE
                    isCustomToolbarVisible = false
                }
            }
        })
    }

    private fun observeResponse() {
        viewModel.amenityEncrypted.observe(this) { response ->
            binding.progressBar.visibility = View.VISIBLE
            when (response) {
                is Resource.Success -> {
                    Log.d("success", response.data?.response.toString())
                    decryptResponse(response.data?.response.toString())
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("error", response.message.toString())
                    Toast.makeText(
                        this,
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

    private fun decryptResponse(encrypt: String) {
        RNCryptorNative.decryptAsync(
            encrypt, Constants.DECRYPTION_KEY
        ) { result, e ->
            if (e != null) {
                Log.e("Decryption", "Error occurred during decryption: ${e.message}")
            } else if (result != null) {
                resultMapped = Gson().fromJson(result, DecryptionResposne::class.java)
                setUpUI()
                Log.d("Decryption", "${resultMapped.name} and ${resultMapped.facilities.size}")
            }
        }
    }
}