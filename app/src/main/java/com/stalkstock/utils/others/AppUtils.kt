package com.stalkstock.utils.others

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.net.ConnectivityManager
import android.text.format.DateFormat
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
        val date = DateFormat.format("HH:mm", cal).toString()
        return date
    }

    fun changeDateFormat(dateString: String, from: String, to: String): String {
        //dd.MMM.yyyy
        //
        val df = SimpleDateFormat(from, Locale.US)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(dateString)
        df.timeZone = TimeZone.getDefault()
        val spf = SimpleDateFormat(to)

        return spf.format(date!!)
    }

    fun convertTimestampToTime(timestamp: Long): String? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.timeZone= TimeZone.getTimeZone("UTC")
        val sdf = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(calendar.time)
    }

    fun getDateFromUTCTimestamp(mTimestamp: Long, mDateFormate: String?): String? {
        var date: String? = null
        try {
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            cal.timeInMillis = mTimestamp * 1000L
            date = android.text.format.DateFormat.format(mDateFormate, cal.timeInMillis).toString()
            val formatter = SimpleDateFormat(mDateFormate)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(date)
            val dateFormatter = SimpleDateFormat(mDateFormate)
            dateFormatter.timeZone = TimeZone.getDefault()
            date = dateFormatter.format(value)
            return date
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun getTimeTest(timeStamp: Long): String? {
        return try {
            val sdf = SimpleDateFormat("hh:mm a")
            sdf.timeZone= TimeZone.getTimeZone("GMT")
            val netDate = Date(timeStamp * 1000L)
            sdf.format(netDate)
        } catch (ex: java.lang.Exception) {
            "xx"
        }
    }
    fun convertTimeStampToDateTime(timestamp: Long): String? {
        val calendar = Calendar.getInstance()
        val tz = TimeZone.getDefault()
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
        val sdf = SimpleDateFormat("hh:mm a")
        sdf.timeZone = tz
        val currenTimeZone = Date(timestamp * 1000)
        return sdf.format(currenTimeZone)
    }







}
