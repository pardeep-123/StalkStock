package com.stalkstock.consumer.model

data class NotificationListData(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // Notification List.
    var body: List<NotificationListBody> = listOf()
)

data class NotificationListBody(
    var extraInfo: String = "", // {}
    var id: Int = 0, // 73
    var userId: Int = 0, // 78
    var user2Id: Int = 0, // 66
    var orderId: Int = 0, // 45
    var bidId: Int = 0, // 0
    var code: Int = 0, // 3
    var type: Int = 0, // 21
    var title: String = "", // Order Accepted
    var message: String = "", // Sanju Baghla has accepted your order.
    var image: String = "",
    var isRead: Int = 0 // 0
)