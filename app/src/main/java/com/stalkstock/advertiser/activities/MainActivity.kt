package com.stalkstock.advertiser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.advertiser.fragments.AccountFragment
import com.stalkstock.advertiser.fragments.AddPostFragment
import com.stalkstock.advertiser.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        rl_home.setOnClickListener(this)
        rl_plus.setOnClickListener(this)
        rl_profile.setOnClickListener(this)
        switchFragment(HomeFragment())
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.rl_home->{
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_green_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.plus_grey_icon))
                iv_profile.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon))

                switchFragment(HomeFragment())
            }
            R.id.rl_plus->{
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.plus_green_icon))
                iv_profile.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon))

                switchFragment(AddPostFragment())
            }
            R.id.rl_profile->{
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.plus_grey_icon))
                iv_profile.setImageDrawable(resources.getDrawable(R.drawable.user_green_icon))

                switchFragment(AccountFragment())
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit)
        fragmentTransaction.replace(R.id.rl_main, fragment)
        fragmentTransaction.commit()
    }

}