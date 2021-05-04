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
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.utils.others.AppController
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.image
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var successfulUpdatedDialog:Dialog
    val mContext: Context =this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_edit_profile)
        tv_heading.text = "Edit Profile"
        iv_back.setOnClickListener(this)
        btn_update_profile.setOnClickListener(this)
        image.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }
            R.id.btn_update_profile->{
                updateDailogMethod()
            }R.id.image->{
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(image,"1")
            }
        }
    }


    private fun selectImage(ivProduct: ImageView, type:String) {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            //.selectCount(1)
            //.checkedList(mAlbumFiles)
            .widget(
                Widget.newDarkBuilder(this)
                    .title(getString(R.string.app_name))
                    .build()
            )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProduct)
                if (type.equals("1"))
                {
                    firstimage = result[0].path
                }
            }
            .onCancel {

            }
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
        successfulUpdatedDialog.tv_msg.text = "Your profile has been successfully updated!"
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(AppController.getInstance().getString("usertype").equals("2")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else  if(AppController.getInstance().getString("usertype").equals("1")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else  if(AppController.getInstance().getString("usertype").equals("5")){

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