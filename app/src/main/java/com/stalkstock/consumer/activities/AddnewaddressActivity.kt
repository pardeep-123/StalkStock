package com.stalkstock.consumer.activities;

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.stalkstock.R;
import com.stalkstock.utils.GPSTracker;
import kotlinx.android.synthetic.main.activity_addnewaddress.*


public class AddnewaddressActivity : GPSTracker() , OnMapReadyCallback {
  /*  AddnewaddressActivity context;
    ImageView back;
    Button btn_signup;*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewaddress)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

       /* btn_signup = findViewById(R.id.btn_signup);
        back = findViewById(R.id.back);*/
        btn_signup.setOnClickListener {
            finish();
        }
        back.setOnClickListener {
            finish();
        }

    }
    var mLatitude = ""
    var mLongitude = ""
    override fun checkgps() {
        mLatitude = getLatitude().toString()
        mLongitude = getLongitude().toString()


        val sydney: LatLng

        //sydney  = LatLng(getPrefrence(Constant.lat,"0.0").toDouble(), getPrefrence(Constant.long,"0.0").toDouble())

        sydney = LatLng(mLatitude.toDouble(), mLongitude.toDouble())
        //marker.visible()

        mMap!!.  addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Your are Here").icon(
                    BitmapDescriptorFactory.fromBitmap(
                        Bitmap.createScaledBitmap(
                            BitmapFactory.decodeResource(resources, R.drawable.black_map_circle), 70, 120, false)))
        )


        val zoomLevel = 12.0f //This goes up to 21
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
        mMap!!.isMyLocationEnabled = false
    }
    private var mMap: GoogleMap? = null
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        checkLocationPermission()
    }

}
