package com.stalkstock.vender.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.stalkstock.R
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.adapter.AccpetAdapter
import com.stalkstock.vender.adapter.RequestAdapter
import kotlinx.android.synthetic.main.activity_bid_product.*

class BidProduct : AppCompatActivity(), RequestAdapter.RequestInterface {
    var mContext: Context? = null
    var requestAdapter: RequestAdapter? = null
    var accpetAdapter: AccpetAdapter? = null
    var arrayList= ArrayList<BidData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_product)
        mContext = this@BidProduct
                requestAdapter = RequestAdapter(mContext!!,arrayList,this);
        bidrecyclerview.setLayoutManager(LinearLayoutManager(mContext))
        bidrecyclerview.setAdapter(requestAdapter)
        val backarrow = findViewById<ImageView>(R.id.bidproductbackarrow)
        val btnRequest = findViewById<Button>(R.id.btnrequest)
        val btnAccpet = findViewById<Button>(R.id.btnaccpet)
        backarrow.setOnClickListener { view: View? -> onBackPressed() }
        btnRequest.setOnClickListener { v: View? ->
            btnRequest.background = resources.getDrawable(R.drawable.current_button)
            btnAccpet.background = resources.getDrawable(R.drawable.past_button2)
            btnRequest.setTextColor(resources.getColor(R.color.white))
            btnAccpet.setTextColor(resources.getColor(R.color.balck))
             requestAdapter =  RequestAdapter(mContext!!,arrayList,this);
            bidrecyclerview.setLayoutManager(LinearLayoutManager(mContext))
            bidrecyclerview.setAdapter(requestAdapter)
            requestAdapter?.requestInterface=this
        }
        btnAccpet.setOnClickListener { v: View? ->
            btnAccpet.background = resources.getDrawable(R.drawable.current_button)
            btnRequest.background = resources.getDrawable(R.drawable.past_button2)
            btnAccpet.setTextColor(resources.getColor(R.color.white))
            btnRequest.setTextColor(resources.getColor(R.color.balck))
            bidrecyclerview.setLayoutManager(LinearLayoutManager(mContext))
            bidrecyclerview.setAdapter(accpetAdapter)
        }
    }

    override fun OnItemClick(position: Int, id: String) {

    }
}