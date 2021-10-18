package com.stalkstock.vender.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.`interface`.GetLatLongInterface
import com.stalkstock.utils.commonmodel.LocationModel
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.extention.setAutoComplete
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.VendorBusinessDetailResponse
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_bussiness_profile.*
import okhttp3.RequestBody
import java.util.*

class EditBussinessProfile : BaseActivity(), GetLatLongInterface,
    AdapterView.OnItemSelectedListener, Observer<RestObservable> {
    private var mAlbumFiles = ArrayList<AlbumFile>()
    var firstimage = ""
    var business_type = 0
    var business_delivery_type = 0
    var mLatitude = "0"
    var mLongitude = "0"
    var mCountryName = ""
    val viewModel: VendorViewModel by viewModels()

    override fun getContentId(): Int {
        return R.layout.activity_edit_bussiness_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageView = findViewById<ImageView>(R.id.edit_businessbackarrow)
        val button = findViewById<Button>(R.id.businessupdatebutton)

        setAutoComplete(
            LocationModel(
                editboxbusinesscity,
                editboxbusinessstate,
                editboxbusinesscode,
                autoTvLocation!!
            ), this
        )

        business_imageset.setOnClickListener { askCameraPermissons() }
        imageView.setOnClickListener { onBackPressed() }
        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinner_type = findViewById<Spinner>(R.id.spinner_type)
        val spinner_delivery_type = findViewById<Spinner>(R.id.spinner_delivery_type)
        spinner.onItemSelectedListener = this
        spinner_type.onItemSelectedListener = this
        spinner_delivery_type.onItemSelectedListener = this
        val foodadapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter
        val foodadapter2: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_type.adapter = foodadapter2

        val foodadapter3: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_delivery_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter3.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_delivery_type.adapter = foodadapter3

        button.setOnClickListener {
            val logoutUpdatedDialog2 = Dialog(this@EditBussinessProfile)
            logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
            logoutUpdatedDialog2.setContentView(R.layout.businessprofilealertbox)
            logoutUpdatedDialog2.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            logoutUpdatedDialog2.setCancelable(true)
            logoutUpdatedDialog2.setCanceledOnTouchOutside(false)
            logoutUpdatedDialog2.window!!.setGravity(Gravity.CENTER)
            logoutUpdatedDialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btncontinue = logoutUpdatedDialog2.findViewById<Button>(R.id.done_button)
            btncontinue.setOnClickListener {
                onBackPressed()
                logoutUpdatedDialog2.dismiss()
            }
            logoutUpdatedDialog2.show()
        }
        CommonMethods.hideKeyboard(this, business_imageset)

        if (intent.hasExtra("data")) {
            val body = intent.getSerializableExtra("data") as VendorBusinessDetailResponse.Body
            setDataUI(body)
        }
        businessupdatebutton.setOnClickListener {
            setValidation()
        }
    }

    private fun setDataUI(body: VendorBusinessDetailResponse.Body) {
        val vendorDetail = body.vendorDetail
        business_imageset.loadImage(vendorDetail.shopLogo)
        editboxbusinessname.setText(vendorDetail.firstName)
        editlastboxbusinessname.setText(vendorDetail.lastName)
        ediboxbusinessname2.setText(vendorDetail.firstName + " " + vendorDetail.lastName)
        editboxbusinessabout.setText(vendorDetail.shopDescription)
        editboxbusinesslicense.setText(vendorDetail.buisnessLicense)
        editboxbusinessEmail.setText(body.email)
        editboxbusinessmobile.setText(body.mobile)
        editboxbusinessmobilenumber.setText(vendorDetail.buisnessPhone)
        editboxbusinesswebsite.setText(vendorDetail.website)
        autoTvLocation.setText(vendorDetail.shopAddress)
        if (!checkStringNull(vendorDetail.addressLine2))
            editboxbusinessadressline2.setText(vendorDetail.addressLine2)
        editboxbusinesscity.setText(vendorDetail.city)
        editboxbusinessstate.setText(vendorDetail.state)
        editboxbusinesscode.setText(vendorDetail.postalCode)
        business_type = vendorDetail.buisnessTypeId
        mLatitude = vendorDetail.latitude
        mLongitude = vendorDetail.longitude
        business_delivery_type = vendorDetail.deliveryType + 1
        mCountryName = vendorDetail.country
        spinner_type.setSelection(vendorDetail.buisnessTypeId)
        spinner_delivery_type.setSelection(business_delivery_type)
        val appThemeList = resources.getStringArray(R.array.Select_country)
        for (i in appThemeList.indices) {
            if (appThemeList[i].equals(vendorDetail.country, ignoreCase = true)) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList()
        mAlbumFiles.clear()
        selectImage(business_imageset, "1")
    }

    private fun selectImage(setimage: ImageView?, s: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this@EditBussinessProfile).load(result[0].path).into(setimage!!)
                if (s == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel { }
            .start()
    }

    override fun getLatLongListner(lat: String, long: String) {
        mLatitude = lat
        mLongitude = long
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner) {
            val array = this.resources.getStringArray(R.array.Select_country)

            mCountryName = array.get(p2)
        } else if (p0?.id == R.id.spinner_type) {
            var array = this.resources.getStringArray(R.array.Select_business_type)

            business_type = p2
        } else if (p0?.id == R.id.spinner_delivery_type) {
            var array = this.resources.getStringArray(R.array.Select_business_delivery_type)

            business_delivery_type = p2
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun setValidation() {

        if (editboxbusinessname.text.toString().isEmpty()) {
            editboxbusinessname.requestFocus()
            editboxbusinessname.error = resources.getString(R.string.please_enter_first_name)
        } else if (editlastboxbusinessname.text.toString().isEmpty()) {
            editlastboxbusinessname.requestFocus()
            editlastboxbusinessname.error = resources.getString(R.string.please_enter_last_name)
        } else if (ediboxbusinessname2.text.toString().isEmpty()) {
            ediboxbusinessname2.requestFocus()
            ediboxbusinessname2.error = resources.getString(R.string.please_enter_business_name)
        } else if (editboxbusinessabout.text.toString().isEmpty()) {
            editboxbusinessabout.requestFocus()
            editboxbusinessabout.error = resources.getString(R.string.please_enter_business_description)
        } else if (business_type == 0) {
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_business_type))
        } else if (business_delivery_type == 0) {
            AppUtils.showErrorAlert(
                this,
                resources.getString(R.string.please_enter_business_delivery_type)
            )
        } else if (editboxbusinesslicense.text.toString().isEmpty()) {
            editboxbusinesslicense.requestFocus()
            editboxbusinesslicense.error = resources.getString(R.string.please_enter_business_license)
        } else if (editboxbusinessEmail.text.toString().isEmpty()) {
            editboxbusinessEmail.requestFocus()
            editboxbusinessEmail.error = resources.getString(R.string.please_enter_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editboxbusinessEmail.text.toString())
                .matches()
        ) {
            editboxbusinessEmail.requestFocus()
            editboxbusinessEmail.error = resources.getString(R.string.please_enter_valid_email)
        } else if (editboxbusinessmobile.text.toString().isEmpty()) {
            editboxbusinessmobile.requestFocus()
            editboxbusinessmobile.error = resources.getString(R.string.please_enter_mobile_number)
        } else if (editboxbusinessmobile.text
                .toString().length < 10 || editboxbusinessmobile.text
                .toString().length > 13
        ) {
            editboxbusinessmobile.requestFocus()
            editboxbusinessmobile.error = resources.getString(R.string.please_enter_valid_number)
        } else if (editboxbusinessmobilenumber.text.toString().isEmpty()) {
            editboxbusinessmobilenumber.requestFocus()
            editboxbusinessmobilenumber.error = resources.getString(R.string.please_enter_business_phone_number)
        } else if (editboxbusinessmobilenumber.text.toString().length < 6) {
            editboxbusinessmobilenumber.requestFocus()
            editboxbusinessmobilenumber.error = resources.getString(R.string.please_enter_valid_number)
        } else if (editboxbusinesswebsite.text.toString().isEmpty()) {
            editboxbusinesswebsite.requestFocus()
            editboxbusinesswebsite.error = resources.getString(R.string.please_enter_website)
        } else if (autoTvLocation.text.toString().isEmpty()) {
            autoTvLocation.requestFocus()
            autoTvLocation.error = resources.getString(R.string.please_enter_business_address)
        } else if (editboxbusinesscity.text.toString().isEmpty()) {
            editboxbusinesscity.requestFocus()
            editboxbusinesscity.error = resources.getString(R.string.please_enter_city)
        } else if (editboxbusinessstate.text.toString().isEmpty()) {
            editboxbusinessstate.requestFocus()
            editboxbusinessstate.error = resources.getString(R.string.please_enter_state)
        } else if (editboxbusinesscode.text.toString().isEmpty()) {
            editboxbusinesscode.requestFocus()
            editboxbusinesscode.error = resources.getString(R.string.please_enter_postal_code)
        } else {
            val hashMap = HashMap<String, RequestBody>()
            hashMap[GlobalVariables.PARAM.firstname] =
                mUtils.createPartFromString(editboxbusinessname.text.toString().trim())
            hashMap[GlobalVariables.PARAM.lastname] =
                mUtils.createPartFromString(editlastboxbusinessname.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopName] =
                mUtils.createPartFromString(ediboxbusinessname2.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopDescription] =
                mUtils.createPartFromString(editboxbusinessabout.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessTypeId] =
                mUtils.createPartFromString(business_type.toString())
            hashMap[GlobalVariables.PARAM.buisnessDeliveryTypeId] =
                mUtils.createPartFromString((business_delivery_type - 1).toString())
            hashMap[GlobalVariables.PARAM.buisnessLicense] =
                mUtils.createPartFromString(editboxbusinesslicense.text.toString().trim())
            hashMap[GlobalVariables.PARAM.email] =
                mUtils.createPartFromString(editboxbusinessEmail.text.toString().trim())
            hashMap[GlobalVariables.PARAM.mobile] =
                mUtils.createPartFromString(editboxbusinessmobile.text.toString().trim())
            hashMap[GlobalVariables.PARAM.businessPhone] =
                mUtils.createPartFromString(editboxbusinessmobilenumber.text.toString().trim())
            hashMap[GlobalVariables.PARAM.website] =
                mUtils.createPartFromString(editboxbusinesswebsite.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopAddress] =
                mUtils.createPartFromString(autoTvLocation.text.toString().trim())
            if (!checkStringNull(editboxbusinessadressline2.text.toString().trim()))
                hashMap[GlobalVariables.PARAM.addressLine2] =
                    mUtils.createPartFromString(editboxbusinessadressline2.text.toString().trim())
            hashMap[GlobalVariables.PARAM.city] =
                mUtils.createPartFromString(editboxbusinesscity.text.toString().trim())
            hashMap[GlobalVariables.PARAM.state] =
                mUtils.createPartFromString(editboxbusinessstate.text.toString().trim())
            hashMap[GlobalVariables.PARAM.postalCode] =
                mUtils.createPartFromString(editboxbusinesscode.text.toString().trim())
            hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(mCountryName)
            hashMap[GlobalVariables.PARAM.latitude] = mUtils.createPartFromString(mLatitude)
            hashMap[GlobalVariables.PARAM.longitude] = mUtils.createPartFromString(mLongitude)

            viewModel.updateVendorBusinessDetailApi(this, true, hashMap, firstimage, mUtils)
            viewModel.mResponse.observe(this, this)
        }

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorBusinessDetailResponse) {
                    val mResponse: VendorBusinessDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        showToast(mResponse.message)
                        finish()
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
}