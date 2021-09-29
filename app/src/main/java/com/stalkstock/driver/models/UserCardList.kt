package com.stalkstock.driver.models

data class UserCardList(
    var success: Boolean = false, // false
    var message: String = "", // User Card not found
    var code: Int = 0 // 403

)

