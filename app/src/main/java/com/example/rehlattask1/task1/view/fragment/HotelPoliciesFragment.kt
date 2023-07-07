package com.example.rehlattask1.task1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rehlattask1.databinding.FragmentHotelPoliciesBinding
import com.example.rehlattask1.task1.model.decrypted.DecryptionResposne
import com.example.rehlattask1.task1.utils.Constants
import com.example.rehlattask1.task1.utils.Resource
import com.example.rehlattask1.task1.view.MainActivity
import com.example.rehlattask1.task1.viewmodel.ViewModel
import com.google.gson.Gson
import tgio.rncryptor.RNCryptorNative

class HotelPoliciesFragment : Fragment() {

    private lateinit var binding: FragmentHotelPoliciesBinding
    private lateinit var viewModel: ViewModel
    private lateinit var resultMapped: DecryptionResposne

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelPoliciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        observeResponse()

    }

    private fun observeResponse() {
        viewModel.amenityEncrypted.observe(viewLifecycleOwner) { response ->
//            binding.progressBar.visibility = View.VISIBLE
            when (response) {
                is Resource.Success -> {
                    Log.d("success", response.data?.response.toString())
                    decryptResponse(response.data?.response.toString())
//                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
//                    binding.progressBar.visibility = View.GONE
                    Log.d("error", response.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "Error occurred: ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
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

    private fun setUpUI() {
        binding.apply {
            tvCheckIn.text = resultMapped.checkInTime
            tvCheckOut.text = resultMapped.checkOutTime
        }
    }
}