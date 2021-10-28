package com.stalkstock.commercial.view.model

data class Sendbidresponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)