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
import com.tamam.utils.others.AppUtils
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
    var mWhichScreen  = "1"  // 0 userHomeScreen 1 for vendor
    lateinit var adapter: HomedetailAdapter
    lateinit var mRecentSearchAdapter: RecentSearchAdapter
    private var mProductId = ""
    private var mProductName = ""

    override fun getContentId(): Int {
        return R.layout.activity_search_screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /* if (intent.hasExtra("whichScreen"))
        {
            mWhichScreen = intent.getStringExtra("whichScreen")!!
        currentDeliveryType = intent.getStringExtra("currentDeliveryType")!!
            adapter = HomedetailAdapter(this, currentModel, currentDeliveryType, this)
            detail_recycle.setAdapter(adapter)
            detail_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentOffset > 1 && currentModel.size > 4) {
                            currentOffset += 5
                            getProductAsPerCatSub()
                        }
                    }

                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
        mRecentSearchAdapter = RecentSearchAdapter(this, recentSearchList, this)
        searchRecycle.setAdapter(mRecentSearchAdapter)
        id_backarrow.setOnClickListener { onBackPressed() }
        editTextSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getProductAsPerCatSub()
                return@OnEditorActionListener true
            }
            false
        })
        viewModel.homeResponse.observe(this, this)*/
    }

    override fun onResume() {
        super.onResume()
        getRecentSearchAPI()
    }

    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.addAll(mResponse.body)
        adapter!!.notifyDataSetChanged()
        reset = false
    }

    /*Params Can be used in API:-
    offset:0
limit:10
categoryId:38
sortBy:high_to_low ----sort by high_to_low => high to low low_to_high =>low to high
lowPrice:0
highPrice:60
subCategoryId :
latitude:30.862749
deliveryType =0 pickup , 1 deli 2- all
longitude:75.901640
search:c78 ----not compulsory
    * */
    private fun getProductAsPerCatSub() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()
        map.put("offset", mUtils.createPartFromString(currentOffset.toString()))
        map.put("limit", mUtils.createPartFromString("5"))
        map.put("search", mUtils.createPartFromString(editTextSearch.text.toString()))
        map.put("deliveryType", mUtils.createPartFromString(currentDeliveryType))
//        map.put("latitude", mUtils.createPartFromString(mLat.toString()))
//        map.put("longitude", mUtils.createPartFromString(mLong.toString()))
        viewModel.getProductAccToCategorySubcategoryAPI(this, true, map)

//        whichApi = "productList"
    }

    fun addRecentSearchApi(productId: String, name: String)
    {
        mProductId = productId
        mProductName = name
        val map = HashMap<String, String>()
        map.put("productId",productId)
        viewModel.addRecentSearchAPI(this, true, map)
    }

    fun getRecentSearchAPI()
    {
        val map = HashMap<String, String>()
        viewModel.getRecentSearchAPI(this, true, map)
    }

    fun deleteRecentSearchAPI(searchId:String)
    {
        val map = HashMap<String, String>()
        map.put("searchId",searchId)
        viewModel.deleteRecentSearchAPI(this, true, map)
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

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}