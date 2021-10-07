package com.stalkstock.advertiser.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AdvertiserViewModel: ViewModel() {
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()
    private val restApiInterface = MyApplication.getinstance().provideAuthservice()

    @SuppressLint("CheckResult")
    fun postAdvertiserSignUpApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>,
        firstImage: String,
        mUtils: Util
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (firstImage.isNotEmpty()) {
                val file = File(firstImage)
                image = mUtils.prepareFilePart("image", file)
            }

            restApiInterface.advertiserSignUp(hashMap, image)
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
                        postAdvertiserSignUpApi(activity, showLoader, hashMap, firstImage, mUtils)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getUserProfile(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getUserProfile(hashMap)
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
                        getUserProfile(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun editUserProfile(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>,
        firstImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (firstImage.isNotEmpty()) {
                val file = File(firstImage)
                image = mUtils.prepareFilePart("image", file)
            }
            restApiInterface.editAdvertiserProfileDetail(hashMap, image)
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
                        editUserProfile(activity, showLoader, hashMap,firstImage,mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getBusinessDetail(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ){
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getBusinessProfile(hashMap)
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
                        getBusinessDetail(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun editBusinessProfile(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>,
        firstImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (firstImage.isNotEmpty()) {
                val file = File(firstImage)
                image = mUtils.prepareFilePart("image", file)
            }
            restApiInterface.editAdvertiserBuisnessDetail(hashMap, image)
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
                        editBusinessProfile(activity, showLoader, hashMap,firstImage,mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getBusinessType(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getBuisnessType()
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
                        getBusinessType(activity, showLoader)
                    }
                })
        }

    }

}