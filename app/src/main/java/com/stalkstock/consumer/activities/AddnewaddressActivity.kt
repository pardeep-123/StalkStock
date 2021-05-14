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
import android.util.Log
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
import com.stalkstock.consumer.model.ModelSignupUser
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.GPSTracker
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_addnewaddress.*
import okhttp3.RequestBody
import java.util.*


class AddnewaddressActivity : GPSTracker(), OnMapReadyCallback, Observer<RestObservable> {
    /*  AddnewaddressActivity context;
      ImageView back;
      Button btn_signup;*/

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


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /* btn_signup = findViewById(R.id.btn_signup);
         back = findViewById(R.id.back);*/
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


        val sydney: LatLng

        //sydney  = LatLng(getPrefrence(Constant.lat,"0.0").toDouble(), getPrefrence(Constant.long,"0.0").toDouble())

        sydney = LatLng(mLatitude.toDouble(), mLongitude.toDouble())
        //marker.visible()

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

        val zoomLevel = 16.0f //This goes up to 21
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
/*
        val position = pinMarker!!.position
        val clatitude = position.latitude
        val cLong = position.longitude
        Log.e("current LatLong>>",clatitude.toString()+"------"+cLong.toString())
*/

        mMap!!.setOnCameraMoveListener {
            target = mMap!!.cameraPosition.target
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

    fun getAddress(latitude: Double, longitude: Double) {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        tv_ll.setText(address)

        if (addresses[0].getLocality() != null)
            city = addresses[0].getLocality()
        if (addresses[0].getAdminArea() != null)
            state = addresses[0].getAdminArea()
        if (addresses[0].getCountryName() != null)
            country = addresses[0].getCountryName()
        if (addresses[0].getPostalCode() != null)
            postalCode = addresses[0].getPostalCode()
        if (addresses[0].getFeatureName() != null) {
            knownName = addresses[0].getFeatureName() // Only if available else return NULL
//            tv_ll.setText(knownName)
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
