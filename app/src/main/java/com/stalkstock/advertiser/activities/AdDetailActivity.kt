package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.PreviewPagerAdapter
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.toolbar3.*
import kotlinx.android.synthetic.main.update_successfully_alert.*

class AdDetailActivity : AppCompatActivity(), View.OnClickListener {
    val mContext:Context = this
    lateinit var successfulUpdatedDialog:Dialog
    private lateinit var filterPopUp: PopupWindow
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_ad_detail)
        tv_heading.text = "Request Ad"
        iv_back.setOnClickListener(this)
        iv_dots.setOnClickListener(this)
        setSlider()
    }

    fun setSlider(){
        view_pager.adapter = PreviewPagerAdapter(mContext)
        circle_indicator.setViewPager(view_pager)
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

    private fun showDotsPopup(anchorview: View) {
        val layout = getLayoutInflater().inflate(R.layout.dots_alert, null)
        val editTxt: TextView = layout.findViewById(R.id.editTxt)
        val deleteTxt: TextView = layout.findViewById(R.id.deleteTxt)
        editTxt.setOnClickListener {
            val intent = Intent(mContext, EditAdActivity::class.java)
            startActivity(intent)
            filterPopUp.dismiss()
        }
        deleteTxt.setOnClickListener {
            deleteDailogMethod()
            filterPopUp.dismiss()
        }

        anchorview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        filterPopUp = PopupWindow(mContext)
        filterPopUp.setContentView(layout)
        filterPopUp.isOutsideTouchable = true
        filterPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT)
        filterPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
        filterPopUp.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            filterPopUp.showAsDropDown(anchorview, 0, 0, Gravity.RIGHT)
        }

        filterPopUp.setFocusable(true)
    }

    private fun deleteDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tv_msg.text = "Your ad details has been successfully deleted. You can check the status of your ad in the Ad Manager!"
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


}