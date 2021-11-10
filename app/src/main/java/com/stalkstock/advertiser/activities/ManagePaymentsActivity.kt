package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.UserCardAdapter
import com.stalkstock.consumer.model.DeleteCardData
import com.stalkstock.driver.models.UserCardBody
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
     var deleteCardPos= 0
    var rvCards:RecyclerView?=null
     val viewModel: DriverViewModel by viewModels()
     lateinit var adapter : UserCardAdapter
     private var listCards  = mutableListOf<UserCardBody>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_payments)
        rvCards= findViewById(R.id.rvCards)
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
            btn_checkout.visibility=View.VISIBLE }

        adapter = UserCardAdapter(listCards)
        rvCards?.adapter =adapter
        adapter.onPerformClick(object :UserCardAdapter.CardClicked{
            override fun clicked(position: Int, id: Int) {
                Log.e("ivDeleteCard","=====2222====")
                val map = HashMap<String, String>()
                map["cardId"] = "$id"
                deleteCardPos = position
                if(listCards.isEmpty()) tvNoCards.visibility = View.VISIBLE
                viewModel.deleteCard(this@ManagePaymentsActivity, true, map)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        callCardList()
    }

    private fun callCardList() {
        val map = HashMap<String, String>()
        map["offset"] = "0"
        map["limit"] = "20"
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
            R.id.layout_delete->{ reportUser() }
            R.id.layout_delete1->{ reportUser() }
        }}

    private fun reportUser() {
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
                        listCards.clear()
                        listCards.addAll(mResponse.body)
                        adapter.notifyDataSetChanged()
                    }
                }
                if (it.data is DeleteCardData) {
                    val mResponse: DeleteCardData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        listCards.removeAt(deleteCardPos)
                        adapter.notifyItemRemoved(deleteCardPos)
                        adapter.notifyItemRangeChanged(deleteCardPos,listCards.size)
                        if(listCards.isEmpty()) tvNoCards.visibility = View.VISIBLE
                    } }
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