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
import com.stalkstock.advertiser.model.EditProfileResponse
import com.stalkstock.advertiser.model.AdvertiserProfileDetailResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.CommercialProfileDetailResponse
import com.stalkstock.commercial.view.model.EditCommercialProfileResponse
import com.stalkstock.driver.models.DriverProfileDetailResponse
import com.stalkstock.driver.models.EditDriverResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.emailEdittext
import kotlinx.android.synthetic.main.activity_edit_profile.image
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody
import java.util.HashMap

class EditProfileActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()
    val homeModel: HomeViewModel by viewModels()
    lateinit var successfulUpdatedDialog:Dialog
    val mContext: Context =this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""

    val userType = MyApplication.instance.getString("usertype")

    private val viewModelAdvertiser: AdvertiserViewModel by viewModels()

    override fun getContentId(): Int {
        return R.layout.activity_edit_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_heading.text = "Edit Profile"
        iv_back.setOnClickListener(this)
        btn_update_profile.setOnClickListener(this)
        image.setOnClickListener(this)

        if (userType.equals("5")){
            viewModelAdvertiser.mResponse.observe(this,this)
            getUserProfile()
        }
        else if (userType.equals("4")){
            homeModel.homeResponse.observe(this,this)
            getCommercialUserProfile()
        }
        else{
            viewModel.mResponse.observe(this, this)
            getprofileAPI()
        }
    }

    private fun getCommercialUserProfile() {
        val map = HashMap<String, String>()
        homeModel.getCommercialUserProfile(this, true,map)
        homeModel.homeResponse.observe(this, this)
    }

    private fun getUserProfile() {

        val map = HashMap<String, String>()
        viewModelAdvertiser.getUserProfile(this, true,map)
        viewModelAdvertiser.mResponse.observe(this, this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{ finish() }
            R.id.btn_update_profile->{
                if (userType.equals("5")){
                    setValidationEditprofile()
                }

                else if (userType.equals("4")){
                    setValidationEditprofile()
                }
                else{
                    updateProfileAPI()
                }
            }
            R.id.image->{
            mAlbumFiles = java.util.ArrayList()
            mAlbumFiles.clear()
            selectImage(image)
            } }
    }

    private fun setValidationEditprofile() {
        when {
            edtFirstName.text.toString().isEmpty() -> {
                et_firstName.requestFocus()
                et_firstName.error = resources.getString(R.string.please_enter_first_name)
            }
            edtLastName.text.toString().isEmpty() -> {
                et_lastName.requestFocus()
                et_lastName.error = resources.getString(R.string.please_enter_last_name)
            }
            edtMobile.text.toString().isEmpty() -> {
                edtMobile.requestFocus()
                edtMobile.error = resources.getString(R.string.please_enter_mobile_number)
            }
            emailEdittext.text.toString().isEmpty() -> {
                emailEdittext.requestFocus()
                emailEdittext.error = resources.getString(R.string.please_enter_email)
            }
            else -> {
                editProfile()
            }
        }
    }

    private fun selectImage(ivProduct: ImageView) {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            .widget(
                Widget.newDarkBuilder(this)
                    .title(getString(R.string.app_name))
                    .build()
            )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProduct)
                    firstimage = result[0].path
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

        when {
            MyApplication.instance.getString("usertype").equals("4") -> {

                successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
            }
            MyApplication.instance.getString("usertype").equals("5") -> {

                successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
            }
            MyApplication.instance.getString("usertype").equals("2") -> {

                successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
            }
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
        map["firstName"] = mUtils.createPartFromString(edtFirstName.text.toString())
        map["lastName"] = mUtils.createPartFromString(edtLastName.text.toString())
        viewModel.editDriverProfileDetail(this, true, map, firstimage, mUtils)

    }

    private fun editProfile() {
        val map = HashMap<String, RequestBody>()
        map["firstName"] = mUtils.createPartFromString(edtFirstName.text.toString())
        map["lastName"] = mUtils.createPartFromString(edtLastName.text.toString())
        if (userType.equals("5")){
            viewModelAdvertiser.editUserProfile(this,true,map,firstimage,mUtils)
        }
       else {
            homeModel.editCommercialUserProfile(this,true,map,firstimage,mUtils)
        }

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditDriverResponse) {
                    val mResponse: EditDriverResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        updateDailogMethod()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is DriverProfileDetailResponse) {
                    val mResponse: DriverProfileDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                    }
                }

                if (it.data is AdvertiserProfileDetailResponse) {
                    val mResponse: AdvertiserProfileDetailResponse = it.data
                    val data = it.data
                    if (data.code==200){
                        setUserData(mResponse)
                    }
                }

                if (it.data is EditProfileResponse){
                    val mResponse: EditProfileResponse = it.data
                    val data = it.data
                    if (mResponse.code == GlobalVariables.URL.code ){
                        savePrefrence(
                            GlobalVariables.SHARED_PREF_ADVERTISER.firstName,
                            data.body.advertiserDetail.firstName
                        )
                        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.lastName, data.body.advertiserDetail.lastName)
                        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.image, data.body.advertiserDetail.image)
                        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.email, data.body.email)
                        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.mobile, data.body.mobile)
                        updateDailogMethod()
                    }
                }
                if (it.data is CommercialProfileDetailResponse) {
                    val mResponse: CommercialProfileDetailResponse = it.data
                    val data = it.data
                    if (data.code==200){
                        setCommercialData(mResponse)
                    }
                }

                if (it.data is EditCommercialProfileResponse){
                    val mResponse: EditCommercialProfileResponse = it.data
                    val data = it.data
                    if (mResponse.code == GlobalVariables.URL.code ){
                        savePrefrence(
                            GlobalVariables.SHARED_PREF_ADVERTISER.firstName,
                            data.body.commercialDetail.firstName
                        )
                        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.lastName, data.body.commercialDetail.lastName)
                        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.image, data.body.commercialDetail.image)
                        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.email, data.body.email)
                        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.mobile, data.body.mobile)
                        updateDailogMethod()
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setCommercialData(mResponse: CommercialProfileDetailResponse) {
        Glide.with(this).load(mResponse.body.commercialDetail.image).into(image)
        edtFirstName.setText(mResponse.body.commercialDetail.firstName)
        edtLastName.setText(mResponse.body.commercialDetail.lastName)
        edtMobile.setText(mResponse.body.mobile)
        emailEdittext.setText(mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "2")
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.notification, mResponse.body.notification)
    }

    private fun setUserData(mResponse: AdvertiserProfileDetailResponse) {
        Glide.with(this).load(mResponse.body.advertiserDetail.image).into(image)
        edtFirstName.setText(mResponse.body.advertiserDetail.firstName)
        edtLastName.setText(mResponse.body.advertiserDetail.lastName)
        edtMobile.setText(mResponse.body.mobile)
        emailEdittext.setText(mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "2")
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.notification, mResponse.body.notification)
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