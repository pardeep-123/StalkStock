package com.stalkstock.consumer.model

data class ModelSignupUser(
    val body: Body,
    val code: Int, // 200
    val message: String, // User signed up successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1620628728
        val createdAt: String, // 2021-05-10T06:38:48.000Z
        val deviceToken: String,
        val deviceType: Int, // 0
        val email: String, // anikaesash@mailinator.com
        val id: Int, // 66
        val mobile: String, // 2615889823681
        val notification: String, // off
        val remember_token: String,
        val role: Int, // 1
        val status: Int, // 1
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjo2NiwiZW1haWwiOiJhbmlrYWVzYXNoQG1haWxpbmF0b3IuY29tIn0sImlhdCI6MTYyMDYyODcyOH0.BiXj6Oc_MQc9K6DQB_NyBm_lyihiQqVYAUeS1GofNI4
        val updated: Int, // 1620628728
        val updatedAt: String, // 2021-05-10T06:38:48.000Z
        val verified: Int // 1
    )
}