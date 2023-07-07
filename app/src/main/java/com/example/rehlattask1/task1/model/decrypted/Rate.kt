package com.example.rehlattask1.task1.model.decrypted

data class Rate(
    val IsDefaultRoomSelection: Boolean,
    val SupHotelCode: String,
    val adults: Int,
    val allotment: Int,
    val base_amount: Double,
    val bed_types: List<Any>,
    val binding: Boolean,
    val boardCode: String,
    val boardName: String,
    val cancellationPolicies: List<CancellationPolicy>,
    val cancellationPolicies_text: List<String>,
    val cancellationPolicies_text_Ar: List<String>,
    val children: Int,
    val childrenAges: String,
    val expediaMarketingFee: String,
    val hP_MarkedPrice: Double,
    val hP_OldMarkedPrice: Double,
    val hP_net: String,
    val is_freecancellation: Boolean,
    val is_paylater: Boolean,
    val markedPrice: Double,
    val net: Double,
    val oldMarkedPrice: Double,
    val paylater_deadline: String,
    val promotions: List<Any>,
    val property_currency: String,
    val property_fee: Int,
    val rateKey: String,
    val roomAmenities: List<Any>,
    val roomTaxesAndPrices: List<RoomTaxesAndPrice>,
    val roomTypeName: String,
    val room_code: String,
    val room_name: String,
    val room_occupancy: RoomOccupancy,
    val room_size: String,
    val tax_amount: Double,
    val wego_base_amount: Double,
    val wego_tax_amount: Double
)