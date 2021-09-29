package com.stalkstock.driver.models

data class AddCardData(
    var success: Boolean = false, // false
    var message: String = "", // Error...You are not linked to stripe
    var code: Int = 0 // 403
)

