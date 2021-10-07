package com.stalkstock.driver.models

data class OrderHistoryData(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // Order list.
    var body: List<HistoryDataBody> = listOf()
)

data class HistoryDataBody(
    var id: Int = 0, // 57
    var orderNo: String = "", // O16335839473069
    var driverId: Int = 0, // 88
    var createdAt: String = "", // 2021-10-07T05:19:07.000Z
    var deliveryTime: String = "", // 10:49:07
    var deliveryDate: String = "", // 2021-10-07
    var orderStatus: Int = 0, // 1
    var vendorId: Int = 0, // 78
    var customerId: Int = 0, // 66
    var acceptedLat: String = "", // 30.70464830
    var acceptedLong: String = "", // 76.71787170
    var shippingCharges: String = "", // 2.00
    var isDriverReview: Int = 0, // 0
    var firstName: String = "", // Abh
    var lastName: String = "", // k
    var email: String = "", // anikaesash@mailinator.com
    var phone: String = "", // 2615889823681
    var rating: String = "", // 0.000000
    var review: String = "",
    var image: String = "", // http://192.168.1.156:8800/uploads/user/9e2d437d-4caa-478b-b529-927556e82b2c.jpg
    var userDeclinedOrder: Any = Any(), // null
    var vendorDetail: VendorDetail = VendorDetail(),
    var orderAddress: OrderAddress = OrderAddress()
)

data class VendorDetail(
    var id: Int = 0, // 14
    var shopName: String = "", // Sanju Baghla
    var shopLogo: String = "",
    var buisnessPhone: String = "", // 596568686
    var city: String = "", // India
    var state: String = "", // Punjab
    var country: String = "", // India
    var postalCode: String = "", // 140306
    var geoLocation: String = "",
    var shopAddress: String = "", // Mohali Airport Chowk
    var latitude: String = "", // 30.64278250
    var longitude: String = "", // 76.75319730
    var phone: String = "" // 9495686683
)

data class OrderAddress(
    var id: Int = 0, // 51
    var orderId: Int = 0, // 57
    var bidId: Int = 0, // 0
    var userId: Int = 0, // 66
    var latitude: String = "", // 30.71209210
    var longitude: String = "", // 76.69266010
    var geoLocation: String = "", // Vedantu innovation Pvt. Ltd, D-199, Industrial Area, 8B, Sahibzada Ajit Singh Nagar, Punjab 160055, India
    var street_address: String = "", // Vedantu innovation Pvt. Ltd
    var address_line2: String = "",
    var city: String = "", // Sahibzada Ajit Singh Nagar
    var state: String = "", // Punjab
    var zipcode: String = "", // 160055
    var country: String = "", // India
    var special_instruction: String = "",
    var type: String = "", // 1
    var created: Int = 0, // 1633583948
    var updated: Int = 0, // 1633583948
    var createdAt: String = "", // 2021-10-05T09:49:14.000Z
    var updatedAt: String = "" // 2021-10-07T05:19:08.000Z
)