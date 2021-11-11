package com.stalkstock.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.stalkstock.R
import org.json.JSONObject

class NotificationHandlingService : FirebaseMessagingService() {
    private val TAG = "FireBasePush"
    private var i = 0
    var title = ""
    var message: String? = ""
    var type:Int=0
    var CHANNEL_ID = "BuzzAMaid"

    var id = ""
    var name = ""
    var image = ""
    var recieverName = ""
    var recieverImage = ""
    var bookingId: String? = ""
    var status: Int? = null
    var receiverId: String? = null
    lateinit var soundUri: Uri
    var objectBody: JSONObject? = null


    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)
        Log.e(TAG, "Refreshed token: $refreshedToken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e(TAG, "Notification: ${remoteMessage.data}")
        Log.e(TAG, "Notification---: ${remoteMessage.data["body"]}")

        try {
            type = remoteMessage.data["type"].toString().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            type=6
        }
        message = remoteMessage.data["body"].toString()

        try {
            title = remoteMessage.data["title"].toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val componentName = am.getRunningTasks(1)[0].topActivity
        val className = componentName?.className
        try {
            title = getString(R.string.app_name)//Setting APP Name as Title
            objectBody = JSONObject(remoteMessage.data["data_to_send"].toString())
            if(type!=5){
                receiverId = objectBody!!.getString("providerId").orEmpty().toString()
            }else{
                recieverName = objectBody?.getString("sender_name").orEmpty()
                recieverImage = objectBody!!.getString("sender_image").orEmpty()
                receiverId = objectBody!!.getString("sender_id").orEmpty().toString()
            }

            bookingId = objectBody!!.getInt("id").toString()

        } catch (ex: Exception) {
           // ex.message?.let { Log.e("FirebaseRam", it) } //providerId
        }
        var intent = Intent()
        makePush(intent)
      /*  if (type == 5) {
            intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("user2Id", receiverId)
            intent.putExtra("userId", getPrefrence(CommonKeys.UserId, ""))
            intent.putExtra("UserName", recieverName)
            intent.putExtra("userImage", recieverImage)
            makePush(intent)
        }else if(type==2){   //when booking cancel
            intent= Intent(this, HomeActivity::class.java)
            intent.putExtra("type",type)
            intent.putExtra("booking_id",bookingId)
            makePush(intent)
        }else if(type==1){     //when booking accept
            intent= Intent(this, HomeActivity::class.java)
            intent.putExtra("type",type)
            intent.putExtra("booking_id",bookingId)
            makePush(intent)
        }else if(type==4){     //when job finished
            intent= Intent(this, HomeActivity::class.java)
            intent.putExtra("type",type)
            intent.putExtra("booking_id",bookingId)
            makePush(intent)
        }else if(type==6){     //when job started
            intent= Intent(this, HomeActivity::class.java)
            intent.putExtra("type",type)
            intent.putExtra("booking_id",bookingId)
            makePush(intent)
        }*/
    }

    private fun makePush(intent: Intent?) {

        var pendingIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT)

        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val channelId = CHANNEL_ID
        soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notificationIcon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.app_icon))
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setColor(ContextCompat.getColor(this, R.color.themeColor))
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "BuzzAMaid", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private val notificationIcon: Int
        get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.app_icon else R.drawable.app_icon
        }

}