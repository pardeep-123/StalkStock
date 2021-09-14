package com.stalkstock.consumer.model

data class DeleteRecentSearchResponse(
    val code: Int, // 200
    val message: String, // search deleted successfully.
    val success: Boolean // true
)