package com.stalkstock.advertiser.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_new.*
import kotlinx.android.synthetic.main.activity_new.btn_preview
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class NewActivity : AppCompatActivity(), View.OnClickListener {
    val mContext : Context =this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_new)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tv_heading.text = "Post Ad"
        iv_back.setOnClickListener(this)
        btn_preview.setOnClickListener(this)
        c1234.setOnClickListener(this)
        c98.setOnClickListener(this)
        delete.setOnClickListener(this)
        iii.setOnClickListener(this)
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

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                finish()
            } R.id.iii -> {
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(iii,"1")
            }
            R.id.c1234 -> {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                c1234.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
            }, year, month, day)

            dpd.show()
            }R.id.c98 -> {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                c98.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
            }, year, month, day)

            dpd.show()
            }

            R.id.btn_preview -> {
                val intent = Intent(mContext, PreviewActivity::class.java)
                startActivity(intent)
            } R.id.delete -> {
            reportuser()
            }


        }
    }

    fun reportuser() {
        val customDialog: Dialog
        val customView = LayoutInflater.from(mContext).inflate(R.layout.delete_successfully_alert1, null)
        // Build the dialog
        customDialog = Dialog(mContext)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(customView)
        customDialog.btn_yes.setOnClickListener { customDialog.dismiss() }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show() }
}
