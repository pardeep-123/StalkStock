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
import retrofit2.http.Multipart
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
    fun postvendorsignupApi(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.vendorsignup(hashMap)
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
                        postvendorsignupApi(activity, showLoader, hashMap)
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
//
//    @SuppressLint("CheckResult")
//    fun gettermsconditionsApi(activity: Activity, showLoader:Boolean)
//    {
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.termcondition()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        gettermsconditionsApi(activity, showLoader)
//                    }
//                })
//        }
//
//    }
//    @SuppressLint("CheckResult")
//    fun logoutApi(activity: Activity, showLoader:Boolean)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.logout()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        logoutApi(activity, showLoader)
//                    }
//                })
//        }
//
//    }
//
//
//    @SuppressLint("CheckResult")
//    fun getprivacypolicyApi(activity: Activity, showLoader:Boolean)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.privacypolicy()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        getprivacypolicyApi(activity, showLoader)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun getprofileApi(activity: Activity, showLoader:Boolean)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.getProfile()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        getprofileApi(activity, showLoader)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun posteditprofileApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.editprofile(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postforgotpasswordApi(activity, showLoader, hashMap)
//                    }
//                })
//            }
//
//    }
//
//
//    @SuppressLint("CheckResult")
//    fun imagesUpload(activity: Activity, showLoader:Boolean, images: ArrayList<MultipartBody.Part?>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.uploadImage(images)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        imagesUpload(activity, showLoader, images)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun putchangepasswordApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.changepassword(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        putchangepasswordApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun postnotificationsstatusApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.notificationsstatus(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postnotificationsstatusApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun getnotificationsApi(activity: Activity, showLoader:Boolean)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.getnotifications()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        getnotificationsApi(activity, showLoader)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun postdeletenotificationApi(activity: Activity, showLoader:Boolean, id: String)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.deletenotification(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postdeletenotificationApi(activity, showLoader, id)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun getcurrentorderApi(activity: Activity, showLoader:Boolean)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.getcurrentorder()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        getcurrentorderApi(activity, showLoader)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun poststartjobApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.startjob(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        poststartjobApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//
//
//    @SuppressLint("CheckResult")
//    fun poststopjobApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.stopjob(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        poststopjobApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun postjobhistoryApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.jobhistory(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postjobhistoryApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//
//    @SuppressLint("CheckResult")
//    fun postrestartjobApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.restartjob(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postrestartjobApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun postcompletejobApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.completejob(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postcompletejobApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    fun postpaymentApi(activity: Activity, showLoader:Boolean, hashMap: HashMap<String,String>)
//    {
//
//        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
//            restApiInterface.payment(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { homeResponse.value = RestObservable.loading(activity,showLoader) }
//                .subscribe(
//                    { homeResponse.value = RestObservable.success(it) },
//                    { homeResponse.value = RestObservable.error(activity,it) }
//                )
//        }else{
//            AppUtils.showNoInternetAlert(activity,
//                activity.getString(R.string.no_internet_connection),
//                object: OnNoInternetConnectionListener {
//                    override fun onRetryApi() {
//                        postpaymentApi(activity, showLoader, hashMap)
//                    }
//                })
//        }
//
//    }


}