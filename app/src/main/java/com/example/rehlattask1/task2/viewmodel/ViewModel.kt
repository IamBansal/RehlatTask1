package com.example.rehlattask1.task2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rehlattask1.task2.model.DataClass
import com.example.rehlattask1.task2.repository.Repository
import com.example.rehlattask1.task2.model.response.DealsModified
import com.example.rehlattask1.task2.utils.Constants.DATA_CLASS
import com.example.rehlattask1.task2.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class ViewModel(private val repository: Repository) : ViewModel() {

    val hotels: MutableLiveData<Resource<DealsModified>> = MutableLiveData()

    init {
        getHotels(DATA_CLASS)
    }

    private fun getHotels(dataObj: DataClass) = viewModelScope.launch {
        getSafeHotels(dataObj)
    }

    private suspend fun getSafeHotels(dataObj: DataClass) {
        hotels.postValue(Resource.Loading())
        try {
            val response = repository.getHotels(dataObj)
            Log.d("response", response.toString())
            if (response!!.isSuccessful) {
                response.body()?.let {
                    hotels.postValue(Resource.Success(it))
                }
            } else {
                hotels.postValue(Resource.Error(response.message()))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> hotels.postValue(Resource.Error("Network failure"))
                else -> hotels.postValue(Resource.Error(t.message.toString()))
            }
        }
    }

}