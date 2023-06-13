package com.example.rehlattask1.task1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rehlattask1.databinding.ActivityMainBinding
import com.example.rehlattask1.task1.adapters.ItemAdapter
import com.example.rehlattask1.task1.adapters.RoomPopupAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var popupAdapter: RoomPopupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCustomToolbarAfterCollapsing()
        setUpRecyclerView()

        binding.roomButton.apply {
            "${list.size} Room Options".also { text = it }
            setOnClickListener{
                setUpPopupRecyclerView()
                if (binding.roomPopupLayout.root.visibility == View.GONE){
                    showPopup()
                } else {
                    hidePopup()
                }
            }
        }

    }

    private fun hidePopup() {
        binding.roomPopupLayout.root.visibility = View.GONE
        "${list.size} Room Options".also { binding.roomButton.text = it }
    }

    private fun showPopup() {
        binding.roomPopupLayout.root.visibility = View.VISIBLE
        "Close Options".also { binding.roomButton.text = it }
    }

    private fun setUpPopupRecyclerView() {
        binding.roomPopupLayout.rvPopupRooms.apply {
            popupAdapter = RoomPopupAdapter(itemAdapter)
            popupAdapter.differ.submitList(list)
            adapter = popupAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            popupAdapter.onItemSelectedListener = object : RoomPopupAdapter.OnItemSelectedListener {
                override fun onItemSelected(position: Int) {
                    scrollToPosition(position)
                    hidePopup()
                }
            }
        }
    }

    private val list = listOf(
        RoomItem("Standard Single Room"),
        RoomItem("Deluxe Double Room"),
        RoomItem("Two Bedroom Family Suite"),
        RoomItem("Family Suite"),
    )

    private fun setUpRecyclerView() {
        binding.rvContent.apply {
            itemAdapter = ItemAdapter()
            itemAdapter.differ.submitList(list)
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
                    binding. collapsingToolbar.visibility = View.VISIBLE
                    isCustomToolbarVisible = false
                }
            }
        })
    }
}