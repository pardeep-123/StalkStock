package com.stalkstock.driver.models

data class AddBankData(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // Bank Account Detail added successfully.
    var body: AddBankBody = AddBankBody()
)

data class AddBankBody(
    var id: Int = 0, // 2
    var userId: Int = 0, // 88
    var isDefault: Int = 0, // 1
    var name: String = "", // abhishek
    var bankName: String = "", // hdfc
    var routingNumber: String = "", // 2363234757
    var bankAccount: String = "", // 4321432143214321
    var created: Int = 0, // 1632382031
    var updated: Int = 0, // 1632382031
    var createdAt: String = "", // 2021-09-23T07:27:11.000Z
    var updatedAt: String = "" // 2021-09-23T07:27:11.000Z
)