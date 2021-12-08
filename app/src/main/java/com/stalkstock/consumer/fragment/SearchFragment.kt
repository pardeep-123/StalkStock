package com.stalkstock.consumer.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.adapter.HomedetailAdapter
import com.stalkstock.consumer.model.AddRecentSearchResponse
import com.stalkstock.consumer.model.DeleteRecentSearchResponse
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.consumer.model.RecentSearchListResponse
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.adapter.RecentSearchAdapter
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_search_screen.*
import kotlinx.android.synthetic.main.activity_search_screen.view.*
import kotlinx.android.synthetic.main.row_manageaddress.view.*
import okhttp3.RequestBody
import java.util.*

class SearchFragment : Fragment(), Observer<RestObservable> {

    val viewModel: HomeViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelProductListAsPerSubCat.Body> = ArrayList()
    private var recentSearchList: ArrayList<RecentSearchListResponse.Body> = ArrayList()
    lateinit var adapter: HomedetailAdapter
    lateinit var mRecentSearchAdapter: RecentSearchAdapter
    private var mProductId = ""
    private var mProductName = ""
    lateinit var mActivity: MainConsumerActivity
    private var mWhichApi = 0
    private lateinit var viewFrag: View
    var deliveryType=""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainConsumerActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFrag = inflater.inflate(R.layout.activity_search_screen, container, false)
        viewFrag.id_backarrow.visibility = View.GONE
        return viewFrag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        deliveryType= bundle?.getString("deliveryType")!!
        adapter = HomedetailAdapter(
            mActivity,
            currentModel,
            deliveryType,
            this
        ,null)
        view.detail_recycle.adapter = adapter
        view.detail_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && currentModel.size > 4) {
                        currentOffset += 5
                        //getProductAsPerCatSub()
                    }
                }
            }
        })

        mRecentSearchAdapter = RecentSearchAdapter(mActivity, recentSearchList, this)
        view.searchRecycle.adapter = mRecentSearchAdapter
        view.editTextSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getProductAsPerCatSub()
                return@OnEditorActionListener true
            }
            false
        })
        view.search.setOnClickListener {
            if (view.editTextSearch.text.toString().isNotEmpty()) getProductAsPerCatSub()

        }
        getProductAsPerCatSub()
        viewModel.homeResponse.observe(mActivity, this)
    }

    override fun onResume() {
        super.onResume()
        getRecentSearchAPI()
    }

    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.clear()


        // currentModel.addAll(mResponse.body)

        val list = mResponse.body.sortedBy { it.mrp.toFloat() }
        if (GlobalVariables.FilterVariables.currentSortBy == "low_to_high") {
            currentModel.addAll(list)
        } else {
            currentModel.addAll(list.reversed())
        }

        tvNoProducts.visibility = if (currentModel.isEmpty()) View.VISIBLE else View.GONE
        adapter!!.notifyDataSetChanged()
        reset = false
    }

    private fun getProductAsPerCatSub() {
        mWhichApi = 0
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()
        map["offset"] = mActivity.mUtils.createPartFromString("0")
        map["limit"] = mActivity.mUtils.createPartFromString("50")
        map["search"] =
            mActivity.mUtils.createPartFromString(viewFrag.editTextSearch.text.toString())
        map["deliveryType"] =
            mActivity.mUtils.createPartFromString(mActivity.currentDeliveryType.toString())
        viewModel.getProductAccToCategorySubcategoryAPI(mActivity, true, map)
    }

    fun addRecentSearchApi(productId: String, name: String) {
        mWhichApi = 1
        mProductId = productId
        mProductName = name
        val map = HashMap<String, String>()
        map["productId"] = productId
        viewModel.addRecentSearchAPI(mActivity, true, map)
    }

    private fun getRecentSearchAPI() {
        mWhichApi = 2
        val map = HashMap<String, String>()
        viewModel.getRecentSearchAPI(mActivity, true, map)
    }

    fun deleteRecentSearchAPI(searchId: String) {
        mWhichApi = 3
        val map = HashMap<String, String>()
        map["searchId"] = searchId
        viewModel.deleteRecentSearchAPI(mActivity, true, map)
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
                        AppUtils.showErrorAlert(mActivity, mResponse.message)
                    }
                }
                if (it.data is AddRecentSearchResponse) {
                    val mResponse: AddRecentSearchResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        val intent = Intent(mActivity, ProductActivity::class.java)
                        intent.putExtra("product_id", mProductId)
                        intent.putExtra("title", mProductName)
                        intent.putExtra("deliveryType", deliveryType)
                        startActivity(intent)
                    }
                }
                if (it.data is RecentSearchListResponse) {
                    val mResponse: RecentSearchListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        recentSearchList.clear()
                        recentSearchList.addAll(mResponse.body)
                        mRecentSearchAdapter.notifyDataSetChanged()
                    }
                }
                if (it.data is DeleteRecentSearchResponse) {
                    val mResponse: DeleteRecentSearchResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        getRecentSearchAPI()
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    tvNoProducts.visibility=View.VISIBLE
                    //Toast.makeText(mActivity, it.data as String, Toast.LENGTH_SHORT).show()
                    if (mWhichApi == 0) {
                        currentModel.clear()
                        adapter!!.notifyDataSetChanged()
                    }
                } else {
                    tvNoProducts.visibility=View.VISIBLE
                  //  Toast.makeText(mActivity, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                    if (mWhichApi == 0) {
                        currentModel.clear()
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    fun setSearchTag(search: String) {
        viewFrag.editTextSearch.setText(search)
        getProductAsPerCatSub()
    }

}