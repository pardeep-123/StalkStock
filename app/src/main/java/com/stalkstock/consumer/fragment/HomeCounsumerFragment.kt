package com.stalkstock.consumer.fragment

import android.app.Activity
import android.app.Dialog
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.stalkstock.R
import com.stalkstock.advertiser.activities.Notification_firstActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.consumer.activities.HomedetailsActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.adapter.CategoryAdapter
import com.stalkstock.consumer.adapter.SuggestedAdapter
import com.stalkstock.consumer.adapter.View_detailAdapter
import com.stalkstock.consumer.model.UserBannerModel
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Utils.CurrentLocationActivity
import com.stalkstock.vender.ui.MessageActivity
import com.stalkstock.vender.ui.SearchScreen
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import com.viewpagerindicator.CirclePageIndicator
import okhttp3.RequestBody
import com.stalkstock.consumer.activities.FilterActivity
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class HomeCounsumerFragment : CurrentLocationActivity(), Observer<RestObservable> {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var mLat: Double = 0.0
    private var mLong: Double = 0.0
    var stAddress = ""

    var currentDeliveryType = 0 // 0- pickup,1-deelivery , 2 -all
    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"//sort by high_to_low => high to low low_to_high =>low to high

    var viewFrag: View? = null
    lateinit var adapter: CategoryAdapter
    lateinit var category_recycle: RecyclerView
    lateinit var suggested_recycle: RecyclerView
    lateinit var viewPagerDetail: ViewPager
    lateinit var indicator: CirclePageIndicator
    lateinit var detailAdapter: View_detailAdapter
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
    var statusClick = 1
    var tickClick = 1
    var clickMsg = 0

    private var isLastPageSwiped = false
    private var counterPageScroll = 0


    lateinit var iv_msg: ImageView
    lateinit var bt_sort: Button
    lateinit var bt_pickup: Button
    lateinit var tv_delivery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK ) {
                    // There are no request codes
                    val data: Intent? = result.data
                    currentLowPrice = data!!.getStringExtra("lowPrice")!!
                    currentHighPrice = data!!.getStringExtra("highPrice")!!
                    currentSortBy = data!!.getStringExtra("sortBy")!!
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFrag = inflater.inflate(R.layout.fragment_home_consumer, container, false)
        category_recycle = viewFrag!!.findViewById(R.id.category_recycle)
        viewPagerDetail = viewFrag!!.findViewById(R.id.viewPagerDetail)
        suggested_recycle = viewFrag!!.findViewById(R.id.suggested_recycle)
        tv_address = viewFrag!!.findViewById(R.id.ss)
        indicator = viewFrag!!.findViewById(R.id.indicator)
        notification = viewFrag!!.findViewById(R.id.notification)
        fillter = viewFrag!!.findViewById(R.id.fillter)
        etSearch = viewFrag!!.findViewById(R.id.etSearch)
        iv_msg = viewFrag!!.findViewById<ImageView>(R.id.iv_msg)
        bt_sort = viewFrag!!.findViewById<Button>(R.id.bt_sort)
        bt_pickup = viewFrag!!.findViewById<Button>(R.id.bt_pickup)
        tv_delivery = viewFrag!!.findViewById<Button>(R.id.tv_delivery)


        iv_msg.setOnClickListener {
            if (clickMsg == 0) {
                clickMsg = 1
                val intent = Intent(requireActivity(), MessageActivity::class.java)
                startActivity(intent)
            } else {
            }
        }
        adapter = CategoryAdapter(this, requireContext(), arrayListCategory)
        category_recycle.setLayoutManager(GridLayoutManager(activity, 4))
        category_recycle.setAdapter(adapter)
        detailAdapter = View_detailAdapter(requireContext(), currentModel)
        viewPagerDetail.setAdapter(detailAdapter)
        indicator.setFillColor(resources.getColor(R.color.theme_green))
        indicator.setViewPager(viewPagerDetail)

        viewPagerDetail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

//                replace 6 with last position
                if (position == currentModel.size && positionOffset == 0f && !isLastPageSwiped) {
                    if (counterPageScroll != 0) {
                        isLastPageSwiped = true;
                        if (currentModel.size>4)
                        {
                            currentOffset += 5
                            getAdsBannerAPI()
                        }
                        //Next Activity here
                    }
                    counterPageScroll++;
                } else {
                    counterPageScroll = 0;
                }


            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        adapter3 = SuggestedAdapter(activity)
        suggested_recycle.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        suggested_recycle.setAdapter(adapter3)
        notification.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, Notification_firstActivity::class.java)
            startActivity(intent)
        })
        fillter.setOnClickListener(View.OnClickListener {

            val intent = Intent(requireContext(), FilterActivity::class.java)
            intent.putExtra("from","HomeCounsumerFragment")
            resultLauncher.launch(intent)
        })
        etSearch.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, SearchScreen::class.java)
            startActivity(intent)
        })
        bt_sort.setOnClickListener {
            bt_sort.background = requireActivity().resources.getDrawable(R.drawable.btn_shape)
            bt_sort.setTextColor(requireActivity().getColor(R.color.white))
            bt_pickup.background =
                requireActivity().resources.getDrawable(R.drawable.btn_shape_gray)
            bt_pickup.setTextColor(requireActivity().getColor(R.color.green_ss))
            tv_delivery.background =
                requireActivity().resources.getDrawable(R.drawable.btn_shape_gray)
            tv_delivery.setTextColor(requireActivity().getColor(R.color.green_ss))
            popupSort()
        }
        bt_pickup.setOnClickListener {
            bt_sort.background = requireActivity().resources.getDrawable(R.drawable.btn_shape_gray)
            bt_sort.setTextColor(requireActivity().getColor(R.color.green_ss))
            bt_pickup.background = requireActivity().resources.getDrawable(R.drawable.btn_shape)
            bt_pickup.setTextColor(requireActivity().getColor(R.color.white))
            tv_delivery.background =
                requireActivity().resources.getDrawable(R.drawable.btn_shape_gray)
            tv_delivery.setTextColor(requireActivity().getColor(R.color.green_ss))
            currentDeliveryType = 0
        }
        tv_delivery.setOnClickListener {
            bt_sort.background = requireActivity().resources.getDrawable(R.drawable.btn_shape_gray)
            bt_sort.setTextColor(requireActivity().getColor(R.color.green_ss))
            bt_pickup.background =
                requireActivity().resources.getDrawable(R.drawable.btn_shape_gray)
            bt_pickup.setTextColor(requireActivity().getColor(R.color.green_ss))
            tv_delivery.background = requireActivity().resources.getDrawable(R.drawable.btn_shape)
            tv_delivery.setTextColor(requireActivity().getColor(R.color.white))
            currentDeliveryType = 1
        }

        getAdsBannerAPI()
        getCategories()

        if (requireActivity() != null) {
            CurrentLocationActivity(requireActivity())
        }
        return viewFrag
    }

    private fun getCategories() {
        val mainConsumerActivity = activity as MainConsumerActivity
        viewModel.getCategoryListAPI(mainConsumerActivity, false)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }


    private var reset = false
    private var currentOffset = 0

    private fun getAdsBannerAPI() {
        val mainConsumerActivity = activity as MainConsumerActivity
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = HashMap<String, RequestBody>()
        map.put(
            "offset",
            mainConsumerActivity.mUtils.createPartFromString(currentOffset.toString())
        )
        map.put("limit", mainConsumerActivity.mUtils.createPartFromString("5"))
        viewModel.userBannerListAPI(mainConsumerActivity, false, map)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }

    override fun onResume() {
        super.onResume()
        clickMsg = 0
    }

    fun completeAddress(latitude: Double, longitude: Double) {
        try {
            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(requireActivity(), Locale.getDefault())

            addresses = geocoder.getFromLocation(
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


        tv_address.setText(stAddress)

    }

    override fun onLocationGet(latitude: String?, longitude: String?) {
        mLat = latitude!!.toDouble()
        mLong = longitude!!.toDouble()
        completeAddress(latitude!!.toDouble(), longitude!!.toDouble())

    }

    private fun popupSort() {
        val logoutUpdatedDialog2 = Dialog(requireContext())
        logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialog2.setContentView(R.layout.layout_sort_popup)
        logoutUpdatedDialog2.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
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
            if (currentDeliveryType == 0)
                bt_pickup.performClick()
            else
                this.tv_delivery.performClick()
        }
        tv_default = logoutUpdatedDialog2.findViewById(R.id.tv_default)
        tv_most = logoutUpdatedDialog2.findViewById(R.id.tv_most)
        tv_rating = logoutUpdatedDialog2.findViewById(R.id.tv_rating)
        btn_apply.setOnClickListener {
            logoutUpdatedDialog2.dismiss()
            if (currentDeliveryType == 0)
                bt_pickup.performClick()
            else
                this.tv_delivery.performClick()
        }
        rl_delivery.setOnClickListener {
            statusClick = 1
            currentDeliveryType = 1
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
            currentDeliveryType = 0
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
        tv_default.setOnClickListener(View.OnClickListener {
            tickClick = 1
            tickVisible(tickClick)
        })
        tv_most.setOnClickListener(View.OnClickListener {
            tickClick = 2
            tickVisible(tickClick)
        })
        tv_rating.setOnClickListener(View.OnClickListener {
            tickClick = 3
            tickVisible(tickClick)
        })
        tickVisible(tickClick)
        logoutUpdatedDialog2.show()
    }

    val viewModel: HomeViewModel by viewModels()


    fun tickVisible(tickClick: Int) {
        if (tickClick == 1) {
            iv_tick_popuplar!!.visibility = View.VISIBLE
            iv_tick_most!!.visibility = View.GONE
            iv_tick_rating!!.visibility = View.GONE
        } else if (tickClick == 2) {
            iv_tick_popuplar!!.visibility = View.GONE
            iv_tick_most!!.visibility = View.VISIBLE
            iv_tick_rating!!.visibility = View.GONE
        } else if (tickClick == 3) {
            iv_tick_popuplar!!.visibility = View.GONE
            iv_tick_most!!.visibility = View.GONE
            iv_tick_rating!!.visibility = View.VISIBLE
        }
    }

    fun iconAndTextColorChange(
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
        if (status == 1) {
            rl1.background = resources.getDrawable(R.drawable.strokegreen)
            t1.setTextColor(resources.getColor(R.color.green_colour))
            //  iv1.setColorFilter(getResources().getColor(R.color.green_colour), PorterDuff.Mode.SRC_OVER);
            iv1.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_colour))
            rl2.background = resources.getDrawable(R.drawable.strokegray_sort)
            t2.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
            //iv2.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv2.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.sort_popup_gray_color
                )
            )
            rl3.background = resources.getDrawable(R.drawable.strokegray_sort)
            t3.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
            //iv3.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv3.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.sort_popup_gray_color
                )
            )
            //            iv_tick_popuplar.setVisibility(View.VISIBLE);
//            iv_tick_most.setVisibility(View.GONE);
//            iv_tick_rating.setVisibility(View.GONE);
        } else if (status == 2) {
            rl1.background = resources.getDrawable(R.drawable.strokegray_sort)
            t1.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
            //iv1.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv1.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.sort_popup_gray_color
                )
            )
            rl2.background = resources.getDrawable(R.drawable.strokegreen)
            t2.setTextColor(resources.getColor(R.color.green_colour))
            //iv2.setColorFilter(getResources().getColor(R.color.green_colour), PorterDuff.Mode.SRC_OVER);
            iv2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_colour))
            rl3.background = resources.getDrawable(R.drawable.strokegray_sort)
            t3.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
            //iv3.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv3.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.sort_popup_gray_color
                )
            )
        } else if (status == 3) {
            rl1.background = resources.getDrawable(R.drawable.strokegray_sort)
            t1.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
            // iv1.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv1.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.sort_popup_gray_color
                )
            )
            rl2.background = resources.getDrawable(R.drawable.strokegray_sort)
            t2.setTextColor(resources.getColor(R.color.sort_popup_gray_color))
            //   iv2.setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_OVER);
            iv2.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.sort_popup_gray_color
                )
            )
            rl3.background = resources.getDrawable(R.drawable.strokegreen)
            t3.setTextColor(resources.getColor(R.color.green_colour))
            //iv3.setColorFilter(getResources().getColor(R.color.green_colour), PorterDuff.Mode.SRC_OVER);
            iv3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_colour))
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
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }

                if (it.data is ModelCategoryList) {
                    val mResponse: ModelCategoryList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setDataCategody(mResponse)
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(requireActivity(), mResponse.message.toString())
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
                        Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()
//

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
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
        intent.putExtra("currentDeliveryType", currentDeliveryType.toString())
        intent.putExtra("currentHighPrice", currentHighPrice)
        intent.putExtra("currentLowPrice", currentLowPrice)
        intent.putExtra("currentSortBy", currentSortBy)
        startActivity(intent)

    }
}