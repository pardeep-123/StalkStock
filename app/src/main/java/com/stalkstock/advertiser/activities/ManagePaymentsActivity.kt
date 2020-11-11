package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_manage_payments.*
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.toolbar.*

class ManagePaymentsActivity : AppCompatActivity(), View.OnClickListener {
    val mContext:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_manage_payments)
        tv_heading.text = "Manage Payments"
        iv_back.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        one.setOnClickListener(this)
        two.setOnClickListener(this)
        layout_delete.setOnClickListener(this)
        layout_delete1.setOnClickListener(this)
       // setAdapter()
    }

//    fun setAdapter() {
//        val adapter = PaymentMethodsAdapter(mContext)
//        rv_paymethod.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
//        rv_paymethod.adapter = adapter
//    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            } R.id.one->{
            item_address_rb.setImageResource(R.drawable.radio_fill)
            item_address_rb1.setImageResource(R.drawable.radio_circle)

        } R.id.two->{
            item_address_rb.setImageResource(R.drawable.radio_circle)
            item_address_rb1.setImageResource(R.drawable.radio_fill)
            }
            R.id.btn_add->{
                val intent = Intent(mContext, AddNewCardActivity::class.java)
                startActivity(intent)
            } R.id.layout_delete->{
            reportuser()
            }R.id.layout_delete1->{
            reportuser()
            }
        }
    }

    fun reportuser() {
        val customDialog: Dialog
        val customView = LayoutInflater.from(mContext).inflate(R.layout.delete_successfully_alert, null)
        // Build the dialog
        customDialog = Dialog(mContext)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(customView)
        customDialog.btn_yes.setOnClickListener { customDialog.dismiss() }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show() }
}