package com.stalkstock.common.model

data class PayPalWebResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)

data class Body(
    val headers: Headers,
    val result: Result,
    val statusCode: Int
)

data class Headers(
    val application_id: String,
    val caller_acct_num: String,
    val connection: String,
    val date: String,
)

data class Result(
    val id: String,
    val links: List<Link>,
    val status: String
)

data class Link(
    val href: String,
    val method: String,
    val rel: String
)