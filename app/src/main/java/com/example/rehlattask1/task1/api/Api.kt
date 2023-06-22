package com.example.rehlattask1.task1.api

import com.example.rehlattask1.task1.model.DataClass
import com.example.rehlattask1.task1.model.DataResponseEncrypted
import com.example.rehlattask1.task1.utils.Constants
import com.example.rehlattask1.task1.utils.Constants.END_POINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST(END_POINT)
    suspend fun getHotelAmenity(
        @Header(value = "DecryptionKey") key: String = Constants.DECRYPTION_KEY,
        @Body msgObj: DataClass
    ): Response<DataResponseEncrypted>

}