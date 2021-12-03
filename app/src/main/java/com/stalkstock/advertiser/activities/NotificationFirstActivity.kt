package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.NotificationsAdapter
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.RequestDetail
import com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial
import com.stalkstock.commercial.view.fragments.home.myorders.MyOrdersFragment
import com.stalkstock.consumer.fragment.ListFragment
import com.stalkstock.consumer.model.NotificationListBody
import com.stalkstock.consumer.model.NotificationListData
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.ui.BidFragment
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_notification_first.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.HashMap

class NotificationFirstActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable>, NotificationsAdapter.NotificationClick {
     val mContext: Context = this
     lateinit var adapter: NotificationsAdapter
     var listNotify = mutableListOf<NotificationListBody>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_first)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_heading.text = getString(R.string.notifications)
        iv_back.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        setAdapter()
        getNotification()
    }
    val viewModel: HomeViewModel by viewModels()

    private fun getNotification() {
        val map = HashMap<String, String>()
        map["offset"] = "0"
        map["limit"] = "100"
        viewModel.getNotificationList(this, true,map)
        viewModel.homeResponse.observe(this, this)

    }

    fun setAdapter() {
         adapter = NotificationsAdapter(listNotify)
         rv_notification.adapter = adapter
        adapter?.notificationClick=this
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back->{ finish() }
            R.id.btn_ok->{ startActivity(Intent(mContext, NotificationListActivity::class.java)) }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is NotificationListData) {
                    val mResponse: NotificationListData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        listNotify.addAll(mResponse.body)
                        adapter.notifyItemRangeInserted(0,listNotify.size)
                        if(listNotify.isNotEmpty()) rl_ll.visibility = View.GONE
                    }
                    else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    override fun OnItemClick(position: Int, data: NotificationListBody) {
        if(MyApplication.instance.getString("usertype").equals("1")){
            if(data.type==26 || data.type==21 || data.type==24 || data.type==22){
                val listFragment= ListFragment()
                loadFragment(listFragment)
            }
        }else{
            if(data.type==26 || data.type==21 || data.type==24 || data.type==22){
                val listFragment= MyOrdersFragment()
                loadFragment(listFragment)
            } else if (data.type==32){
                val intent = Intent(this, RequestDetail::class.java)
                intent.putExtra("bidId",data.bidId)
                startActivity(intent)
            }
        }

    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.rlNotificaion, fragment).commit()
            return true
        }
        return false
    }

}
