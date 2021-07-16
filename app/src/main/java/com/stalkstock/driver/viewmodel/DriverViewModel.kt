package com.stalkstock.driver.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.consumer.model.PlaceOrderModel
import com.stalkstock.utils.others.Util
import com.tamam.utils.others.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DriverViewModel : ViewModel() {
    val restApiInterface = MyApplication.getinstance().provideAuthservice()
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun driverSignUpApi(
        activity: Activity,
        showLoader: Boolean,
        map: HashMap<String, RequestBody>,
        profileImage: String,
        license1Image: String,
        license2Image: String,
        registerImage: String,
        insuranceImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            var imageL1: MultipartBody.Part? = null
            var imageL2: MultipartBody.Part? = null
            var imageR: MultipartBody.Part? = null
            var imageI: MultipartBody.Part? = null
            if (profileImage.isNotEmpty()) {
                val file = File(profileImage)
                image = mUtils.prepareFilePart("image", file)
            }
            if (license1Image.isNotEmpty()) {
                val file = File(license1Image)
                imageL1 = mUtils.prepareFilePart("licenceFrontImage", file)
            }
            if (license2Image.isNotEmpty()) {
                val file = File(license2Image)
                imageL2 = mUtils.prepareFilePart("licenceBackImage", file)
            }
            if (registerImage.isNotEmpty()) {
                val file = File(registerImage)
                imageR = mUtils.prepareFilePart("registrationImage", file)
            }
            if (insuranceImage.isNotEmpty()) {
                val file = File(insuranceImage)
                imageI = mUtils.prepareFilePart("insuranceProof", file)
            }

            restApiInterface.driverSignup(map, image,imageL1,imageL2,imageR,imageI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { mResponse.value = RestObservable.success(it) },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        driverSignUpApi(activity, showLoader, map, profileImage,
                            license1Image,
                            license2Image,
                            registerImage,
                            insuranceImage, mUtils)
                    }
                })
        }

    }



}