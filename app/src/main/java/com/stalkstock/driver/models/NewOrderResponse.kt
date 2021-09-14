package com.stalkstock.driver.models

data class NewOrderResponse(
    var body: Body,
    var code: Int, // 200
    var message: String, // You have a new order for approval.
    var success: Boolean // true
) {
    data class Body(
        var acceptedLat: String, // 0.00000000
        var acceptedLong: String, // 0.00000000
        var cardId: Int, // 0
        var createdAt: String, // 2021-08-25T07:09:12.000Z
        var customerId: Int, // 66
        var deliveryDate: String, // 2021-08-25
        var deliveryTime: String, // 07:09:12
        var driverId: Int, // 0
        var email: String, // anikaesash@mailinator.com
        var firstName: String, // Abh
        var id: Int, // 16
        var image: String, // http://3.13.214.27:8800/uploads/user/9e2d437d-4caa-478b-b529-927556e82b2c.jpg
        var isDriverReview: Int, // 0
        var isSelfpickup: Int, // 0
        var isUserReview: Int, // 0
        var lastName: String, // k
        var netAmount: String, // 124.00
        var orderAddress: OrderAddress,
        var orderItems: List<OrderItem>,
        var orderNo: String, // O16298753521458
        var orderStatus: Int, // 1
        var paymentMethod: Int, // 1
        var phone: String, // 2615889823681
        var role: Int, // 1
        var shippingCharges: String, // 2.00
        var shopCharges: String, // 0.00
        var total: String, // 126.00
        var transactionDetail: String, // {}
        var transactionId: String,
        var updatedAt: String, // 2021-09-03T06:30:29.000Z
        var vendorCommission: String, // 74.40
        var vendorDetail: VendorDetail,
        var vendorId: Int // 78
    ) {
        data class OrderAddress(
            var address_line2: String,
            var bidId: Int, // 0
            var city: String, // Mountain View
            var country: String, // United States
            var geoLocation: String, // 1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA
            var id: Int, // 12
            var latitude: String, // 37.42199833
            var longitude: String, // -99.99999999
            var orderId: Int, // 16
            var special_instruction: String,
            var state: String, // California
            var street_address: String, // 1600
            var type: String, // 1
            var userId: Int, // 66
            var zipcode: String // 94043
        )

        data class OrderItem(
            var id: Int, // 15
            var netAmount: String, // 124.00
            var product: Product,
            var productId: Int, // 29
            var qty: Int, // 1
            var total: String // 124.00
        ) {
            data class Product(
                var availability: Int, // 1
                var brandName: String, // Brand
                var id: Int, // 29
                var mrp: String, // 124.00
                var name: String, // NewItems
                var percentageDiscount: Int, // 0
                var productType: Int // 0
            )
        }

        data class VendorDetail(
            var buisnessPhone: String, // 596568686
            var city: String, // India
            var country: String, // India
            var geoLocation: String,
            var id: Int, // 14
            var latitude: String, // 30.64278250
            var longitude: String, // 76.75319730
            var phone: String, // 9495686683
            var postalCode: String, // 140306
            var shopAddress: String, // Mohali Airport Chowk
            var shopLogo: String,
            var shopName: String, // Sanju Baghla
            var state: String, // Punjab
            var userId: Int // 78
        )
    }
}