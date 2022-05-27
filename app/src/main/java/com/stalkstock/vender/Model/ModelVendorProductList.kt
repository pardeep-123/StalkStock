package com.stalkstock.vender.Model

data class ModelVendorProductList(
    val body: Body,
    val code: Int, // 200
    val message: String, // Product list.
    val success: Boolean // true
) {
    data class Body(
        val product: List<Product>,
        val total: Int // 2
    ) {
        data class Product(
            val availability: Int, // 0
            val brandName: String, // brand
            val categoryId: Int, // 38
            val country: String, // country
            val createdAt: String, // 2021-05-21T13:18:07.000Z
            val description: String, // hdofof
            val id: Int, // 21
            val isApproved: Int, // 1
            val measurementId: Int, // 2
            val mrp: String, // 4634.00
            val name: String, // jdjdfn
            val oldMrp: String, // 4634.00
            val parentId: Int, // 0
            val percentageDiscount: Int, // 0
            val productCategory: ProductCategory,
            val productImage: List<ProductImage>,
            val productMeasurement: ProductMeasurement,
            val productSubCategory: ProductSubCategory,
            val productTag: List<ProductTag>,
            val status: Int, // 1
            val subCategoryId: Int, // 49
            val vendorId: Int, // 78
            val title: String // 78
        ) {
            data class ProductCategory(
                val id: Int, // 38
                val name: String // Vegetables
            )

            data class ProductImage(
                val id: Int, // 3
                val image: String, // http://3.13.214.27:8800/uploads/product/b8c523de-8e8a-4f02-b30e-2cbed577331b.jpg
                val productId: Int // 21
            )

            data class ProductMeasurement(
                val id: Int, // 2
                val name: String // Cup
            )

            data class ProductSubCategory(
                val id: Int, // 49
                val name: String // beans
            )

            data class ProductTag(
                val id: Int, // 3
                val productId: Int, // 21
                val tag: String // jdkf,fjfj
            )
        }
    }
}