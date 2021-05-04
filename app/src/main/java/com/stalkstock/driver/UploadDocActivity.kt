package com.stalkstock.driver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
 import kotlinx.android.synthetic.main.activity_upload_doc.*
import kotlinx.android.synthetic.main.toolbar.*

class UploadDocActivity : AppCompatActivity() {

    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_upload_doc)
        tv_heading.text = "Upload Documents"
        btn_Upload1.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
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
            selectImage(ivCamera2, "1")
        }

        iv_camera3.setOnClickListener {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivCamera3, "1")
        }
        iv_camera4.setOnClickListener {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivCamera4, "1")
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
                Glide.with(this).load(result[0].path).into(ivProduct)
                if (type.equals("1")) {
                    firstimage = result[0].path
                }
            }
            .onCancel {

            }
            .start()
    }
}
