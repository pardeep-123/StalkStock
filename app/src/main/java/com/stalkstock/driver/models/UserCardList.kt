package com.stalkstock.driver.models

 data class UserCardList(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // User Card list.
    var body: List<UserCardBody> = listOf()
)

data class UserCardBody(
    var id: Int = 0, // 29
    var userId: Int = 0, // 91
    var stripeCardId: String = "", // card_1JfLtzGlrdcC7JVi2DeQ536X
    var isDefault: Int = 0, // 1
    var cardType: Int = 0, // 1
    var name: String = "", // Anikesh
    var cardNumber: String = "", // 4242424242424242
    var month: Int = 0, // 2
    var year: Int = 0, // 2022
    var created: Int = 0, // 1632995409
    var updated: Int = 0, // 1632995409
    var createdAt: String = "", // 2021-09-30T09:50:09.000Z
    var updatedAt: String = "" // 2021-09-30T09:50:09.000Z
)