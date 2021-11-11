package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.PreviewPagerAdapter
import com.stalkstock.advertiser.model.BusinessAdsList
import com.stalkstock.advertiser.model.DeleteAdsResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_ad_detail.*
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.activity_preview.circle_indicator
import kotlinx.android.synthetic.main.activity_preview.view_pager
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.toolbar3.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat

class AdDetailActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    val mContext:Context = this
    private val imglist = ArrayList<String>()
    lateinit var successfulUpdatedDialog:Dialog
    private lateinit var filterPopUp: PopupWindow
    lateinit var title : String
    lateinit var content : String
    lateinit var adLink : String
    lateinit var startDate : String
    lateinit var endDate : String
    lateinit var budget : String
     var id : Int = 0
    lateinit var image: String
    lateinit var adsList : BusinessAdsList.Body
    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    override fun getContentId(): Int {
        return R.layout.activity_ad_detail
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        tv_heading.text = "Request Ad"

        if(intent.hasExtra("model")&& intent.hasExtra("intentfrom"))
        {
            adsList = intent.extras?.get("model") as BusinessAdsList.Body
            initdata(adsList)
        }
        else if(intent.hasExtra("approvedAds")&& intent.hasExtra("intentfrom"))
        {
            adsList = intent.extras?.get("approvedAds") as BusinessAdsList.Body
            initdata(adsList)
        }

        else if(intent.hasExtra("expiredAds")&& intent.hasExtra("intentfrom"))
        {
            adsList = intent.extras?.get("expiredAds") as BusinessAdsList.Body
            initdata(adsList)
        }

        else{
            title = adsList.title
            content = adsList.description
            adLink = adsList.adLink
            startDate = adsList.startDate
            endDate = adsList.endDate
            budget = adsList.budget
            id = adsList.id
        }

        iv_back.setOnClickListener(this)
        iv_dots.setOnClickListener(this)
        setSlider()
    }

    private fun initdata(adsList: BusinessAdsList.Body) {

        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("MMM dd, yyyy")
        val startDate: String = formatter.format(parser.parse(adsList.startDate))
        val endDate: String = formatter.format(parser.parse(adsList.endDate))

        tvAdDetailTitle.text = adsList.title
        tvAdDetailDescription.text = adsList.description
        tvAdDetailAdLink.text = adsList.adLink
        tvAdDetailStartDate.text = startDate
        tvAdDetailEndDate.text = endDate
        tvAdDetailBudget.text = adsList.budget

        id = adsList.id

        imglist.clear()

            imglist.add(adsList.image)

    }

    private fun setSlider(){
        view_pager.adapter = PreviewPagerAdapter(mContext,imglist )
       // circle_indicator.setViewPager(view_pager)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.iv_dots -> {
                showDotsPopup(iv_dots)
            }
        }
    }

    private fun showDotsPopup(anchorView: View) {
        val layout = layoutInflater.inflate(R.layout.dots_alert, null)
        val editTxt: TextView = layout.findViewById(R.id.editTxt)
        val deleteTxt: TextView = layout.findViewById(R.id.deleteTxt)
        editTxt.setOnClickListener {
            val intent = Intent(mContext, EditAdActivity::class.java)

            intent.putExtra("title",adsList.title)
            intent.putExtra("startDate",adsList.startDate)
            intent.putExtra("endDate",adsList.endDate)
            intent.putExtra("adLink",adsList.adLink)
            intent.putExtra("budget",adsList.budget)
            intent.putExtra("description",adsList.description)
            intent.putExtra("id",adsList.id)
            intent.putExtra("image",imglist)
            intent.putExtra("action",adsList.action)
            startActivity(intent)
            filterPopUp.dismiss()
        }

        deleteTxt.setOnClickListener {
            deleteAdsApi()
            filterPopUp.dismiss()
        }

        anchorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        filterPopUp = PopupWindow(mContext)
        filterPopUp.contentView = layout
        filterPopUp.isOutsideTouchable = true
        filterPopUp.width = LinearLayout.LayoutParams.WRAP_CONTENT
        filterPopUp.height = LinearLayout.LayoutParams.WRAP_CONTENT
        filterPopUp.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            filterPopUp.showAsDropDown(anchorView, 0, 0, Gravity.RIGHT)
        }

        filterPopUp.isFocusable = true
    }

    private fun deleteDialogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tv_msg.text = "Your ad details have been successfully deleted. You can check the status of your ad in the Ad Manager!"
        successfulUpdatedDialog.btn_ok.text = "Go to Ad Manager"
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        successfulUpdatedDialog.btn_ok.setOnClickListener {
            val intent = Intent(mContext, MainActivity::class.java)
            startActivity(intent)
            successfulUpdatedDialog.dismiss()
        }

        successfulUpdatedDialog.show()
    }

    private fun deleteAdsApi() {

        val map = HashMap<String, RequestBody>()
        map["advertiseId"] = mUtils.createPartFromString(id.toString())
        viewModel.deleteBusinessAds(this,true,map)
        viewModel.mResponse.observe(this,this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is DeleteAdsResponse){
                    val data = it.data
                    if (data.code == 200){
                        deleteDialogMethod()
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }


}