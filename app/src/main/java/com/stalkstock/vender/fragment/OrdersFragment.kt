package com.stalkstock.vender.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.VendorOrderListResponse
import com.stalkstock.vender.adapter.CurrentAdapter
import com.stalkstock.vender.adapter.PastAdapter
import com.stalkstock.vender.ui.BottomnavigationScreen
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import com.stalkstock.utils.others.AppUtils
import java.util.*
import kotlin.collections.ArrayList

class OrdersFragment : Fragment(), Observer<RestObservable> {
    var mContext: Context? = null
    private lateinit var currentAdapter: CurrentAdapter
    lateinit var recyclerview1: RecyclerView
    private lateinit var pastAdapter: PastAdapter

    private var reset = false
    private var currentOffset = 0
    private val mOrderArrayList: ArrayList<VendorOrderListResponse.Body> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_orders, container, false)
        mContext = activity
        val btnCurrent = view.findViewById<Button>(R.id.btnCurrent)
        val btnPast = view.findViewById<Button>(R.id.btnPast)
        recyclerview1 = view.findViewById(R.id.currentrecyclerview)
        currentAdapter = CurrentAdapter(mContext)
        recyclerview1.layoutManager = LinearLayoutManager(mContext)
        recyclerview1.adapter = currentAdapter
        btnCurrent.setOnClickListener {
            btnCurrent.background = resources.getDrawable(R.drawable.current_button)
            btnPast.background = resources.getDrawable(R.drawable.past_button2)
            btnCurrent.setTextColor(resources.getColor(R.color.white))
            btnPast.setTextColor(resources.getColor(R.color.balck))
            currentAdapter = CurrentAdapter(mContext)
            recyclerview1.layoutManager = LinearLayoutManager(mContext)
            recyclerview1.adapter = currentAdapter
        }
        btnPast.setOnClickListener {
            btnPast.background = resources.getDrawable(R.drawable.current_button)
            btnCurrent.background = resources.getDrawable(R.drawable.past_button2)
            btnPast.setTextColor(resources.getColor(R.color.white))
            btnCurrent.setTextColor(resources.getColor(R.color.balck))

            getOrderList()
            val bottomnavigationScreen = activity as BottomnavigationScreen

            pastAdapter = PastAdapter(bottomnavigationScreen, mOrderArrayList)
            recyclerview1.layoutManager = LinearLayoutManager(mContext)
            recyclerview1.adapter = pastAdapter

            recyclerview1.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentOffset > 1 && mOrderArrayList.size > 9)
                            getOrderList()
                    }
                }

            })

        }
        return view
    }

    override fun onResume() {
        super.onResume()
        reset = true

    }

    val viewModel: VendorViewModel by viewModels()


    private fun getOrderList() {
        if (reset) {
            currentOffset = 0
            mOrderArrayList.clear()
        }
        val hashMap = HashMap<String, String>()
        hashMap["offset"] = currentOffset.toString()
        hashMap["limit"] = "10"
        hashMap["type"] = "1"    // 0=>current 1=>past
        val mActivity = activity as BottomnavigationScreen
        viewModel.orderListVendorApi(mActivity, true, hashMap)
        viewModel.mResponse.observe(mActivity, this)

    }

    override fun onChanged(it: RestObservable?) {
        val bottomnavigationScreen = activity as BottomnavigationScreen
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorOrderListResponse) {
                    val mResponse: VendorOrderListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 10
                        mOrderArrayList.addAll(mResponse.body)
                        pastAdapter!!.notifyDataSetChanged()
                        reset = false
                    } else {
                        AppUtils.showErrorAlert(bottomnavigationScreen, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("User Address") && currentOffset > 1) {
                    } else
                        Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

}