package com.stalkstock.driver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.utils.BaseActivity
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_upload_doc.*
import kotlinx.android.synthetic.main.toolbar.*

class UploadDocActivity : BaseActivity() {

    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var mLicenseimage1 = ""
    var mLicenseimage2 = ""
    var mRegistrationImage = ""
    var mInsuranceImage = ""
    var mFirstImage = ""
    var mHashMap : HashMap<String, String> = HashMap()

    override fun getContentId(): Int {
        return R.layout.activity_upload_doc
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        tv_heading.text = "Upload Documents"
        btn_Upload1.setOnClickListener {
            validations()

        }
        iv_back.setOnClickListener { finish() }

        ivCamera1.setOnClickListener {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivCamera, "1")
        }

        iv_camera2.setOnClickListener {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivCamera2, "2")
        }

        iv_camera3.setOnClickListener {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivCamera3, "3")
        }
        iv_camera4.setOnClickListener {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivCamera4, "4")
        }

        if (intent.hasExtra("driverData"))
        {
            mHashMap = intent.getSerializableExtra("driverData") as HashMap<String, String>
            mFirstImage = intent.getStringExtra("profileImage")!!
        }

    }

    private fun selectImage(ivProduct: ImageView, type: String) {
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
                if (type.equals("1")) {
                    mLicenseimage1 = result[0].path
                    Glide.with(this).load(result[0].path).into(ivCamera)
                    ivCamera1.visibility = View.GONE
                }else if (type.equals("2")) {
                    mLicenseimage2 = result[0].path
                    Glide.with(this).load(result[0].path).into(ivCamera2)
                    iv_camera2.visibility = View.GONE
                }
                else if (type.equals("3")) {
                    mRegistrationImage = result[0].path
                    Glide.with(this).load(result[0].path).into(ivCamera3)
                    iv_camera3.visibility = View.GONE
                }
                else if (type.equals("4")) {
                    mInsuranceImage = result[0].path
                    Glide.with(this).load(result[0].path).into(ivCamera4)
                    iv_camera4.visibility = View.GONE
                }
            }
            .onCancel {

            }
            .start()
    }

    private fun validations()
    {
        if (mLicenseimage1.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_license_frontphoto),
                Toast.LENGTH_LONG
            ).show()

        }else if (mLicenseimage2.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_license_backphoto),
                Toast.LENGTH_LONG
            ).show()

        }else if (mRegistrationImage.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_registration_photo),
                Toast.LENGTH_LONG
            ).show()

        }else if (mInsuranceImage.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_insurance_photo),
                Toast.LENGTH_LONG
            ).show()

        }else{
            startActivity(Intent(this, UploadActivity::class.java)
                .putExtra("driverData",mHashMap)
                .putExtra("profileImage",mFirstImage)
                .putExtra("license1",mLicenseimage1)
                .putExtra("license2",mLicenseimage2)
                .putExtra("registration",mRegistrationImage)
                .putExtra("insurance",mInsuranceImage))
        }
    }
}
