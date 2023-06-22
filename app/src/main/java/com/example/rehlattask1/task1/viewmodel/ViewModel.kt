package com.example.rehlattask1.task1.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rehlattask1.task1.model.DataClass
import com.example.rehlattask1.task1.model.DataResponseEncrypted
import com.example.rehlattask1.task1.repository.Repository
import com.example.rehlattask1.task1.utils.Constants.DATA_CLASS
import com.example.rehlattask1.task1.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class ViewModel(private val repository: Repository) : ViewModel() {

    val amenityEncrypted: MutableLiveData<Resource<DataResponseEncrypted>> = MutableLiveData()

    init {
        getHotelsAmenity(DATA_CLASS)
    }

    private fun getHotelsAmenity(dataObj: DataClass) = viewModelScope.launch {
        getSafeHotelsAmenity(dataObj)
    }

    private suspend fun getSafeHotelsAmenity(dataObj: DataClass) {
        amenityEncrypted.postValue(Resource.Loading())
        try {
            val response = repository.getHotelAmenity(dataObj)
            Log.d("response", response.toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    amenityEncrypted.postValue(Resource.Success(it))
                }
            } else {
                amenityEncrypted.postValue(Resource.Error(response.message()))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> amenityEncrypted.postValue(Resource.Error("Network failure"))
                else -> amenityEncrypted.postValue(Resource.Error(t.message.toString()))
            }
        }
    }

}