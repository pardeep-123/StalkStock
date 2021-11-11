package com.stalkstock.vender.Model

data class MessageList(
    val bidId: Int,
    val created: Long,
    val createdAt: String,
    val deletedId: Int,
    val id: Int,
    var message: String,
    val lastMessage: LastMessage,
    val lastMessageId: Int,
    val orderId: Int,
    val receiver: Receiver,
    val receiverId: Int,
    val sender: Sender,
    val senderId: Int,
    val updated: Long,
    val updatedAt: String
)

data class LastMessage(
    val chatId: Int,
    val created: Int,
    val createdAt: String,
    val deletedId: Int,
    val id: Int,
    val isRead: Int,
    val message: String,
    val messageType: Int,
    val receiverId: Int,
    val senderId: Int,
    val updated: Int,
    val updatedAt: String
)

data class Receiver(
    val email: String,
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val name: Any,
    val role: Int
)

data class Sender(
    val email: String,
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val name: Any,
    val role: Int
)