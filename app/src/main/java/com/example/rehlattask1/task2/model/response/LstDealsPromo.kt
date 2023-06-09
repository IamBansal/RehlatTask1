package com.example.rehlattask1.task2.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class LstDealsPromo : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("displayOrder")
    @Expose
    var displayOrder: Int? = null

    @SerializedName("dealsHubName")
    @Expose
    var dealsHubName: String? = null

    @SerializedName("dealsHubDescription")
    @Expose
    var dealsHubDescription: String? = null

    @SerializedName("dealsHubValidity")
    @Expose
    var dealsHubValidity: String? = null

    @SerializedName("dealsHubURL")
    @Expose
    var dealsHubURL: String? = null

    @SerializedName("promoBanner")
    @Expose
    var promoBanner: String? = null

    @SerializedName("promoBanner1")
    @Expose
    var promoBanner1: String? = null

    /* @SerializedName("langType")
     @Expose
     var langType: String? = null*/
    @SerializedName("isActive")
    @Expose
    var isActive: Boolean? = null

    @SerializedName("createdOn")
    @Expose
    var createdOn: String? = null

    @SerializedName("updatedOn")
    @Expose
    var updatedOn: String? = null

    @SerializedName("createdBy")

    @Expose
    var createdBy: String? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: String? = null

    @SerializedName("dealSource")
    @Expose
    var dealSource: String? = null

    /*@SerializedName("searchResultBannerAlt")
    @Expose
    var searchResultBannerAlt: Any? = null
    @SerializedName("searchResultBannerTitle")
    @Expose
    var searchResultBannerTitle: Any? = null*/
    @SerializedName("couponCode")
    @Expose
    var couponCode: String? = null

    /* @SerializedName("isDealsPromo")
     @Expose
     var isDealsPromo: Boolean? = null
     @SerializedName("isOnlyForLogedinUser")
     @Expose
     var isOnlyForLogedinUser: Boolean? = null*/
    @SerializedName("isIosDeal")
    @Expose
    var isIosDeal: Boolean? = null

    @SerializedName("isAndroidDeal")
    @Expose
    var isAndroidDeal: Boolean = false

    @SerializedName("isTopDeal")
    @Expose
    var isTopDeal: Boolean? = null

    @SerializedName("noofUsesLeft")
    @Expose
    var noofUsesLeft: Int? = null
    /*  @SerializedName("isLimitationGiven")
      @Expose
      var isLimitationGiven: Boolean? = null*/

    @SerializedName("dealFor")
    @Expose
    var dealFor: String? = null

    @SerializedName("isAppHomeScreen")
    @Expose
    var isAppHomeScreen: Boolean = false

    @SerializedName("dealType")
    @Expose
    var dealType: String? = null

    @SerializedName("isDealFor")
    @Expose
    var isDealForr: String? = null
    var dealTimeStamp : Long = 0
}
