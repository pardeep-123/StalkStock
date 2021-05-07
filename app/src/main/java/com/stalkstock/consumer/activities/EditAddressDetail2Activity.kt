package com.stalkstock.consumer.activities

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stalkstock.MyApplication
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_edit_address_detail2.*
import kotlinx.android.synthetic.main.add_address.btnEdit
import kotlinx.android.synthetic.main.add_address.ivBackAddress

class EditAddressDetail2Activity : AppCompatActivity(), OnMapReadyCallback {

    var dataSet = 2

    lateinit var tvTagHome:TextView
    lateinit var tvTagWork:TextView
    lateinit var tvTagHotel:TextView
    lateinit var tvTagOther:TextView
    override fun onMapReady(p0: GoogleMap?) {
        p0?.apply {
            val sydney = LatLng(30.7121687,76.6928878)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Your are Here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.black_map_circle))
//                    .icon(
//                        BitmapDescriptorFactory.fromBitmap(
//                            Bitmap.createScaledBitmap(
//                                BitmapFactory.decodeResource(resources, R.drawable.black_map_circle), 70, 120, false)))
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


        tvTagHome=findViewById(R.id.tvTagHome)
        tvTagWork=findViewById(R.id.tvTagWork)
        tvTagHotel=findViewById(R.id.tvTagHotel)
        tvTagOther=findViewById(R.id.tvTagOther)

        rl_home.setOnClickListener {
            changeColorRelative(rl_home,iv_home_edit,tvTagHome,rl_work,iv_work_edit,tvTagWork,rl_other,iv_loca_edit,tvTagOther)

        }

        rl_work.setOnClickListener {
            changeColorRelative(rl_work,iv_work_edit,tvTagWork,rl_home,iv_home_edit,tvTagHome,rl_other,iv_loca_edit,tvTagOther)

        }

        rl_other.setOnClickListener {
            changeColorRelative(rl_other,iv_loca_edit,tvTagOther,rl_home,iv_home_edit,tvTagHome,rl_work,iv_work_edit,tvTagWork)

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

        if ( MyApplication.instance.getString("usertype").equals("2")){
            rl_work.visibility= View.GONE
            tvTagHome.text="Business"
            iv_home_edit.setImageResource(R.drawable.ic_work_for_edit)
         }else{

        }

        val foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_country, R.layout.spinner_layout_for_vehicle)
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter
    }

    private  fun changeColor(textview1: TextView, textview2: TextView,textview3: TextView,textview4: TextView) {
        //textview1.setBackgroundColor(Color.parseColor("#7DBB00"))
//        textview1.setTextColor(Color.parseColor("#FFFFFF"))
//        textview2.setBackgroundColor(Color.parseColor("#C3C3C3"))
//        textview2.setTextColor(Color.parseColor("#000000"))
//        textview3.setBackgroundColor(Color.parseColor("#C3C3C3"))
//        textview3.setTextColor(Color.parseColor("#000000"))
//        textview4.setBackgroundColor(Color.parseColor("#C3C3C3"))
//        textview4.setTextColor(Color.parseColor("#000000"))

    }


    private  fun changeColorRelative(rl: RelativeLayout, im: ImageView,tv:TextView,rl2: RelativeLayout, im2: ImageView,tv2:TextView,rl3: RelativeLayout, im3: ImageView,tv3:TextView /*,rl4: RelativeLayout, im4: ImageView,tv4:TextView*/) {
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