package com.stalkstock.utils.extention

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import com.mender.utlis.ProgressHUD
import com.stalkstock.utils.`interface`.GetLatLongInterface
import com.stalkstock.utils.commonmodel.LocationModel
import com.stalkstock.utils.helper.PlaceAutocompleteAdapterNew
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

var mProgressDialog: ProgressHUD? = null
fun Context.showLoader() {
    mProgressDialog = ProgressHUD.create(this,"Please wait..",indeterminate = true,
        cancelable = false,
        show = true,
        cancelListener = null)
    mProgressDialog!!.show()
}

fun hideLoader() {
    if (mProgressDialog != null)
        mProgressDialog!!.dismiss()
}

fun Context.hideKeyboard(view: View?) {
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.setAutoComplete(model: LocationModel, listner: GetLatLongInterface) {
    val autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    val mAdapter: PlaceAutocompleteAdapterNew
    val placesClient: PlacesClient = Places.createClient(applicationContext)
    mAdapter = PlaceAutocompleteAdapterNew(this, placesClient, autocompleteSessionToken)
    model.autoTvLocation!!.setAdapter(mAdapter)
    model.autoTvLocation!!.threshold = 0
    model.autoTvLocation!!.onItemClickListener =
        AdapterView.OnItemClickListener { arg0, arg1, arg2, arg3 ->
            model.autoTvLocation!!.setText("")
            if (model.etCity != null)
                model.etCity!!.setText("")
            if (model.etState != null)
                model.etState!!.setText("")
            if (model.etZipcode != null)
                model.etZipcode!!.setText("")
            Log.d("SELECTED TEXT ", arg0.getItemAtPosition(arg2).toString())
            showLoader()
            GlobalScope.launch(Dispatchers.Main){
                hideKeyboard(model.autoTvLocation!!)
                getLocationFromAddress(
                    arg0.getItemAtPosition(arg2).toString(), listner, model
                )
            }
        }
}

fun Activity.getLocationFromAddress(
    strAddress: String,
    listner: GetLatLongInterface,
    model: LocationModel
) {
    val coder = Geocoder(this)
    var location: Address? = null
    try {
        val address = coder.getFromLocationName(strAddress, 5)
        if (address == null) {
            hideLoader()
            return
        }
        hideLoader()
        if (address.size == 0) {
            AlerterError("Please select other Location")

        } else {
            location = address[0]
            listner.getLatLongListner(location!!.latitude.toString(), location.longitude.toString())
            GlobalScope.launch(Dispatchers.Main) {
                setDataFields(address, model)
            }
        }

    } catch (ex: IOException) {
        ex.printStackTrace()
        hideLoader()
    }
}

fun setDataFields(address: List<Address>, model: LocationModel) {
    val country = address[0].countryName
    val zipcode = address[0].postalCode
    var state = address[0].adminArea
    val city = address[0].locality
    if (!checkStringNull(address[0].getAddressLine(0))) {
        val split = address[0].getAddressLine(0).split(",")
        if (model.etCity != null) {
            model.autoTvLocation!!.setText(split[0])
            model.autoTvLocation!!.setSelection(split[0].length-1)
            model.autoTvLocation!!.dismissDropDown()
        }

        if (split.size > 2) {
            val s = split[split.size - 2]
            val split1 = s.split(" ")
            state = if (checkStringNull(split1[0]))
                split1[1]
            else
                split1[0]
        }
    }
    if (model.etCity == null) {
        model.autoTvLocation!!.setText("$city,$state")
        model.autoTvLocation!!.setSelection(model.autoTvLocation!!.text.length)
        model.autoTvLocation!!.dismissDropDown()
    }

    if (model.etZipcode != null) {
        if (zipcode != null) {
            model.etZipcode!!.setText(zipcode)
            model.etZipcode!!.setSelection(model.etZipcode!!.text!!.length);
        } else {
            model.etZipcode!!.setText("")
        }
    }
    if (model.etState != null) {
        if (state != null) {
            model.etState!!.setText(state)
            model.etState!!.setSelection(state.length);
        } else {
            if (country != null) {
                model.etState!!.setText(country)
                model.etState!!.setSelection(country.length);
            } else {
                model.etState!!.setText("")
            }
        }
    }
    if (model.etCity != null) {
        if (city != null) {
            model.etCity!!.setText(city)
            model.etCity!!.setSelection(city.length);
        } else {
            if (country != null) {
                model.etCity!!.setText(country)
                model.etCity!!.setSelection(country.length);
            } else {
                model.etCity!!.setText("")
            }
        }
    }
}