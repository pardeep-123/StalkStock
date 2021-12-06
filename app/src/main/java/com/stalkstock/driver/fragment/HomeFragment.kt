package com.stalkstock.driver.fragment


import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.gson.GsonBuilder
import com.stalkstock.updateCamera
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.driver.HomeActivity
import com.stalkstock.driver.models.NewOrderResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.`interface`.GetLatLongInterface
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GPSTracker
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.socket.SocketManager
import com.stalkstock.vender.Utils.CurrentLocationActivity
import kotlinx.android.synthetic.main.accept_request_alert.*
import kotlinx.android.synthetic.main.fragment_h_ome.*
import kotlinx.android.synthetic.main.home_popup.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*


class HomeFragment : CurrentLocationActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    GetLatLongInterface, SocketManager.SocketInterface, Observer<RestObservable> {

    private lateinit var currentOrder: NewOrderResponse
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var mGoogleMap: GoogleMap
    lateinit var mapFragment: SupportMapFragment
     var orderID: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_h_ome, container, false)
    }

    var mactivity: HomeActivity? = null

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
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_declin.setOnClickListener {
            val map = HashMap<String, RequestBody>()
            map["status"] = RequestBody.create(MultipartBody.FORM, "2")
            map["orderId"] = RequestBody.create(MultipartBody.FORM, orderID)

            viewModel.driverAcceptRejectOrder(mactivity!!, true, map)
            listner!!.getYourFragmentActive(0)
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
        MyApplication.getSocketManager().onRegister(this)
    }

    override fun onLocationGet(latitude: String?, longitude: String?) {
        mLatitude = latitude!!
        mLongitude = longitude!!
        if (mGoogleMap != null)
            updateCamera(mGoogleMap, mLatitude, mLongitude, 12F)

    }
    val viewModel: DriverViewModel by viewModels()

    override fun onMapReady(p0: GoogleMap?) {
        this.mGoogleMap = p0!!
        checkLocationPermission()
        if (mactivity != null) {
            CurrentLocationActivity(requireActivity())
        }
        enabaleMyLocationIfPermitted()
        mGoogleMap.setOnMyLocationButtonClickListener(this)
        mGoogleMap.setOnMyLocationClickListener(this)
        setZoomControlPOsition()
    }

    override fun onResume() {
        super.onResume()
        mactivity
        val map = HashMap<String, RequestBody>()
        viewModel.driverOrderRequestAPI(mactivity!!, true)
        viewModel.mResponse.observe(this, this)
    }

    private fun enabaleMyLocationIfPermitted() {
        val homeActivity = activity as HomeActivity
        if (ActivityCompat.checkSelfPermission(
                homeActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                homeActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                homeActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            mGoogleMap.isMyLocationEnabled = true
            mGoogleMap.uiSettings.isMyLocationButtonEnabled = true
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(p0: Location) {}

    private fun setZoomControlPOsition() {
        val locationButton =
            (mapFragment.requireView().findViewById<View>(Integer.parseInt("1"))
                .parent as View).findViewById<View>(
                Integer.parseInt("2")
            )
        val rlp = locationButton.layoutParams as RelativeLayout.LayoutParams
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        val margin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 10f,
            resources.displayMetrics).toInt()
        val marginBottom = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 120f,
            resources.displayMetrics).toInt()
        rlp.setMargins(margin, margin, margin, marginBottom)
    }

    override fun getLatLongListner(lat: String, long: String) {
        this.mLatitude = lat
        this.mLongitude = long
        updateCamera(mGoogleMap, mLatitude, mLongitude, 12F)
    }

    var mLatitude = ""
    var mLongitude = ""


    fun checkgps() {
        val homeActivity = activity as HomeActivity
        gpsTracker = GPSTracker(requireContext())
        if (!gpsTracker!!.canGetLocation()) {
            gpsTracker!!.showSettingsAlert()
        } else {
            mLatitude = gpsTracker!!.latitude.toString()
            mLongitude = gpsTracker!!.longitude.toString()
            if (mLatitude == "0.0" && mLongitude == "0.0")
                checkgps()
            else {

                updateDriverLocationSocket()
                val sydney = LatLng(mLatitude.toDouble(), mLongitude.toDouble())

                mGoogleMap!!.addMarker(
                    MarkerOptions()
                        .position(sydney)
                        .title("Your are Here").icon(
                            BitmapDescriptorFactory.fromBitmap(
                                Bitmap.createScaledBitmap(
                                    BitmapFactory.decodeResource(
                                        resources,
                                        R.drawable.black_map_circle
                                    ), 70, 120, false
                                ))))


                val zoomLevel = 12.0f
                mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
                if (ActivityCompat.checkSelfPermission(
                        homeActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        homeActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                mGoogleMap!!.isMyLocationEnabled = false
            } } }

    private fun updateDriverLocationSocket() {
        val userId = getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)

        val jsonObject = JSONObject()
        jsonObject.put("providerId", userId)
        jsonObject.put("latitude", mLatitude)
        jsonObject.put("longitude", mLongitude)
        Log.e(SocketManager.UPDATE_DRIVER_LOCATION, jsonObject.toString())
        SocketManager.socket?.sendDataToServer(SocketManager.UPDATE_DRIVER_LOCATION, jsonObject)
    }

    fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkgps()
        } else {
            //condition for Marshmello and above
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

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.getSocketManager().unRegister(this)
    }

    private fun dialogconfirmation() {
        val dialog = Dialog(requireContext())
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
        dialog.tv_order.text = "Order ID : " + currentOrder.body.orderNo
        dialog.txtEstEarning.text = "$ " + currentOrder.body.shippingCharges
        dialog.txtDatePopup.text = AppUtils.changeDateFormat(currentOrder.body.updatedAt, GlobalVariables.DATEFORMAT.DateTimeFormat3, GlobalVariables.DATEFORMAT.DateTimeFormat2)
        dialog.txtRestLocation.text = currentOrder.body.vendorDetail.shopAddress
        dialog.txtDestinationLocation.text = currentOrder.body.orderAddress.geoLocation
        dialog.txtAddressPopup.text = currentOrder.body.orderAddress.geoLocation
        dialog.tv_name.text = currentOrder.body.firstName + " " + currentOrder.body.lastName
        Glide.with(this).load(currentOrder.body.vendorDetail.shopLogo).into(dialog.iv_sub as ImageView)
        Glide.with(this).load(currentOrder.body.vendorDetail.shopLogo).into(dialog.ivShopLogo as ImageView)
        Glide.with(this).load(currentOrder.body.image).into(dialog.iv_profile)

        dialog.btn_accept.setOnClickListener {
            dialog.dismiss()
            dialo()
        }

        dialog.iv_cross.setOnClickListener {
            dialog.dismiss()

        }

        dialog.btn_decline.setOnClickListener {
            dialog.dismiss()
            val map = HashMap<String, RequestBody>()
            map["status"] = RequestBody.create(MultipartBody.FORM, "2")
            map["orderId"] = RequestBody.create(MultipartBody.FORM, orderID)

            viewModel.driverAcceptRejectOrder(mactivity!!, true, map)
            listner!!.getYourFragmentActive(0)
            rl_top.visibility = View.VISIBLE
            rl_tv.visibility = View.GONE
        }

        dialog.show()
    }

    private fun dialo() {
        val dialog = Dialog(requireContext())
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
            val map = HashMap<String, RequestBody>()

            Log.e("adsfdfas=====","$orderID")
            map["status"] = RequestBody.create(MultipartBody.FORM, "1")
            map["acceptedLong"] = RequestBody.create(MultipartBody.FORM, mLongitude)
            map["acceptedLat"] = RequestBody.create(MultipartBody.FORM, mLatitude)
            map["orderId"] = RequestBody.create(MultipartBody.FORM, orderID)
            viewModel.driverAcceptRejectOrder(mactivity!!, true, map)
        }
        dialog.btn_decline1.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onSocketCall(event: String?, vararg args: Any?) {

        if (event != null) {
            Log.e("onSocketCall", event)
        }
        when (event) {
            /*SocketManager.driverOrderRequest -> {
                ca_tv1.visibility=View.VISIBLE
                val gson = GsonBuilder().serializeNulls().create()

                val mObject = args[0] as JSONObject
                try {
                    Log.e("sockVendorOrderListener", mObject.toString())
                } catch (e: Exception) {
                }
                val offeredOrder = gson.fromJson(mObject.toString(), NewOrderResponse::class.java)
                ca_tv1.visibility = View.VISIBLE
                tv_orderHome.text = "Order ID : " + offeredOrder.body.orderNo
                txtDateHome.text = offeredOrder.body.updatedAt.substring(0, 10)
                tv_nameHome.text = offeredOrder.body.firstName + " " + offeredOrder.body.lastName
                txtAddressHome.text = offeredOrder.body.orderAddress.geoLocation
                Glide.with(this).load(offeredOrder.body.vendorDetail.shopLogo).into(imgVendorImage as ImageView)
                Glide.with(this).load(offeredOrder.body.image).into(iv_profileHome)
                currentOrder = offeredOrder

            } */
        }
    }

    override fun onSocketConnect(vararg args: Any?) {

    }

    override fun onSocketDisconnect(vararg args: Any?) {
        TODO("Not yet implemented")
    }


    override fun onError(event: String?, vararg args: Any?) {
    }



    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is NewOrderResponse) {
                    ca_tv1.visibility=View.VISIBLE
                    val mResponse: NewOrderResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOrder = mResponse
                        if(currentOrder.body.orderStatus==1){
                            if (currentOrder.body.orderNo != null) {
                                orderID = currentOrder.body.id.toString()
                                ca_tv1.visibility = View.VISIBLE
                                tv_orderHome.text = "Order ID : " + currentOrder.body.orderNo
                                txtDateHome.text = AppUtils.changeDateFormat(currentOrder.body.updatedAt, GlobalVariables.DATEFORMAT.DateTimeFormat3, GlobalVariables.DATEFORMAT.DateTimeFormat2)
                                tv_nameHome.text = currentOrder.body.firstName + " " + currentOrder.body.lastName
                                txtAddressHome.text = currentOrder.body.orderAddress.geoLocation
                                Glide.with(this).load(currentOrder.body.vendorDetail.shopLogo)
                                    .into(imgVendorImage as ImageView)
                                Glide.with(this).load(currentOrder.body.image).into(iv_profileHome)
                            }
                        }
                        else {
                            ca_tv1.visibility = View.GONE
                        }

                    } else {
                        ca_tv1.visibility = View.VISIBLE
                    }
                }
                else{
                    ca_tv1.visibility = View.GONE
                }
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        ca_tv1.visibility = View.VISIBLE
                        Toast.makeText(mactivity!!, mResponse.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(mactivity!!, it.data as String, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        mactivity!!,
                        it.error!!.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

}
