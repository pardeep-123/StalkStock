package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interfaces.OnClick
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.AddImageAdapter
import com.stalkstock.advertiser.dialogClass.FromEditDatePickerFragment
import com.stalkstock.advertiser.dialogClass.ToEditDatePickerFragment
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_ad.*
import kotlinx.android.synthetic.main.activity_edit_ad.btn_manage_payment
import kotlinx.android.synthetic.main.activity_edit_ad.btn_preview
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.row_productdetsils.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class EditAdActivity : AppCompatActivity(), View.OnClickListener,OnClick {
    val mContext : Context=this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    var actionselected = ""
    lateinit var title: String
    lateinit var startDate: String
    lateinit var endDate: String
    lateinit var adLink: String
    lateinit var budget: String
    var action = 0
    var image = ""
    lateinit var actionSelectedTitle: String
    lateinit var description: String
    lateinit var  adapter : AddImageAdapter
    var id: Int = 0
    var imagelist = ArrayList<String>()
    var editImageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_edit_ad)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tv_heading.text = "Edit Ad"

        title = intent.getStringExtra("title").toString()
        startDate = intent.getStringExtra("startDate").toString()
        endDate = intent.getStringExtra("endDate").toString()
        adLink = intent.getStringExtra("adLink").toString()
        budget = intent.getStringExtra("budget").toString()
        description = intent.getStringExtra("description").toString()
        title = intent.getStringExtra("title").toString()
        action = intent.getIntExtra("action",0)

        imagelist = intent.getStringArrayListExtra("image") as ArrayList<String>
        image = imagelist[0]
        id = intent.getIntExtra("id",0)

        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        val startD: String = formatter.format(parser.parse(startDate))
        val endD: String = formatter.format(parser.parse(endDate))

        etEditTitle.setText(title)
        cStartDate.text = startD
        cEndDate.text = endD
        etEditBudget.setText(budget)
        etEditDestinationLink.setText(adLink)
        etEditAdsDescription.setText(description)
//        Glide.with(this).load(imagelist[imagelist.size-1]).into(imagslideid)
        Glide.with(this).load(imagelist[0]).into(imagslideid)

        when(action){
            0 ->
            {
                cbLearnmore.isChecked = true

                actionselected = "0"
                actionSelectedTitle = "Learn More"
            }
            1 ->
            {
                cbShopnow.isChecked = true

                actionselected = "1"
                actionSelectedTitle = "Shop Now"
            }
            2 ->
            {
                cbGetnow.isChecked = true

                actionselected = "2"
                actionSelectedTitle = "Get Now"
            }
            3 ->
            {
                cbJoinnow.isChecked = true

                actionselected = "3"
                actionSelectedTitle = "Join Now"
            }
            4 ->
            {
                cbsinupnow.isChecked = true

                actionselected = "4"
                actionSelectedTitle = "SignUp Now"
            }

        }

        iv_back.setOnClickListener(this)
        btn_preview.setOnClickListener(this)
        cStartDate.setOnClickListener(this)
        cEndDate.setOnClickListener(this)
        delete123.setOnClickListener(this)

        cbLearnmore.setOnClickListener(this)
        cbShopnow.setOnClickListener(this)
        cbGetnow.setOnClickListener(this)
        cbJoinnow.setOnClickListener(this)
        cbsinupnow.setOnClickListener(this)

        ivEditImagePicker.setOnClickListener(this)

      //  setEditImageAdapter()

         btn_manage_payment.setOnClickListener {
            val intent = Intent(this, ManagePaymentsActivity::class.java)
             intent.putExtra("from","add_post")
             startActivity(intent)
        }
    }

    private fun setEditImageAdapter() {
        adapter = AddImageAdapter(mContext,imagelist,this)
        recyclerViewEditAds.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        recyclerViewEditAds.adapter = adapter
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
                //Glide.with(this).load(result[0].path).into(ivProduct)
                if (type == "1")
                {

                    firstimage = result[0].path
                    imagelist.add(firstimage)
                    editImageList.add(firstimage)
                    adapter.notifyDataSetChanged()
                    updateUi()
                }
               else{
                    firstimage = result[0].path
                    Glide.with(this).load(result[0].path).into(imagslideid)
//                    recyclerViewEditAds.visibility = VISIBLE
//                    delete123.visibility = VISIBLE
//                    ivEditImagePicker.visibility = GONE
//                    firstimage = result[0].path
//                    imagelist.add(firstimage)
//                    editImageList.add(firstimage)
//                    adapter.notifyDataSetChanged()
//                    updateUi()
                }
            }
            .onCancel {

            }
            .start()
    }

    private fun updateUi() {
        if(imagelist.isNotEmpty()){
            Glide.with(this).load(imagelist.last()).into(imagslideid)
        }
        else{
            recyclerViewEditAds.visibility = GONE
            delete123.visibility = GONE
            ivEditImagePicker.visibility = VISIBLE
            imagelist.clear()
            editImageList.clear()
            imagslideid.setImageResource(0)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.delete123 -> {
            reportuser()
            }

            R.id.cStartDate ->
            {
                showEditFromDatePickerDialog(this)
            }
            R.id.cEndDate -> {
                if (cStartDate.text.isNotEmpty()){
                    showEditToDatePickerDialog(this)
                }
                else{
                    Toast.makeText(this,"Please select start date", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.ivEditImagePicker ->{
                selectImage(ivEditImagePicker,"2")
            }

            R.id.btn_preview -> {
                setEditAdsValidation()
            }

            R.id.cbLearnmore ->{
                cbLearnmore.isChecked = true
                cbGetnow.isChecked = false
                cbShopnow.isChecked = false
                cbJoinnow.isChecked = false
                cbsinupnow.isChecked = false
                actionselected = "0"
                actionSelectedTitle = "Learn More"
            }
            R.id.cbGetnow ->{
                cbGetnow.isChecked = true
                cbLearnmore.isChecked = false
                cbShopnow.isChecked = false
                cbJoinnow.isChecked = false
                cbsinupnow.isChecked = false
                actionselected = "2"
                actionSelectedTitle = "Get Now"
            }
            R.id.cbsinupnow ->{
                cbLearnmore.isChecked = false
                cbGetnow.isChecked = false
                cbShopnow.isChecked = false
                cbJoinnow.isChecked = false
                cbsinupnow.isChecked = true
                actionselected = "4"
                actionSelectedTitle = "Signup Now"
            }
            R.id.cbShopnow ->{
                cbLearnmore.isChecked = false
                cbGetnow.isChecked = false
                cbShopnow.isChecked = true
                cbJoinnow.isChecked = false
                cbsinupnow.isChecked = false
                actionselected = "1"
                actionSelectedTitle = "Shop Now"
            }
            R.id.cbJoinNow ->{
                cbLearnmore.isChecked = false
                cbGetnow.isChecked = false
                cbShopnow.isChecked = false
                cbJoinnow.isChecked = true
                cbsinupnow.isChecked = false
                actionselected = "3"
                actionSelectedTitle = "Join Now"
            }
        }
    }

    private fun showEditToDatePickerDialog(editAdActivity: EditAdActivity) {
        val newFragment: DialogFragment = ToEditDatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun showEditFromDatePickerDialog(editAdActivity: EditAdActivity) {
        val newFragment: DialogFragment = FromEditDatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun setEditAdsValidation() {
        when {

//            firstimage.isEmpty() -> {
//                Toast.makeText(
//                   this,
//                    resources.getString(R.string.please_select_image),
//                    Toast.LENGTH_LONG
//                ).show()
//
//            }
            etEditTitle.text.toString().isEmpty() -> {
                etEditTitle.requestFocus()
                etEditTitle.error = resources.getString(R.string.please_enter_title)
            }
            etEditBudget.text.toString().isEmpty() -> {
                etEditBudget.requestFocus()
                etEditBudget.error = resources.getString(R.string.please_enter_budget)
            }
            etEditDestinationLink.text.toString().isEmpty() -> {
                etEditDestinationLink.requestFocus()
                etEditDestinationLink.error = resources.getString(R.string.please_enter_link)
            }
            etEditAdsDescription.text.toString().isEmpty() -> {
                etEditAdsDescription.requestFocus()
                etEditAdsDescription.error = resources.getString(R.string.please_enter_description)
            }
            cStartDate.text.toString().isEmpty() -> {
                cStartDate.requestFocus()
                cStartDate.error = resources.getString(R.string.please_enter_start_date)
            }
            cEndDate.text.toString().isEmpty() -> {
                cEndDate.requestFocus()
                cEndDate.error = resources.getString(R.string.please_enter_end_date)
            }
            !ivCheckboxTermsCondition.isChecked ->{

                Toast.makeText(this,"Please select terms and condition", Toast.LENGTH_SHORT).show()

            }

            actionselected.trim().isEmpty()-> {
                Toast.makeText(this,"Please select an action", Toast.LENGTH_SHORT).show()
            }

            else -> {
                val intent = Intent(mContext, PreviewActivity::class.java)
                intent.putExtra("title",etEditTitle.text.toString().trim())
                intent.putExtra("startDate",cStartDate.text.toString().trim())
                intent.putExtra("endDate",cEndDate.text.toString().trim())
                intent.putExtra("adLink",etEditDestinationLink.text.toString().trim())
                intent.putExtra("budget",etEditBudget.text.toString().trim())
                intent.putExtra("description",etEditAdsDescription.text.toString().trim())
                intent.putExtra("intentFrom","edit")
//                intent.putExtra("image",imagelist)
//                intent.putExtra("editImage",editImageList)
                intent.putExtra("editImage",firstimage)
                intent.putExtra("image",image)
                intent.putExtra("id",id)
                intent.putExtra("action",actionselected)
                intent.putExtra("actionTitle", actionSelectedTitle)
                startActivity(intent)
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
        customDialog.btn_yes.setOnClickListener { customDialog.dismiss()
            if (editImageList.isNotEmpty()){
            editImageList.removeAt(editImageList.size-1)
            adapter.notifyDataSetChanged()
            updateUi()
        }
            imagelist.removeAt(imagelist.size-1)
            adapter.notifyDataSetChanged()
            updateUi()
        }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show() }

    override fun onclick(positon: Int) {
        mAlbumFiles = java.util.ArrayList()
        mAlbumFiles.clear()
        selectImage(imagslideid,"1")
    }
}