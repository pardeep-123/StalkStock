package com.stalkstock.vender.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.FilterActivity
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelVendorProductList
import com.stalkstock.vender.adapter.TestAdapter
import com.stalkstock.vender.ui.BottomnavigationScreen
import com.stalkstock.vender.ui.MessageActivity
import com.stalkstock.vender.ui.SelectCategory
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home_two_fragment.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class MainHomeFragment : Fragment(), View.OnClickListener, Observer<RestObservable>, TextWatcher {
    var mcontext: Context? = null
    private var testAdapter: TestAdapter? = null

    var clickMsg = 0
    private var loading = true
    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"
    var tvNoProducts: LinearLayout? = null
    var llAddProduct: LinearLayout? = null
    var rvCategory: RecyclerView? = null
    var etSearch: EditText? = null
    var totalItemCount: Int = 0
    var visibleItemCount: Int = 0
    var previousTotal: Int = 0
    var pastVisiblesItems: Int = 0
    private var reset = false
    private var currentOffset = 0

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var currentModel: ArrayList<ModelVendorProductList.Body.Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home_two_fragment, container, false)
        mcontext = activity

        tvNoProducts = view.findViewById(R.id.tvNoProducts)
        llAddProduct = view.findViewById(R.id.llAddProduct)
        etSearch = view.findViewById(R.id.edtSearch)
        rvCategory = view.findViewById(R.id.recyclerview)
        testAdapter = TestAdapter(mcontext!!, currentModel, this)
        var mLayoutManager = LinearLayoutManager(requireContext())
        rvCategory?.layoutManager = mLayoutManager
        rvCategory?.adapter = testAdapter
        testAdapter?.arrayList = currentModel

      /*  rvCategory?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && currentModel.size>4)
                        getVendorProducts()
                } } })*/




        val notification = view.findViewById<ImageView>(R.id.notification)
        val filter = view.findViewById<ImageView>(R.id.filter)
        val iv_msg = view.findViewById<ImageView>(R.id.iv_msg)

        // val editText = view.findViewById<RelativeLayout>(R.id.edit_search)
        val button = view.findViewById<Button>(R.id.addproductbutton)
        val addNewProduct = view.findViewById<Button>(R.id.addNewProduct)
        button.setOnClickListener(this)
        addNewProduct.setOnClickListener(this)
        notification.setOnClickListener(this)
        filter.setOnClickListener(this)
        etSearch?.addTextChangedListener(this)
        //editText.setOnClickListener(this)
        iv_msg.setOnClickListener {

            if (clickMsg == 0) {
                clickMsg = 1
                val intent = Intent(requireActivity(), MessageActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   getVendorProducts()
    }


    val viewModel: HomeViewModel by viewModels()

    private fun getVendorProducts() {

        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()

        if (activity != null) {
            val mActivity = activity as BottomnavigationScreen
            map["sortBy"] = mActivity.mUtils.createPartFromString(currentSortBy.toString())
            map["lowPrice"] =
                mActivity.mUtils.createPartFromString(currentLowPrice.toString())
            map["highPrice"] =
                mActivity.mUtils.createPartFromString(currentHighPrice.toString())
            map["offset"] = mActivity.mUtils.createPartFromString(currentOffset.toString())
            map["limit"] = mActivity.mUtils.createPartFromString("500")
            viewModel.getVendorProductListAPI(mActivity, true, map)
            viewModel.homeResponse.observe(requireActivity(), this)
        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.notification -> {
                //  editDialog()
                val intent = Intent(activity, NotificationFirstActivity::class.java)
                startActivity(intent)
            }
            R.id.filter -> {
                val intent2 = Intent(activity, FilterActivity::class.java)
                intent2.putExtra("from", "MainHomeFragment")
                startActivityForResult(intent2, 0)
            }
            /*R.id.edit_search -> {
                val intent1 = Intent(activity, SearchScreen::class.java)
                startActivity(intent1)
            }*/
            R.id.addproductbutton -> {
                val i = Intent(activity, SelectCategory::class.java)
                startActivity(i)
            }
            R.id.addNewProduct -> {
                val i = Intent(activity, SelectCategory::class.java)
                startActivity(i)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 0) {
            if (resultCode === Activity.RESULT_OK) {
                currentLowPrice = data!!.getStringExtra("lowPrice")!!
                currentHighPrice = data.getStringExtra("highPrice")!!
                currentSortBy = data.getStringExtra("sortBy")!!
                 getVendorProducts()
            }
            if (resultCode === Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelVendorProductList) {
                    val mResponse: ModelVendorProductList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        totalItemCount = mResponse.body.total
                        currentOffset += 500
                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(requireActivity(), mResponse.message)
                        reset = true
                           getVendorProducts()
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (it.error!!.toString().contains("User Address") && currentOffset > 1) {
                    } else
                        Toast.makeText(
                            requireContext(),
                            it.error.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: ModelVendorProductList) {
        txtHomeTotal.text = "Total:${mResponse.body.total}"
        currentModel.addAll(mResponse.body.product)
        testAdapter?.arrayList = currentModel
        reset=false
        if (currentModel.size == 0) {
            tvNoProducts?.visibility = View.VISIBLE
            rvCategory?.visibility = View.GONE
            llAddProduct?.visibility = View.GONE

        } else {
            tvNoProducts?.visibility = View.GONE
            rvCategory?.visibility = View.VISIBLE
            llAddProduct?.visibility = View.VISIBLE
            testAdapter?.notifyDataSetChanged()

        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        testAdapter?.filter?.filter(s.toString().trim())
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun onResume() {
        super.onResume()
        getVendorProducts()
    }
}