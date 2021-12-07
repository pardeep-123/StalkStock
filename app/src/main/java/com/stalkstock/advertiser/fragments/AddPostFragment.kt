package com.stalkstock.advertiser.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interfaces.OnClick
import com.stalkstock.R
import com.stalkstock.advertiser.activities.ManagePaymentsActivity
import com.stalkstock.advertiser.activities.NewActivity
import com.stalkstock.advertiser.activities.PreviewActivity
import com.stalkstock.advertiser.adapters.AddImageAdapter
import com.stalkstock.advertiser.adapters.PendingAdsAdapter
import com.stalkstock.advertiser.dialogClass.FromDatePickerFragment
import com.stalkstock.advertiser.dialogClass.ToDatePickerFragment
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_ad.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.btn_preview
import kotlinx.android.synthetic.main.fragment_add_post.ivCheckbox
import kotlinx.android.synthetic.main.fragment_add_post.view.*
import kotlinx.android.synthetic.main.fragment_pending_ads.view.*
import kotlinx.android.synthetic.main.toolbar2.view.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class AddPostFragment : Fragment(), View.OnClickListener ,OnClick{

    lateinit var v:View
    lateinit var mContext: Context
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""

    lateinit var  adapter :AddImageAdapter
    var adsList = ArrayList<String>()

    var actionselected = ""

    lateinit var actionSelectedTitle: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v= inflater.inflate(R.layout.fragment_add_post, container, false)
        v.tv_heading.text= "Post Ad"
        mContext = activity as Context
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_preview.setOnClickListener(this)
        ivAdsCamera.setOnClickListener(this)
        c123.setOnClickListener(this)
        tvFromDate.setOnClickListener(this)

        cbGetNow.setOnClickListener(this)
        cbShopNow.setOnClickListener(this)
        cbSignup.setOnClickListener(this)
        cbLearnMore.setOnClickListener(this)
        cbJoinNow.setOnClickListener(this)
      /*  ivDelete.setOnClickListener(this)*/

        v.btn_manage_payment.setOnClickListener {
            val intent = Intent(activity, ManagePaymentsActivity::class.java)
            intent.putExtra("from","add_post")
            startActivity(intent)
        }
//        setAddImageAdapter()
    }


    private fun setAddImageAdapter() {
         adapter = AddImageAdapter(mContext,adsList,this)
       addPostRecyclerView.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        addPostRecyclerView.adapter = adapter
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_preview->{
                checkAdPostValidation()

            } R.id.tvFromDate->{
            showFromDatePickerDialog(view)
            }

            R.id.c123->{
                if (tvFromDate.text.isNotEmpty()){
                    showToDatePickerDialog(view)
                }
                else{
                    Toast.makeText(requireActivity(),"Please select start date", Toast.LENGTH_SHORT).show()
                }


            }


            R.id.ivAdsCamera->{
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(ivAddImage,"1")

            }

            R.id.ivDelete ->{
                reportuser()
            }

            R.id.cbLearnMore ->{
                cbLearnMore.isChecked = true
                cbGetNow.isChecked = false
                cbShopNow.isChecked = false
                cbJoinNow.isChecked = false
                cbSignup.isChecked = false
                actionselected = "0"
                actionSelectedTitle = "LearnMore"
            }

            R.id.cbGetNow ->{
                cbLearnMore.isChecked = false
                cbGetNow.isChecked = true
                cbShopNow.isChecked = false
                cbJoinNow.isChecked = false
                cbSignup.isChecked = false
                actionselected = "2"
                actionSelectedTitle = "GetNow"
            }

            R.id.cbSignup ->{
                cbLearnMore.isChecked = false
                cbGetNow.isChecked = false
                cbShopNow.isChecked = false
                cbJoinNow.isChecked = false
                cbSignup.isChecked = true
                actionselected = "4"
                actionSelectedTitle = "Signup"
            }

            R.id.cbShopNow ->{
                cbLearnMore.isChecked = false
                cbGetNow.isChecked = false
                cbShopNow.isChecked = true
                cbJoinNow.isChecked = false
                cbSignup.isChecked = false
                actionselected = "1"
                actionSelectedTitle = "ShopNow"
            }

            R.id.cbJoinNow ->{
                cbLearnMore.isChecked = false
                cbGetNow.isChecked = false
                cbShopNow.isChecked = false
                cbJoinNow.isChecked = true
                cbSignup.isChecked = false
                actionselected = "3"
                actionSelectedTitle = "JoinNow"
            }
        }
    }

    private fun showFromDatePickerDialog(view: View?) {
        val newFragment: DialogFragment = FromDatePickerFragment()
        newFragment.show(childFragmentManager, "datePicker")
    }

    private fun showToDatePickerDialog(view: View?) {
        val newFragment: DialogFragment = ToDatePickerFragment()
        newFragment.show(childFragmentManager, "datePicker")
    }

    private fun reportuser() {
        val customDialog: Dialog
        val customView = LayoutInflater.from(mContext).inflate(R.layout.delete_successfully_alert1, null)
        // Build the dialog
        customDialog = Dialog(mContext)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(customView)
        customDialog.btn_yes.setOnClickListener {
       if(adsList.isNotEmpty())
       {
           adsList.removeAt(adsList.size-1)
           adapter.notifyDataSetChanged()
           updateUi()
       }
            customDialog.dismiss() }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show()
    }

    private fun checkAdPostValidation() {

        val url= etAdsLink.text.toString().trim()
        when {

            firstimage.isEmpty() -> {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.please_select_image),
                    Toast.LENGTH_LONG
                ).show()

            }
            etAddTitle.text.toString().isEmpty() -> {
                etAddTitle.requestFocus()
                etAddTitle.error = resources.getString(R.string.please_enter_title)
            }
            tvFromDate.text.toString().isEmpty() -> {
                tvFromDate.requestFocus()
                tvFromDate.error = resources.getString(R.string.please_enter_start_date)
            }
            c123.text.toString().isEmpty() -> {
                c123.requestFocus()
                c123.error = resources.getString(R.string.please_enter_end_date)
            }

            etAdsLink.text.toString().isEmpty() -> {
                etAdsLink.requestFocus()
                etAdsLink.error = resources.getString(R.string.please_enter_link)
            }
           !Patterns.WEB_URL.matcher(url).matches() -> {
                etAdsLink.requestFocus()
                etAdsLink.error = resources.getString(R.string.please_enter_valid_link)
            }
            etEnterBudget.text.toString().isEmpty() -> {
                etEnterBudget.requestFocus()
                etEnterBudget.error = resources.getString(R.string.please_enter_budget)
            }
            etEnterBudget.text.toString().equals("0") -> {
                etEnterBudget.requestFocus()
                etEnterBudget.error = resources.getString(R.string.please_enter_budget_price)
            }
            etEnterDescription.text.toString().isEmpty() -> {
                etEnterDescription.requestFocus()
                etEnterDescription.error = resources.getString(R.string.please_enter_description)
            }
            !ivCheckbox.isChecked->{
                Toast.makeText(requireActivity(),"Please select terms and condition", Toast.LENGTH_SHORT).show()
            }

         actionselected.trim().isEmpty()-> {
                Toast.makeText(requireActivity(),"Please select an action", Toast.LENGTH_SHORT).show()
            }

            else -> {
                val intent = Intent(activity, PreviewActivity::class.java)
                intent.putExtra("title",etAddTitle.text.toString().trim())
                intent.putExtra("startDate",tvFromDate.text.toString().trim())
                intent.putExtra("endDate",c123.text.toString().trim())
                intent.putExtra("adLink",etAdsLink.text.toString().trim())
                intent.putExtra("budget",etEnterBudget.text.toString().trim())
                intent.putExtra("description",etEnterDescription.text.toString().trim())
//                intent.putExtra("adsList",adsList)
                intent.putExtra("adsList",firstimage)
                intent.putExtra("action",actionselected)
                intent.putExtra("actionContent",actionSelectedTitle)
                intent.putExtra("intentFrom","add")
                startActivity(intent)
            }
        }
    }

    private fun updateUi()
    {
        if(adsList.isNotEmpty()){
        Glide.with(requireContext()).load(adsList.last()).into(ivAddImage)
        }
        else{
            ivAdsCamera.visibility = VISIBLE
            ivDelete.visibility = GONE
            ivAddImage.setImageResource(0)
            //adsList.clear()
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
                Glide.with(this).load(result[0].path).into(ivAddImage)
//                ivAdsCamera.visibility = GONE
//                ivDelete.visibility = VISIBLE

                if (type == "1")
                {
                    firstimage = result[0].path
                   // adsList.add(firstimage)

//                    adapter.notifyDataSetChanged()
//                    updateUi()
                }
            }
            .onCancel {

            }
            .start()
    }

    override fun onclick(positon: Int) {
        mAlbumFiles = java.util.ArrayList()
        mAlbumFiles.clear()
        selectImage(ivAddImage,"1")
    }

}



