package com.stalkstock.advertiser.model

import java.io.Serializable

data class BusinessAdsList(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val action: Int,
        val adLink: String,
        val image: String,
        val budget: String,
        val createdAt: String,
        val description: String,
        val endDate: String,
        val id: Int,
        val isApproved: Int,
        val startDate: String,
        val status: Int,
        val title: String,
        val userId: Int
    ): Serializable
    data class BannerImage(
        val bannerId: Int,
        val id: Int,
        val image: String
    ) : Serializable
}