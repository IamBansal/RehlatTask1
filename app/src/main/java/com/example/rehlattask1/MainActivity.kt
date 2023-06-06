package com.example.rehlattask1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.rehlattask1.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCustomToolbarAfterCollapsing()
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