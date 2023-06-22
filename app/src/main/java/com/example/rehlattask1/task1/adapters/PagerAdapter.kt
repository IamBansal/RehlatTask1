@file:Suppress("DEPRECATION")

package com.example.rehlattask1.task1.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rehlattask1.task1.view.fragment.AmenitiesFragment
import com.example.rehlattask1.task1.view.fragment.HotelPoliciesFragment
import com.example.rehlattask1.task1.view.fragment.ReviewFragment
import com.example.rehlattask1.task1.view.fragment.LocationFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HotelPoliciesFragment()
            1 -> AmenitiesFragment()
            2 -> LocationFragment()
            3 -> ReviewFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Hotel Policies"
            1 -> "Amenities"
            2 -> "Location"
            3 -> "Review"
            else -> null
        }
    }
}