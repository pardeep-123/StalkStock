package com.stalkstock.vender.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelVendorProductList
import com.stalkstock.vender.adapter.TestAdapter
import com.stalkstock.vender.ui.*
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_home_two_fragment.*
import okhttp3.RequestBody
import com.stalkstock.consumer.activities.FilterActivity
import java.util.HashMap

//Vendor Home
class MainHomeFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    var mcontext: Context? = null
    private var testAdapter: TestAdapter? = null
    lateinit var recyclerview: RecyclerView
    var clickMsg = 0

    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelVendorProductList.Body.Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home_two_fragment, container, false)
        mcontext = activity
        recyclerview = view.findViewById(R.id.recyclerview)
        testAdapter = TestAdapter(mcontext!!,currentModel)
        recyclerview.setLayoutManager(LinearLayoutManager(mcontext))
        recyclerview.setAdapter(testAdapter)

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && currentModel.size>4)
                        getVendorProducts()
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        val imageView = view.findViewById<ImageView>(R.id.notification)
        val imageView1 = view.findViewById<ImageView>(R.id.filter)
        val iv_msg = view.findViewById<ImageView>(R.id.iv_msg)
        val editText = view.findViewById<RelativeLayout>(R.id.edit_search)
        val button = view.findViewById<Button>(R.id.addproductbutton)
        button.setOnClickListener(this)
        imageView.setOnClickListener(this)
        imageView1.setOnClickListener(this)
        editText.setOnClickListener(this)
        iv_msg.setOnClickListener {
            if (clickMsg == 0) {
                clickMsg = 1
                val intent = Intent(requireActivity(), MessageActivity::class.java)
                startActivity(intent)
            } else {
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        clickMsg = 0
        reset = true
        getVendorProducts()
    }

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
            map.put("offset", mActivity.mUtils.createPartFromString(currentOffset.toString()))
            map.put("limit", mActivity.mUtils.createPartFromString("5"))
            viewModel.getVendorProductListAPI(mActivity, true, map)
            viewModel.homeResponse.observe(this, this)
        }

    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.notification -> {
                val intent = Intent(activity, NotificationScreen::class.java)
                startActivity(intent)
            }
            R.id.filter -> {
                val intent2 = Intent(activity, FilterActivity::class.java)
                intent2.putExtra("from","MainHomeFragment")
                startActivity(intent2)
            }
            R.id.edit_search -> {
                val intent1 = Intent(activity, SearchScreen::class.java)
                startActivity(intent1)
            }
            R.id.addproductbutton -> {
                val i = Intent(activity, SelectCategory::class.java)
                startActivity(i)
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        val bottomnavigationScreen = activity as BottomnavigationScreen
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelVendorProductList) {
                    val mResponse: ModelVendorProductList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(requireActivity(), mResponse.message.toString())
                        reset = true
                        getVendorProducts()
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("User Address") && currentOffset > 1) {
//                        AppUtils.showErrorAlert(this, "No more addresses")
                    } else
                        Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT).show()
//

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: ModelVendorProductList) {
        txtHomeTotal.setText("Total:${mResponse.body.total}")
        currentModel.addAll(mResponse.body.product)
        testAdapter!!.notifyDataSetChanged()
        reset = false
    }
}