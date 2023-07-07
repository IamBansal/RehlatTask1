package com.example.rehlattask1.task1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rehlattask1.databinding.FragmentAmenitiesBinding
import com.example.rehlattask1.task1.adapters.amenity.GeneralAmenityAdapter
import com.example.rehlattask1.task1.adapters.amenity.PopularAmenityAdapter
import com.example.rehlattask1.task1.model.decrypted.DecryptionResposne
import com.example.rehlattask1.task1.model.decrypted.GeneralAmenity
import com.example.rehlattask1.task1.model.decrypted.PopularAmenity
import com.example.rehlattask1.task1.repository.Repository
import com.example.rehlattask1.task1.utils.Constants
import com.example.rehlattask1.task1.utils.Resource
import com.example.rehlattask1.task1.view.MainActivity
import com.example.rehlattask1.task1.viewmodel.ViewModel
import com.example.rehlattask1.task1.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import tgio.rncryptor.RNCryptorNative

class AmenitiesFragment : Fragment() {

    private lateinit var binding: FragmentAmenitiesBinding
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAmenitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val factory = ViewModelProviderFactory(Repository())
//        viewModel = ViewModelProvider(this, factory)[ViewModel::class.java]
        viewModel = (activity as MainActivity).viewModel
        binding.nestedScroll.isNestedScrollingEnabled = true
        observeResponse()

    }

    private fun observeResponse() {
        viewModel.amenityEncrypted.observe(viewLifecycleOwner) { response ->
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
                        requireContext(),
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
                val resultMapped = Gson().fromJson(result, DecryptionResposne::class.java)
                Log.d("Decryption", "${resultMapped.name} and ${resultMapped.facilities.size}")
                setUpRecyclerViews(resultMapped.popularAmenities, resultMapped.generalAmenities)
            }
        }
    }

    private fun setUpRecyclerViews(popular: List<PopularAmenity>, general: List<GeneralAmenity>) {

        if (popular.isNotEmpty()) binding.tvPopular.visibility = View.VISIBLE
        if (general.isNotEmpty()) binding.tvGeneral.visibility = View.VISIBLE
        if (popular.isEmpty() && general.isEmpty()) binding.tvNoData.visibility = View.VISIBLE

        Log.d("Decryption", "${popular.size} and ${general.size}")

        binding.rvPopular.apply {
            val popAdapter = PopularAmenityAdapter()
            popAdapter.differ.submitList(popular)
            adapter = popAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvGeneral.apply {
            val genAdapter = GeneralAmenityAdapter()
            genAdapter.differ.submitList(general)
            adapter = genAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}