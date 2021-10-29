package com.stalkstock.api

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.stalkstock.utils.others.ProgressHUD
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.utils.others.clearPrefrences

import com.stalkstock.utils.others.AppUtils

import okhttp3.ResponseBody
import java.io.IOException


class RestObservable(
    val status: Status,
    val data: Any?,
    val error: Any?
) {

    companion object {
        var progressDialog: ProgressHUD? = null
        fun loading(activity: Activity, isDialogShow: Boolean): RestObservable {
            Log.e("REST", "Loading")
            if (isDialogShow) {
                if (progressDialog != null && progressDialog!!.isShowing) { progressDialog!!.dismiss() }
                progressDialog = ProgressHUD.create(
                    activity, cancelable = false,
                    show = true,
                    cancelListener = null)
                progressDialog!!.show()
            }
            return RestObservable(Status.LOADING, null, null)
        }

        fun success(@androidx.annotation.NonNull data: Any): RestObservable {
            Log.e("REST", "Success")
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            return RestObservable(Status.SUCCESS, data, null)
        }

        fun error(activity: Activity, @androidx.annotation.NonNull error: Throwable): RestObservable {
            Log.e("REST", "Error")
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            try {
                if (error is HttpException) {
                    val httpException = error as HttpException
                    val response = httpException.response()
                    val errorMessage = callErrorMethod(response.errorBody())
                    Log.i(TAG, error.message() + " // / " + errorMessage)
                    if (response.code() == 401) {
                        val intent = Intent(activity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        activity.startActivity(intent)
                        activity.finish()
                        clearPrefrences()
                    } else {
                    }
                    return RestObservable(Status.ERROR, null, errorMessage)
                }
                if (error is IOException) {
                    Log.i(TAG, error.message + " / " + error.javaClass)
                    AppUtils.showErrorAlert(activity, activity.getString(R.string.unable_to_connect_server))
                    return RestObservable(Status.ERROR, null, error)
                }
                Log.i(TAG, error.message + " / " + error.javaClass)
            } catch (e: Exception) {
                return RestObservable(Status.ERROR, null, error)
            }
            return RestObservable(Status.ERROR, null, error)
        }

        private fun callErrorMethod(responseBody: ResponseBody?): String {

            val converter = ServiceGenerator.getRetrofit()
                .responseBodyConverter<RestError>(RestError::class.java, arrayOfNulls<Annotation>(0))
            return try {
                val errorResponse = converter.convert(responseBody!!)
                val errorMessage = errorResponse!!.message
                errorMessage!!
            } catch (e: IOException) {
                e.toString()
            } } }
}
