package com.stalkstock.advertiser.model

data class AddBusinesssAdsResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val action: Int,
        val adLink: String,
        val budget: String,
        val created: Int,
        val createdAt: String,
        val description: String,
        val endDate: String,
        val id: Int,
        val image: String,
        val isApproved: Int,
        val startDate: String,
        val status: Int,
        val title: String,
        val updated: Int,
        val updatedAt: String,
        val userId: Int
    )
}