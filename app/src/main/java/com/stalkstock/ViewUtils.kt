package com.stalkstock


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.stalkstock.utils.extention.checkStringNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.util.regex.Pattern


val base_URL=""
val image_URL=""

    fun isUserNameValid(username: String): Boolean {
        return !Pattern.compile(
            "^(?=.{3,20}\\\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\\\$"
        ).matcher(username).matches()
        }
    fun Context.toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun isPasswordValid(password: String):Boolean{
        return Pattern.compile(
            "^(?=[^0-9]*[0-9])(?=(?:[^A-Za-z]*[A-Za-z]){2})(?=[^@#\$%^&+=]*[@#\$%^&+=])\\S{8,20}\$"
        ).matcher(password).matches()
    }
fun ProgressBar.showProgressBar(){
    visibility= View.VISIBLE
}
fun ProgressBar.hideProgressBar(){
    visibility= View.GONE
}
fun isEmailValid(email: String): Boolean {
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}
fun iscityValid(c: String): Boolean {
    return c.matches("([a - zA - Z] + |[a - zA - Z] + \\s[a - zA - Z] + )".toRegex())
}


fun showSnackBar(activity: Activity, messageToShow: String): Snackbar?{
    try {
        val snackbar =
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                messageToShow,
                Snackbar.LENGTH_SHORT
            )
        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<TextView>(R.id.snackbar_text)
        textView.maxLines = 3
        snackbar.setAction(activity.getString(R.string.strok)) { snackbar.dismiss() }
        snackbar.setActionTextColor(ContextCompat.getColor(activity, android.R.color.white))
        snackbar.view.setBackgroundColor(activity.resources.getColor(R.color.colorPrimary))
        snackbar.duration=1500
        snackbar.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


fun updateCamera(mGoogleMap: GoogleMap, latitude:String, longitude:String, zoom: Float) {
//        Thread(Runnable {
    // Moving CameraPosition to last clicked position
    try {
        if (!checkStringNull(latitude)) {
            GlobalScope.launch(Dispatchers.Main) {
                mGoogleMap.moveCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            latitude.toDouble(),
                            longitude.toDouble()
                        )
                    )
                )
                mGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(zoom))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T> Context.OpenActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    var intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}


@Throws(OutOfMemoryError::class)
fun decodeFile(uriFile: Uri): Bitmap? {
    val filePath = uriFile.path
    val bmOptions: BitmapFactory.Options
    var imageBitmap: Bitmap?
    try {
        imageBitmap = BitmapFactory.decodeFile(filePath)
    } catch (e: OutOfMemoryError) {
        bmOptions = BitmapFactory.Options()
        bmOptions.inSampleSize = 4
        bmOptions.inPurgeable = true
        imageBitmap = BitmapFactory.decodeFile(filePath, bmOptions)
    }
    return imageBitmap
}







