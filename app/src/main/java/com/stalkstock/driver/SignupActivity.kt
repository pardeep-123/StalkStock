package com.stalkstock.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.utils.others.CommonMethods
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup3.*
import kotlinx.android.synthetic.main.activity_signup3.btn_signup
import kotlinx.android.synthetic.main.activity_signup3.image
import kotlinx.android.synthetic.main.activity_signup3.total
import kotlinx.android.synthetic.main.activity_signup3.tv_signin
import kotlinx.android.synthetic.main.toolbar.*

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_signup3)
        tv_heading.text = getString(R.string.sign_up)


        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)

        CommonMethods.hideKeyboard(this,btn_signup)


        // addItemsOnSpinner2();
        var spinner = findViewById<View>(R.id.spinner) as Spinner



        val foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_Vehicle_type, R.layout.spinner_layout_for_vehicle)
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter


        val foodadapter2 = ArrayAdapter.createFromResource(this, R.array.Select_country, R.layout.spinner_layout_for_vehicle)
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_country.adapter = foodadapter2




    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_signin->{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } R.id.tv_signin->{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }R.id.total->{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }R.id.btn_signup->{

                startActivity(Intent(this, UploadDocActivity::class.java))


        }
            R.id.iv_back->{
                finish()
            } R.id.image->{
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
}
