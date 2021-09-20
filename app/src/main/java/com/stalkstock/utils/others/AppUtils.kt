package com.stalkstock.utils.others

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.net.ConnectivityManager
import android.text.format.DateFormat
import android.util.Log
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.R
import com.tapadoo.alerter.Alerter
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object AppUtils {


    @JvmStatic
    fun showErrorAlert(context: Activity, msg: String) {
        Alerter.create(context)
            .setTitle(context.getString(R.string.error_))
            .setTitleAppearance(R.style.AlertTextAppearanceTitle)
            .setText(msg)
            .setTextAppearance(R.style.AlertTextAppearanceText)
                .setBackgroundColorRes(android.R.color.holo_red_light)
            .show()
    }

    @JvmStatic
    fun showSuccessAlert(context: Activity, msg: String) {
        Alerter.create(context)
            .setTitle(context.getString(R.string.success))
            .setTitleAppearance(R.style.AlertTextAppearanceTitle)
            .setText(msg)
            .setTextAppearance(R.style.AlertTextAppearanceText)
                .setBackgroundColorRes(android.R.color.holo_green_dark)
            .show()
    }

    @JvmStatic
    fun showNoInternetAlert(context: Activity, msg: String, listener: OnNoInternetConnectionListener) {
        Alerter.create(context)
            .setTitle(context.getString(R.string.error_))
            .setTitleAppearance(R.style.AlertTextAppearanceTitle)
            .setText(msg)
            .setTextAppearance(R.style.AlertTextAppearanceText)
            .setBackgroundColorRes(android.R.color.holo_red_light)
          /*  .addButton(context.getString(R.s
          tring.retry), R.style.AlertButton, View.OnClickListener {
                listener.onRetryApi()
            })*/
            .show()
    }



    @JvmStatic
    fun scaleBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val scaleX = newWidth / bitmap.width.toFloat()
        val scaleY = newHeight / bitmap.height.toFloat()
        val pivotX = 0f
        val pivotY = 0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY)
        val canvas = Canvas(scaledBitmap)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(bitmap, 0f, 0f, Paint(Paint.FILTER_BITMAP_FLAG))
        return scaledBitmap
    }

    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }

    fun getMilliFromDate(dateFormat: String): Long {
        var date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        try {
            date = formatter.parse(dateFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date.time
    }
     fun convertBitmapToFile(context:Context,fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(context.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
    fun timeInString(timeStamp:Long):String{
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timeStamp * 1000L
        val date =
            DateFormat.format("HH:mm", cal).toString()
        return date
//        return DateFormat.format("hh:mm aa", Date(timeStamp)).toString()
    }

    fun changeDateFormat(dateString: String, from: String, to: String): String {
        //dd.MMM.yyyy
        Log.e("dateChanged","======dateString==1=$dateString")
        Log.e("dateChanged","======dateString==2=$from")
        Log.e("dateChanged","======dateString==3=$to")

        val sdf = SimpleDateFormat(from,Locale.getDefault());
        //val sdf = SimpleDateFormat(from, Locale.US);

        val dateFormat = SimpleDateFormat(to, Locale.getDefault())

        sdf.timeZone = TimeZone.getTimeZone("GMT")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        //var dateFormat = SimpleDateFormat(to, Locale.US);
        val newDate = dateFormat.format(sdf.parse(dateString)!!)

        Log.e("dateChanged","======dateString===$dateString")
        return newDate
    }

}
