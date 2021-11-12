package com.stalkstock.driver;

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.driver.fragment.AccountFragment
import com.stalkstock.driver.fragment.HomeFragment
import com.stalkstock.driver.fragment.MyRequestFragment
import com.stalkstock.driver.fragment.PaymentFragment
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.RequestBody
import java.util.*


class HomeActivity : BaseActivity(), View.OnClickListener, CommunicationListner,
    Observer<RestObservable> {

    var tv_home: TextView? = null
    var tv_order: TextView? = null
    var tv_cart: TextView? = null
    var tv_account: TextView? = null

    override fun getContentId(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(
        savedInstanceState
        : Bundle?
    ) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.statusBarColor = Color.WHITE;
        }
        tv_home = findViewById(R.id.tv_home)
        tv_order = findViewById(R.id.tv_order)
        tv_cart = findViewById(R.id.tv_cart)
        tv_account = findViewById(R.id.tv_account)

        if (getPrefrence(GlobalVariables.SHARED_PREF_USER.status, 0) != null) {
            iv_notification.isChecked = getPrefrence(GlobalVariables.SHARED_PREF_USER.status, 0) == 1
        }

        iv_notification.setOnCheckedChangeListener { compoundButton, b ->
            if (b) { changeOnlineStatus("1") }
            else { changeOnlineStatus("0") } }
        rl_home.setOnClickListener(this)
        rl_plus.setOnClickListener(this)
        rl_profile1.setOnClickListener(this)
        rl_payment.setOnClickListener(this)
        switchFragment(HomeFragment())
    }

    val viewModel: DriverViewModel by viewModels()

    private fun changeOnlineStatus(s: String) {
        val map = HashMap<String, RequestBody>()
        map["type"] = mUtils.createPartFromString(s) //1 online, 0- offline
        viewModel.driveronlineOffline(this, true, map)
        viewModel.mResponse.observe(this, this)
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

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rl_home -> {
                iv_im.visibility = View.VISIBLE
                iv_notification.visibility = View.VISIBLE
                tv_titele.visibility = View.GONE
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_green_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_black_icon1))
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon1))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler1))
                textColorChange(tv_home!!, tv_order!!, tv_cart!!, tv_account!!)
                switchFragment(HomeFragment())
            }
            R.id.rl_plus -> {
                iv_im.visibility = View.GONE
                iv_notification.visibility = View.GONE
                tv_titele.visibility = View.VISIBLE
                tv_titele.text = "My Requests"
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_green_icon1))
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon1))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler1))
                textColorChange(tv_order!!, tv_home!!, tv_cart!!, tv_account!!)
                switchFragment(MyRequestFragment())
            }
            R.id.rl_payment -> {
                iv_im.visibility = View.GONE
                iv_notification.visibility = View.GONE
                tv_titele.visibility = View.VISIBLE
                tv_titele.text = "Payments"
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_black_icon1))
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon1))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a13doller1))
                textColorChange(tv_cart!!, tv_order!!, tv_home!!, tv_account!!)
                switchFragment(PaymentFragment())
            }

            R.id.rl_profile1 -> {
                iv_im.visibility = View.GONE
                iv_notification.visibility = View.GONE
                tv_titele.visibility = View.VISIBLE
                tv_titele.text = "Account"
                iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_green_icon1))
                iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon1))
                iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_black_icon1))
                iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler1))
                textColorChange(tv_account!!, tv_cart!!, tv_order!!, tv_home!!)
                switchFragment(AccountFragment())
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.rl_main, fragment)
        fragmentTransaction.commit()
    }

    override fun getYourFragmentActive(value: Int) {
        if (value == 1) {
            iv_im.visibility = View.GONE
            iv_notification.visibility = View.GONE
            tv_titele.visibility = View.VISIBLE
            tv_titele.text = "My Requests"
            iv_home.setImageDrawable(resources.getDrawable(R.drawable.home_black_icon1))
            iv_add.setImageDrawable(resources.getDrawable(R.drawable.list_green_icon1))
            iv_profile1.setImageDrawable(resources.getDrawable(R.drawable.user_black_icon1))
            iv_payment.setImageDrawable(resources.getDrawable(R.drawable.a14dooler1))
            switchFragment(MyRequestFragment())
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(
                            this,
                            mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        it.error!!.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}
