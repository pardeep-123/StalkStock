package com.stalkstock.driver.models

data class DriverSignUpResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // Driver signed up successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1626692741
        val createdAt: String, // 2021-07-19T11:05:40.000Z
        val deviceToken: String,
        val deviceType: Int, // 0
        val driverDetail: DriverDetail,
        val email: String, // testdriver1@yopmail.com
        val id: Int, // 88
        val isOnline: Int, // 0
        val mobile: String, // 9988998822
        val notification: String, // off
        val remember_token: String,
        val role: Int, // 2
        val status: Int, // 1
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjo4OCwiZW1haWwiOiJ0ZXN0ZHJpdmVyMUB5b3BtYWlsLmNvbSJ9LCJpYXQiOjE2MjY2OTI3NDB9.TdYIDo3Rw2X-7ipPnEd3jo-NEcFAaZ7p06XI3UaVia8
        val updated: Int, // 1626692741
        val updatedAt: String, // 2021-07-19T11:05:40.000Z
        val verified: Int // 1
    ) {
        data class DriverDetail(
            val address: String,
            val addressLine2: String,
            val approvalStatus: Int, // 0
            val approvalStatusReason: String,
            val city: String, // fdfdfd
            val country: String, // Algeria
            val created: Int, // 1626692741
            val createdAt: String, // 2021-07-19T11:05:40.000Z
            val firstName: String, // test1
            val id: Int, // 24
            val image: String, // http://3.13.214.27:8800/uploads/user/48572343-1b33-4ad4-af1a-906e42947b53.jpg
            val insuranceCompany: String,
            val insuranceExpiryDate: String, // null
            val insurancePolicy: String,
            val insuranceProof: String, // 7b9d71ea-7453-4d70-92cb-51a0ad6b7256.jpg
            val lastName: String, // driver
            val latitude: String, // 0.00000000
            val licenceBackImage: String, // 2ce7b9a8-34c5-4951-a092-926919a4400c.jpg
            val licenceExpiryDate: String, // 2001-12-23
            val licenceFrontImage: String, // a4f6ba93-5a4a-40e8-bd90-bfead0af934d.jpg
            val licenceNumber: String, // fccxcxc
            val longitude: String, // 0.00000000
            val name: String,
            val postalCode: String,
            val registrationExpiryDate: String, // 2001-12-24
            val registrationImage: String, // 2a8ed17b-4fd6-4725-889a-a9459d03ea5c.jpg
            val registrationNumber: String, // dcdc454
            val state: String, // dsdsdsd
            val updated: Int, // 1626692741
            val updatedAt: String, // 2021-07-19T11:05:40.000Z
            val userId: Int, // 88
            val vehicleMake: String, // asasas
            val vehicleModel: String, // dfdfd
            val vehicleType: String // Scooter
        )
    }
}