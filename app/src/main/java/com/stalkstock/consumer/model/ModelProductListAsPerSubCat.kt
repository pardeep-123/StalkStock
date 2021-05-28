package com.stalkstock.consumer.model

data class ModelProductListAsPerSubCat(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // product list.
    val success: Boolean // true
) {
    data class Body(
        val availability: Int, // 1
        val brandName: String, // brand
        val categoryId: Int, // 38
        val country: String, // dndndm
        val createdAt: String, // 2021-05-27T10:42:56.000Z
        val description: String, // jfkfkfkofif
        val id: Int, // 24
        val isApproved: Int, // 1
        val measurementId: Int, // 1
        val mrp: String, // 56.00
        val name: String, // dnnfn
        val oldMrp: String, // 5665656.00
        val parentId: Any, // null
        val percentageDiscount: Int, // 0
        val productImage: List<ProductImage>,
        val productType: Int, // 0
        val status: Int, // 1
        val subCategoryId: Int, // 49
        val vendorId: Int // 78
    ) {
        data class ProductImage(
            val id: Int, // 6
            val image: String, // http://3.13.214.27:8800/uploads/product/07171cc5-234f-43b0-bb31-7179b2fa5625.jpg
            val productId: Int // 24
        )
    }
}