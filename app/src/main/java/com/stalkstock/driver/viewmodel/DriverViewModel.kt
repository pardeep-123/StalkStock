package com.stalkstock.driver.viewmodel

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

            restApiInterface.driverSignup(map, image, imageL1, imageL2, imageR, imageI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { mResponse.value = RestObservable.success(it) },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        }
        else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        driverSignUpApi(
                            activity, showLoader, map, profileImage,
                            license1Image,
                            license2Image,
                            registerImage,
                            insuranceImage, mUtils
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun editDriverDocumentDetail(
        activity: Activity,
        showLoader: Boolean,
        map: HashMap<String, RequestBody>,
        license1Image: String,
        license2Image: String,
        registerImage: String,
        insuranceImage: String,
        mUtils: Util
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            var imageL1: MultipartBody.Part? = null
            var imageL2: MultipartBody.Part? = null
            var imageR: MultipartBody.Part? = null
            var imageI: MultipartBody.Part? = null
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

            restApiInterface.editDriverDocumentDetail(map, imageL1, imageL2, imageR, imageI)
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
                        editDriverDocumentDetail(
                            activity, showLoader, map, license1Image,
                            license2Image,
                            registerImage,
                            insuranceImage, mUtils
                        )
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
            restApiInterface.getDriverProfileDetail(hashMap)
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
                        getProfileDetail(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun driveronlineOffline(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.driveronlineOffline(hashMap)
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
                        driveronlineOffline(activity, showLoader, hashMap)
                    }
                })
        }

    }

    //accept reject order
    @SuppressLint("CheckResult")
    fun driverAcceptRejectOrder(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.driverAcceptRejectOrder(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { mResponse.value = RestObservable.success(it) },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        }
        else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        driverAcceptRejectOrder(activity, showLoader, hashMap)
                    }
                }) }
    }

    @SuppressLint("CheckResult")
    fun driverOrderRequestAPI(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.driverOrderRequestAPI()
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
                        driverOrderRequestAPI(activity, showLoader, hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun orderHistoryDriver(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.orderHistoryDriver(hashMap)
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
                        orderHistoryDriver(activity, showLoader, hashMap)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun changeDiverOrder(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.changeDiverOrder(hashMap)
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
                        changeDiverOrder(activity, showLoader, hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun checkWalletBalance(
        activity: Activity,
        showLoader: Boolean
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.checkWalletBalance()
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
                        checkWalletBalance(activity, showLoader)
                    }
                }) } }

    @SuppressLint("CheckResult")
    fun addBankAccount(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.addBankAccount(hashMap)
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
                        addBankAccount(activity, showLoader,hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun transferFunds(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.transferFunds(hashMap)
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
                        transferFunds(activity, showLoader,hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun addUserCards(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.addUserCards(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { mResponse.value = RestObservable.success(it) },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        }
        else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        addUserCards(activity, showLoader,hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getCardList(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getCardList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { mResponse.value = RestObservable.success(it) },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        }
        else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getCardList(activity, showLoader,hashMap)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun bankAccountList(
        activity: Activity,
        showLoader: Boolean
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.checkBankList()
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
                        bankAccountList(activity, showLoader)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun getDocumentDetail(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.getDocumentDetail(hashMap)
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
                        getDocumentDetail(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun checkEmailMobileExist(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, RequestBody>
    ) {

        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.checkEmailMobileExist(hashMap)
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
                        checkEmailMobileExist(activity, showLoader, hashMap)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun editDriverProfileDetail(
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

            restApiInterface.editDriverProfileDetail(map, image)
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
                        editDriverProfileDetail(activity, showLoader, map, profileImage, mUtils)
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
                        notification_on_offAPI(activity, showLoader, hashMap)
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
                        logout(activity, showLoader)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun deleteCard(
        activity: Activity,
        showLoader: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (AppUtils.isNetworkConnected(MyApplication.getinstance())) {
            restApiInterface.deleteCard(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mResponse.value = RestObservable.loading(activity, showLoader) }
                .subscribe(
                    { mResponse.value = RestObservable.success(it) },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        }
        else {
            AppUtils.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deleteCard(activity, showLoader,hashMap)
                    }
                })
        }
    }

}