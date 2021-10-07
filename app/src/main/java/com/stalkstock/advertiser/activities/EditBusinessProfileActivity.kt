package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.model.BuisnessDetailResponse
import com.stalkstock.advertiser.model.BusinessTypeResponse
import com.stalkstock.advertiser.model.EditBuisnessDetail
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.DriverProfileDetailResponse
import com.stalkstock.driver.models.EditDriverResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.*
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_edit_business_profile.*
import kotlinx.android.synthetic.main.activity_edit_business_profile.emailEdittext
import kotlinx.android.synthetic.main.activity_edit_business_profile.image
import kotlinx.android.synthetic.main.activity_edit_business_profile.spinner
import kotlinx.android.synthetic.main.activity_edit_business_profile.spinner_type
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.spiner_layout_text.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody
import java.util.HashMap

class EditBusinessProfileActivity :BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    lateinit var successfulUpdatedDialog: Dialog
    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage=""

    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    override fun getContentId(): Int {
        return R.layout.activity_edit_business_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        tv_heading.text = "Edit Business Profile"

        getBusinessTypeApi()

        getBusinessProfileApi()

        iv_back.setOnClickListener(this)
        btn_update.setOnClickListener(this)
        image.setOnClickListener(this)

        //CommonMethods.hideKeyboard(this@EditBusinessProfileActivity, btn_update)

        val foodAdapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodAdapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_type.adapter = foodAdapter2

        val foodadapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter

    }

    private fun getBusinessTypeApi() {
       viewModel.getBusinessType(this,true)
        viewModel.mResponse.observe(this,this)
    }

    private fun getBusinessProfileApi() {
        val map = HashMap<String, String>()

        viewModel.getBusinessDetail(this, true,map)
        viewModel.mResponse.observe(this, this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.btn_update -> {
                setValidation()

            }
            R.id.image ->{
                mAlbumFiles = java.util.ArrayList()
                mAlbumFiles.clear()
                selectImage(image)
            }
        }
    }

    private fun selectImage(image: CircleImageView) {
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
                Glide.with(this).load(result[0].path).into(image)
                firstimage = result[0].path
            }
            .onCancel {

            }
            .start()
    }

    private fun setValidation() {
        when {
//            firstimage.isEmpty() -> {
//                Toast.makeText(
//                    this,
//                    resources.getString(R.string.please_select_image),
//                    Toast.LENGTH_LONG
//                ).show()
//
//            }
            etFirstName.text.toString().isEmpty() -> {
                etFirstName.requestFocus()
                etFirstName.error = resources.getString(R.string.please_enter_first_name)
            }
            etLastName.text.toString().isEmpty() -> {
                etLastName.requestFocus()
                etLastName.error = resources.getString(R.string.please_enter_last_name)
            }
            etPhone.text.toString().isEmpty() -> {
                etPhone.requestFocus()
                etPhone.error = resources.getString(R.string.please_enter_mobile_number)
            }
            etEmail.text.toString().isEmpty() -> {
                etEmail.requestFocus()
                etEmail.error = resources.getString(R.string.please_enter_email)
            }

            etBusinessName.text.toString().isEmpty() -> {
                etBusinessName.requestFocus()
                etBusinessName.error = resources.getString(R.string.please_enter_business_name)
            }
            etBusinessLicense.text.toString().isEmpty() -> {
                etBusinessLicense.requestFocus()
                etBusinessLicense.error = resources.getString(R.string.please_enter_business_license)
            }

            spinner_type.selectedItemPosition == 0 ->{
                AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_business_type))
            }

            etBusinessPhone.text.toString().isEmpty() -> {
                etBusinessPhone.requestFocus()
                etBusinessPhone.error = resources.getString(R.string.please_enter_business_phone_number)
            }
            etWebsite.text.toString().isEmpty() -> {
                etWebsite.requestFocus()
                etWebsite.error = resources.getString(R.string.please_enter_website)
            }
            etSreetAddress.text.toString().isEmpty() -> {
                etSreetAddress.requestFocus()
                etSreetAddress.error = resources.getString(R.string.please_enter_business_address)
            }
            etFloor.text.toString().isEmpty() -> {
                etFloor.requestFocus()
                etFloor.error = resources.getString(R.string.please_enter_business_address)
            }
            etCity.text.toString().isEmpty() -> {
                etCity.requestFocus()
                etCity.error = resources.getString(R.string.please_enter_city)
            }
            etState.text.toString().isEmpty() -> {
                etState.requestFocus()
                etState.error = resources.getString(R.string.please_enter_state)
            }

            etZipCode.text.toString().isEmpty() -> {
                etZipCode.requestFocus()
                etZipCode.error = resources.getString(R.string.please_enter_postal_code)
            }
            spinner.selectedItemPosition == 0 -> {
                AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_country))
            }

            else -> {
                editBusinessProfileApi()
            }

        }
    }

    private fun editBusinessProfileApi() {
        val map = HashMap<String, RequestBody>()

        map["buisnessLicense"] = mUtils.createPartFromString(etBusinessLicense.text.toString())
        map["website"] = mUtils.createPartFromString(etWebsite.text.toString())
        map["city"] = mUtils.createPartFromString(etCity.text.toString())
        map["country"] = mUtils.createPartFromString(spinner.selectedItem.toString())
        map["state"] = mUtils.createPartFromString(etState.text.toString())
        map["postalCode"] = mUtils.createPartFromString(etZipCode.text.toString())
        map["buisnessAddress"] = mUtils.createPartFromString(etSreetAddress.text.toString())
        map["addressLine2"] = mUtils.createPartFromString(etFloor.text.toString())
        map["buisnessDescription"] = mUtils.createPartFromString(etDecription.text.toString())
        map["buisnessTypeId"] = mUtils.createPartFromString(spinner_type.selectedItemPosition.toString())
        map["buisnessTypeName"] = mUtils.createPartFromString(spinner_type.selectedItem.toString())
        map["buisnessName"] = mUtils.createPartFromString(etBusinessName.text.toString())
        map["buisnessPhone"] = mUtils.createPartFromString(etBusinessPhone.text.toString())
        map["buisnessLogo"] = mUtils.createPartFromString("")

        viewModel.editBusinessProfile(this,true,map,firstimage,mUtils)
    }


    private fun updateDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        if (MyApplication.instance.getString("usertype").equals("4")) {

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        } else if (MyApplication.instance.getString("usertype").equals("5")) {

            successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        } else {

        }
        successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            finish()
        }

        successfulUpdatedDialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is EditBuisnessDetail) {
                    val mResponse: EditBuisnessDetail = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        updateDailogMethod()

                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is BuisnessDetailResponse) {
                    val mResponse: BuisnessDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                    }
                }

                if (it.data is BusinessTypeResponse){
                    val mResponse: BusinessTypeResponse = it.data

                       if (mResponse.code == GlobalVariables.URL.code){

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

    private fun setData(mResponse: BuisnessDetailResponse) {
        image.loadImage(mResponse.body.advertiserDetail.image)
        etFirstName.setText(mResponse.body.advertiserDetail.firstName)
        etLastName.setText(mResponse.body.advertiserDetail.lastName)
        etBusinessName.setText(mResponse.body.advertiserDetail.buisnessName)
        etDecription.setText(mResponse.body.advertiserDetail.buisnessDescription)

        etBusinessPhone.setText(mResponse.body.advertiserDetail.buisnessPhone)
        etEmail.setText(mResponse.body.email)
        etPhone.setText(mResponse.body.mobile)
        etBusinessLicense.setText(mResponse.body.advertiserDetail.buisnessLicense)
        etWebsite.setText(mResponse.body.advertiserDetail.website)

        etSreetAddress.setText(mResponse.body.advertiserDetail.buisnessAddress)
        etFloor.setText(mResponse.body.advertiserDetail.addressLine2)
        etCity.setText(mResponse.body.advertiserDetail.city)
        etState.setText(mResponse.body.advertiserDetail.state)
        etZipCode.setText(mResponse.body.advertiserDetail.postalCode)

        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "2")
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.notification, mResponse.body.notification)
    }
}

