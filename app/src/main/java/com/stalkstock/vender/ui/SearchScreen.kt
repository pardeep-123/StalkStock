package com.stalkstock.vender.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.adapter.HomedetailAdapter
import com.stalkstock.consumer.model.AddRecentSearchResponse
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.consumer.model.RecentSearchListResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.adapter.RecentSearchAdapter
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_search_screen.*
import okhttp3.RequestBody
import java.util.*


class SearchScreen : BaseActivity(), Observer<RestObservable> {

    val viewModel: HomeViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelProductListAsPerSubCat.Body> = ArrayList()
    private var recentSearchList: ArrayList<RecentSearchListResponse.Body> = ArrayList()
    var currentDeliveryType = "0" // 0- pickup,1-deelivery , 2 -all
    lateinit var adapter: HomedetailAdapter
    lateinit var mRecentSearchAdapter: RecentSearchAdapter
    private var mProductId = ""
    private var mProductName = ""

    override fun getContentId(): Int {
        return R.layout.activity_search_screen
    }

    override fun onResume() {
        super.onResume()
        getRecentSearchAPI()
        id_backarrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.addAll(mResponse.body)
        adapter!!.notifyDataSetChanged()
        reset = false
    }

    fun getRecentSearchAPI()
    {
        val map = HashMap<String, String>()
        viewModel.getRecentSearchAPI(this, true, map)
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is ModelProductListAsPerSubCat) {
                    val mResponse: ModelProductListAsPerSubCat = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is AddRecentSearchResponse)
                {
                    val mResponse: AddRecentSearchResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code)
                    {
                        val intent = Intent(this, ProductActivity::class.java)
                        intent.putExtra("product_id",mProductId)
                        intent.putExtra("title",mProductName)
                        intent.putExtra("delivery_type",currentDeliveryType)
                        startActivity(intent)
                    }
                }
                if (it.data is RecentSearchListResponse)
                {
                    val mResponse: RecentSearchListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code)
                    {
                        recentSearchList.clear()
                        recentSearchList.addAll(mResponse.body)
                        mRecentSearchAdapter.notifyDataSetChanged()
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                        currentModel.clear()
                        adapter!!.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                        currentModel.clear()
                        adapter!!.notifyDataSetChanged()

                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}