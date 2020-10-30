package com.stalkstock.consumer.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stalkstock.R
import kotlinx.android.synthetic.main.add_address.*

class EditAddressDetail2Activity : AppCompatActivity(), OnMapReadyCallback {

    var dataSet = 2
    override fun onMapReady(p0: GoogleMap?) {
        p0?.apply {
            val sydney = LatLng(30.7121687,76.6928878)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Your are Here").icon(
                        BitmapDescriptorFactory.fromBitmap(
                            Bitmap.createScaledBitmap(
                                BitmapFactory.decodeResource(resources, R.drawable.black_map_circle), 70, 120, false)))
            )
            moveCamera(
                CameraUpdateFactory
                    .newLatLngZoom(sydney,12f))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address_detail2)
        setMapInit(mapBool)

        dataSet = if(intent.hasExtra("key")) {
            if(intent.getStringExtra("key")=="edit") {1} else {2}
        } else {
            2
        }
        ivBackAddress.setOnClickListener { onBackPressed() }
        btnEdit.setOnClickListener { onBackPressed() }
    }

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mapFragment : SupportMapFragment

    var mapBool = true



    private fun setMapInit(bool:Boolean){
        if (bool){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mapBool=false
            if (!this::mapFragment.isInitialized)
            {
                mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        } }


}