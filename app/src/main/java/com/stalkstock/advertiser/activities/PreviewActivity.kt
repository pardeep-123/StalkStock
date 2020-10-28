package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.PreviewPagerAdapter
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*

class PreviewActivity : AppCompatActivity(), View.OnClickListener {
    val mContext : Context = this
    lateinit var successfulUpdatedDialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_preview)
        tv_heading.text = "Preview Ad"
        iv_back.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        setSlider()
    }

    fun setSlider(){
        view_pager.adapter = PreviewPagerAdapter(mContext)
        circle_indicator.setViewPager(view_pager)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }
            R.id.btn_submit->{
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
        successfulUpdatedDialog.tv_msg.text = "Your ad details has been successfully submitted for approval. You can check the status of your ad in the Ad Manager!"
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

}