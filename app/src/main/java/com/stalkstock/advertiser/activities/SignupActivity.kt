package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.Verification
import com.stalkstock.utils.others.AppController
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    val mContext:Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_signup)
       // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tv_heading.text = getString(R.string.sign_up)
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)


        val foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_country, R.layout.spinner_layout_for_vehicle)
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter


        val foodadapter2 = ArrayAdapter.createFromResource(this, R.array.Select_business_type, R.layout.spinner_layout_for_vehicle)
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_type.adapter = foodadapter2


    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_signin->{
                startActivity(Intent(mContext, LoginActivity::class.java))
                finish()
            } R.id.tv_signin->{
                startActivity(Intent(mContext, LoginActivity::class.java))
                finish()
            }R.id.total->{
                startActivity(Intent(mContext, LoginActivity::class.java))
                finish()
            }R.id.btn_signup->{
            if(AppController.getInstance().getString("usertype").equals("2")){

               var intent=Intent(mContext,Verification::class.java)
                startActivityForResult(intent,125)
                //startActivity(Intent(mContext, Verification::class.java))
//                finish()
            }else{
                startActivity(Intent(mContext, LoginActivity::class.java))
                finish()
            }

            }
            R.id.iv_back->{
                finish()
            }

            R.id.image->{
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(image,"1")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 125) {
            if (resultCode == RESULT_OK) {
                try {
                    if (data!!.getStringExtra("status")!=null) {
                      onBackPressed()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
}