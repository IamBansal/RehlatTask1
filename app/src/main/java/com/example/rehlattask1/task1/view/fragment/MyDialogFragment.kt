package com.example.rehlattask1.task1.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.rehlattask1.R
import com.example.rehlattask1.databinding.BottomSheetBinding
import com.example.rehlattask1.task1.adapters.PagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Set the maximum height for the bottom sheet
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val screenHeight = resources.displayMetrics.heightPixels
                val maxHeight = (screenHeight * 0.75).toInt()
                val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
                layoutParams.height = maxHeight
                bottomSheet.layoutParams = layoutParams
            }
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Inflate your custom cross image view
        val crossImage = ImageView(requireContext())
        crossImage.setImageResource(R.drawable.close) // Replace with your cross image resource
        crossImage.setOnClickListener {
            // Handle cross image click event
            dialog.dismiss()
        }

        // Add cross image to the dialog window
        dialog.window?.apply {
            addContentView(crossImage, ViewGroup.LayoutParams(
                30, 30
            ))

            // Adjust cross image positioning as needed
            val crossImageMargin = 20
            setGravity(Gravity.TOP and Gravity.END)
            attributes = attributes?.apply {
                y += crossImageMargin
                x -= crossImageMargin
            }
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PagerAdapter(childFragmentManager)
        binding.viewPager.adapter = adapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)
//        binding.closeIv.setOnClickListener { dismiss() }
//        addCrossImage()
    }

    private fun addCrossImage() {
        val dialog = dialog as? BottomSheetDialog
        dialog?.let {
            val crossImage = ImageView(requireContext())
            crossImage.setImageResource(R.drawable.close) // Replace with your cross image resource

            val layoutParams = LinearLayout.LayoutParams(
               30, 30
            ).apply {
                gravity = Gravity.END and Gravity.TOP
                bottomMargin = 20
                marginEnd = 30
//                topMargin = resources.getDimensionPixelSize(R.dimen.cross_image_margin_top) // Adjust the margin as needed
//                endMargin = resources.getDimensionPixelSize(R.dimen.cross_image_margin_end) // Adjust the margin as needed
            }

            crossImage.layoutParams = layoutParams

            val contentView = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            contentView?.addView(crossImage)

//            dialog.addContentView(crossImage, layoutParams)

            crossImage.setOnClickListener {
                // Handle cross image click event
                dialog.dismiss()
            }
        }
    }

}
