package com.stalkstock.consumer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.MyordersAdapter
import com.stalkstock.consumer.model.ModelProductVendorList
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_my_order.*
import java.util.HashMap

class MyOrderActivity : AppCompatActivity(), Observer<RestObservable> {

    var adapter: MyordersAdapter? = null
    val viewModel: HomeViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        /* ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
onBackPressed();            }
        });*/
        ivBackButton.setOnClickListener { onBackPressed() }
//        adapter = MyordersAdapter(this, mOrderArrayList)
        myorder_recycle.layoutManager = LinearLayoutManager(this)
        myorder_recycle.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getOrderList()
    }

    private fun getOrderList() {
        if (reset) {
            currentOffset = 0
//            currentModel.clear()
        }
        val hashMap = HashMap<String, String>()
        hashMap.put("offset", currentOffset.toString())
        hashMap.put("limit", "10")
        viewModel.getOrderListAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is ModelProductVendorList) {
                    val mResponse: ModelProductVendorList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 10
//                        setAdapterVendorList(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}