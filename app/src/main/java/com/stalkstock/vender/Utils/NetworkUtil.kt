package com.stalkstock.vender.Utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import java.text.SimpleDateFormat
import java.util.*

object NetworkUtil {

     private val TYPE_CONNECTED = 1
    private val TYPE_NOT_CONNECTED = 0
    val REQUEST_GPS_ENABLE = 199
    var FROM_DETAIL="fromDetail"
    var FROM_DETAIL_VALUE="1"

    private fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var activeNetwork: NetworkInfo? = null
        if (cm != null) {
            activeNetwork = cm.activeNetworkInfo
        }
        if (null != activeNetwork) {
            if (activeNetwork.isConnected)
                return TYPE_CONNECTED

        }
        return TYPE_NOT_CONNECTED
    }

    fun getDate(time: Long): String? {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }

    open fun getNotificationTime(time_stamp: Long): String? {
        var date: Date? = null
        try {
            date = Date(time_stamp * 1000)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        System.out.println("dateeee" + date.toString())
        var string_date = ""
        val current = Calendar.getInstance().time
        var diffInSeconds = (current.time - date!!.time) / 1000
        val sec = if (diffInSeconds >= 60) diffInSeconds % 60 else diffInSeconds
        val min = if ((diffInSeconds / 60).also {
                    diffInSeconds = it
                } >= 60) diffInSeconds % 60 else diffInSeconds
        val hrs = if ((diffInSeconds / 60).also {
                    diffInSeconds = it
                } >= 24) diffInSeconds % 24 else diffInSeconds
        val days = if ((diffInSeconds / 24).also {
                    diffInSeconds = it
                } >= 30) diffInSeconds % 30 else diffInSeconds
        val weeks = days / 7
        val months = if ((diffInSeconds / 30).also {
                    diffInSeconds = it
                } >= 12) diffInSeconds % 12 else diffInSeconds
        val years = (diffInSeconds / 12).also { diffInSeconds = it }
        if (years > 0) {
            string_date = if (years == 1L) {
                "1 year"
            } else {
                "$years years"
            }
        } else if (months > 0) {
            string_date = if (months == 1L) {
                "1 month"
            } else {
                "$months months"
            }
        } else if (weeks > 0) {
            string_date = if (weeks == 1L) {
                "1 week"
            } else {
                "$weeks Weeks"
            }
        } else if (days > 0) {
            string_date = if (days == 1L) {
                "1 day"
            } else {
                "$days days"
            }
        } else if (hrs > 0) {
            string_date = if (hrs == 1L) {
                "1 hour"
            } else {
                "$hrs hours"
            }
        } else if (min > 0) {
            string_date = if (min == 1L) {
                "1 minute"
            } else {
                "$min minutes"
            }
        }
        string_date = "$string_date ago"
        if (string_date == " ago") {
            string_date = "1 sec" + " ago"
        }
        return string_date
    }

    fun isConnected(context: Context): Boolean {
        val conn = getConnectivityStatus(context)
        var status = false
        if (conn == TYPE_CONNECTED) {
            status = true
        } else if (conn == TYPE_NOT_CONNECTED) {
            Toast.makeText(context, "Not connected to Internet", Toast.LENGTH_LONG).show()
            status = false
        }
        return status
    }

    fun convertTimeStampTime(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        val tz = TimeZone.getDefault()
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
        val sdf = SimpleDateFormat("hh:mm a")
        sdf.timeZone = tz
        val currenTimeZone = Date(timestamp * 1000)
        return sdf.format(currenTimeZone)
    }

    fun isConnectedBroadCast(context: Context): Boolean {
        val conn = getConnectivityStatus(context)
        var status = false
        if (conn == TYPE_CONNECTED) {
            status = true
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = false
        }
        return status
    }

    fun checkLocPermission(c: Activity): Boolean {
        var check = false
        val PERMISSION_CALLBACK_CONSTANT = 100
        val permissionsRequired = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ActivityCompat.checkSelfPermission(c, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
            when {
                ActivityCompat.shouldShowRequestPermissionRationale(c, permissionsRequired[0]) -> {
                    Log.d("TAG", "if permission")
                    ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
                else -> {
                    Log.d("TAG", "else permission")
                    ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
            }
        } else {
            check = true
            Log.d("TAG", "else permission already granted")
        }

        return check
    }

    fun checkGps(c: Activity) {
        val client = FusedLoc(c)
        var googleApiClient = client.getClient()
        googleApiClient?.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result = LocationServices.SettingsApi.checkLocationSettings(
                googleApiClient,
                builder.build()
        )
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {

                    status.startResolutionForResult(c, REQUEST_GPS_ENABLE)
                } catch (e: IntentSender.SendIntentException) {
                    Toast.makeText(c, "Errror", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}
