package com.stalkstock.driver.models

 data class BankDataList(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // bank Account list fetch successfully.
    var body: List<BankBody> = listOf()
)

data class BankBody(
    var id: Int = 0, // 1
    var userId: Int = 0, // 88
    var isDefault: Int = 0, // 1
    var name: String = "", // abhishek
    var bankName: String = "", // hdfc
    var routingNumber: String = "", // 23634757
    var bankAccount: String = "", // 1234123412341234
    var createdAt: String = "" // 2021-09-23T06:54:04.000Z
)