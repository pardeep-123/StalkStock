package com.stalkstock.driver.models

data class TransferFundsData(
    var success: Boolean = false, // false
    var message: String = "", // You cannot withdraw money
    var code: Int = 0 // 403

)

