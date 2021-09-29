package com.stalkstock.consumer.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.consumer.fragment.*
import com.stalkstock.utils.BaseActivity

class MainConsumerActivity : BaseActivity() {
    var context: MainConsumerActivity? = null
    lateinit var home: RelativeLayout
    lateinit var list: RelativeLayout
    lateinit var cart: RelativeLayout
    lateinit var profile: RelativeLayout
    lateinit var search_home: RelativeLayout
    var type = "1"
    lateinit var home_img: ImageView
    lateinit var list_img: ImageView
    lateinit var cart_img: ImageView
    lateinit var profile_img: ImageView
    lateinit var search_img: ImageView
    lateinit var tv_home: TextView
    lateinit var tv_search: TextView
    lateinit var tv_order: TextView
    lateinit var tv_cart: TextView
    lateinit var tv_account: TextView
    var currentDeliveryType = 0 // 0- pickup,1-deelivery , 2 -all

    override fun getContentId(): Int {
        return R.layout.activity_main_consumer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        home = findViewById(R.id.home)
        list = findViewById(R.id.list)
        cart = findViewById(R.id.cart)
        profile = findViewById(R.id.profile)
        search_home = findViewById(R.id.search_home)
        home_img = findViewById(R.id.home_img)
        search_img = findViewById(R.id.search_img)
        list_img = findViewById(R.id.list_img)
        cart_img = findViewById(R.id.cart_img)
        profile_img = findViewById(R.id.profile_img)
        tv_home = findViewById(R.id.tv_home)
        tv_search = findViewById(R.id.tv_search)
        tv_order = findViewById(R.id.tv_order)
        tv_cart = findViewById(R.id.tv_cart)
        tv_account = findViewById(R.id.tv_account)
        if (savedInstanceState == null) {
            switchFragment(HomeCounsumerFragment())
        }
        home.setOnClickListener {
            home_img.setImageResource(R.drawable.home_green_icon1)
            search_img.setImageResource(R.drawable.search_icon_new)
            list_img.setImageResource(R.drawable.list_black_icon1)
            cart_img.setImageResource(R.drawable.cart_black_icon1)
            profile_img.setImageResource(R.drawable.user_black_icon1)
            textColorChange(tv_home, tv_search, tv_order, tv_cart, tv_account)
            switchFragment(HomeCounsumerFragment())
        }
        search_home.setOnClickListener {
            openSearchFragment()
        }
        list.setOnClickListener {
            home_img.setImageResource(R.drawable.home_black_icon1)
            search_img.setImageResource(R.drawable.search_icon_new)
            list_img.setImageResource(R.drawable.list_green_icon1)
            cart_img.setImageResource(R.drawable.cart_black_icon1)
            profile_img.setImageResource(R.drawable.user_black_icon1)
            textColorChange(tv_order, tv_home, tv_search, tv_cart, tv_account)
            switchFragment(ListFragment())
        }
        cart.setOnClickListener {
            home_img.setImageResource(R.drawable.home_black_icon1)
            search_img.setImageResource(R.drawable.search_icon_new)
            list_img.setImageResource(R.drawable.list_black_icon1)
            cart_img.setImageResource(R.drawable.cart_green_icon1)
            profile_img.setImageResource(R.drawable.user_black_icon1)
            textColorChange(tv_cart, tv_home, tv_search, tv_order, tv_account)
            switchFragment(CartFragment())
        }
        profile.setOnClickListener {
            home_img.setImageResource(R.drawable.home_black_icon1)
            search_img.setImageResource(R.drawable.search_icon_new)
            list_img.setImageResource(R.drawable.list_black_icon1)
            cart_img.setImageResource(R.drawable.cart_black_icon1)
            profile_img.setImageResource(R.drawable.user_green_icon1)
            textColorChange(tv_account, tv_home, tv_search, tv_order, tv_cart)
            switchFragment(ProfileConsumerFragment())
        }
        try {
            if (intent.hasExtra("is_open")) {
                when {
                    intent.getStringExtra("is_open") == "1" -> {

                        home_img.setImageResource(R.drawable.home_black_icon1)
                        search_img.setImageResource(R.drawable.search_icon_new)
                        list_img.setImageResource(R.drawable.list_black_icon1)
                        cart_img.setImageResource(R.drawable.cart_green_icon1)
                        profile_img.setImageResource(R.drawable.user_black_icon1)
                        textColorChange(tv_cart, tv_home, tv_search, tv_order, tv_account)
                        switchFragment(CartFragment())
                    }
                    intent.getStringExtra("is_open") == "2" -> {
                        openSearchFragment()
                    }
                    intent.getStringExtra("is_open") == "3" -> {
                        home_img.setImageResource(R.drawable.home_black_icon1)
                        search_img.setImageResource(R.drawable.search_icon_new)
                        list_img.setImageResource(R.drawable.list_green_icon1)
                        cart_img.setImageResource(R.drawable.cart_black_icon1)
                        profile_img.setImageResource(R.drawable.user_black_icon1)
                        textColorChange(tv_order, tv_home, tv_search, tv_cart, tv_account)
                        switchFragment(ListFragment())
                    }
                    intent.getStringExtra("is_open") == "4" -> {
                        home_img.setImageResource(R.drawable.home_green_icon1)
                        search_img.setImageResource(R.drawable.search_icon_new)
                        list_img.setImageResource(R.drawable.list_black_icon1)
                        cart_img.setImageResource(R.drawable.cart_black_icon1)
                        profile_img.setImageResource(R.drawable.user_black_icon1)
                        textColorChange(tv_home, tv_search, tv_order, tv_cart, tv_account)
                        switchFragment(HomeCounsumerFragment())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun textColorChange(
        tv1: TextView?,
        tv2: TextView?,
        tv3: TextView?,
        tv4: TextView?,
        tv5: TextView?
    ) {
        tv1!!.setTextColor(resources.getColor(R.color.green))
        tv2!!.setTextColor(resources.getColor(R.color.colorIcon))
        tv3!!.setTextColor(resources.getColor(R.color.colorIcon))
        tv4!!.setTextColor(resources.getColor(R.color.colorIcon))
        tv5!!.setTextColor(resources.getColor(R.color.colorIcon))
    }

    private fun switchFragment(fragment: Fragment?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment!!)
        fragmentTransaction.commit()
    }

    fun openSearchFragment()
    {
        home_img.setImageResource(R.drawable.home_black_icon1)
        search_img.setImageResource(R.drawable.ic_search_green_new)
        list_img.setImageResource(R.drawable.list_black_icon1)
        cart_img.setImageResource(R.drawable.cart_black_icon1)
        profile_img.setImageResource(R.drawable.user_black_icon1)
        textColorChange(tv_search, tv_home, tv_order, tv_cart, tv_account)
        switchFragment(SearchFragment())
    }
}