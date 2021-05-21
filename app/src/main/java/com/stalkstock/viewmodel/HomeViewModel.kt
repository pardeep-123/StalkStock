package com.stalkstock.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.utils.others.Util
import com.tamam.utils.others.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class HomeViewModel : ViewModel() {
    var homeResponse: MutableLiveData<RestObservable> = MutableLiveData()
    fun getRegisterResponse(): LiveData<RestObservable> {
        return homeResponse
    }

    val restApiInterface = MyApplication.getinstance().provideAuthservice()


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
                })
        }

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
                })
        }

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
            var image: ArrayList<MultipartBody.Part> = ArrayList()
            if (profileImage.size > 0) {
                for (i in profileImage) {
                    var file = File(i)
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
}