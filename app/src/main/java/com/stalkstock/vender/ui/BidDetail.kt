package com.stalkstock.vender.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.Chat
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.VendorBiddingListResponse
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.activity_bid_product.*
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import java.util.*

class BidDetail : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {

    val viewModel: VendorViewModel by viewModels()
    var bidId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_detail)
        getIntentData()

        setOnClicks()

    }

    private fun getIntentData() {

        getBidDetail(bidId)

    }

    private fun getBidDetail(bidId: String) {
        val hashMap = HashMap<String, String>()
        //   hashMap["type"] = "0"    // 0=>new request 1=>accepted
        viewModel.vendorBiddingDetail(this, true, hashMap)
        viewModel.mResponse.observe(this, this)

    }

    private fun setOnClicks() {
        placebid_button.setOnClickListener(this)
        newchat.setOnClickListener(this)
        biddetails_backarrow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.newchat->{
                startActivity(Intent(this@BidDetail, Chat::class.java))
            }

            R.id.biddetails_backarrow ->{
                onBackPressed()
            }



            R.id.placebid_button ->{
                val inflater = LayoutInflater.from(this@BidDetail)
                val v = inflater.inflate(R.layout.biddetailsalertbox, null)
                val deleteDialog = AlertDialog.Builder(this@BidDetail).create()
                deleteDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                deleteDialog.setView(v)
                val btncontinue = v.findViewById<Button>(R.id.submitbutton)
                btncontinue.setOnClickListener { v ->
                    bidamt.visibility = View.VISIBLE
                    biddisc.visibility = View.VISIBLE
                    placebid_button.tag = 1
                    placebid_button.text = "Place Bid"
                    if (placebid_button.text.toString() == "Place Bid") {
                        placebid_button.text = "Edit Bid"


//openNewActivity();
                        v.tag = 1 //pause
                    } else {
                        val status = v.tag as Int
                        if (status == 1) {
                            //  nextbutton.setText("Checkout");
                            //btn_add.setVisibility(View.VISIBLE);
                            v.tag = 0 //pause
                        } else {
                            placebid_button.text = "Edit Bid"

//                         Intent intent = new Intent(SelectPaymentMethod.this, AddNewCard.class);
//                         startActivity(intent);


//openNewActivity();
                            v.tag = 1 //pause
                        }
                    }

//
                    deleteDialog.dismiss()
                }
                deleteDialog.show()
            }


        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorBiddingListResponse) {
                    val mResponse: VendorBiddingListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        Log.i("====",mResponse.message)
                        setBidData()

                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setBidData() {

    }
}