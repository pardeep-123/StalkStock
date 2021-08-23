package com.stalkstock.driver.models

import java.io.Serializable

data class DriverDocResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile fetched successfully.
    val success: Boolean // true
):Serializable {
    data class Body(
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val driverDetail: DriverDetail,
        val email: String, // testdriver1@yopmail.com
        val id: Int, // 88
        val mobile: String, // 9988998822
        val notification: String, // off
        val role: Int // 2
    ):Serializable {
        data class DriverDetail(
            val address: String,
            val addressLine2: String,
            val approvalStatus: Int, // 2
            val approvalStatusReason: String,
            val city: String, // fdfdfd
            val country: String, // Algeria
            val created: Int, // 1626692741
            val createdAt: String, // 2021-07-19T11:05:40.000Z
            val firstName: String, // testd1
            val id: Int, // 24
            val image: String, // bee984df-6397-4f60-9547-b00b1fbb26b7.jpg
            val insuranceCompany: String,
            val insuranceExpiryDate: String, // null
            val insurancePolicy: String,
            val insuranceProof: String, // http://3.13.214.27:8800/uploads/driverProof/7b9d71ea-7453-4d70-92cb-51a0ad6b7256.jpg
            val lastName: String, // driver
            val latitude: String, // 0.00000000
            val licenceBackImage: String, // http://3.13.214.27:8800/uploads/driverProof/2ce7b9a8-34c5-4951-a092-926919a4400c.jpg
            val licenceExpiryDate: String, // 2001-12-23
            val licenceFrontImage: String, // http://3.13.214.27:8800/uploads/driverProof/a4f6ba93-5a4a-40e8-bd90-bfead0af934d.jpg
            val licenceNumber: String, // fccxcxc
            val longitude: String, // 0.00000000
            val name: String,
            val postalCode: String,
            val registrationExpiryDate: String, // 2001-12-24
            val registrationImage: String, // http://3.13.214.27:8800/uploads/driverProof/2a8ed17b-4fd6-4725-889a-a9459d03ea5c.jpg
            val registrationNumber: String, // dcdc454
            val state: String, // dsdsdsd
            val updated: Int, // 1627019146
            val updatedAt: String, // 2021-07-23T05:45:45.000Z
            val userId: Int, // 88
            val vehicleMake: String, // asasas
            val vehicleModel: String, // dfdfd
            val vehicleType: String // Scooter
        ):Serializable
    }
}