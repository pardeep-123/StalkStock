package com.stalkstock.consumer.model

data class ModelHelpContent(
    val body: Body,
    val code: Int, // 200
    val message: String, // Help Content fetch successfully.
    val success: Boolean // true
) {
    data class Body(
        val accessor: String, // helpContent
        val content: String, // <p><strong>Help </strong></p><p></p><p>content goes here..123123 test</p>
        val created: Int, // 1591716283
        val createdAt: String, // 2021-04-20T12:46:59.000Z
        val id: Int, // 4
        val title: String, // Help
        val updated: Int, // 1593409514
        val updatedAt: String // 2021-04-20T12:46:59.000Z
    )
}