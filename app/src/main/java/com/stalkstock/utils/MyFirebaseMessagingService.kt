package com.stalkstock.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.stalkstock.R
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService()  {

    var title: String? = ""
    var message: String? = ""
    var notificationManager: NotificationManager? = null

    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)
        Log.d("TAG", "Refreshed token: $refreshedToken")
        Log.d("fcm_token", "-----$refreshedToken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e("new_message", "=======")
        Log.e("new_message", "=======${remoteMessage.notification!!.body}")

        val channelId = getString(R.string.default_notification_channel_id)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.notification!!.title)
            .setContentText(remoteMessage.notification!!.body).setAutoCancel(true)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        manager.notify(((Date().time / 1000L % Int.MAX_VALUE).toInt()), builder.build())
    }

    private val manager: NotificationManager?
        get() {
            if (notificationManager == null) {
                notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }

            return notificationManager
        }
}