package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.AddCardData
import com.stalkstock.driver.models.UserCardList
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_manage_payments.*
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.HashMap

class ManagePaymentsActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    val mContext:Context=this
    var from = ""
    val viewModel: DriverViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_payments)
        tv_heading.text = "Manage Payments"
        if (intent.getStringExtra("from")!=null){
            from = intent.getStringExtra("from")!!
            btn_checkout.text = "Save" }
        else{ from = "" }
        iv_back.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        one.setOnClickListener(this)
        layout_delete.setOnClickListener(this)

        btn_checkout.setOnClickListener {
            if (from.isEmpty()){
                val intents = Intent(this@ManagePaymentsActivity,ThankyouActivity2::class.java)
                startActivity(intents) }
            else{ onBackPressed() }}

        if(MyApplication.instance.getString("usertype").equals("5")){
            btn_checkout.visibility=View.VISIBLE
        }

        callCardList()



    }

    private fun callCardList() {

        val map = HashMap<String, String>()
        map["offset"] = "0"
        map["limit"] = "10"
        viewModel.getCardList(this, true, map)
        viewModel.mResponse.observe(this, this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{ finish() }
            R.id.one->{item_address_rb.setImageResource(R.drawable.radio_fill) }
            R.id.btn_add->{
                val intent = Intent(mContext, AddNewCardActivity::class.java)
                startActivity(intent)
            }
            R.id.layout_delete->{ reportuser() }
            R.id.layout_delete1->{ reportuser() }
        }}

    fun reportuser() {
        val customView = LayoutInflater.from(mContext).inflate(R.layout.delete_successfully_alert, null)
        val customDialog = Dialog(mContext)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(customView)
        customDialog.btn_yes.setOnClickListener { customDialog.dismiss() }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show() }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is UserCardList) {
                    val mResponse: UserCardList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        tvNoCards.visibility = View.GONE
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                } }
            it.status == Status.LOADING -> {
            }
        }
    }
}