package com.stalkstock.advertiser.activities

 import android.content.Context
 import android.content.Intent
 import android.os.Bundle
 import android.os.CountDownTimer
 import android.util.Log
 import android.view.WindowManager
 import androidx.appcompat.app.AppCompatActivity
 import com.google.android.gms.wallet.WalletConstants
 import com.stalkstock.MyApplication
 import com.stalkstock.R
 import com.stalkstock.commercial.view.activities.MainCommercialActivity
 import com.stalkstock.consumer.activities.MainConsumerActivity
 import com.stalkstock.consumer.activities.SelectuserActivity
 import com.stalkstock.driver.HomeActivity
 import com.stalkstock.utils.others.GlobalVariables
 import com.stalkstock.utils.others.getPrefrence
 import com.stalkstock.vender.ui.BottomnavigationScreen
 import com.stripe.android.Stripe
 import com.stripe.android.model.PaymentMethodCreateParams


class SplashActivity : AppCompatActivity() {
    val mContext : Context = this

    val PUBLISHABLE_KEY = "pk_test_TYooMQauvdEDq54NiTphI7jx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                selectLoginOrHome()
            }
        }.start()


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
}