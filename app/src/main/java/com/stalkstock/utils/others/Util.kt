package com.stalkstock.utils.others

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.math.*

object Util {
    val REQUEST_GPS_ENABLE = 199
    fun fulscr(appCompatActivity: AppCompatActivity) {
    }


    fun fulscr2(appCompatActivity: AppCompatActivity) {
        appCompatActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        appCompatActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        appCompatActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        appCompatActivity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    fun statusbarTrans(appCompatActivity: AppCompatActivity) {
        appCompatActivity.window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun clickablefalse(appCompatActivity: AppCompatActivity) {
        appCompatActivity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun clickabletrue(appCompatActivity: AppCompatActivity) {
        appCompatActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun isvalidEmail(string: String): Boolean {
        return string.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }

    fun isvalidPhone(string: String): Boolean {
        return string.isNotEmpty() && Patterns.PHONE.matcher(string).matches()
    }

    fun toast(context: Context, msg: String) {
        Toast.makeText(
                context, "" + msg,
                Toast.LENGTH_SHORT
        ).show()
    }


    fun hideSoftKeyboard(activity: Activity) {

        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    fun my_frag(sf: FragmentManager, frame: Int, frag: Fragment) {
        val ff = sf.findFragmentById(frame)
        sf.beginTransaction().replace(frame, frag).commit()
    }


    fun checkCamPermission(c: Activity): Boolean {
        var check = false
        val PERMISSION_CALLBACK_CONSTANT = 100
        var permissionsRequired = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_SMS,
                Manifest.permission.CAMERA
        )

        if (ActivityCompat.checkSelfPermission(c, permissionsRequired[0])
                != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(c, permissionsRequired[0])) {
                Log.d("TAG", "if permission")
                ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)

            } else {
                Log.d("TAG", "else permission")
                ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
            }
        } else {
            check = true
            Log.d("TAG", "else permission already granted")
        }

        return check
    }

    fun checkCallPermission(c: Activity): Boolean {
        var check = false
        val PERMISSION_CALLBACK_CONSTANT = 100
        var permissionsRequired = arrayOf(
                Manifest.permission.CALL_PHONE)

        if (ActivityCompat.checkSelfPermission(c, permissionsRequired[0])
                != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(c, permissionsRequired[0])) {
                Log.d("TAG", "if permission")
                ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)

            } else {
                Log.d("TAG", "else permission")
                ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
            }
        } else {
            check = true
            Log.d("TAG", "else permission already granted")
        }

        return check
    }

    fun checkLocPermission(c: Activity): Boolean {
        var check = false
        val PERMISSION_CALLBACK_CONSTANT = 100
        var permissionsRequired = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(c, permissionsRequired[0])
                != PackageManager.PERMISSION_GRANTED
        ) {
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



    fun hashkey(c: Context) {
        try {
            val info = c.packageManager.getPackageInfo(
                    "com.lyftbeautyuser",
                    PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash1:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }



    fun getdate(c: Context, editText: EditText) {
        var date = ""
        var calendar = Calendar.getInstance()
        DatePickerDialog(c, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            date = "$dayOfMonth/$month/$year"
            editText.setText(date)

        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()

    }


    fun capWord(str: String): String {

        return str.toLowerCase().split(' ').joinToString(" ") { it.capitalize() }

    }

    fun dial(c: Context, str: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$str"))
        c.startActivity(intent)
    }




    fun isDeliverable(latt1: String, lon1: String, latt2: String, lon2: String,km:Int): Boolean {
        var status = false
        var lat1 = latt1.toDouble()
        var lng1 = lon1.toDouble()
        var lat2 = latt2.toDouble()
        var lng2 = lon2.toDouble()
        Log.d("TAG", "KM:$km")
        var theta = lng1 - lng2
        var dist = sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(theta))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 2
        status = dist <= km
        return status

    }

    fun deg2rad(deg: Double): Double {
        return (deg * Math.PI / 180.0)
    }

    fun rad2deg(rad: Double): Double {
        return (rad * 180.0 / Math.PI)
    }

     fun getpercent( sprice: Int,mrp: Int): Int {
         var per=0
         if (mrp>sprice){
             var f=0.0
             var disc=mrp-sprice
             f=(disc.toDouble()/mrp.toDouble()) * 100
            // per = (disc/mrp) * 100

             per=f.toInt()
             Log.d("TAG","dppp: "+f)
             Log.d("TAG","dppp2: "+per)
         }
        return per
    }
}


