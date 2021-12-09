package com.stalkstock.vender.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
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
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.activity_home_two_fragment.*
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class MainHomeFragment : Fragment(), View.OnClickListener, Observer<RestObservable>, TextWatcher {
    var mcontext: Context? = null
    private var testAdapter: TestAdapter? = null

    var clickMsg = 0
    private var reset = false
    private var currentOffset = 0
    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"
    var tv_Notfound: TextView?=null
    var rvCategory: RecyclerView?=null
    var etSearch: EditText?=null

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var currentModel: ArrayList<ModelVendorProductList.Body.Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home_two_fragment, container, false)
        mcontext = activity

        tv_Notfound = view.findViewById(R.id.tvNoData)
        etSearch = view.findViewById(R.id.edtSearch)
        rvCategory = view.findViewById(R.id.recyclerview)
        testAdapter = TestAdapter(mcontext!!,currentModel,this)
        rvCategory?.layoutManager = LinearLayoutManager(mcontext)
        rvCategory?.adapter = testAdapter
        testAdapter?.arrayList=currentModel

        rvCategory?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && currentModel.size>4)
                        getVendorProducts()
                } } })

        val notification = view.findViewById<ImageView>(R.id.notification)
        val filter = view.findViewById<ImageView>(R.id.filter)
        val iv_msg = view.findViewById<ImageView>(R.id.iv_msg)
       // val editText = view.findViewById<RelativeLayout>(R.id.edit_search)
        val button = view.findViewById<Button>(R.id.addproductbutton)
        button.setOnClickListener(this)
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
        getVendorProducts()
    }

   /* override fun onResume() {
        super.onResume()
        clickMsg = 0
        reset = true
        getVendorProducts()
    }*/

    val viewModel: HomeViewModel by viewModels()

    private fun getVendorProducts() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()
        if (activity!=null)
        {
            val mActivity = activity as BottomnavigationScreen
            map["sortBy"] = mActivity.mUtils.createPartFromString(currentSortBy.toString())
            map["lowPrice"] = mActivity.mUtils.createPartFromString(currentLowPrice.toString())
            map["highPrice"] = mActivity.mUtils.createPartFromString(currentHighPrice.toString())
            map["offset"] = mActivity.mUtils.createPartFromString(currentOffset.toString())
            map["limit"] = mActivity.mUtils.createPartFromString("5")
            viewModel.getVendorProductListAPI(mActivity, true, map)
            viewModel.homeResponse.observe(this, this)
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
                intent2.putExtra("from","MainHomeFragment")
                startActivityForResult(intent2,0)
            }
            /*R.id.edit_search -> {
                val intent1 = Intent(activity, SearchScreen::class.java)
                startActivity(intent1)
            }*/
            R.id.addproductbutton -> {
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

    private fun editDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.biddetailsalertbox)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.submitbutton.setOnClickListener {
            if (dialog.edtSellingPrice.text.toString().isEmpty()) {
                dialog.edtSellingPrice.error = resources.getString(R.string.please_enter_sale_price)
            } else if (dialog.edtSellngDesc.text.toString().isEmpty()) {
                dialog.edtSellngDesc.error = resources.getString(R.string.please_enter_sale_terms)
            } else {
                val hashMap = HashMap<String, RequestBody>()
//                hashMap["bidId"] = mUtil.createPartFromString("bidId")
//                hashMap["amount"] = mUtil.createPartFromString(dialog.edtSellingPrice?.text.toString())
//                hashMap["description"] = mUtil.createPartFromString(dialog.edtSellngDesc?.text.toString())
//                viewModel.vendorAcceptBid(this, true, hashMap)
//                viewModel.mResponse.observe(this, this)

                bidamt.visibility = View.VISIBLE
                biddisc.visibility = View.VISIBLE
                placebid_button.tag = 1
                placebid_button.text = "Place Bid"
                if (placebid_button.text.toString() == "Place Bid") {
                    placebid_button.text = "Edit Bid"
                    it.tag = 1 //pause
                } else {
                    val status = it.tag as Int
                    if (status == 1) {
                        it.tag = 0 //pause
                    } else {
                        placebid_button.text = "Edit Bid"
                        it.tag = 1 //pause
                    }
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelVendorProductList) {
                    val mResponse: ModelVendorProductList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
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
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("User Address") && currentOffset > 1) {
                    } else
                        Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: ModelVendorProductList) {
        txtHomeTotal.text = "Total:${mResponse.body.product.size}"
        currentModel.clear()

        currentModel.addAll(mResponse.body.product)
        testAdapter?.arrayList=currentModel

        if(currentModel.size==0){
            tvNoData.visibility=View.VISIBLE
            recyclerview.visibility=View.GONE
        }else{
            tvNoData.visibility=View.GONE
            recyclerview.visibility=View.VISIBLE
            testAdapter!!.notifyDataSetChanged()
            reset = false
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        testAdapter?.filter?.filter(s.toString().trim())
    }

    override fun afterTextChanged(s: Editable?) {
    }

   /* override fun onResume() {
        super.onResume()
        getVendorProducts()
    }*/
}