package com.stalkstock.advertiser.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.stalkstock.R
import com.stalkstock.advertiser.activities.Notification_firstActivity
import com.stalkstock.advertiser.adapters.MyAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    lateinit var v:View
    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_home, container, false)

        mContext = activity as Context
        v.tabLayout.addTab(v.tabLayout.newTab().setText("Pending Ads"))
        v.tabLayout.addTab(v.tabLayout.newTab().setText("Approved Ads"))
        v.tabLayout.addTab(v.tabLayout.newTab().setText("Expired Ads"))

        val adapter = MyAdapter(
            mContext, childFragmentManager,
            v.tabLayout.tabCount
        )
        v.viewPager.adapter = adapter
        v.viewPager.setCurrentItem(0)

        v.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(v.tabLayout))
        v.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                v.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        v.iv_notification.setOnClickListener{
            val intent = Intent(activity, Notification_firstActivity::class.java)
            startActivity(intent)
        }
        return v
    }

}