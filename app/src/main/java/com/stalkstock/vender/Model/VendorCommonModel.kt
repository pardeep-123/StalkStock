package com.stalkstock.vender.Model

data class VendorCommonModel(
    var body: Body,
    var code: Int, // 200
    var message: String, // Order status updated successfully 
    var success: Boolean // true
) {
    class Body(
    )
}