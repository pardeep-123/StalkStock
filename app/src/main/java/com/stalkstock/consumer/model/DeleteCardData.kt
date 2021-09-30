package com.stalkstock.consumer.model

data class DeleteCardData(
    var success: Boolean = false, // false
    var message: String = "", // Card not found!
    var code: Int = 0 // 403
)

