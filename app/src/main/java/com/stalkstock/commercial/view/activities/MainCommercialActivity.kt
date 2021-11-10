package com.stalkstock.commercial.view.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.commercial.view.fragments.home.account.AccountFragment
import com.stalkstock.commercial.view.fragments.home.message.MessagesFragment
import com.stalkstock.commercial.view.fragments.home.myorders.MyOrdersFragment
import kotlinx.android.synthetic.main.bottom_layout.*


class MainCommercialActivity : AppCompatActivity(), CommunicationListner {

    var tv_home: TextView? = null
    var tv_order:TextView? = null
    var tv_cart:TextView? = null
    var tv_account:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_commercial)
        tv_home = findViewById(R.id.tv_home)
        tv_order = findViewById(R.id.tv_order)
        tv_cart = findViewById(R.id.tv_cart)
        tv_account = findViewById(R.id.tv_account)
        tv_home!!.text="HOME"
        tv_order!!.text="MY ORDERS"
        tv_cart!!.text="MESSAGES"
        tv_account!!.text="ACCOUNT"

        if (savedInstanceState == null) {
            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial) {
                switchFragment(R.id.rl_content_frame, com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial())
            }}

        home_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_green_icon1)
            iv_orderTab.setImageResource(R.drawable.list_black_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_grey_icon1)
            iv_accountTab.setImageResource(R.drawable.user_black_icon1)
            textColorChange(tv_home!!, tv_order!!, tv_cart!!, tv_account!!)
            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial) {
                switchFragment(R.id.rl_content_frame, com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial())
            }}

        order_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_black_icon1)
            iv_orderTab.setImageResource(R.drawable.list_green_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_grey_icon1)
            iv_accountTab.setImageResource(R.drawable.user_black_icon1)
            textColorChange(tv_order!!,tv_home!!, tv_cart!!, tv_account!!)
            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is MyOrdersFragment) {
                switchFragment(R.id.rl_content_frame, MyOrdersFragment())
            }
        }

        message_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_black_icon1)
            iv_orderTab.setImageResource(R.drawable.list_black_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_green_icon1)
            iv_accountTab.setImageResource(R.drawable.user_black_icon1)
            textColorChange(tv_cart!!,tv_order!!,tv_home!! , tv_account!!)
            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is MessagesFragment) {
                switchFragment(R.id.rl_content_frame, MessagesFragment())
            }}

        account_tab.setOnClickListener {
            iv_homeTab.setImageResource(R.drawable.home_black_icon1)
            iv_orderTab.setImageResource(R.drawable.list_black_icon1)
            iv_messageTab.setImageResource(R.drawable.chat_grey_icon1)
            iv_accountTab.setImageResource(R.drawable.user_green_icon1)
            textColorChange(tv_account!!,tv_cart!!,tv_order!!,tv_home!!  )
            val f = supportFragmentManager.findFragmentById(R.id.rl_content_frame)
            if (f !is AccountFragment) {
                switchFragment(R.id.rl_content_frame, AccountFragment())
            }
        }}

    protected fun switchFragment(
        main_frame: Int,
        fragment: Fragment?
    ) {
        val fragmentTransaction =
        supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(main_frame, fragment!!)
        fragmentTransaction.commit()
    }

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

    override fun getYourFragmentActive(value: Int) {}
}