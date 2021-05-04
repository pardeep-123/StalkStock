package com.stalkstock.commercial.view.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stalkstock.R
import kotlinx.android.synthetic.main.bottom_layout.*


class MainCommercialActivity : AppCompatActivity(), CommunicationListner {

    var listner: CommunicationListner? = null

    var tv_home: TextView? = null
     var tv_order:TextView? = null
    var tv_cart:TextView? = null
    var tv_account:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_commercial)
      //  val navView: BottomNavigationView = findViewById(R.id.nav_view)

      //  val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
      //  val appBarConfiguration = AppBarConfiguration(setOf(
         //   R.id.navigation_home, R.id.navigation_orders, R.id.navigation_messages, R.id.navigation_account
      //  ))
       // setupActionBarWithNavController(navController, appBarConfiguration)
       // navView.setupWithNavController(navController)


        /*navView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        iv_homeTab.setImageResource(R.drawable.home_green_icon)
                        iv_orderTab.setImageResource(R.drawable.list_black_icon)
                        iv_messageTab.setImageResource(R.drawable.chat_grey_icon)
                        iv_accountTab.setImageResource(R.drawable.user_black_icon)

                        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                        if (f !is com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial) {
                            switchFragment(R.id.nav_host_fragment, com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial())
                        } else {
                        }
                    }

                    R.id.navigation_orders -> {

                        iv_homeTab.setImageResource(R.drawable.home_black_icon)
                        iv_orderTab.setImageResource(R.drawable.list_green_icon)
                        iv_messageTab.setImageResource(R.drawable.chat_grey_icon)
                        iv_accountTab.setImageResource(R.drawable.user_black_icon)
                        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                        if (f !is com.live.stalkstockcommercial.ui.view.fragments.myorders.MyOrdersFragment) {
                            switchFragment(R.id.nav_host_fragment, com.live.stalkstockcommercial.ui.view.fragments.myorders.MyOrdersFragment())
                        } else {
                        }
                    }
                    R.id.navigation_messages -> {

                        iv_homeTab.setImageResource(R.drawable.home_black_icon)
                        iv_orderTab.setImageResource(R.drawable.list_black_icon)
                        iv_messageTab.setImageResource(R.drawable.chat_green_icon)
                        iv_accountTab.setImageResource(R.drawable.user_black_icon)
                        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

                        if (f !is com.live.stalkstockcommercial.ui.view.fragments.messages.MessagesFragment) {
                            switchFragment(R.id.nav_host_fragment, com.live.stalkstockcommercial.ui.view.fragments.messages.MessagesFragment())
                        } else {
                        }

                    }
                    R.id.navigation_account -> {

                        iv_homeTab.setImageResource(R.drawable.home_black_icon)
                        iv_orderTab.setImageResource(R.drawable.list_black_icon)
                        iv_messageTab.setImageResource(R.drawable.chat_grey_icon)
                        iv_accountTab.setImageResource(R.drawable.user_green_icon)

                        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

                        if (f !is com.live.stalkstockcommercial.ui.view.fragments.account.AccountFragment) {
                            switchFragment(R.id.nav_host_fragment, com.live.stalkstockcommercial.ui.view.fragments.account.AccountFragment())
                        } else {
                        }
                    }

                }
                return true
            }
        })*/

        tv_home = findViewById(R.id.tv_home)
        tv_order = findViewById(R.id.tv_order)
        tv_cart = findViewById(R.id.tv_cart)
        tv_account = findViewById(R.id.tv_account)

        tv_home!!.text="HOME"
        tv_order!!.text="MY ORDERS"
        tv_cart!!.text="MESSAGES"
        tv_account!!.text="ACCOUNT"

        if (savedInstanceState == null) {
            val f =
                supportFragmentManager.findFragmentById(R.id.rl_content_frame)

            if (f !is com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial) {
                switchFragment(R.id.rl_content_frame, com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial())
            } else {
            }

        }

        home_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_green_icon1)
            iv_orderTab.setImageResource(R.drawable.list_black_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_grey_icon1)
            iv_accountTab.setImageResource(R.drawable.user_black_icon1)

            textColorChange(tv_home!!, tv_order!!, tv_cart!!, tv_account!!)

            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial) {
                switchFragment(R.id.rl_content_frame, com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial())
            } else {
            }
        }

        order_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_black_icon1)
            iv_orderTab.setImageResource(R.drawable.list_green_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_grey_icon1)
            iv_accountTab.setImageResource(R.drawable.user_black_icon1)

            textColorChange(tv_order!!,tv_home!!, tv_cart!!, tv_account!!)

            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is com.live.stalkstockcommercial.ui.view.fragments.myorders.MyOrdersFragment) {
                switchFragment(R.id.rl_content_frame, com.live.stalkstockcommercial.ui.view.fragments.myorders.MyOrdersFragment())
            } else {
            }
        }

        message_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_black_icon1)
            iv_orderTab.setImageResource(R.drawable.list_black_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_green_icon1)
            iv_accountTab.setImageResource(R.drawable.user_black_icon1)

            textColorChange(tv_cart!!,tv_order!!,tv_home!! , tv_account!!)


            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)

            if (f !is com.live.stalkstockcommercial.ui.view.fragments.messages.MessagesFragment) {
                switchFragment(R.id.rl_content_frame, com.live.stalkstockcommercial.ui.view.fragments.messages.MessagesFragment())
            } else {
            }
        }

        account_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_black_icon1)
            iv_orderTab.setImageResource(R.drawable.list_black_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_grey_icon1)
            iv_accountTab.setImageResource(R.drawable.user_green_icon1)
            textColorChange(tv_account!!,tv_cart!!,tv_order!!,tv_home!!  )

            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)

            if (f !is com.live.stalkstockcommercial.ui.view.fragments.account.AccountFragment) {
                switchFragment(R.id.rl_content_frame, com.live.stalkstockcommercial.ui.view.fragments.account.AccountFragment())
            } else {
            }
        }
    }

    protected fun switchFragment(
        main_frame: Int,
        fragment: Fragment?
    ) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(main_frame, fragment!!)
        fragmentTransaction.commit()
    }


    //    private void backgroundChange(ImageView rl1, ImageView rl2, ImageView rl3 , ImageView rl4) {
//        rl1.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
////        rl2.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
////        rl3.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
////        rl4.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
//
//    }
    fun textColorChange(
        tv1: TextView,

        tv3: TextView,
        tv4: TextView,
        tv5: TextView
    ) {
        tv1.setTextColor(resources.getColor(R.color.green))

        tv3.setTextColor(resources.getColor(R.color.colorIcon))
        tv4.setTextColor(resources.getColor(R.color.colorIcon))
        tv5.setTextColor(resources.getColor(R.color.colorIcon))
    }

    override fun getYourFragmentActive(value: Int) {
      /*  when(value){
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

        }*/
    }
}