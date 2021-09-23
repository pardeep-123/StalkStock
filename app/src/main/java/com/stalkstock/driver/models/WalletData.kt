package com.stalkstock.driver.models

data class WalletData(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // Wallet Balance.
    var body: Body = Body()
)

data class Body(
    var id: Int = 0, // 88
    var walletAmount: String = "" // 0.00
)