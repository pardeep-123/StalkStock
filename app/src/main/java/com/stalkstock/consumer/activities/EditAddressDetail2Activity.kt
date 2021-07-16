package com.stalkstock.consumer.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.ModelUserAddressList
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_edit_address_detail2.*
import okhttp3.RequestBody
import java.util.*

class EditAddressDetail2Activity : BaseActivity(), OnMapReadyCallback, Observer<RestObservable> {

    private var currentAddressId=""
    private var selectedAddressType = "1"
    private lateinit var foodadapter: ArrayAdapter<CharSequence>
    private var pinMarker: Marker? = null
    private var mLatitude = ""
    private var mLongitude = ""
    private var mMap: GoogleMap? = null
    var dataSet = 2

    lateinit var tvTagHome: TextView
    lateinit var tvTagWork: TextView
    lateinit var tvTagHotel: TextView
    lateinit var tvTagOther: TextView
    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0

        if (mLatitude != "") {
            val sydney: LatLng
            sydney = LatLng(mLatitude.toDouble(), mLongitude.toDouble())


//            getAddress(mLatitude.toDouble(), mLongitude.toDouble())
            pinMarker = mMap!!.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .anchor(0.5f, 0.5f)
                    .title("Your are Here").icon(
                        BitmapDescriptorFactory.fromBitmap(
                            Bitmap.createScaledBitmap(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.black_map_circle
                                ),
                                70,
                                120,
                                false
                            )
                        )
                    )
            )

            val zoomLevel = 16.0f //This goes up to 21
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
        }

        mMap!!.setOnCameraMoveListener {
            target = mMap!!.cameraPosition.target
            mLatitude = target!!.latitude.toString()
            mLongitude = target!!.longitude.toString()
            if (pinMarker == null) {
                val sydney: LatLng
                sydney = LatLng(mLatitude.toDouble(), mLongitude.toDouble())

                pinMarker = mMap!!.addMarker(
                    MarkerOptions()
                        .position(sydney)
                        .anchor(0.5f, 0.5f)
                        .title("Your are Here").icon(
                            BitmapDescriptorFactory.fromBitmap(
                                Bitmap.createScaledBitmap(
                                    BitmapFactory.decodeResource(
                                        resources,
                                        R.drawable.black_map_circle
                                    ),
                                    70,
                                    120,
                                    false
                                )
                            )
                        )
                )

                val zoomLevel = 16.0f //This goes up to 21
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))

            }
            pinMarker!!.position = target
/*
            Log.e(
                "current LatLong>>",
                target.latitude.toString() + "------" + target.longitude.toString()
            )
*/
        }
        mMap!!.setOnCameraIdleListener {

            if (target != null)
                getAddress(target!!.latitude, target!!.longitude)

        }

    }

    private var target: LatLng? = null
    var city = ""
    var address = ""
    var geoLocation = ""
    var state = ""
    var country = ""
    var postalCode = ""
    var knownName = ""

    fun getAddress(latitude: Double, longitude: Double) {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        geoLocation =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        tvLocation.setText(geoLocation)

        if (addresses[0].getLocality() != null) {
            city = addresses[0].getLocality()
            edtCity.setText(city)
        }
        if (addresses[0].getAdminArea() != null) {
            state = addresses[0].getAdminArea()
            edtState.setText(state)
        }
        if (addresses[0].getCountryName() != null) {
            country = addresses[0].getCountryName()
            spinner.setSelection(foodadapter.getPosition(country))
        }
        if (addresses[0].getPostalCode() != null) {
            postalCode = addresses[0].getPostalCode()
            edtPostalCode.setText(postalCode)
        }
        if (addresses[0].getFeatureName() != null) {
            knownName = addresses[0].getFeatureName() // Only if available else return NULL
            edtStreetAddress.setText(knownName)
//            tv_ll.setText(knownName)
        }


    }

    override fun getContentId(): Int {
        return R.layout.activity_edit_address_detail2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMapInit(mapBool)

        dataSet = if (intent.hasExtra("key")) {
            if (intent.getStringExtra("key") == "edit") {
                1
            } else {
                2
            }
        } else {
            2
        }


        ivBackAddress.setOnClickListener { onBackPressed() }
        btnEdit.setOnClickListener { saveAddressAPI() }


        tvTagHome = findViewById(R.id.tvTagHome)
        tvTagWork = findViewById(R.id.tvTagWork)
        tvTagHotel = findViewById(R.id.tvTagHotel)
        tvTagOther = findViewById(R.id.tvTagOther)

        rl_home.setOnClickListener {
            selectedAddressType = "1"
            changeColorRelative(
                rl_home,
                iv_home_edit,
                tvTagHome,
                rl_work,
                iv_work_edit,
                tvTagWork,
                rl_other,
                iv_loca_edit,
                tvTagOther
            )

        }

        rl_work.setOnClickListener {
            selectedAddressType = "2"
            changeColorRelative(
                rl_work,
                iv_work_edit,
                tvTagWork,
                rl_home,
                iv_home_edit,
                tvTagHome,
                rl_other,
                iv_loca_edit,
                tvTagOther
            )

        }

        rl_other.setOnClickListener {
            selectedAddressType = "3"
            changeColorRelative(
                rl_other,
                iv_loca_edit,
                tvTagOther,
                rl_home,
                iv_home_edit,
                tvTagHome,
                rl_work,
                iv_work_edit,
                tvTagWork
            )

        }
//        tvTagHome.setOnClickListener {
//           // changeColor(tvTagHome,tvTagWork,tvTagHotel,tvTagOther)
//        }
//        tvTagWork.setOnClickListener {
//           // changeColor(tvTagWork,tvTagHome,tvTagHotel,tvTagOther)
//        }
        tvTagHotel.setOnClickListener {
            // changeColor(tvTagHotel,tvTagHome,tvTagWork,tvTagOther)
        }
//        tvTagOther.setOnClickListener {
//           // changeColor(tvTagOther,tvTagHotel,tvTagHome,tvTagWork)
//        }

        if (MyApplication.instance.getString("usertype").equals("4")) {
            rl_work.visibility = View.GONE
            tvTagHome.text = "Business"
            iv_home_edit.setImageResource(R.drawable.ic_work_for_edit)
        } else {

        }

        foodadapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter

        if (dataSet == 1) {
            val body = intent.getSerializableExtra("body") as ModelUserAddressList.Body
            setData(body)
        }

    }

    val viewModel: HomeViewModel by viewModels()

    private fun saveAddressAPI() {
        val map = HashMap<String, RequestBody>()
        map["latitude"] = mUtils.createPartFromString(mLatitude)
        map["longitude"] = mUtils.createPartFromString(mLongitude)
        map["street_address"] = mUtils.createPartFromString(edtStreetAddress.text.toString())
        map["geoLocation"] = mUtils.createPartFromString(tvLocation.text.toString())
        map["address_line2"] = mUtils.createPartFromString(edtFloor.text.toString())
        map["city"] = mUtils.createPartFromString(edtCity.text.toString())
        map["state"] = mUtils.createPartFromString(edtState.text.toString())
        map["zipcode"] = mUtils.createPartFromString(edtPostalCode.text.toString())
        map["country"] = mUtils.createPartFromString(spinner.selectedItem.toString())
        map["special_instruction"] = mUtils.createPartFromString(edtDeliveryInstructions.text.toString())
        map["address_id"] = mUtils.createPartFromString(currentAddressId)
        map["type"] = mUtils.createPartFromString(selectedAddressType)
        viewModel.editUserAddressAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)
    }


    private fun setData(body: ModelUserAddressList.Body) {
        currentAddressId= body.id.toString()
        tvLocation.setText(body.geoLocation)
        edtFloor.setText(body.address_line2)
        edtStreetAddress.setText(body.street_address)
        edtCity.setText(body.city)
        edtState.setText(body.state)
        edtPostalCode.setText(body.zipcode.toString())
        mLatitude = body.latitude
        mLongitude = body.longitude

        if (body.country != null && body.country != "")
            spinner.setSelection(foodadapter.getPosition(body.country))
//        val selectCountry = R.array.Select_country
        edtDeliveryInstructions.setText(body.special_instruction)

        if (body.type.equals("1")) {
            selectedAddressType = "1"
            changeColorRelative(
                rl_home,
                iv_home_edit,
                tvTagHome,
                rl_work,
                iv_work_edit,
                tvTagWork,
                rl_other,
                iv_loca_edit,
                tvTagOther
            )

        } else if (body.type.equals("2")) {
            selectedAddressType = "2"
            changeColorRelative(
                rl_work,
                iv_work_edit,
                tvTagWork,
                rl_home,
                iv_home_edit,
                tvTagHome,
                rl_other,
                iv_loca_edit,
                tvTagOther
            )
        } else if (body.type.equals("3")) {
            selectedAddressType = "3"
            changeColorRelative(
                rl_other,
                iv_loca_edit,
                tvTagOther,
                rl_home,
                iv_home_edit,
                tvTagHome,
                rl_work,
                iv_work_edit,
                tvTagWork
            )
        }
    }

    private fun changeColor(
        textview1: TextView,
        textview2: TextView,
        textview3: TextView,
        textview4: TextView
    ) {
        //textview1.setBackgroundColor(Color.parseColor("#7DBB00"))
//        textview1.setTextColor(Color.parseColor("#FFFFFF"))
//        textview2.setBackgroundColor(Color.parseColor("#C3C3C3"))
//        textview2.setTextColor(Color.parseColor("#000000"))
//        textview3.setBackgroundColor(Color.parseColor("#C3C3C3"))
//        textview3.setTextColor(Color.parseColor("#000000"))
//        textview4.setBackgroundColor(Color.parseColor("#C3C3C3"))
//        textview4.setTextColor(Color.parseColor("#000000"))

    }


    private fun changeColorRelative(
        rl: RelativeLayout,
        im: ImageView,
        tv: TextView,
        rl2: RelativeLayout,
        im2: ImageView,
        tv2: TextView,
        rl3: RelativeLayout,
        im3: ImageView,
        tv3: TextView /*,rl4: RelativeLayout, im4: ImageView,tv4:TextView*/
    ) {
        rl.setBackgroundColor(Color.parseColor("#7DBB00"))
        im.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tv.setTextColor(Color.parseColor("#FFFFFF"))

        rl2.setBackgroundColor(Color.parseColor("#C3C3C3"))
        im2.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        tv2.setTextColor(Color.parseColor("#000000"))

        rl3.setBackgroundColor(Color.parseColor("#C3C3C3"))
        im3.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        tv3.setTextColor(Color.parseColor("#000000"))


        // im.setTextColor(Color.parseColor("#FFFFFF"))
        /*  textview2.setBackgroundColor(Color.parseColor("#C3C3C3"))
          textview2.setTextColor(Color.parseColor("#000000"))
          textview3.setBackgroundColor(Color.parseColor("#C3C3C3"))
          textview3.setTextColor(Color.parseColor("#000000"))
          textview4.setBackgroundColor(Color.parseColor("#C3C3C3"))
          textview4.setTextColor(Color.parseColor("#000000"))*/

    }

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mapFragment: SupportMapFragment

    var mapBool = true


    private fun setMapInit(bool: Boolean) {
        if (bool) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mapBool = false
            if (!this::mapFragment.isInitialized) {
                mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                            //Do something after 100ms
                        }, 2000)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
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


}