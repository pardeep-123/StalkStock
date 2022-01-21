package com.stalkstock.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.commercial.view.model.SendRequestData
import com.stalkstock.consumer.model.PlaceOrderModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import java.io.File

class HomeViewModel : ViewModel() {
    var homeResponse: MutableLiveData<RestObservable> = MutableLiveData()
    fun getRegisterResponse(): LiveData<RestObservable> {
        return homeResponse
    }

    private val restApiInterface = MyApplication.getinstance().provideAuthservice()


    @SuppressLint("CheckResult")
    fun postuserloginApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.userlogin(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        postuserloginApi(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun termsAndCondition(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.termsAndCondition()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        termsAndCondition(activity, showLoader)
                    }
                }) } }

    @SuppressLint("CheckResult")
    fun logout(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        logout(activity, showLoader)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun getProfileDetail(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getProfileDetail(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getProfileDetail(activity, showLoader, hashMap)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun getVendorProduct(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getVendorProduct(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getVendorProduct(activity, showLoader, hashMap)
                    }
                }) }
    }



    @SuppressLint("CheckResult")
    fun getProfileDetailVendor(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getProfileDetailVendor(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getProfileDetailVendor(activity, showLoader, hashMap)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun useraddUserAddressAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.useraddUserAddressAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        useraddUserAddressAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun makeDefaultAddress(
        activity: Activity,
        showLoader: Boolean,
        addressId: HashMap<String,Int>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.makeDefaultAddress(addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        makeDefaultAddress(activity, showLoader, addressId)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun editUserAddressAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.editUserAddressAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editUserAddressAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getUserAddressListAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getUserAddressListAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getUserAddressListAPI(activity, showLoader, hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getVendorProductListAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getVendorProductListAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getVendorProductListAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getVendorProductDetailsAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getVendorProductDetailsAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getVendorProductDetailsAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun deleteUserAddressAPI(
        activity: Activity,
        showLoader: Boolean,
        mId: Int
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.deleteUserAddressAPI(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deleteUserAddressAPI(activity, showLoader, mId)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun deleteVendorProductAPI(
        activity: Activity,
        showLoader: Boolean,
        mId: Int
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.deleteVendorProductAPI(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deleteVendorProductAPI(activity, showLoader, mId)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun notification_on_offAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.notification_on_offAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        notification_on_offAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun editProductAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>,
        deleteImage: ArrayList<MultipartBody.Part>,
        profileImage: ArrayList<String>,
        mUtils: Util
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            val image: ArrayList<MultipartBody.Part> = ArrayList()
            if (profileImage.size > 0) {
                for (i in profileImage) {
                    val file = File(i)
                    image.add(mUtils.prepareFilePart("image", file))
                }
            }

            restApiInterface.editProductAPI(hashMap,deleteImage, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editProductAPI(activity, showLoader, hashMap, deleteImage,profileImage, mUtils)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun changePasswordAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.changePasswordAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        changePasswordAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getSubCategoryListAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getSubCategoryListAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getSubCategoryListAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getProductAccToCategorySubcategoryAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getProductAccToCategorySubcategoryAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getProductAccToCategorySubcategoryAPI(activity, showLoader, hashMap)
                    }
                }) }
    }


    @SuppressLint("CheckResult")
    fun getProductAccToCategoryAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getProductAccToCategoryAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getProductAccToCategoryAPI(activity, showLoader, hashMap)
                    }
                }) }
    }


    @SuppressLint("CheckResult")
    fun addRecentSearchAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.addRecentSearchAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        addRecentSearchAPI(activity, showLoader, hashMap)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun getRecentSearchAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getRecentSearchListAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getRecentSearchAPI(activity, showLoader, hashMap)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun deleteRecentSearchAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.deleteSearchListAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deleteRecentSearchAPI(activity, showLoader, hashMap)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun userBannerListAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.userBannerListAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        userBannerListAPI(activity, showLoader, hashMap)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun userGetVendorAsPerProductAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.userGetVendorAsPerProductAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        userGetVendorAsPerProductAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun userGetVendorProductListAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.userGetVendorProductListAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        userGetVendorProductListAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun userAddUpdateToCartAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.userAddUpdateToCartAPI(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        userAddUpdateToCartAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun helpContentAPI(
        activity: Activity,
        showLoader: Boolean
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.helpContentAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        helpContentAPI(activity, showLoader)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getPrimaryAddress(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getPrimaryAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getPrimaryAddress(activity, showLoader)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun getCategoryListAPI(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getCategoryListAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getCategoryListAPI(activity, showLoader)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun getBusinessTypeApi(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getBuisnessType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getBusinessTypeApi(activity, showLoader)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun getUserCardDataAPI(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getUserCardDataAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getUserCardDataAPI(activity, showLoader)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun measurementListAPI(
        activity: Activity,
        showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.measurementListAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        measurementListAPI(activity, showLoader)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun sendOtp(
        activity: Activity,
        showLoader: Boolean,
        data: HashMap<String,RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.sendOtp(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        sendOtp(activity, showLoader, data)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun postvendorsignupApi(
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
                image = mUtils.prepareFilePart("image", file)
            }

            restApiInterface.vendorsignup(hashMap, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        postvendorsignupApi(activity, showLoader, hashMap, firstimage, mUtils)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun getusersignupApi(
        activity: Activity,
        showLoader: Boolean,
        map: HashMap<String, RequestBody>,
        profileImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (profileImage.isNotEmpty()) {
                val file = File(profileImage)
                image = mUtils.prepareFilePart("image", file)
            }

            restApiInterface.usersignup(map, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getusersignupApi(activity, showLoader, map, profileImage, mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun vendorAddProductAPI(
        activity: Activity,
        showLoader: Boolean,
        map: HashMap<String, RequestBody>,
        profileImage: ArrayList<String>,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            val image: ArrayList<MultipartBody.Part> = ArrayList()
            if (profileImage.size > 0) {
                for (i in profileImage) {
                    val file = File(i)
                    image.add(mUtils.prepareFilePart("image", file))
                }
            }

            restApiInterface.vendorAddProductAPI(map, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        vendorAddProductAPI(activity, showLoader, map, profileImage, mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun editUserProfileDetail(
        activity: Activity,
        showLoader: Boolean,
        map: HashMap<String, RequestBody>,
        profileImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (profileImage.isNotEmpty()) {
                val file = File(profileImage)
                image = mUtils.prepareFilePart("image", file)
            }

            restApiInterface.editUserProfileDetail(map, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editUserProfileDetail(activity, showLoader, map, profileImage, mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun editVendorProfileDetail(
        activity: Activity,
        showLoader: Boolean,
        map: HashMap<String, RequestBody>,
        profileImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var image: MultipartBody.Part? = null
            if (profileImage.isNotEmpty()) {
                val file = File(profileImage)
                image = mUtils.prepareFilePart("image", file)
            }

            restApiInterface.editVendorProfileDetail(map, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editVendorProfileDetail(activity, showLoader, map, profileImage, mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun postforgotpasswordApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.forgotpassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        postforgotpasswordApi(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun placeOrderApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: PlaceOrderModel
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.userOrderPlace(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        placeOrderApi(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getOrderListAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getOrderList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getOrderListAPI(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getOrderDetailAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getOrderDetail(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getOrderDetailAPI(activity, showLoader, hashMap)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun getSuggestedProduct(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getSuggestedProduct(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getSuggestedProduct(activity, showLoader, hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getNotificationList(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getNotificationList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getNotificationList(activity, showLoader, hashMap)
                    }
                })
        }
    }


    /*-------------------------------------Commercial ViewModel-----------------------------*/

    @SuppressLint("CheckResult")
    fun commrercialSignupApi(
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

            restApiInterface.commercialSignUp(hashMap, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        commrercialSignupApi(activity, showLoader, hashMap, firstImage, mUtils)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun getCommercialUserProfile(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getCommercialUserProfile(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getCommercialUserProfile(activity, showLoader, hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun editCommercialUserProfile(
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
            restApiInterface.editCommercialProfileDetail(hashMap, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editCommercialUserProfile(activity, showLoader, hashMap, firstImage, mUtils)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getCommercialBusinessProfile(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getCommercialBusinessDetail(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getCommercialBusinessProfile(activity, showLoader, hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun editCommercialBusinessProfile(
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
                image = mUtils.prepareFilePart("buisnessLogo", file)
            }
            restApiInterface.editCommercialBusinessDetail(hashMap, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editCommercialBusinessProfile(
                            activity,
                            showLoader,
                            hashMap,
                            firstImage,
                            mUtils
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun sendBidingRequest(
        activity: Activity,addressId:Int,data:JSONArray, showLoader: Boolean
    ) {
        val jsonParser = JsonParser()
        val gsonObject = jsonParser.parse(data.toString()) as JsonArray
        val dataObj= SendRequestData(addressId, gsonObject)

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.sendBidingRequest(dataObj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        sendBidingRequest(activity,addressId,data, showLoader)
                    }
                })
        }
    }

        @SuppressLint("CheckResult")
        fun getBidingList(
            activity: Activity,
            showLoader: Boolean,
            hashMap: HashMap<String, String>
        ) {
            if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
                restApiInterface.getBidinglist(hashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        homeResponse.value = RestObservable.loading(activity, showLoader)
                    }
                    .subscribe(
                        { homeResponse.value = RestObservable.success(it) },
                        { homeResponse.value = RestObservable.error(activity, it) }
                    )
            } else {
                AppUtils.showNoInternetAlert(activity,
                    activity.getString(R.string.no_internet_connection),
                    object : OnNoInternetConnectionListener {
                        override fun onRetryApi() {
                            getBidingList(activity, showLoader, hashMap)
                        }
                    })
            }
        }

            @SuppressLint("CheckResult")
            fun getBidingDetail(
                activity: Activity,
                showLoader: Boolean,
                hashMap: HashMap<String, Int>
            ) {
                if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
                    restApiInterface.getBidingDetail(hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            homeResponse.value = RestObservable.loading(activity, showLoader)
                        }
                        .subscribe(
                            { homeResponse.value = RestObservable.success(it) },
                            { homeResponse.value = RestObservable.error(activity, it) }
                        )
                } else {
                    AppUtils.showNoInternetAlert(activity,
                        activity.getString(R.string.no_internet_connection),
                        object : OnNoInternetConnectionListener {
                            override fun onRetryApi() {
                                getBidingDetail(activity, showLoader, hashMap)
                            }
                        })
                }
            }

    @SuppressLint("CheckResult")
    fun orderPlace(
        activity: Activity, data: HashMap<String,Any>, showLoader: Boolean
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.orderPlace(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { homeResponse.value = RestObservable.success(it) },
                    { homeResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        orderPlace(activity,data, showLoader)
                    }
                })
        }
    }


}




