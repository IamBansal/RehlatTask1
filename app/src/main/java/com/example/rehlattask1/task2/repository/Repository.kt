package com.example.rehlattask1.task2.repository

import com.example.rehlattask1.task2.api.RetrofitInstance
import com.example.rehlattask1.task2.model.DataClass
import com.example.rehlattask1.task2.utils.Constants

class Repository {

    suspend fun getHotels(dataObj: DataClass) = RetrofitInstance.api.getHotels(
        Constants.CONTENT_TYPE, dataObj
    )

}