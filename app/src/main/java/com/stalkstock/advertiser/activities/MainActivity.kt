package com.stalkstock.advertiser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.advertiser.fragments.AccountFragment
import com.stalkstock.advertiser.fragments.AddPostFragment
import com.stalkstock.advertiser.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var tv_home: TextView? = null
     var tv_cart: TextView? = null
    var tv_account: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        rl_home.setOnClickListener(this)
        rl_plus.setOnClickListener(this)
        rl_profile.setOnClickListener(this)

        tv_home = findViewById(R.id.tv_home)
         tv_cart = findViewById(R.id.tv_cart)
        tv_account = findViewById(R.id.tv_account)

        if (savedInstanceState == null) {
            val f = supportFragmentManager.findFragmentById(R.id.rl_main)
            switchFragment(R.id.rl_main,HomeFragment())
        }
    }

    private fun textColorChange(tv1: TextView, tv4: TextView, tv5: TextView) {
        tv1.setTextColor(resources.getColor(R.color.green))
         tv4.setTextColor(resources.getColor(R.color.colorIcon))
        tv5.setTextColor(resources.getColor(R.color.colorIcon))
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.rl_home->{
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_green_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.plus_grey_icon))
                iv_profile.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon1))
                textColorChange(tv_home!!,tv_cart!! ,tv_account!!)

                val f = supportFragmentManager.findFragmentById(R.id.rl_main)
                if (f !is HomeFragment) {
                    switchFragment(R.id.rl_main, HomeFragment())
                } else {
                    
                }
               // switchFragment(R.id.rl_main,HomeFragment())
            }
            R.id.rl_plus->{
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.plus_green_icon))
                iv_profile.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon1))
                textColorChange(tv_cart!!,tv_home!!,tv_account!!)

                val f = supportFragmentManager.findFragmentById(R.id.rl_main)
                if (f !is AddPostFragment) {
                    switchFragment(R.id.rl_main, AddPostFragment())
                } else {
                }

               // switchFragment(R.id.rl_main,AddPostFragment())
            }
            R.id.rl_profile->{
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.plus_grey_icon))
                iv_profile.setImageDrawable(resources.getDrawable(R.drawable.user_green_icon1))

                textColorChange(tv_account!!,tv_cart!!,tv_home!!)

                val f = supportFragmentManager.findFragmentById(R.id.rl_main)
                if (f !is AccountFragment) {
                    switchFragment(R.id.rl_main, AccountFragment())
                } else {
                }

               // switchFragment(R.id.rl_main,AccountFragment())
            }
        }
    }

    private fun switchFragment(main_frame: Int, fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit)
        fragmentTransaction.replace(R.id.rl_main, fragment)
        fragmentTransaction.commit()
    }

}