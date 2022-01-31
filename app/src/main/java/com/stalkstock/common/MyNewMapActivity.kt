package com.stalkstock.common

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.stalkstock.vender.Utils.FusedLoc
import com.stalkstock.vender.Utils.MyInterface
import kotlinx.android.synthetic.main.activity_my_new_map.*
import java.io.IOException
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.utils.others.Anim
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.ProgressHUD
import com.stalkstock.vender.Utils.NetworkUtil
import java.util.*

class MyNewMapActivity : AppCompatActivity(), OnMapReadyCallback {
    var fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.NAME)
    var city = ""
    var address = ""
    var postalCode = ""
    var lat = ""
    var lng = ""
    var cnt = 1
    lateinit var locObj: FusedLoc
    var type = ""
    lateinit var getLoc: MyInterface
    lateinit var mapFragment: SupportMapFragment
    var map: GoogleMap? = null
    var startLatLng = LatLng(30.700777, 76.868308)
    var PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    var lat_changeaddress1: Double? = null
    var lng_changeaddress1: Double? = null
    var destination_name = ""
    var pincode = ""
    var state = ""

    internal lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var changelatlng1: LatLng
    var location = ""
    private var c = this@MyNewMapActivity
    var progressDialog: ProgressHUD? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_new_map)
       progressDialog = ProgressHUD.create(
            this, cancelable = false,
            show = true,
            cancelListener = null)
        locObj = FusedLoc(c)
        locObj.connectClient()

        mLocationRequest = LocationRequest()
        type = intent.getStringExtra("type").toString()

        // Initialize Places.
        Places.initialize(applicationContext, resources.getString(R.string.maps_api_key))
        mapFragment = this.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        my_map_my_loc.setOnClickListener {
            startLocationUpdates()
            /*if (Utils.isGPSEnable(c)) {
                progressDialog?.show()
                getlastLoc()
            } else {
                NetworkUtil.checkGps(c)
            }*/
        }

        btn_save.setOnClickListener {

            if (txt_location.text.toString().isEmpty()) {
                Toast.makeText(c, "Select your location first", Toast.LENGTH_LONG).show()
            } else {
                when (type) {
                    "1" -> {
                        val intent = Intent()
                        intent.putExtra("area", txt_location.text.toString())
                        intent.putExtra("lat", lat)
                        intent.putExtra("lng", lng)
                        intent.putExtra("city", city)
                        intent.putExtra("state", state)
                        intent.putExtra("pin", postalCode)
                        setResult(1, intent)
                        finish()
                    }
                   /* "2" -> {
                        val intent = Intent()
                        intent.putExtra("area", txt_location.text.toString())
                        intent.putExtra("lat", lat)
                        intent.putExtra("lng", lng)
                        intent.putExtra("city", city)
                        intent.putExtra("state", state)
                        intent.putExtra("pin", postalCode)
                        setResult(11, intent)
                        finish()
                    }*/
                }
            }
        }

        my_map_search_txt.setOnClickListener {

            try {
                Log.d("TAG", "place")

                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields)
                       .build(this@MyNewMapActivity)

                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLoc = object : MyInterface {
            override fun getloc(loc: Location) {
                progressDialog?.dismiss()
                Log.d("TAG", "interface")
                startLatLng = LatLng(loc.latitude, loc.longitude)
                val location = CameraUpdateFactory.newLatLngZoom(startLatLng, 15f)
                map!!.animateCamera(location)
            }
        }

        if (map != null) {
            progressDialog?.show()
            map!!.setOnCameraIdleListener {
                val midLatLng = map!!.cameraPosition.target
                lat = midLatLng.latitude.toString()
                lng = midLatLng.longitude.toString()
                Log.d("TAG", "move" + locObj.isConnected())
                if (CommonMethods.isGPSEnable(c)) {
                    getcurrLoc()
                } else {
                    NetworkUtil.checkGps(c)
                }

                val geocoder = Geocoder(this@MyNewMapActivity, Locale.getDefault())
                var addresses: List<Address>? = null

                if (lat != null) {
                    try {
                        progressDialog?.dismiss()
                        addresses = geocoder.getFromLocation(midLatLng.latitude, midLatLng.longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        val address1 = addresses!![0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        city = addresses[0].locality
                        postalCode = addresses[0].postalCode
                        state = addresses[0].adminArea
                        address = address1
                        txt_location.text = address1
                        if (!address1.isNullOrEmpty()) {
                            showbtn()
                        }
                    } catch (e: Exception) {
                        progressDialog?.dismiss()
                        e.printStackTrace()
                        Log.d("TAG", "catch")
                    }
                }
            }
        }
    }

    protected fun startLocationUpdates() {
        progressDialog?.show()
        // Create the location request to start receiving updates

        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.setInterval(1200000)
        mLocationRequest.setFastestInterval(1200000)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(c)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(c)
        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        c,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper())

    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            progressDialog?.dismiss()
            // do work here
            locationResult.lastLocation
            changelatlng1 = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
            try {
                val location = CameraUpdateFactory.newLatLngZoom(changelatlng1, 15f)
                map!!.animateCamera(location)
                progressDialog?.dismiss()
            } catch (e: Exception) {
                progressDialog?.dismiss()
            }
        }
    }

    private fun getlastLoc() {
        progressDialog?.show()
        if (locObj.isConnected()) {
            Log.d("TAG", "lll" + locObj.getLastLoc().longitude)
            if (locObj.getLastLoc() != null) {
                changelatlng1 = LatLng(locObj.getLastLoc().latitude, locObj.getLastLoc().longitude)
                try {
                    val location = CameraUpdateFactory.newLatLngZoom(changelatlng1, 15f)
                    // map!!.moveCamera(CameraUpdateFactory.newLatLng(changelatlng1))
                    map!!.animateCamera(location)
                    progressDialog?.dismiss()
                } catch (e: Exception) {
                    progressDialog?.dismiss()
                }
            }

        } else {
            progressDialog?.dismiss()
            locObj.connectClient()
            Log.d("TAG", "else is not connected")
        }
    }

    private fun getcurrLoc() {

        if (cnt == 1) {
            progressDialog?.show()
            cnt++
            if (locObj.isConnected()) {
                // progressDialog?.show()
                Log.d("TAG", "interface call")
                locObj.getCurrentLoc(getLoc)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                location = "1"
                val place = Autocomplete.getPlaceFromIntent(data!!)
                Log.i("Place: ", place.name.toString() + " " + place.address)
                destination_name = place.address!!.toString() + ""
                val lati = place.latLng.toString()
                val latlng2 = place.latLng
                val lat1 = latlng2!!.latitude
                val lng2 = latlng2.longitude
                val latlng = lati.split("lat/lng:".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val latlng1 = latlng[1]
                val latlngArray = latlng1.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val lat12 = latlngArray[0]
                val lat13 = latlngArray[1]
                val mGeocoder = Geocoder(c, Locale.getDefault())
                var addressess: List<Address>? = null
                try {
                    addressess = mGeocoder.getFromLocation(lat1, lng2, 1)
                    if (addressess != null && addressess.isNotEmpty()) {
                        // if ()
                        //  city = addressess[0].locality
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                lat = lat12.replace("\\(".toRegex(), "")
                lng = lat13.replace("\\)".toRegex(), "")
                lat_changeaddress1 = java.lang.Double.valueOf(lat)
                lng_changeaddress1 = java.lang.Double.valueOf(lng)
                changelatlng1 = LatLng(lat_changeaddress1!!, lng_changeaddress1!!)
                val geocoder = Geocoder(this, Locale.getDefault())
                try {
                    map!!.moveCamera(CameraUpdateFactory.newLatLng(changelatlng1))
                } catch (e: Exception) {

                }
                map!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
                try {
                    val addresses = geocoder.getFromLocation(lat1, lng2, 1)
                    pincode = addresses[0].postalCode
                } catch (e: Exception) {

                }

                txt_location.text = destination_name
            }

        } else if (requestCode == NetworkUtil.REQUEST_GPS_ENABLE) {

            if (resultCode == Activity.RESULT_OK) {
                getcurrLoc()
            } else {
                progressDialog?.dismiss()
            }
        }
    }

    fun showbtn() {
        if (ll_bottom_sheet.visibility != View.VISIBLE) {
            ll_bottom_sheet.visibility = View.VISIBLE
            ll_bottom_sheet.animation = Anim(c).slide_in_bottom
        }
    }
}