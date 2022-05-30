package com.stalkstock.consumer.model

data class UserVendorsProductList(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)

data class Body(
    val product: Product,
    val sellerProduct: List<SellerProduct>
)

data class Product(
    val availability: Int,
    val brandName: String,
    val categoryId: Int,
    val country: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val isApproved: Int,
    val measurementId: Int,
    val mrp: String,
    val name: String,
    val oldMrp: String,
    val parentId: Int,
    val percentageDiscount: Int,
    val productImage: List<ProductImage>,
    val productType: Int,
    val productVendor: ProductVendor,
    val status: Int,
    val subCategoryId: Int,
    val vendorId: Int
)

data class SellerProduct(
    val availability: Int,
    val brandName: String,
    val cart: Cart,
    val categoryId: Int,
    val country: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val isApproved: Int,
    val measurement: Measurement,
    val measurementId: Int,
    val mrp: String,
    val name: String,
    val title: String,
    val oldMrp: String,
    val parentId: Int,
    val percentageDiscount: Int,
    val productImage: List<ProductImageX>,
    val productType: Int,
    val ratingCount: String,
    val status: Int,
    val subCategoryId: Int,
    val vendorId: Int
)

data class ProductImage(
    val id: Int,
    val image: String,
    val productId: Int
)

data class ProductVendor(
    val ShopAddress: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val deliveryTime: Int,
    val geoLocation: String,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val ratingCount: String,
    val shopLogo: String,
    val shopName: String,
    val totalRating: Int,
    val userId: Int,
    val coverImage: String, //http://192.168.1.222:8800/uploads/cover/fa2cce29-a61b-4401-90f1-e8b54faf6d33.jpeg
    val firstName: String, // fndidk
    val lastName: String // fndidk
)

data class Cart(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val userId: Int,
    val vendorId: Int
)

data class Measurement(
    val id: Int,
    val name: String
)

data class ProductImageX(
    val id: Int,
    val image: String,
    val productId: Int
)