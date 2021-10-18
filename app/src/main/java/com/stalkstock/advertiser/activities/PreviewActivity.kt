package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.PreviewPagerAdapter
import com.stalkstock.advertiser.model.AddBusinesssAdsResponse
import com.stalkstock.advertiser.model.EditAdsResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.HashMap

class PreviewActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    val mContext : Context = this
    lateinit var successfulUpdatedDialog:Dialog
    var firstimage=""
    var imglist = ArrayList<String>()
    lateinit var title: String
    lateinit var action: String
    lateinit var actionContent: String
    lateinit var startDate: String
    lateinit var endDate: String
    lateinit var adLink: String
    lateinit var budget: String
    lateinit var description: String
    private var editImage = ArrayList<String>()
    var image = ArrayList<String>()
    var adsList = ArrayList<String>()
    var id: Int = 0
    var intentFrom = ""

    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    override fun getContentId(): Int {
        return R.layout.activity_preview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        tv_heading.text = "Preview Ad"

        if(intent.hasExtra("intentFrom"))
        {
            intentFrom = intent.extras?.getString("intentFrom").toString()
        }

        when {
            intentFrom == "add" -> {
                title = intent.getStringExtra("title").toString()
                startDate = intent.getStringExtra("startDate").toString()
                endDate = intent.getStringExtra("endDate").toString()
                adLink = intent.getStringExtra("adLink").toString()
                budget = intent.getStringExtra("budget").toString()
                description = intent.getStringExtra("description").toString()
                title = intent.getStringExtra("title").toString()
                adsList = intent.getStringArrayListExtra("adsList") as ArrayList<String>
                intentFrom = intent.getStringExtra("intentFrom").toString()
                id = intent.getIntExtra("id",0)
                action = intent.getStringExtra("action").toString()
                actionContent = intent.getStringExtra("actionContent").toString()
                imglist.clear()
                for(i in 0 until adsList.size) {
                    imglist.add(adsList[i])
                }
            }
            intentFrom == "edit" -> {
                title = intent.getStringExtra("title").toString()
                startDate = intent.getStringExtra("startDate").toString()
                endDate = intent.getStringExtra("endDate").toString()
                adLink = intent.getStringExtra("adLink").toString()
                budget = intent.getStringExtra("budget").toString()
                description = intent.getStringExtra("description").toString()
                title = intent.getStringExtra("title").toString()
                image = intent.getStringArrayListExtra("image") as ArrayList<String>
                editImage = intent.getStringArrayListExtra("editImage") as ArrayList<String>
                intentFrom = intent.getStringExtra("intentFrom").toString()
                id = intent.getIntExtra("id",0)
                action = intent.getStringExtra("action").toString()
                actionContent = intent.getStringExtra("actionTitle").toString()
                imglist.clear()
                for(i in 0 until image.size) {
                    imglist.add(image[i])
                }

                for(i in 0 until editImage.size) {
                    imglist.add(editImage[i])
                }
            }
            else -> {

            }
        }

        val parser = SimpleDateFormat("yyyy/MM/dd")
        val formatter = SimpleDateFormat("MMM dd, yyyy")
        val sDate: String = formatter.format(parser.parse(startDate))
        val eDate: String = formatter.format(parser.parse(endDate))

        tvAdsTitle.text = title
        tvFromDate.text = sDate
        tvToDate.text = eDate
        tvBudget.text = budget
        tvDestinationLink.text = adLink
        tvAdsDescription.text = description
        tvAction.text = actionContent

        iv_back.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        setSlider()
    }

    private fun setSlider(){
        view_pager.adapter = PreviewPagerAdapter(mContext,imglist)
        circle_indicator.setViewPager(view_pager)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }
            R.id.btn_submit->{
                when (intentFrom) {
                    "edit" -> {
                        editAdsApi()
                    }
                    "add" -> {
                        addAdsApi()
                    }
                    else -> {
                        Toast.makeText(this,"no internet", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun addAdsApi() {
        val map = HashMap<String, RequestBody>()
            map["title"] = mUtils.createPartFromString(title)
            map["startDate"] = mUtils.createPartFromString(startDate)
            map["endDate"] = mUtils.createPartFromString(endDate)
            map["adLink"] = mUtils.createPartFromString(adLink)
            map["budget"] = mUtils.createPartFromString(budget)
            map["description"] = mUtils.createPartFromString(description)
            map["action"] = mUtils.createPartFromString(action)
        viewModel.addAds(this, true, map,adsList,mUtils)
        viewModel.mResponse.observe(this,this)
    }

    private fun editAdsApi() {
        val map = HashMap<String, RequestBody>()
        map["advertiseId"] = mUtils.createPartFromString(id.toString())
        map["title"] = mUtils.createPartFromString(title)
        map["startDate"] = mUtils.createPartFromString(startDate)
        map["endDate"] = mUtils.createPartFromString(endDate)
        map["adLink"] = mUtils.createPartFromString(adLink)
        map["budget"] = mUtils.createPartFromString(budget)
        map["description"] = mUtils.createPartFromString(description)
        map["action"] = mUtils.createPartFromString(action)
        viewModel.editAds(this, true, map,editImage,mUtils)
        viewModel.mResponse.observe(this,this)
    }

    private fun updateDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tv_msg.text = "Your ad details have been successfully submitted for approval. You can check the status of your ad in the Ad Manager!"
        successfulUpdatedDialog.btn_ok.text = "Go to Ad Manager"
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)
        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            val intent = Intent(mContext, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        successfulUpdatedDialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditAdsResponse){
                    val data = it.data
                    if (data.code == 200){
                        updateDailogMethod()
                    }
                }

                if (it.data is AddBusinesssAdsResponse){
                    val data = it.data
                    if (data.code == 200){
                        updateDailogMethod()
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