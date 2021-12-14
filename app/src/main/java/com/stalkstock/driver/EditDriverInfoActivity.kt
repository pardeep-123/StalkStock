package com.stalkstock.driver

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.DriverDocResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.extention.checkObjectNull
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_detail.*
import kotlinx.android.synthetic.main.activity_edit_business_profile.*
import kotlinx.android.synthetic.main.activity_edit_driver_info.*
import kotlinx.android.synthetic.main.activity_edit_driver_info.btn_update
import kotlinx.android.synthetic.main.activity_edit_driver_info.edtCity
import kotlinx.android.synthetic.main.activity_signup3.*
import kotlinx.android.synthetic.main.activity_signup3.spinner
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*


class EditDriverInfoActivity : BaseActivity(), Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {
    val mContext: Context = this
    private val myCalendar = Calendar.getInstance()
    private lateinit var date: DatePickerDialog.OnDateSetListener
    private var mAlbumFiles = ArrayList<AlbumFile>()
    var mLicenseimage1 = ""
    var mLicenseimage2 = ""
    var mRegistrationImage = ""
    var mInsuranceImage = ""
    var mFirstImage = ""
    var type=1
    private var mCountryName = ""
    var city = ""
    var address = ""
    var geoLocation = ""
    var state = ""
    var postalCode = ""
    private var latitude = ""
    private var longitude = ""
    private val AUTOCOMPLETE_REQUEST_CODE = 1

    lateinit var successfulUpdatedDialog: Dialog
    override fun getContentId(): Int {
        return R.layout.activity_edit_driver_info
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Places.initialize(this, getString(R.string.maps_api_key))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        tv_heading.text = "Edit Driver Information"
        iv_back.setOnClickListener {
            finish()
        }
        date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel()
        }

        edtInsuranceExpiry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!checkObjectNull(s)) {

                }
            }

        })

        edtRegisterationExpiry.setOnClickListener {

            type=1
            datePicker()

        }
        edtLicenceExpiry.setOnClickListener {

            type=2
            datePicker()
        }

        btn_update.setOnClickListener {
            if (validations())
                hitUpdateDriverDocAPI()
//                updateDailogMethod()
        }

        edtDriverStreetAddress.setOnClickListener {
            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS,
                Place.Field.ADDRESS
            )
            // Start the autocomplete intent.
            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(
                    this
                )
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        rlEditDriverFront.setOnClickListener {
            askCameraPermissons("1")
        }

        rlEditDriverBack.setOnClickListener {
            askCameraPermissons("2")
        }

        rl_EditRegistration.setOnClickListener {
            askCameraPermissons("3")
        }
        rlEditProofInsurance.setOnClickListener {
            askCameraPermissons("4")
        }
        CommonMethods.hideKeyboard(this@EditDriverInfoActivity, btn_update)


        // addItemsOnSpinner2();
        var spinner = findViewById(R.id.spinnerVehicleType) as Spinner


        val foodadapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_Vehicle_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter
        spinner.onItemSelectedListener = this

        val foodadapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinnerCountry.adapter = foodadapter2
        spinnerCountry.onItemSelectedListener = this

        if (intent.hasExtra("body")) {
            var driverDocResponse = intent.getSerializableExtra("body") as DriverDocResponse
            setData(driverDocResponse)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val place = Autocomplete.getPlaceFromIntent(data)
                latitude = place.latLng?.latitude.toString()
                longitude = place.latLng?.longitude.toString()
                getAddress(latitude.toDouble(),longitude.toDouble())
                edtDriverStreetAddress.setText(place.name.toString())

            }
        }
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )

        if (addresses[0].locality != null) {
            city = addresses[0].locality
            edtCity.setText(city)
        }
        if (addresses[0].adminArea != null) {
            state = addresses[0].adminArea
            edtDriverState.setText(state)
        }

    }

    private fun datePicker() {

        val datePickerDialog = DatePickerDialog(this, date,  myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH])

        datePickerDialog.datePicker.minDate= System.currentTimeMillis() -1000
        datePickerDialog.show()

    }

    private fun setData(driverDocResponse: DriverDocResponse) {
        Glide.with(this).load(driverDocResponse.body.driverDetail.licenceFrontImage).into(img1)
        Glide.with(this).load(driverDocResponse.body.driverDetail.licenceBackImage).into(img2)
        Glide.with(this).load(driverDocResponse.body.driverDetail.registrationImage).into(img3)
        Glide.with(this).load(driverDocResponse.body.driverDetail.insuranceProof).into(img4)

        val vehicleType = getResources().getStringArray(R.array.Select_Vehicle_type)

        spinnerVehicleType.setSelection(vehicleType.indexOf(driverDocResponse.body.driverDetail.vehicleType))
        if (!checkStringNull(driverDocResponse.body.driverDetail.vehicleMake))
            edtVehicleMake.setText(driverDocResponse.body.driverDetail.vehicleMake)
        if (!checkStringNull(driverDocResponse.body.driverDetail.vehicleModel))
            edtVehicleModel.setText(driverDocResponse.body.driverDetail.vehicleModel)
        if (!checkStringNull(driverDocResponse.body.driverDetail.licenceNumber))
            edtDriverLicenceNumber.setText(driverDocResponse.body.driverDetail.licenceNumber)
        if (!checkStringNull(driverDocResponse.body.driverDetail.licenceExpiryDate))
            edtLicenceExpiry.setText(driverDocResponse.body.driverDetail.licenceExpiryDate)
        if (!checkStringNull(driverDocResponse.body.driverDetail.registrationNumber))
            edtRegisterationNumber.setText(driverDocResponse.body.driverDetail.registrationNumber)
        if (!checkStringNull(driverDocResponse.body.driverDetail.registrationExpiryDate))
            edtRegisterationExpiry.setText(driverDocResponse.body.driverDetail.registrationExpiryDate)
        if (!checkStringNull(driverDocResponse.body.driverDetail.insuranceCompany))
            edtInsuranceProvider.setText(driverDocResponse.body.driverDetail.insuranceCompany)
        if (!checkStringNull(driverDocResponse.body.driverDetail.insurancePolicy))
            edtInsuranceNumber.setText(driverDocResponse.body.driverDetail.insurancePolicy)
        if (!checkStringNull(driverDocResponse.body.driverDetail.insuranceExpiryDate))
            edtInsuranceExpiry.setText(driverDocResponse.body.driverDetail.insuranceExpiryDate)
        if (!checkStringNull(driverDocResponse.body.driverDetail.address))
            edtDriverStreetAddress.setText(driverDocResponse.body.driverDetail.address)
        if (!checkStringNull(driverDocResponse.body.driverDetail.addressLine2))
            edtEnterApartment.setText(driverDocResponse.body.driverDetail.addressLine2)
        if (!checkStringNull(driverDocResponse.body.driverDetail.city))
            edtCity.setText(driverDocResponse.body.driverDetail.city)
        if (!checkStringNull(driverDocResponse.body.driverDetail.state))
            edtDriverState.setText(driverDocResponse.body.driverDetail.state)
        if (!checkStringNull(driverDocResponse.body.driverDetail.postalCode))
            edtZipCode.setText(driverDocResponse.body.driverDetail.postalCode)

        val countryName: Array<String> = resources.getStringArray(R.array.Select_country)
        for(i in countryName.indices)
        {
            if(driverDocResponse.body.driverDetail.country == countryName[i])
            {
                spinnerCountry.setSelection(countryName.indexOf(countryName[i]))
            }
        }

    }

    val viewModel: DriverViewModel by viewModels()

    private fun hitUpdateDriverDocAPI() {
        var hashMap = HashMap<String, RequestBody>()
        hashMap.put(
            "insuranceCompany",
            mUtils.createPartFromString(edtInsuranceProvider.text.toString())
        )
        hashMap.put(
            "licenceNumber",
            mUtils.createPartFromString(edtDriverLicenceNumber.text.toString())
        )
        hashMap.put(
            "licenceExpiryDate",
            mUtils.createPartFromString(edtLicenceExpiry.text.toString())
        )
        hashMap.put(
            "registrationNumber",
            mUtils.createPartFromString(edtRegisterationNumber.text.toString())
        )
        hashMap.put(
            "registrationExpiryDate",
            mUtils.createPartFromString(edtRegisterationExpiry.text.toString())
        )
        hashMap.put(
            "insurancePolicy",
            mUtils.createPartFromString(edtInsuranceNumber.text.toString())
        )
        hashMap.put(
            "insuranceExpiryDate",
            mUtils.createPartFromString(edtInsuranceExpiry.text.toString())
        )
        hashMap.put("address", mUtils.createPartFromString(edtDriverStreetAddress.text.toString()))
        hashMap.put("addressLine2", mUtils.createPartFromString(edtEnterApartment.text.toString()))
        hashMap.put(
            "vehicleType",
            mUtils.createPartFromString(spinnerVehicleType.selectedItem.toString())
        )
        hashMap.put("vehicleMake", mUtils.createPartFromString(edtVehicleMake.text.toString()))
        hashMap.put("vehicleModel", mUtils.createPartFromString(edtVehicleModel.text.toString()))
        hashMap.put("city", mUtils.createPartFromString(edtCity.text.toString()))
        hashMap.put("state", mUtils.createPartFromString(edtDriverState.text.toString()))
        hashMap.put("postalCode", mUtils.createPartFromString(edtZipCode.text.toString()))
        hashMap.put("country", mUtils.createPartFromString(spinnerCountry.selectedItemPosition.toString()))
        hashMap.put("latitude", mUtils.createPartFromString(""))
        hashMap.put("longitude", mUtils.createPartFromString(""))
        viewModel.editDriverDocumentDetail(
            this,
            true,
            hashMap,
            mLicenseimage1,
            mLicenseimage2,
            mRegistrationImage,
            mInsuranceImage,
            mUtils
        )
        viewModel.mResponse.observe(this, this)
    }

    private fun validations(): Boolean {
        if (edtVehicleMake.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.enter_vehicle_make),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtVehicleModel.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.enter_vehicle_model),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtDriverLicenceNumber.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.enter_driver_license_number),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtLicenceExpiry.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_enter_driver_license_exp_date),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtRegisterationNumber.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.enter_registration_number),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtRegisterationExpiry.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_enter_registration_expdate),
                Toast.LENGTH_LONG
            ).show()
            return false
        }else if (edtDriverStreetAddress.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.enter_your_street_address),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtCity.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_enter_city),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtDriverState.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_enter_state),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtZipCode.text.toString().toString().isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.enter_postal_zipcode),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (spinnerCountry.selectedItemPosition==0) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_countryname),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun isInvalidDate(toString: String): Boolean {
        if (toString.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$".toRegex()))
        {
            return false
        }
        else
            return true
//        ^\d{4}\-(0?[1-9]|1[012])\-(0?[1-9]|[12][0-9]|3[01])$
    }

    private fun askCameraPermissons(s: String) {
        mAlbumFiles = ArrayList<AlbumFile>()
        mAlbumFiles.clear()
        selectImage(s)
    }

    private fun selectImage(s: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                if (s.equals("1")) {
                    mLicenseimage1 = result[0].path
                    Glide.with(this).load(result[0].path).into(img1)
                    imgCamera1.visibility = View.GONE
                } else if (s.equals("2")) {
                    mLicenseimage2 = result[0].path
                    Glide.with(this).load(result[0].path).into(img2)
                    imgCamera2.visibility = View.GONE
                } else if (s.equals("3")) {
                    mRegistrationImage = result[0].path
                    Glide.with(this).load(result[0].path).into(img3)
                    imgCamera3.visibility = View.GONE
                } else if (s.equals("4")) {
                    mInsuranceImage = result[0].path
                    Glide.with(this).load(result[0].path).into(img4)
                    imgCamera4.visibility = View.GONE
                }
            }
            .onCancel { }
            .start()
    }

    private fun updateDateLabel() {
        val dateFormat = "yyyy/MM/dd" //In which you need put here
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        if(type==1){
            edtRegisterationExpiry.setText(sdf.format(myCalendar.time))
        }else{
            edtLicenceExpiry.setText(sdf.format(myCalendar.time))
        }


    }

    private fun updateDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tvmsg.text = "Your information has been successfully updated!"
        successfulUpdatedDialog.iv_congrats.setImageResource(R.drawable.thumb_up)
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            finish()
        }

        successfulUpdatedDialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is DriverDocResponse) {
                    val data = it.data as DriverDocResponse
                    if (data.code == 200) {
                        AppUtils.showSuccessAlert(this, data.message.toString())
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 2000)
//                        setdata(data)
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

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, p2: Int, id: Long) {
        if (p2 == 0) {
            (view as? TextView)?.setTextColor(
                ContextCompat.getColor(
                    this@EditDriverInfoActivity, R.color.sort_popup_gray_color
                )
            )
        } else {
            (view as? TextView)?.setTextColor(
                ContextCompat.getColor(
                    this@EditDriverInfoActivity, R.color.black_color
                )
            )
            if (p0?.id == R.id.spinnerCountry) {
                val array = this.resources.getStringArray(R.array.Select_country)
                mCountryName = array[p2]
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}