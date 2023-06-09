package com.example.rehlattask1.task2.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DealsModified {

    @SerializedName("lstDealsPromosMobile")
    @Expose
    var lstDealsPromos: List<LstDealsPromo>? = null
    @SerializedName("tokenId")
    @Expose
    var tokenId: Any? = null
    @SerializedName("apiStatus")
    @Expose
    var apiStatus: String? = null
    @SerializedName("apiMessage")
    @Expose
    var apiMessage: Any? = null

}
