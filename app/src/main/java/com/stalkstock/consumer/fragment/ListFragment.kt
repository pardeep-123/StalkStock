package com.stalkstock.consumer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.adapter.MyordersAdapter
import com.stalkstock.consumer.model.OrderListModel
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : Fragment(), Observer<RestObservable> {
    var adapter: MyordersAdapter? = null
    lateinit var mActivity:MainConsumerActivity
    val viewModel: HomeViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0
    private val mOrderArrayList: ArrayList<OrderListModel.Body> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //mActivity = context as requ
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        viewModel.homeResponse.observe(requireActivity(), this)
        adapter = MyordersAdapter(requireActivity(),mOrderArrayList)
        view.myorder_recycle.adapter = adapter
        view.myorder_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && mOrderArrayList.size > 9)
                        getOrderList()
                }
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reset = true
        getOrderList()
    }

    private fun getOrderList() {
        if (reset) {
            currentOffset = 0
            mOrderArrayList.clear()
        }
        val hashMap = HashMap<String, String>()
        hashMap["offset"] = currentOffset.toString()
        hashMap["limit"] = "10"
        viewModel.getOrderListAPI(requireActivity(), true, hashMap)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderListModel) {
                    val mResponse: OrderListModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 10
                        mOrderArrayList.addAll(mResponse.body)
                        adapter!!.notifyData(mOrderArrayList)
                        adapter!!.notifyDataSetChanged()
                        reset = false
                        tvNoOrders.visibility = if(mOrderArrayList.isEmpty()) View.VISIBLE else View.GONE
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireActivity(), it.data as String, Toast.LENGTH_SHORT).show() }
                else
                { Toast.makeText(requireActivity(), it.error!!.toString(), Toast.LENGTH_SHORT).show() }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}