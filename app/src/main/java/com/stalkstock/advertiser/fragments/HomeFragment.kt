package com.stalkstock.advertiser.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.advertiser.adapters.MyAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    lateinit var v:View
    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        v.viewPager.currentItem = 0

        val rlPending=v.findViewById<RelativeLayout>(R.id.rl_pending)
        val rlApproved=v.findViewById<RelativeLayout>(R.id.rl_approved)
        val relExpired=v.findViewById<RelativeLayout>(R.id.rel_expired)

        v.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(v.tabLayout))
        v.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                v.viewPager.currentItem = tab.position

                if (tab.position==0){
                    rlPending.background=resources.getDrawable(R.drawable.tab_background_selected)
                    rlApproved.background=resources.getDrawable(R.drawable.tab_background)
                    relExpired.background=resources.getDrawable(R.drawable.tab_background)

                    tv_pending.setTextColor(resources.getColor(R.color.white))
                    tv_approved.setTextColor(resources.getColor(R.color.black))
                    tv_expire.setTextColor(resources.getColor(R.color.black))

                }else if (tab.position==1){

                    rlPending.background=resources.getDrawable(R.drawable.tab_background)
                    rlApproved.background=resources.getDrawable(R.drawable.tab_background_selected)
                    relExpired.background=resources.getDrawable(R.drawable.tab_background)

                    tv_pending.setTextColor(resources.getColor(R.color.black))
                    tv_approved.setTextColor(resources.getColor(R.color.white))
                    tv_expire.setTextColor(resources.getColor(R.color.black))

                }else if (tab.position==2){
                    rlPending.background=resources.getDrawable(R.drawable.tab_background)
                    rlApproved.background=resources.getDrawable(R.drawable.tab_background)
                    relExpired.background=resources.getDrawable(R.drawable.tab_background_selected)

                    tv_pending.setTextColor(resources.getColor(R.color.black))

                    tv_approved.setTextColor(resources.getColor(R.color.black))
                    tv_expire.setTextColor(resources.getColor(R.color.white))
                }else{

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        v.iv_notification.setOnClickListener{
            val intent = Intent(activity, NotificationFirstActivity::class.java)
            startActivity(intent)
        }



        rlPending.setOnClickListener {
            v.viewPager.setCurrentItem(0)

            rlPending.background=resources.getDrawable(R.drawable.tab_background_selected)
            rlApproved.background=resources.getDrawable(R.drawable.tab_background)
            relExpired.background=resources.getDrawable(R.drawable.tab_background)

            tv_pending.setTextColor(resources.getColor(R.color.white))
            tv_approved.setTextColor(resources.getColor(R.color.black))
            tv_expire.setTextColor(resources.getColor(R.color.black))
        }
        rlApproved.setOnClickListener {

            v.viewPager.setCurrentItem(1)
            rlPending.background=resources.getDrawable(R.drawable.tab_background)
            rlApproved.background=resources.getDrawable(R.drawable.tab_background_selected)
            relExpired.background=resources.getDrawable(R.drawable.tab_background)

            tv_pending.setTextColor(resources.getColor(R.color.black))
            tv_approved.setTextColor(resources.getColor(R.color.white))
            tv_expire.setTextColor(resources.getColor(R.color.black))

        }
        relExpired.setOnClickListener {
            v.viewPager.setCurrentItem(2)

            rlPending.background=resources.getDrawable(R.drawable.tab_background)
            rlApproved.background=resources.getDrawable(R.drawable.tab_background)
            relExpired.background=resources.getDrawable(R.drawable.tab_background_selected)

            tv_pending.setTextColor(resources.getColor(R.color.black))

            tv_approved.setTextColor(resources.getColor(R.color.black))
            tv_expire.setTextColor(resources.getColor(R.color.white))

        }
        return v
    }

}