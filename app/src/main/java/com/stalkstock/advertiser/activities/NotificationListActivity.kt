package com.stalkstock.advertiser.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.NotificationsAdapter
import kotlinx.android.synthetic.main.activity_notification_list.*
import kotlinx.android.synthetic.main.toolbar.*

class NotificationListActivity : AppCompatActivity(), View.OnClickListener {
    val mContext : Context =this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)
        tv_heading.text = "Notifications"
        iv_back.setOnClickListener(this)

    }



    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }
}