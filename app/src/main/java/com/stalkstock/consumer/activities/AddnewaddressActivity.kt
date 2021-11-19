package com.stalkstock.consumer.activities;

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.GPSTracker
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_addnewaddress.*
import okhttp3.RequestBody
import java.util.*


class AddnewaddressActivity : GPSTracker(), OnMapReadyCallback, Observer<RestObservable> {

    private var target: LatLng? = null
    var city = ""
    var address = ""
    var geoLocation = ""
    var state = ""
    var country = ""
    var postalCode = ""
    var knownName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_signup.setOnClickListener {
            addNewAddressAPI()
        }
        back.setOnClickListener {
            finish();
        }

    }

    val viewModel: HomeViewModel by viewModels()

    private fun addNewAddressAPI() {
        val map = HashMap<String, RequestBody>()
        map["latitude"] = mUtils.createPartFromString(mLatitude)
        map["longitude"] = mUtils.createPartFromString(mLongitude)
        map["street_address"] = mUtils.createPartFromString(knownName)
        map["address_line2"] = mUtils.createPartFromString("")
        map["geoLocation"] = mUtils.createPartFromString(address)
        map["city"] = mUtils.createPartFromString(city)
        map["state"] = mUtils.createPartFromString(state)
        map["zipcode"] = mUtils.createPartFromString(postalCode)
        map["country"] = mUtils.createPartFromString(country)
        map["type"] = mUtils.createPartFromString("1")
        viewModel.useraddUserAddressAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)
    }

    private var pinMarker: Marker? = null
    var mLatitude = ""
    var mLongitude = ""
    override fun checkgps() {
        mLatitude = getLatitude().toString()
        mLongitude = getLongitude().toString()


        val sydney = LatLng(mLatitude.toDouble(), mLongitude.toDouble())
        getAddress(mLatitude.toDouble(), mLongitude.toDouble())
        pinMarker = mMap!!.addMarker(
            MarkerOptions()
                .position(sydney)
                .anchor(0.5f, 0.5f)
                .title("Your are Here").icon(
                    BitmapDescriptorFactory.fromBitmap(
                        Bitmap.createScaledBitmap(
                            BitmapFactory.decodeResource(resources, R.drawable.black_map_circle),
                            70,
                            120,
                            false
                        )
                    )
                )
        )

        val zoomLevel = 16.0f
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap!!.isMyLocationEnabled = true
    }

    override fun getContentId(): Int {
        return R.layout.activity_addnewaddress
    }

    private var mMap: GoogleMap? = null
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        checkLocationPermission()

        mMap!!.setOnCameraMoveListener {
            target = mMap!!.cameraPosition.target
            pinMarker!!.position = target
        }
        mMap!!.setOnCameraIdleListener {

            if (target != null)
                getAddress(target!!.latitude, target!!.longitude)

        }

    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )
        address =
            addresses[0].getAddressLine(0)
        tv_ll.text = address

        if (addresses[0].locality != null)
            city = addresses[0].locality
        if (addresses[0].adminArea != null)
            state = addresses[0].adminArea
        if (addresses[0].countryName != null)
            country = addresses[0].countryName
        if (addresses[0].postalCode != null)
            postalCode = addresses[0].postalCode
        if (addresses[0].featureName != null) {
            knownName = addresses[0].featureName
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 2000)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
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
