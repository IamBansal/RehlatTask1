package com.example.rehlattask1.task1.model.decrypted

data class Rooms(
    val IsDefaultRoomSelection: Boolean,
    val amount_after_service_fee: Double,
    val code: String,
    val has_free_cancellation: Boolean,
    val has_paylater: Boolean,
    val name: String,
    val nameAr: String,
    val rates: List<Rate>,
    val tag: String
)