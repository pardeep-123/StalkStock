package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.model.AddBusinesssAdsResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.UserCardAdapter
import com.stalkstock.consumer.model.DeleteCardData
import com.stalkstock.driver.models.UserCardBody
import com.stalkstock.driver.models.UserCardList
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.CommonResponseModel
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_manage_payments.*
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody
import java.util.*

class ManagePaymentsActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    val mContext: Context = this
    lateinit var mUtils:Util
    var from = ""
    private var reset = false
    private var currentOffset = 0
    var deleteCardPos = 0
    var rvCards: RecyclerView? = null
    val viewModel: DriverViewModel by viewModels()
    lateinit var successfulUpdatedDialog:Dialog

    var cardId="0"
    lateinit var title: String
    lateinit var action: String
    lateinit var actionContent: String
    lateinit var startDate: String
    lateinit var endDate: String
    lateinit var adLink: String
    lateinit var budget: String
    lateinit var description: String
    lateinit var adsList :String
    var id: Int = 0
    val viewModel1: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    lateinit var adapter: UserCardAdapter
    private var listCards = mutableListOf<UserCardBody>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_payments)
        mUtils= Util()
        rvCards = findViewById(R.id.rvCards)
        tv_heading.text = getString(R.string.manage_payment)
        if (intent.getStringExtra("from") != null) {
            from = intent.getStringExtra("from")!!

        } else{
            from=""
        }
        if(from=="add_post"){
            btn_checkout.visibility=View.VISIBLE
            btn_checkout.text = getString(R.string.save)
        }
        else if(from=="account") {
            btn_checkout.visibility=View.GONE
        }else if(from=="preview"){
            btn_checkout.visibility=View.VISIBLE
            title = intent.getStringExtra("title").toString()
            startDate = intent.getStringExtra("startDate").toString()
            endDate = intent.getStringExtra("endDate").toString()
            adLink = intent.getStringExtra("adLink").toString()
            budget = intent.getStringExtra("budget").toString()
            description = intent.getStringExtra("description").toString()
            title = intent.getStringExtra("title").toString()
//                adsList = intent.getStringArrayListExtra("adsList") as ArrayList<String>
            adsList = intent.getStringExtra("adsList").toString()
            id = intent.getIntExtra("id",0)
            action = intent.getStringExtra("action").toString()
            actionContent = intent.getStringExtra("actionContent").toString()

        }

        iv_back.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        one.setOnClickListener(this)
        layout_delete.setOnClickListener(this)

        btn_checkout.setOnClickListener {
            if (from.isEmpty()) {
                val intents = Intent(this@ManagePaymentsActivity, ThankyouActivity2::class.java)
                startActivity(intents)
            } else if(from=="preview") {
                if(cardId=="0"){
                    AppUtils.showErrorAlert(
                        this,
                        getString(R.string.add_your_card)
                    )
                    Handler(Looper.getMainLooper()).postDelayed({

                    }, 2000)
                }else{
                    addAdsApi()
                }

            }else{
                onBackPressed()
            }
        }


        adapter = UserCardAdapter(listCards,this)
        rvCards?.adapter = adapter
        adapter.onPerformClick(object : UserCardAdapter.CardClicked {
            override fun clicked(position: Int, id: Int) {
                Log.e("ivDeleteCard", "=====2222====")
                val map = HashMap<String, String>()
                map["cardId"] = "$id"
                deleteCardPos = position
                if (listCards.isEmpty()) tvNoCards.visibility = View.VISIBLE
                viewModel.deleteCard(this@ManagePaymentsActivity, true, map)
            }
        })

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
        map["cardId"] = mUtils.createPartFromString(cardId)
        map["paymentType"] = mUtils.createPartFromString("1")
        viewModel1.addAds(this, true, map,adsList,mUtils)
        viewModel1.mResponse.observe(this,this)
    }




    override fun onResume() {
        super.onResume()
        callCardList()
    }

    fun setDefaultCardApi(cardId: String) {
        val map= HashMap<String,Int>()
        map["cardId"]= cardId.toInt()

        viewModel.makeDefaultCard(this, false, map)
        viewModel.mResponse.observe(this, this)
    }

    private fun callCardList() {

        if (reset) {
            currentOffset = 0
            listCards.clear()
        }
        val map = HashMap<String, String>()
        map["offset"] = "0"
        map["limit"] = "20"
        viewModel.getCardList(this, true, map)
        viewModel.mResponse.observe(this, this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.one -> {
                item_address_rb.setImageResource(R.drawable.radio_fill)
            }
            R.id.btn_add -> {
                val intent = Intent(mContext, AddNewCardActivity::class.java)
                intent.putExtra("from","account")
                startActivity(intent)
            }
            R.id.layout_delete -> {
                reportUser()
            }
            R.id.layout_delete1 -> {
                reportUser()
            }
        }
    }

    private fun reportUser() {
        val customView =
            LayoutInflater.from(mContext).inflate(R.layout.delete_successfully_alert, null)
        val customDialog = Dialog(mContext)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(customView)
        customDialog.btn_yes.setOnClickListener { customDialog.dismiss() }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is UserCardList) {
                    val mResponse: UserCardList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if(mResponse.body.size==0){
                            cardId="0"
                        }else{
                            for(i in 0 until mResponse.body.size){
                                if(mResponse.body[i].isDefault==1){
                                    cardId= mResponse.body[i].cardNumber.toString()
                                }
                            }
                        }


                        tvNoCards.visibility = View.GONE
                        listCards.clear()
                        listCards.addAll(mResponse.body)
                        adapter.notifyDataSetChanged()
                    }
                }

                if (it.data is AddBusinesssAdsResponse){
                    val data = it.data
                    if (data.code == 200){
                        updateDailogMethod()
                    }
                }

                if (it.data is CommonResponseModel) {
                    val mResponse: CommonResponseModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message)
                        reset=true
                        callCardList()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
                if (it.data is DeleteCardData) {
                    val mResponse: DeleteCardData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message)
                        listCards.removeAt(deleteCardPos)
                        adapter.notifyItemRemoved(deleteCardPos)
                        adapter.notifyItemRangeChanged(deleteCardPos, listCards.size)
                        if (listCards.isEmpty()) tvNoCards.visibility = View.VISIBLE
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                  //  Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                  //  Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun updateDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tvmsg.text = getString(R.string.ad_detail_submitted)
        successfulUpdatedDialog.btn_ok.text = getString(R.string.go_to_ad_manager)
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

}