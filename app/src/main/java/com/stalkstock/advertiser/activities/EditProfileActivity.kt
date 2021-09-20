package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.DriverProfileDetailResponse
import com.stalkstock.driver.models.EditDriverResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_profile.btn_update_profile
import kotlinx.android.synthetic.main.activity_edit_profile.edtFirstName
import kotlinx.android.synthetic.main.activity_edit_profile.edtLastName
import kotlinx.android.synthetic.main.activity_edit_profile.edtMobile
import kotlinx.android.synthetic.main.activity_edit_profile.emailEdittext
import kotlinx.android.synthetic.main.activity_edit_profile.image
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody
import java.util.HashMap

class EditProfileActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()
    lateinit var successfulUpdatedDialog:Dialog
    val mContext: Context =this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""
    override fun getContentId(): Int {
        return R.layout.activity_edit_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        tv_heading.text = "Edit Profile"
        iv_back.setOnClickListener(this)
        btn_update_profile.setOnClickListener(this)
        image.setOnClickListener(this)
        viewModel.mResponse.observe(this, this)
        getprofileAPI()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }
            R.id.btn_update_profile->{
                updateProfileAPI()
                /*updateDailogMethod()*/
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
                /*if (type.equals("1"))
                {*/
                    firstimage = result[0].path
                /*}*/
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

        if(MyApplication.instance.getString("usertype").equals("4")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else  if(MyApplication.instance.getString("usertype").equals("5")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else  if(MyApplication.instance.getString("usertype").equals("2")){

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        }else{

        }

            successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            finish()
        }

        successfulUpdatedDialog.show()
    }


    private fun getprofileAPI() {
        val map = HashMap<String, String>()
        viewModel.getProfileDetail(this, true, map)
    }

    private fun updateProfileAPI() {
        val map = HashMap<String, RequestBody>()
        map.put("firstName", mUtils.createPartFromString(edtFirstName.text.toString()))
        map.put("lastName", mUtils.createPartFromString(edtLastName.text.toString()))
        viewModel.editDriverProfileDetail(this, true, map, firstimage, mUtils)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditDriverResponse) {
                    val mResponse: EditDriverResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        updateDailogMethod()

//                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is DriverProfileDetailResponse) {
                    val mResponse: DriverProfileDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)

                    } else {
//                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: DriverProfileDetailResponse) {
        image.loadImage(mResponse.body.driverDetail.image)
        edtFirstName.setText(mResponse.body.driverDetail.firstName)
        edtLastName.setText(mResponse.body.driverDetail.lastName)
        edtMobile.setText(mResponse.body.mobile)
        emailEdittext.setText(mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "2")
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.notification, mResponse.body.notification)
    }
}