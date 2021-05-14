package com.stalkstock.utils;

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*


abstract class GPSTracker : BaseActivity() , LocationListener {

    // The minimum distance to change Updates in meters
    private var   MIN_DISTANCE_CHANGE_FOR_UPDATES = 1f; //10// 10 meters
    // The minimum time between updates in milliseconds
     var MIN_TIME_BW_UPDATES = 1000L; // 1 minute
  //  private var  mContext :Context? =null;
    // Declaring a Location Manager
     var locationManager :LocationManager ? =null;
    // flag for GPS status
    var isGPSEnabled = false;
    // flag for network status
    var isNetworkEnabled = false;
    // flag for GPS status
    var canGetLocation = false;





     @SuppressLint("MissingPermission")
     fun  getLocation()  {
        try {
            locationManager =  getSystemService(LOCATION_SERVICE) as LocationManager


            // getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnabled && !isNetworkEnabled) {
                displayLocationSettingsRequest(this)
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location1 = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location1 != null) {
                            latitude1 = location1!!.getLatitude();
                            longitude1 = location1!!.getLongitude();
                            checkgps()
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location1 == null) {
                        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location1 = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location1 != null) {
                                latitude1 = location1!!.getLatitude();
                                longitude1 = location1!!.getLongitude();
                                checkgps()
                            }
                        }
                    }
                }
            }

        } catch ( e :Exception) {
            e.printStackTrace();
            getLocation()
        }
       // return location1!!;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app.
     */
    public fun  stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this);
        }
    }

    /**
     * Function to get latitude
     */
    companion object{
        var   location1: Location ? =null; // location
        var  latitude1 =0.0; // latitude
        var  longitude1 =0.0 // longitude
        public fun getLatitude() : Double{
            if (location1 != null) {
                latitude1 = location1!!.getLatitude();
            }
            // return latitude
            return latitude1;
        }

        /**
         * Function to get longitude
         */
        public fun  getLongitude() :Double{
            if (location1 != null) {
                longitude1 = location1!!.getLongitude();
            }

            // return longitude
            return longitude1;
        }
    }


    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public fun  canGetLocation() :Boolean  {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     */
/*    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting DialogHelp Title
        alertDialog.setTitle("GPS is settings");

        // Setting DialogHelp Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }*/


    override fun  onLocationChanged( location :Location) {
        var  bestAccuracy = -1f;
        if (location.getAccuracy() != 0.0f && (location.getAccuracy() < bestAccuracy) || bestAccuracy == -1f) {
            locationManager!!.removeUpdates(this);
        }
        bestAccuracy = location.getAccuracy();
        Log.e("location changed", "lat : " + location.getLatitude() + "\nLng : " + location.getLongitude());
//        Intent mIntent=new Intent(C.TRACK_BROADCAST);
//        mIntent.putExtra("lat",location.getLatitude());
//        mIntent.putExtra("lng",location.getLongitude());
//        LocalBroadcastManager.getInstance(this).sendBroadcast(mIntent);
    }


    override fun onProviderDisabled( provider :String) {
    }

    override fun onProviderEnabled( provider :String) {
    }

    override  fun  onStatusChanged( provider : String,  status :Int,  extras :Bundle) {
    }

  /*  @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }*/

    public fun  getAccurecy() :Float {
        return location1!!.getAccuracy();
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1111){
            if (resultCode == Activity.RESULT_OK){
                getLocation()
            }else{
                getLocation()
            }

        }
    }



    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_FINE_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//permission to access location grant
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                }
            }
            //permission to access location denied
            else {
                Toast.makeText(
                        applicationContext,
                        "This app requires location permissions to be granted",
                        Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }






    // private val locationPermissions = arrayOf(ACCESS_FINE_LOCATION)
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    /*  private fun findCurrentPlace() {


      }*/



    public fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ) {
           // mMap!!.isMyLocationEnabled = false
            getLocation()
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


    abstract fun checkgps()

    private fun  displayLocationSettingsRequest( context : Context) {
        val googleApiClient =  GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build();
        googleApiClient.connect();

        val  locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        val  builder =  LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
            override fun  onResult( result : LocationSettingsResult) {
                val status = result.getStatus();
                when(status.getStatusCode()) {
                    LocationSettingsStatusCodes.SUCCESS ->
                        Log.i("TAG", "All location settings are satisfied.");

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->{
                        Log.i("TAG", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(this@GPSTracker, 1111);
                        } catch (e : IntentSender.SendIntentException ) {
                            Log.i("TAG", "PendingIntent unable to execute request.");
                        }
                    }


                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE->


                        Log.i("TAG", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");

                }
            }
        });
    }

}