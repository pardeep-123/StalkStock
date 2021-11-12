package com.stalkstock.advertiser.activities

 import android.content.Context
 import android.content.Intent
 import android.content.pm.PackageInfo
 import android.content.pm.PackageManager
 import android.os.Bundle
 import android.os.CountDownTimer
 import android.text.TextUtils
 import android.util.Base64
 import android.util.Log
 import android.view.WindowManager
 import androidx.appcompat.app.AppCompatActivity
 import com.google.android.gms.tasks.Task
 import com.google.firebase.messaging.FirebaseMessaging
 import com.stalkstock.MyApplication
 import com.stalkstock.R
 import com.stalkstock.commercial.view.activities.MainCommercialActivity
 import com.stalkstock.consumer.activities.MainConsumerActivity
 import com.stalkstock.consumer.activities.SelectuserActivity
 import com.stalkstock.driver.HomeActivity
 import com.stalkstock.utils.others.GlobalVariables
 import com.stalkstock.utils.others.getPrefrence
 import com.stalkstock.utils.others.savePrefrence
 import com.stalkstock.vender.ui.BottomnavigationScreen
 import java.security.MessageDigest
 import java.security.NoSuchAlgorithmException

class SplashActivity : AppCompatActivity() {
    val mContext : Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        getFirebaseToken()

        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                selectLoginOrHome()
            }
        }.start()

        printHashKey()
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            try {
                FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
                    if (!TextUtils.isEmpty(token)) {
                        savePrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, token)
                        Log.d("AppController", "retrieve token successful : $token")
                    } else {
                        Log.w("AppController", "token should not be null...")
                    }
                }.addOnFailureListener { e: Exception? -> }.addOnCanceledListener {}
                    .addOnCompleteListener { task: Task<String> ->
                        Log.v(
                            "AppController",
                            "This is the token : " + task.result
                        )
                    }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    private fun selectLoginOrHome() {
        if(getPrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, "") != "")
        {
            Log.e("SplashActivity","=${getPrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, "")}")
            val userType = MyApplication.instance.getString("usertype")
            when {
                userType.equals("5") -> {
                    startActivity(Intent(mContext, MainActivity::class.java))
                    finishAffinity()
                }
                userType.equals("1") -> {
                    startActivity(Intent(mContext, MainConsumerActivity::class.java))
                    finishAffinity()
                }
                userType.equals("4") -> {
                    startActivity(Intent(mContext, MainCommercialActivity::class.java))
                    finishAffinity()
                }
                userType.equals("3") -> {
                    startActivity(Intent(mContext, BottomnavigationScreen::class.java))
                    finishAffinity()
                }
                userType.equals("2") -> {
                    startActivity(Intent(mContext, HomeActivity::class.java))
                    finishAffinity()
                }
            }
        }

        else{
            startActivity(Intent(mContext, SelectuserActivity::class.java))
            finishAffinity()
        }
    }


     fun printHashKey() {
        try {
            val info: PackageInfo = packageManager.getPackageInfo(
               packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("THIS", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("THIS", "printHashKey()", e)
        } catch (e: java.lang.Exception) {
            Log.e("THIS", "printHashKey()", e)
        }
    }

}