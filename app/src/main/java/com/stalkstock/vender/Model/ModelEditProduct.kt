package com.stalkstock.vender.Model

data class ModelEditProduct(
    val body: Body,
    val code: Int, // 200
    val message: String, // Product edited successfully.
    val success: Boolean // true
) {
    data class Body(
        val availability: Int, // 1
        val barcode: String,
        val barcodeImage: String,
        val brandName: String, // brand
        val categoryId: Int, // 0
        val country: String, // jdjdjd
        val created: Int, // 1621603228
        val createdAt: String, // 2021-05-21T13:20:27.000Z
        val description: String, // jdiees
        val dimensionsUnit: Int, // 0
        val height: String, // 0.00
        val id: Int, // 22
        val image: String,
        val isApproved: Int, // 1
        val length: String, // 0.00
        val measurementId: Int, // 2
        val minimumSellingPrice: String, // 0.00
        val mrp: String, // 12.00
        val name: String, // prodhdh
        val oldMrp: String, // 12.00
        val parentId: Int, // 0
        val percentageDiscount: Int, // 0
        val productType: Int, // 0
        val sku: String,
        val skuImage: String,
        val status: Int, // 1
        val subCategoryId: Int, // 0
        val taxCategoryId: Int, // 0
        val updated: Int, // 1622111753
        val updatedAt: String, // 2021-05-27T10:35:52.000Z
        val vendorId: Int, // 78
        val weight: String, // 0.00
        val weightUnit: Int, // 0
        val width: String // 0.00
    )
}