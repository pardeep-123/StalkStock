package com.stalkstock.consumer.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.consumer.activities.FilterActivity
import com.stalkstock.consumer.activities.HomedetailsActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.adapter.CategoryAdapter
import com.stalkstock.consumer.adapter.SuggestedAdapter
import com.stalkstock.consumer.adapter.ViewDetailAdapter
import com.stalkstock.consumer.model.UserBannerModel
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.driver.models.SuggestedBody
import com.stalkstock.driver.models.SuggestedDataListed
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Utils.CurrentLocationActivity
import com.stalkstock.viewmodel.HomeViewModel
import com.viewpagerindicator.CirclePageIndicator
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList

class HomeCounsumerFragment : CurrentLocationActivity(), Observer<RestObservable> {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var mLat: Double = 0.0
    private var mLong: Double = 0.0
    var stAddress = ""

    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"
    var currentType = "default"

    var viewFrag: View? = null
    lateinit var adapter: CategoryAdapter
    lateinit var category_recycle: RecyclerView
    lateinit var suggested_recycle: RecyclerView
    lateinit var viewPagerDetail: ViewPager
    lateinit var indicator: CirclePageIndicator
    lateinit var detailAdapter: ViewDetailAdapter
    lateinit var adapter3: SuggestedAdapter
    lateinit var notification: ImageView
    lateinit var fillter: ImageView
    lateinit var etSearch: RelativeLayout
    lateinit var iv_tick_popuplar: ImageView
    lateinit var iv_tick_most: ImageView
    lateinit var iv_tick_rating: ImageView
    lateinit var tv_default: TextView
    lateinit var tv_address: TextView
    lateinit var tv_most: TextView
    lateinit var tv_rating: TextView

    private var currentModel: ArrayList<UserBannerModel.Body> = ArrayList()
    var arrayListCategory: ArrayList<ModelCategoryList.Body> = ArrayList()
    var listSuggested: ArrayList<SuggestedBody> = ArrayList()
    var statusClick = 1
    var tickClick = 1
    var clickMsg = 0

    private var isLastPageSwiped = false
    private var counterPageScroll = 0

    lateinit var bt_sort: Button
    lateinit var bt_pickup: Button
    lateinit var tv_delivery: Button
    lateinit var mActivity:MainConsumerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MainConsumerActivity
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK ) {
                    val data: Intent? = result.data
                    currentLowPrice = data!!.getStringExtra("lowPrice")!!
                    currentHighPrice = data.getStringExtra("highPrice")!!
                    currentSortBy = data.getStringExtra("sortBy")!!
                } }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFrag = inflater.inflate(R.layout.fragment_home_consumer, container, false)
        category_recycle = viewFrag!!.findViewById(R.id.category_recycle)
        viewPagerDetail = viewFrag!!.findViewById(R.id.viewPagerDetail)
        suggested_recycle = viewFrag!!.findViewById(R.id.suggested_recycle)
        tv_address = viewFrag!!.findViewById(R.id.ss)
        indicator = viewFrag!!.findViewById(R.id.indicator)
        notification = viewFrag!!.findViewById(R.id.notification)
        fillter = viewFrag!!.findViewById(R.id.fillter)
        etSearch = viewFrag!!.findViewById(R.id.etSearch)
        bt_sort = viewFrag!!.findViewById(R.id.bt_sort)
        bt_pickup = viewFrag!!.findViewById(R.id.bt_pickup)
        tv_delivery = viewFrag!!.findViewById(R.id.tv_delivery)
        adapter = CategoryAdapter(this, requireContext(), arrayListCategory)
        category_recycle.layoutManager = GridLayoutManager(activity, 4)
        category_recycle.adapter = adapter
        detailAdapter = ViewDetailAdapter(requireContext(), currentModel)
        viewPagerDetail.adapter = detailAdapter
        indicator.fillColor = resources.getColor(R.color.theme_green)
        indicator.setViewPager(viewPagerDetail)
        viewPagerDetail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == currentModel.size && positionOffset == 0f && !isLastPageSwiped) {
                    if (counterPageScroll != 0) {
                        isLastPageSwiped = true
                        if (currentModel.size>4)
                        {
                            currentOffset += 5
                            getAdsBannerAPI()
                        } }
                    counterPageScroll++
                }
                else {
                    counterPageScroll = 0
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
        adapter3 = SuggestedAdapter(listSuggested)
        suggested_recycle.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        suggested_recycle.adapter = adapter3
        notification.setOnClickListener {
            val intent = Intent(activity, NotificationFirstActivity::class.java)
            startActivity(intent)
        }
        fillter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            intent.putExtra("from", "HomeCounsumerFragment")
            resultLauncher.launch(intent)
        }
        etSearch.setOnClickListener {
            mActivity.openSearchFragment()
        }
        bt_sort.setOnClickListener {
            bt_sort.background = mActivity.resources.getDrawable(R.drawable.btn_shape)
            bt_sort.setTextColor(mActivity.getColor(R.color.white))
            bt_pickup.background =
                mActivity.resources.getDrawable(R.drawable.btn_shape_gray)
            bt_pickup.setTextColor(mActivity.getColor(R.color.green_ss))
            tv_delivery.background =
                mActivity.resources.getDrawable(R.drawable.btn_shape_gray)
            tv_delivery.setTextColor(mActivity.getColor(R.color.green_ss))
            popupSort()
        }
        bt_pickup.setOnClickListener {
            bt_sort.background = mActivity.resources.getDrawable(R.drawable.btn_shape_gray)
            bt_sort.setTextColor(mActivity.getColor(R.color.green_ss))
            bt_pickup.background = mActivity.resources.getDrawable(R.drawable.btn_shape)
            bt_pickup.setTextColor(mActivity.getColor(R.color.white))
            tv_delivery.background =
            mActivity.resources.getDrawable(R.drawable.btn_shape_gray)
            tv_delivery.setTextColor(mActivity.getColor(R.color.green_ss))
            mActivity.currentDeliveryType = 0
        }
        tv_delivery.setOnClickListener {
            bt_sort.background = mActivity.resources.getDrawable(R.drawable.btn_shape_gray)
            bt_sort.setTextColor(mActivity.getColor(R.color.green_ss))
            bt_pickup.background =
            mActivity.resources.getDrawable(R.drawable.btn_shape_gray)
            bt_pickup.setTextColor(mActivity.getColor(R.color.green_ss))
            tv_delivery.background = mActivity.resources.getDrawable(R.drawable.btn_shape)
            tv_delivery.setTextColor(mActivity.getColor(R.color.white))
            mActivity.currentDeliveryType = 1
        }

        getAdsBannerAPI()
        getSuggestedBanner()
        getCategories()

        if (mActivity != null) {
            CurrentLocationActivity(requireActivity())
        }
        return viewFrag
    }

    private fun getSuggestedBanner() {
        val map = HashMap<String, String>()
        map["offset"] = suggestionOffSet.toString()
        map["limit"] = "10"
        map["type"] = currentType  // default,rating,popular
        viewModel.getSuggestedProduct((activity as MainConsumerActivity), false,map)
    }

    private fun getCategories() {
        val mainConsumerActivity = activity as MainConsumerActivity
        viewModel.getCategoryListAPI(mainConsumerActivity, false)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }

    private var reset = false
    private var currentOffset = 0
    private var suggestionOffSet = 0

    private fun getAdsBannerAPI() {
        val mainConsumerActivity = activity as MainConsumerActivity
        if (reset) {
            currentOffset = 0
            currentModel.clear()}
        val map = HashMap<String, RequestBody>()
        map["offset"] = mainConsumerActivity.mUtils.createPartFromString(currentOffset.toString())
        map["limit"] = mainConsumerActivity.mUtils.createPartFromString("5")
        viewModel.userBannerListAPI(mainConsumerActivity, false, map)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }

    override fun onResume() {
        super.onResume()
        clickMsg = 0
    }

    private fun completeAddress(latitude: Double, longitude: Double) {
        try {
            val addresses: List<Address>
            val geocoder = Geocoder(mActivity, Locale.getDefault())

            addresses = geocoder.getFromLocation(latitude, longitude, 1)

            val address: String = addresses[0].getAddressLine(0)

            if (address != null) { stAddress = address }
        } catch (e: Exception) {
            stAddress = "No location found"
        }
        tv_address.text = stAddress
    }

    override fun onLocationGet(latitude: String?, longitude: String?) {
        mLat = latitude!!.toDouble()
        mLong = longitude!!.toDouble()
        completeAddress(latitude.toDouble(), longitude.toDouble())
    }

    private fun popupSort() {
        val logoutUpdatedDialog2 = Dialog(requireContext())
        logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialog2.setContentView(R.layout.layout_sort_popup)
        logoutUpdatedDialog2.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        logoutUpdatedDialog2.setCancelable(false)
        logoutUpdatedDialog2.setCanceledOnTouchOutside(false)
        logoutUpdatedDialog2.window!!.setGravity(Gravity.BOTTOM)
        logoutUpdatedDialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val iv_cross = logoutUpdatedDialog2.findViewById<ImageView>(R.id.iv_cross)
        val btn_apply = logoutUpdatedDialog2.findViewById<Button>(R.id.btn_apply)
        val rl_delivery = logoutUpdatedDialog2.findViewById<RelativeLayout>(R.id.rl_delivery)
        val tv_delivery = logoutUpdatedDialog2.findViewById<TextView>(R.id.tv_delivery)
        val ic_delivery = logoutUpdatedDialog2.findViewById<ImageView>(R.id.ic_delivery)
        iv_tick_popuplar = logoutUpdatedDialog2.findViewById(R.id.iv_tick_popuplar)
        iv_tick_most = logoutUpdatedDialog2.findViewById(R.id.iv_tick_most)
        iv_tick_rating = logoutUpdatedDialog2.findViewById(R.id.iv_tick_rating)
        val rl_pickups = logoutUpdatedDialog2.findViewById<RelativeLayout>(R.id.rl_pickups)
        val tv_pickup_o = logoutUpdatedDialog2.findViewById<TextView>(R.id.tv_pickup_o)
        val ic_pickup = logoutUpdatedDialog2.findViewById<ImageView>(R.id.ic_pickup)
        val rl_dining = logoutUpdatedDialog2.findViewById<RelativeLayout>(R.id.rl_dining)
        val tv_dining = logoutUpdatedDialog2.findViewById<TextView>(R.id.tv_dining)
        val ic_dining = logoutUpdatedDialog2.findViewById<ImageView>(R.id.ic_dining)
        iv_cross.setOnClickListener {
            logoutUpdatedDialog2.dismiss()
            if (mActivity.currentDeliveryType == 0)
                bt_pickup.performClick()
            else
                this.tv_delivery.performClick()
        }
        tv_default = logoutUpdatedDialog2.findViewById(R.id.tv_default)
        tv_most = logoutUpdatedDialog2.findViewById(R.id.tv_most)
        tv_rating = logoutUpdatedDialog2.findViewById(R.id.tv_rating)
        btn_apply.setOnClickListener {
            logoutUpdatedDialog2.dismiss()
            if (mActivity.currentDeliveryType == 0)
                bt_pickup.performClick()
            else
                this.tv_delivery.performClick()
        }
        rl_delivery.setOnClickListener {
            statusClick = 1
            mActivity.currentDeliveryType = 1
            iconAndTextColorChange(
                statusClick,
                tv_delivery,
                tv_pickup_o,
                tv_dining,
                rl_delivery,
                rl_pickups,
                rl_dining,
                ic_delivery,
                ic_pickup,
                ic_dining
            )
        }
        rl_pickups.setOnClickListener {
            statusClick = 2
            mActivity.currentDeliveryType = 0
            iconAndTextColorChange(
                statusClick,
                tv_delivery,
                tv_pickup_o,
                tv_dining,
                rl_delivery,
                rl_pickups,
                rl_dining,
                ic_delivery,
                ic_pickup,
                ic_dining
            )
        }
        rl_dining.setOnClickListener {
            statusClick = 3
            iconAndTextColorChange(
                statusClick,
                tv_delivery,
                tv_pickup_o,
                tv_dining,
                rl_delivery,
                rl_pickups,
                rl_dining,
                ic_delivery,
                ic_pickup,
                ic_dining
            )
        }
        iconAndTextColorChange(
            statusClick,
            tv_delivery,
            tv_pickup_o,
            tv_dining,
            rl_delivery,
            rl_pickups,
            rl_dining,
            ic_delivery,
            ic_pickup,
            ic_dining
        )
        tv_default.setOnClickListener {
            tickClick = 1
            currentType = "default"
            tickVisible(tickClick)
        }
        tv_most.setOnClickListener {
            tickClick = 2
            currentType = "popular"
            tickVisible(tickClick)
        }
        tv_rating.setOnClickListener {
            tickClick = 3
            currentType = "rating"
            tickVisible(tickClick)
        }
        tickVisible(tickClick)
        logoutUpdatedDialog2.show()
    }

    val viewModel: HomeViewModel by viewModels()

    private fun tickVisible(tickClick: Int) {
        when (tickClick) {
            1 -> {
                iv_tick_popuplar.visibility = View.VISIBLE
                iv_tick_most.visibility = View.GONE
                iv_tick_rating.visibility = View.GONE
            }
            2 -> {
                iv_tick_popuplar.visibility = View.GONE
                iv_tick_most.visibility = View.VISIBLE
                iv_tick_rating.visibility = View.GONE
            }
            3 -> {
                iv_tick_popuplar.visibility = View.GONE
                iv_tick_most.visibility = View.GONE
                iv_tick_rating.visibility = View.VISIBLE
            }
        }
        getSuggestedBanner()
    }

    private fun iconAndTextColorChange(
        status: Int,
        t1: TextView,
        t2: TextView,
        t3: TextView,
        rl1: RelativeLayout,
        rl2: RelativeLayout,
        rl3: RelativeLayout,
        iv1: ImageView,
        iv2: ImageView,
        iv3: ImageView
    ) {
        when (status) {
            1 -> {
                rl1.background = resources.getDrawable(R.drawable.strokegreen)
                t1.setTextColor(resources.getColor(R.color.green_colour))
                iv1.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_colour))
                rl2.background = resources.getDrawable(R.drawable.strokegray_sort)
                t2.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
                iv2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.sort_popup_gray_color))
                rl3.background = resources.getDrawable(R.drawable.strokegray_sort)
                t3.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
                iv3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.sort_popup_gray_color))
            }
            2 -> {
                rl1.background = resources.getDrawable(R.drawable.strokegray_sort)
                t1.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
                iv1.setColorFilter(ContextCompat.getColor(requireContext(), R.color.sort_popup_gray_color))
                rl2.background = resources.getDrawable(R.drawable.strokegreen)
                t2.setTextColor(resources.getColor(R.color.green_colour))
                iv2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_colour))
                rl3.background = resources.getDrawable(R.drawable.strokegray_sort)
                t3.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
                iv3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.sort_popup_gray_color))
            }
            3 -> {
                rl1.background = resources.getDrawable(R.drawable.strokegray_sort)
                t1.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
                iv1.setColorFilter(ContextCompat.getColor(requireContext(), R.color.sort_popup_gray_color))
                rl2.background = resources.getDrawable(R.drawable.strokegray_sort)
                t2.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
                iv2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.sort_popup_gray_color))
                rl3.background = resources.getDrawable(R.drawable.strokegreen)
                t3.setTextColor(resources.getColor(R.color.green_colour))
                iv3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_colour))
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserBannerModel) {
                    val mResponse: UserBannerModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(mActivity, mResponse.message)
                    }
                }
                if (it.data is SuggestedDataListed) {
                    val mResponse: SuggestedDataListed = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        listSuggested.clear()
                        listSuggested.addAll(mResponse.body)
                        adapter3.notifyDataSetChanged()
                    }
                    else {
                        AppUtils.showErrorAlert(mActivity, mResponse.message)
                    }
                }

                if (it.data is ModelCategoryList) {
                    val mResponse: ModelCategoryList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setDataCategody(mResponse)
                    }
                    else {
                        AppUtils.showErrorAlert(mActivity, mResponse.message)
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(mActivity, mResponse.message)
                    }
                    else {
                        AppUtils.showErrorAlert(mActivity, mResponse.message)
                    } }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("User Address") && currentOffset > 1) {
                    } else
                        Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT).show()
                } }
            it.status == Status.LOADING -> {
            } }
    }

    private fun setDataCategody(mResponse: ModelCategoryList) {
        arrayListCategory.clear()
        arrayListCategory.addAll(mResponse.body)
        adapter.notifyDataSetChanged()
    }

    private fun setData(mResponse: UserBannerModel) {
        currentModel.addAll(mResponse.body)
        detailAdapter.notifyDataSetChanged()
        reset = false
    }

    fun startSubCat(toString: String) {
        val intent = Intent(context, HomedetailsActivity::class.java)
        intent.putExtra("catId", toString)
        intent.putExtra("currentDeliveryType", mActivity.currentDeliveryType.toString())
        intent.putExtra("currentHighPrice", currentHighPrice)
        intent.putExtra("currentLowPrice", currentLowPrice)
        intent.putExtra("currentSortBy", currentSortBy)
        startActivity(intent)
    }
}