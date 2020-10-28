package com.stalkstock.advertiser.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.ExpiredAdsAdapter
import kotlinx.android.synthetic.main.fragment_expired_ads.view.*

class ExpiredAdsFragment : Fragment() {

    lateinit var v:View
    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_expired_ads, container, false)
        mContext = activity as Context
        setAdapter()
        return v
    }
    fun setAdapter() {
        val adapter = ExpiredAdsAdapter(mContext)
        v.rv_expired.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        v.rv_expired.adapter = adapter
    }
}