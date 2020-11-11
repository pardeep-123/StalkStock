package com.stalkstock.advertiser.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NewActivity
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.view.*
import kotlinx.android.synthetic.main.toolbar2.view.*
import java.util.*

class AddPostFragment : Fragment(), View.OnClickListener {

    lateinit var v:View
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v= inflater.inflate(R.layout.fragment_add_post, container, false)
        v.tv_heading.text= "Post Ad"
        v.btn_preview.setOnClickListener(this)
        v.image1.setOnClickListener(this)
        v.c12.setOnClickListener(this)
        v.c123.setOnClickListener(this)
        return v
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_preview->{
                val intent = Intent(activity, NewActivity::class.java)
                startActivity(intent)
            } R.id.c12->{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(activity !!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                c12.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
            }, year, month, day)

            dpd.show()
            }R.id.c123->{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(activity !!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                c123.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
            }, year, month, day)

            dpd.show()
            }
            R.id.image1->{
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(image1,"1")
            }
        }
    }

    private fun selectImage(ivProduct: ImageView, type:String) {
        Album.image(activity)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            //.selectCount(1)
            //.checkedList(mAlbumFiles)
            .widget(
                Widget.newDarkBuilder(activity)
                    .title(getString(R.string.app_name))
                    .build()
            )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(activity).load(result[0].path).into(ivProduct)
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