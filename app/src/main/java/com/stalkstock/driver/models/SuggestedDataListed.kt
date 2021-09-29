package com.stalkstock.driver.models

data class SuggestedDataListed(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // suggested list.
    var body: List<SuggestedBody> = listOf()
)

data class SuggestedBody(
    var id: Int = 0, // 30
    var name: String = "", // dsfds
    var purchaseCount: Int = 0, // 0
    var ratingCount: String = "", // 0.0000
    var commentCount: Int = 0, // 0
    var productImage: List<ProductImage> = listOf()
)

data class ProductImage(
    var id: Int = 0, // 15
    var image: String = "", // http://3.13.214.27:8800/uploads/product/6ef6f891-d438-43b6-b1d5-57614a826f2d.jpeg
    var productId: Int = 0 // 30
)