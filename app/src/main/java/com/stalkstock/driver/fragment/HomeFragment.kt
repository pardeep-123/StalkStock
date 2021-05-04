package com.stalkstock.driver.fragment


import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.driver.HomeActivity
import com.stalkstock.utils.others.GPSTracker
import kotlinx.android.synthetic.main.accept_request_alert.*
import kotlinx.android.synthetic.main.fragment_h_ome.*
import kotlinx.android.synthetic.main.home_popup.*
import kotlinx.android.synthetic.main.home_popup.ca_tv


class HomeFragment : Fragment(), OnMapReadyCallback {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_h_ome, container, false)
    }

     var mactivity :HomeActivity ? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommunicationListner) {
            listner = context
        } else {
            throw RuntimeException("home frag not Attached")
        }
        mactivity = context as HomeActivity

    }
    var listner: CommunicationListner? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/* SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);*/
   /*     val mapFragment =
            mactivity!!.fragmentManager.findFragmentById(R.id.map1) as MapFragment
        mapFragment.getMapAsync(this)*/

        btn_declin.setOnClickListener {
            rl_top.visibility = View.VISIBLE
            rl_tv.visibility = View.GONE
        }

        btn_ok.setOnClickListener {
            rl_top.visibility = View.GONE
            rl_tv.visibility = View.VISIBLE
        }

        ca_tv1.setOnClickListener {
            dialogconfirmation()

        }

        btn_signup.setOnClickListener {
            dialo()
        }

    }
    private var mMap: GoogleMap? = null
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        checkLocationPermission()
    }

    var mLatitude = ""
    var mLongitude = ""
  /*  fun checkgps() {


    }*/

    fun checkgps(){
        gpsTracker = GPSTracker(requireContext())
        if (!gpsTracker!!.canGetLocation()){
            gpsTracker!!.showSettingsAlert()
        }else{
            mLatitude = gpsTracker!!.latitude.toString()
            mLongitude = gpsTracker!!.longitude.toString()
            if (mLatitude.equals("0.0") && mLongitude.equals("0.0") )
                checkgps()
            else{
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
        }
    }

  fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // mMap!!.isMyLocationEnabled = false
            checkgps()
        } else {//condition for Marshmello and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_FINE_LOCATION
                )
            }
        }
    }
    private val MY_PERMISSION_FINE_LOCATION = 101

    var gpsTracker: GPSTracker? = null
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_FINE_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//permission to access location grant
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    checkgps()
                }
            }
            //permission to access location denied
            else {
                Toast.makeText(
                    context,
                    "This app requires location permissions to be granted",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }




    private fun dialogconfirmation() {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.home_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_accept.setOnClickListener {
            dialog.dismiss()
            dialo()
        }
        dialog.btn_decline.setOnClickListener{
            dialog.dismiss()
            rl_top.visibility = View.VISIBLE
            rl_tv.visibility = View.GONE
        }


        dialog.show()
    }
    private fun dialo() {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.accept_request_alert)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_accept1.setOnClickListener {
            dialog.dismiss()
            listner!!.getYourFragmentActive(1)
        }
        dialog.btn_decline1.setOnClickListener{
            dialog.dismiss()
            listner!!.getYourFragmentActive(1)
        }


        dialog.show()

      /*  val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.accept_request_alert)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_accept1.setOnClickListener {
            dialog.dismiss()
            listner!!.getYourFragmentActive(1)
        }
        dialog.btn_decline1.setOnClickListener{
            dialog.dismiss()
            listner!!.getYourFragmentActive(1)
        }


        dialog.show()*/
    }




}
