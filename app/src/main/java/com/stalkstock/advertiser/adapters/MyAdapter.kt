package com.stalkstock.advertiser.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.stalkstock.advertiser.fragments.ApprovedAdsFragment
import com.stalkstock.advertiser.fragments.ExpiredAdsFragment
import com.stalkstock.advertiser.fragments.PendingAdsFragment

internal class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PendingAdsFragment()
            }
            1 -> {
                ApprovedAdsFragment()
            }
            2 -> {
                ExpiredAdsFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}