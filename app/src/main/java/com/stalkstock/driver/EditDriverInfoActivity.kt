package com.stalkstock.driver

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import com.stalkstock.utils.others.CommonMethods
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_driver_info.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import java.util.*


class EditDriverInfoActivity : AppCompatActivity() {
    val mContext: Context =this
    private var mAlbumFiles = ArrayList<AlbumFile>()


    lateinit var successfulUpdatedDialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_edit_driver_info)
        tv_heading.text = "Edit Driver Information"
        iv_back.setOnClickListener{
            finish()
        }
        btn_update.setOnClickListener {
            updateDailogMethod()
        }

        rl_first.setOnClickListener {
            askCameraPermissons()
        }
        rl_second.setOnClickListener {
            askCameraPermissons()
        }

        rl_second.setOnClickListener {
            askCameraPermissons()
        }
        CommonMethods.hideKeyboard(this@EditDriverInfoActivity,btn_update)


        // addItemsOnSpinner2();
        var spinner = findViewById<View>(R.id.spinner) as Spinner



        val foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_Vehicle_type, R.layout.spinner_layout_for_vehicle)
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter
    }
    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList<AlbumFile>()
        mAlbumFiles.clear()
        selectImage("1")
    }

    private fun selectImage(s: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
              /*  Glide.with(this@EditProduct).load(result[0].path).into(adduploadimages)
                if (s == "1") {
                    firstimage = result[0].path
                }*/
            }
            .onCancel { }
            .start()
    }

    private fun updateDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tv_msg.text = "Your information has been successfully updated!"
        successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            finish()
        }

        successfulUpdatedDialog.show()
    }

}