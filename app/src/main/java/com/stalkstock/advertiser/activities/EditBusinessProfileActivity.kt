package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.utils.others.CommonMethods
import kotlinx.android.synthetic.main.activity_edit_business_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*

class EditBusinessProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var successfulUpdatedDialog:Dialog
    val mContext:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_edit_business_profile)

        tv_heading.text = "Edit Business Profile"
        iv_back.setOnClickListener(this)
        btn_update.setOnClickListener(this)


        CommonMethods.hideKeyboard(this@EditBusinessProfileActivity,btn_update)
        val foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_country, R.layout.spinner_layout_for_vehicle)
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter


        val foodadapter2 = ArrayAdapter.createFromResource(this, R.array.Select_business_type, R.layout.spinner_layout_for_vehicle)
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_type.adapter = foodadapter2

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }
            R.id.btn_update->{
                updateDailogMethod()
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
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        if(MyApplication.instance.getString("usertype").equals("2")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else  if(MyApplication.instance.getString("usertype").equals("1")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else{

        }
        successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            finish()
        }

        successfulUpdatedDialog.show()
    }

}