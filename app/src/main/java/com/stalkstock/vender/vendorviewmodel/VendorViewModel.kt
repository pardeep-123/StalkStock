package com.stalkstock.vender.vendorviewmodel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.utils.others.Util
import com.stalkstock.utils.others.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class VendorViewModel : ViewModel() {
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()

    val restApiInterface = MyApplication.getinstance().provideAuthservice()


    @SuppressLint("CheckResult")
    fun vendorChangeOrderStatus(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorChangeOrderStatus(hashMap)
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
                        vendorChangeOrderStatus(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getVendorBusinessDetailApi(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorBusinessDetail()
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
                        getVendorBusinessDetailApi(activity, showLoader)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun updateVendorBusinessDetailApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>,
        firstimage: String,
        mUtils: Util
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (firstimage.isNotEmpty()) {
                val file = File(firstimage)
                image = mUtils.prepareFilePart("shopLogo", file)
            }

            restApiInterface.vendorUpdateBusinessDetail(hashMap, image)
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
                        updateVendorBusinessDetailApi(
                            activity,
                            showLoader,
                            hashMap,
                            firstimage,
                            mUtils
                        )
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun orderListVendorApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorOrderList(hashMap)
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
                        orderListVendorApi(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun orderDetailVendorApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorOrderDetail(hashMap)
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
                        orderDetailVendorApi(activity, showLoader, hashMap)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun vendorBiddingList(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorBiddingList(hashMap)
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
                        vendorBiddingList(activity, showLoader, hashMap)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun vendorBiddingDetail(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorBiddingDetail(hashMap)
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
                        vendorBiddingDetail(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun vendorAcceptBid(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorAcceptBid(hashMap)
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
                        vendorAcceptBid(activity, showLoader, hashMap)
                    }
                })
        }

    }


}