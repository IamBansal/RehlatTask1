package com.example.rehlattask1.task1.repository

import com.example.rehlattask1.task1.api.RetrofitInstance
import com.example.rehlattask1.task1.model.DataClass
import com.example.rehlattask1.task1.utils.Constants

class Repository {

    suspend fun getHotelAmenity(dataObj: DataClass) = RetrofitInstance.api.getHotelAmenity(
        Constants.DECRYPTION_KEY, dataObj
    )

}