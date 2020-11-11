package com.stalkstock.driver;

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.driver.fragment.AccountFragment
import com.stalkstock.driver.fragment.MyRequestFragment
import com.stalkstock.driver.fragment.HomeFragment
import com.stalkstock.driver.fragment.PaymentFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), View.OnClickListener, CommunicationListner {


    override fun  onCreate( savedInstanceState
        : Bundle?){
        super.onCreate(savedInstanceState);
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_home);
        rl_home.setOnClickListener(this)
        rl_plus.setOnClickListener(this)
        rl_profile1.setOnClickListener(this)
        rl_payment.setOnClickListener(this)
        switchFragment(HomeFragment())

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.rl_home->{
                iv_im.visibility = View.VISIBLE
                iv_notification.visibility = View.VISIBLE
                tv_titele.visibility = View.GONE
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_green_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_black_icon))
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler))
                switchFragment(HomeFragment())
            }
            R.id.rl_plus->{
                iv_im.visibility = View.GONE
                iv_notification.visibility = View.GONE
                tv_titele.visibility = View.VISIBLE
                tv_titele.text = "My Requests"
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_green_icon))
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler))
                switchFragment(MyRequestFragment())
            }
            R.id.rl_profile1->{
                iv_im.visibility = View.GONE
                iv_notification.visibility = View.GONE
                tv_titele.visibility = View.VISIBLE
                tv_titele.text = "Account"
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_green_icon))
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_black_icon))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler))
                switchFragment(AccountFragment())
            }

            R.id.rl_payment->{
                iv_im.visibility = View.GONE
                iv_notification.visibility = View.GONE
                tv_titele.visibility = View.VISIBLE
                tv_titele.text = "Payments"
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_black_icon))
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a13doller))
               switchFragment(PaymentFragment())
            }
        }
    }
    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        //fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit)
        fragmentTransaction.replace(R.id.rl_main, fragment)
        fragmentTransaction.commit()
    }

    override fun getYourFragmentActive(value: Int) {
        iv_im.visibility = View.GONE
        iv_notification.visibility = View.GONE
        tv_titele.visibility = View.VISIBLE
        tv_titele.text = "My Requests"
        iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon))
        iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_green_icon))
        iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon))
        iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler))
        switchFragment(MyRequestFragment())
    }


}
