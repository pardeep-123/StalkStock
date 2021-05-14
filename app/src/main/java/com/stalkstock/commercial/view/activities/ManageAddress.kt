package com.stalkstock.commercial.view.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.AddnewaddressActivity
import com.stalkstock.consumer.adapter.MangeaddressAdapter
import com.stalkstock.consumer.model.ModelUserAddressList
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.manage_address.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList

class ManageAddress : BaseActivity(), Observer<RestObservable> {
    override fun getContentId(): Int {
        return R.layout.manage_address
    }

    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelUserAddressList.Body> = ArrayList()
    var adapter: MangeaddressAdapter? = null


    override fun onResume() {
        super.onResume()
        reset = true
        getAddresses()
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getAddresses() {
        if (reset)
        {
            currentOffset=0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()
        map.put("offset", mUtils.createPartFromString(currentOffset.toString()))
        map.put("limit", mUtils.createPartFromString("5"))
        viewModel.getUserAddressListAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)
        currentOffset += 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        back.setOnClickListener {
            onBackPressed()
        }

/*
        btnDelete.setOnClickListener {
            openStartInfoApp()
        }
*/

        btn_signup.setOnClickListener {
            val intent = Intent(this, AddnewaddressActivity::class.java)
            intent.putExtra("key", "add")
            startActivity(intent)
        }

/*
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditAddressDetail2Activity::class.java)
            intent.putExtra("key", "edit")
            startActivity(intent)
        }
*/

        adapter = MangeaddressAdapter(this, currentModel)
        mangeaddress_recycle.layoutManager = LinearLayoutManager(this)
        mangeaddress_recycle.adapter = adapter
        mangeaddress_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    getAddresses()
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelUserAddressList) {
                    val mResponse: ModelUserAddressList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
                        getAddresses()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("User Address") && currentOffset > 1) {
                        AppUtils.showErrorAlert(this, "No more addresses")
                    } else
                        Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: ModelUserAddressList) {
        currentModel.addAll(mResponse.body)
        adapter!!.notifyDataSetChanged()
        reset = false
    }

    fun deleteAddressAPI(get: ModelUserAddressList.Body) {
        val map = HashMap<String, RequestBody>()
        viewModel.deleteUserAddressAPI(this, true, get.id)
        viewModel.homeResponse.observe(this, this)
    }


}
