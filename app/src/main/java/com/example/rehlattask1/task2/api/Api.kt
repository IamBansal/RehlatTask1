package com.example.rehlattask1.task2.api

import com.example.rehlattask1.task2.model.DataClass
import com.example.rehlattask1.task2.utils.Constants.END_POINT
import com.example.rehlattask1.task2.model.response.DealsModified
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST(END_POINT)
    suspend fun getHotels(
        @Header("Content-Type") types: String,
        @Body msgObj: DataClass?
    ): Response<DealsModified?>?

}