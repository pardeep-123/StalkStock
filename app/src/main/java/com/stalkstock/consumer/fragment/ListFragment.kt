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
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), Observer<RestObservable> {
    var adapter: MyordersAdapter? = null
    lateinit var mActivity:MainConsumerActivity
    val viewModel: HomeViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0
    private val mOrderArrayList: ArrayList<OrderListModel.Body> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainConsumerActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
       /* adapter = MyordersAdapter(mActivity,mOrderArrayList)
        myorder_recycle!!.setAdapter(adapter)*/

        viewModel.homeResponse.observe(mActivity, this)
        adapter = MyordersAdapter(mActivity,mOrderArrayList)
        view.myorder_recycle.setAdapter(adapter)
        view.myorder_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && mOrderArrayList.size > 9)
                        getOrderList()
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        reset = true
        getOrderList()
    }

    private fun getOrderList() {
        if (reset) {
            currentOffset = 0
            mOrderArrayList.clear()
        }
        val hashMap = HashMap<String, String>()
        hashMap.put("offset", currentOffset.toString())
        hashMap.put("limit", "10")
        viewModel.getOrderListAPI(mActivity, true, hashMap)


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
                    } else {
                        AppUtils.showErrorAlert(mActivity, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(mActivity, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mActivity, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}