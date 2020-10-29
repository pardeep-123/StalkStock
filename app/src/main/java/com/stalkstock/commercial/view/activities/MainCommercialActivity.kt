package com.stalkstock.commercial.view.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.stalkstock.R
import kotlinx.android.synthetic.main.bottom_layout.*


class MainCommercialActivity : AppCompatActivity(), CommunicationListner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_commercial)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
      //  val appBarConfiguration = AppBarConfiguration(setOf(
         //   R.id.navigation_home, R.id.navigation_orders, R.id.navigation_messages, R.id.navigation_account
      //  ))
       // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun getYourFragmentActive(value: Int) {
        when(value){
            1->{
                iv_homeTab.setImageResource(R.drawable.home_green_icon)
                iv_orderTab.setImageResource(R.drawable.list_black_icon)
                iv_messageTab.setImageResource(R.drawable.chat_grey_icon)
                iv_accountTab.setImageResource(R.drawable.user_black_icon)
            }
            2->{
                iv_homeTab.setImageResource(R.drawable.home_black_icon)
                iv_orderTab.setImageResource(R.drawable.list_green_icon)
                iv_messageTab.setImageResource(R.drawable.chat_grey_icon)
                iv_accountTab.setImageResource(R.drawable.user_black_icon)
            }
            3->{
                iv_homeTab.setImageResource(R.drawable.home_black_icon)
                iv_orderTab.setImageResource(R.drawable.list_black_icon)
                iv_messageTab.setImageResource(R.drawable.chat_green_icon)
                iv_accountTab.setImageResource(R.drawable.user_black_icon)
            }
            4->{
                iv_homeTab.setImageResource(R.drawable.home_black_icon)
                iv_orderTab.setImageResource(R.drawable.list_black_icon)
                iv_messageTab.setImageResource(R.drawable.chat_grey_icon)
                iv_accountTab.setImageResource(R.drawable.user_green_icon)
            }

        }
    }
}