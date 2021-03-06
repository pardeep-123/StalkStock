package com.stalkstock.consumer.activities

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.consumer.adapter.HomedetailAdapter
import com.stalkstock.consumer.adapter.TitleAdapter
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.utils.SliderItemTitleModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Utils.CheckLocationActivity
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_homedetails.*
import okhttp3.RequestBody
import java.util.*

class HomedetailsActivity : CheckLocationActivity(), Observer<RestObservable> {

    private var whichApi = ""
    private var currentSubCatID = ""
    private lateinit var titleAdapter: TitleAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelProductListAsPerSubCat.Body> = ArrayList()
    private var mLat: Double = 0.0
    private var mLong: Double = 0.0
    var stAddress = ""
    private var currentCatId = ""
    var currentDeliveryType = "0" // 0- pickup,1-deelivery , 2 -all
    var productType = "0"
    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"//sort by high_to_low => high to low low_to_high =>low to high
    var context: HomedetailsActivity? = null
    lateinit var detail_recycle: RecyclerView
    lateinit var adapter: HomedetailAdapter
    lateinit var meat: RelativeLayout
    lateinit var dairy: RelativeLayout
    lateinit var fish: RelativeLayout
    lateinit var food: RelativeLayout
    lateinit var chat: TextView
    lateinit var group: TextView
    lateinit var fish_text: TextView
    lateinit var txtLocation: TextView
    lateinit var food_text: TextView
    lateinit var ivNotification: ImageView
    lateinit var search: ImageView
    lateinit var fillter: ImageView
    lateinit var oll: ImageView
    lateinit var request_view: View
    lateinit var inprogress_view: View
    lateinit var inprogress_view1: View
    lateinit var inprogress_view2: View
    lateinit var rv_title: RecyclerView

    override fun getContentId(): Int { return R.layout.activity_homedetails }

    override fun onPermissionGranted() {}

    override fun onLocationGet(latitude: String?, longitude: String?) {
        mLat = latitude!!.toDouble()
        mLong = longitude!!.toDouble()
        completeAddress(latitude.toDouble(), longitude.toDouble()) }

    private fun completeAddress(latitude: Double, longitude: Double) {
        try {
            val geocoder = Geocoder(this, Locale.getDefault())

            val addresses: List<Address> = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            val address: String =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            if (address != null) {

                stAddress = address
            }
        } catch (e: Exception) {
            stAddress = "No location found"
        }


        txtLocation.text = stAddress

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        currentCatId = intent.getStringExtra("catId")!!
        currentDeliveryType = intent.getStringExtra("currentDeliveryType")!!
        currentHighPrice = intent.getStringExtra("currentHighPrice")!!
        currentLowPrice = intent.getStringExtra("currentLowPrice")!!
        currentSortBy = intent.getStringExtra("currentSortBy")!!
        productType = intent.getStringExtra("productType")!!

        detail_recycle = findViewById(R.id.detail_recycle)
        rv_title = findViewById(R.id.rv_title)
        meat = findViewById(R.id.meat)
        dairy = findViewById(R.id.dairy)
        fish = findViewById(R.id.fish)
        food = findViewById(R.id.food)
        chat = findViewById(R.id.chat)
        group = findViewById(R.id.group)
        fish_text = findViewById(R.id.fish_text)
        food_text = findViewById(R.id.food_text)
        request_view = findViewById(R.id.request_view)
        txtLocation = findViewById(R.id.txtLocation)
        inprogress_view = findViewById(R.id.inprogress_view)
        inprogress_view1 = findViewById(R.id.inprogress_view1)
        inprogress_view2 = findViewById(R.id.inprogress_view2)
        ivNotification = findViewById(R.id.ivNotification)
        fillter = findViewById(R.id.fillter)
        search = findViewById(R.id.search)
        oll = findViewById(R.id.oll)
        oll.setOnClickListener { onBackPressed() }
        checkPermissionLocation()
        meat.setOnClickListener {
            chat.setTextColor(resources.getColor(R.color.theme_green))
            group.setTextColor(resources.getColor(R.color.home_grey))
            fish_text.setTextColor(resources.getColor(R.color.home_grey))
            food_text.setTextColor(resources.getColor(R.color.home_grey))
            request_view.setBackgroundColor(resources.getColor(R.color.theme_green))
            inprogress_view.setBackgroundColor(resources.getColor(R.color.home_grey))
            inprogress_view1.setBackgroundColor(resources.getColor(R.color.home_grey))
            inprogress_view2.setBackgroundColor(resources.getColor(R.color.home_grey))
        }
        dairy.setOnClickListener {
            chat.setTextColor(resources.getColor(R.color.home_grey))
            group.setTextColor(resources.getColor(R.color.theme_green))
            fish_text.setTextColor(resources.getColor(R.color.home_grey))
            food_text.setTextColor(resources.getColor(R.color.home_grey))
            request_view.setBackgroundColor(
                resources.getColor(R.color.home_grey)
            )
            inprogress_view.setBackgroundColor(resources.getColor(R.color.theme_green))
            inprogress_view1.setBackgroundColor(resources.getColor(R.color.home_grey))
            inprogress_view2.setBackgroundColor(resources.getColor(R.color.home_grey))
        }
        fish.setOnClickListener {
            chat.setTextColor(resources.getColor(R.color.home_grey))
            group.setTextColor(resources.getColor(R.color.home_grey))
            fish_text.setTextColor(resources.getColor(R.color.theme_green))
            food_text.setTextColor(resources.getColor(R.color.home_grey))
            request_view.setBackgroundColor(
                resources.getColor(R.color.home_grey)
            )
            inprogress_view.setBackgroundColor(resources.getColor(R.color.home_grey))
            inprogress_view1.setBackgroundColor(resources.getColor(R.color.theme_green))
            inprogress_view2.setBackgroundColor(resources.getColor(R.color.home_grey))
        }
        food.setOnClickListener {
            chat.setTextColor(resources.getColor(R.color.home_grey))
            group.setTextColor(resources.getColor(R.color.home_grey))
            fish_text.setTextColor(resources.getColor(R.color.home_grey))
            food_text.setTextColor(resources.getColor(R.color.theme_green))

            request_view.setBackgroundColor(
                resources.getColor(R.color.home_grey)
            )
            inprogress_view.setBackgroundColor(resources.getColor(R.color.home_grey))
            inprogress_view1.setBackgroundColor(resources.getColor(R.color.home_grey))
            inprogress_view2.setBackgroundColor(resources.getColor(R.color.theme_green))
        }
        ivNotification.setOnClickListener {
            val intent = Intent(context, NotificationFirstActivity::class.java)
            startActivity(intent)
        }
        search.setOnClickListener {
            val intent = Intent(this, MainConsumerActivity::class.java)
            intent.putExtra("is_open", "2")
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
        fillter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            intent.putExtra("from", "HomedetailsActivity")
            resultLauncher.launch(intent)
        }
        adapter = HomedetailAdapter(this, currentModel, currentDeliveryType, null,this)
        detail_recycle.adapter = adapter

        setTitleAdapter()

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    currentLowPrice = data!!.getStringExtra("lowPrice")!!
                    currentHighPrice = data.getStringExtra("highPrice")!!
                    currentSortBy = data.getStringExtra("sortBy")!!
                    reset = true
                    getProductAsPerCatSub()
                }
            }
        getSubCategories()
    }

    val viewModel: HomeViewModel by viewModels()

    private var listSub: ArrayList<SliderItemTitleModel> = ArrayList()
    private var listSubCategoryBody: ArrayList<ModelSubCategoriesList.Body> = ArrayList()

    private fun getSubCategories() {

        whichApi = "subCat"
        val hashMap = HashMap<String, RequestBody>()
        hashMap["category_id"] = mUtils.createPartFromString(currentCatId)
        viewModel.getSubCategoryListAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)

    }

    private fun setTitleAdapter() {
        titleAdapter = TitleAdapter(this, listSub)

        rv_title.adapter = titleAdapter
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is ModelSubCategoriesList) {
                    val mResponse: ModelSubCategoriesList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setAdapterSpinnerSub(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

                if (it.data is ModelProductListAsPerSubCat) {
                    val mResponse: ModelProductListAsPerSubCat = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 50
                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    if (whichApi == "subCat")
                        finish()
                    else if (whichApi == "productList") {
                        tvNoProducts.visibility=View.VISIBLE
                        detail_recycle.visibility=View.GONE
                        currentModel.clear()
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    if (whichApi == "subCat")
                        finish()
                    else if (whichApi == "productList") {
                        tvNoProducts.visibility=View.VISIBLE
                        detail_recycle.visibility=View.GONE
                        currentModel.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            it.status == Status.LOADING -> { }
        }
    }

    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.clear()
        currentModel.addAll(mResponse.body)
        if(currentModel.size==0){
            tvNoProducts.visibility=View.VISIBLE
            detail_recycle.visibility=View.GONE
        }else{
            tvNoProducts.visibility=View.GONE
            detail_recycle.visibility=View.VISIBLE
            adapter!!.notifyDataSetChanged()
            reset = false
        }

    }

    private fun setAdapterSpinnerSub(mResponse: ModelSubCategoriesList) {
        listSubCategoryBody.clear()
        listSubCategoryBody.addAll(mResponse.body)
        listSub.clear()
        for (i in 0 until listSubCategoryBody.size) {
            if (i == 0) {
                val sliderItemTitleModel = SliderItemTitleModel(listSubCategoryBody[i].name, "true")
                listSub.add(sliderItemTitleModel)
            } else {
                val sliderItemTitleModel =
                    SliderItemTitleModel(listSubCategoryBody[i].name, "false")
                listSub.add(sliderItemTitleModel)
            }
        }
        currentSubCatID = listSubCategoryBody[0].id.toString()
        getProductAsPerCatSub()
        titleAdapter.notifyDataSetChanged()

    }

    fun setSelectedSubCategoryID(sliderItemTitleModel: SliderItemTitleModel) {
        val get = listSubCategoryBody[listSub.indexOf(sliderItemTitleModel)]
        currentSubCatID = get.id.toString()

        getProductAsPerCatSub()
    }

    private fun getProductAsPerCatSub() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()
        map["offset"] = mUtils.createPartFromString("0")
        map["limit"] = mUtils.createPartFromString("50")
        map["subCategoryId"] = mUtils.createPartFromString(currentSubCatID)
        map["categoryId"] = mUtils.createPartFromString(currentCatId)
        map["sortBy"] = mUtils.createPartFromString(currentSortBy)
        map["lowPrice"] = mUtils.createPartFromString(currentLowPrice)
        map["deliveryType"] = mUtils.createPartFromString(currentDeliveryType)
        map["highPrice"] = mUtils.createPartFromString(currentHighPrice)
        viewModel.getProductAccToCategorySubcategoryAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)
        whichApi = "productList"
    }
}