package com.stalkstock.consumer.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.ProductsAdapter
import com.stalkstock.consumer.model.ModelProductVendorList
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_product.*
import okhttp3.RequestBody
import java.util.HashMap

class ProductActivity : BaseActivity(), Observer<RestObservable> {
    private var currentProductID = ""
    private var currentDelivery_type = ""

    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"//sort by high_to_low => high to low low_to_high =>low to high

    var context: ProductActivity? = null
    lateinit var product_recycle: RecyclerView
    lateinit var adapter: ProductsAdapter
    lateinit var back: ImageView
    lateinit var notification: ImageView
    lateinit var search: ImageView
    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelProductVendorList.Body.ProductSeller> = ArrayList()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    lateinit var fillter: ImageView
    override fun getContentId(): Int {
        return R.layout.activity_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        product_recycle = findViewById(R.id.product_recycle)
        back = findViewById(R.id.back)
        notification = findViewById(R.id.notification)
        search = findViewById(R.id.search)
        fillter = findViewById(R.id.fillter)
        notification.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, NotificationFirstActivity::class.java)
            startActivity(intent)
        })
        search.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainConsumerActivity::class.java)
            intent.putExtra("is_open", "2")
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
           /* val intent = Intent(context, SearchScreen::class.java)
            startActivity(intent)*/
        })
        fillter.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra("from", "ProductActivity")
            resultLauncher.launch(intent)
        })

        currentProductID = intent.getStringExtra("product_id")!!
        currentDelivery_type = intent.getStringExtra("delivery_type")!!
        txtTitle.setText(intent.getStringExtra("title"))!!
        getProductAsVendor()
        back.setOnClickListener(View.OnClickListener { finish() })
        adapter = ProductsAdapter(this,currentModel,currentDelivery_type)
        product_recycle.setLayoutManager(LinearLayoutManager(context))
        product_recycle.setAdapter(adapter)


        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    currentLowPrice = data!!.getStringExtra("lowPrice")!!
                    currentHighPrice = data!!.getStringExtra("highPrice")!!
                    currentSortBy = data!!.getStringExtra("sortBy")!!

                    reset = true
                    getProductAsVendor()
                }
            }

        product_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && currentModel.size > 4)
                        getProductAsVendor()
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getProductAsVendor() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val hashMap = HashMap<String, RequestBody>()
        hashMap.put("offset", mUtils.createPartFromString(currentOffset.toString()))
        hashMap.put("limit", mUtils.createPartFromString("5"))
        hashMap.put("sortBy", mUtils.createPartFromString(currentSortBy))
        hashMap.put("lowPrice", mUtils.createPartFromString(currentLowPrice))
        hashMap.put("highPrice", mUtils.createPartFromString(currentHighPrice))
        hashMap["product_id"] = mUtils.createPartFromString(currentProductID)
        hashMap["deliveryType"] = mUtils.createPartFromString(currentDelivery_type)
        viewModel.userGetVendorAsPerProductAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is ModelProductVendorList) {
                    val mResponse: ModelProductVendorList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
                        setAdapterVendorList(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
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

    private fun setAdapterVendorList(mResponse: ModelProductVendorList) {
        currentModel.addAll(mResponse.body.productSeller)
        adapter!!.notifyDataSetChanged()
        reset = false
    }
}