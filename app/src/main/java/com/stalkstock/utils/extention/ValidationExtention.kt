package com.stalkstock.utils.extention

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Patterns
import com.stalkstock.MyApplication
import java.util.regex.Matcher
import java.util.regex.Pattern


private const val EMAIL_PATTERN =
    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01" +
            "-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

fun checkStringNull(string: String?): Boolean {
    return string == null || string == "null" || string.isEmpty()
}

fun checkObjectNull(obj: Any?): Boolean {
    return obj == null
}

/* public boolean isValidPassword(String mPassword) {
        return mPassword.matches("^.{6,}$");
    }*/
fun isNetworkConnected(): Boolean {
    val cm = MyApplication.instance!!.getApplicationContext()
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var activeNetwork: NetworkInfo? = null
    if (cm != null) activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

// check internet connection
fun isInternetAvailable(): Boolean {
    var result = false
    val connectivityManager =
        MyApplication.instance!!.getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}

fun isValidEmail(mEmail: String?): Boolean {
    if (checkStringNull(mEmail)) return false
    val pattern = Pattern.compile(EMAIL_PATTERN)
    val matcher: Matcher
    matcher = pattern.matcher(mEmail!!)
    return matcher.matches()
}

fun isValidPhone(mPhone: String, minLenght: Int, maxLenght: Int): Boolean {
    return !(mPhone.length < minLenght || mPhone.length > maxLenght) && Patterns.PHONE.matcher(
        mPhone
    ).matches()
}

/*
fun Activity.isPhoneValid(phone: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(phone))
        showAlerterError(resources.getString(R.string.error_phone))
    else
        check = true
    return check
}

fun Activity.isSignUpValid(email: String, password: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(email))
        showAlerterError(resources.getString(R.string.error_email))
    else if (!isValidEmail(email))
        showAlerterError(resources.getString(R.string.error_valid_email))
    else if (checkStringNull(password))
        showAlerterError(resources.getString(R.string.error_password))
    else if (password.length < 8)
        showAlerterError(resources.getString(R.string.error_password_length))
    else
        check = true
    return check
}

fun Activity.isEditEmailValid(email: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(email))
        showAlerterError(resources.getString(R.string.error_email))
    else if (!isValidEmail(email))
        showAlerterError(resources.getString(R.string.error_valid_email))
    else
        check = true
    return check
}

fun Activity.isFullNameValid(firstName: String, lastName: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(firstName))
        showAlerterError(resources.getString(R.string.error_firstname))
    else if (checkStringNull(lastName))
        showAlerterError(resources.getString(R.string.error_lastname))
    else
        check = true
    return check
}

fun Activity.isMaillingValid(
    address: String,
    */
/*suitapt: String,*//*

    city: String,
    state: String,
    zipCode: String
): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(address))
        showAlerterError(resources.getString(R.string.error_address))
   */
/* else if (checkStringNull(suitapt))
        showAlerterError(resources.getString(R.string.error_suit_apt))*//*

    else if (checkStringNull(city))
        showAlerterError(resources.getString(R.string.error_city))
    else if (checkStringNull(state))
        showAlerterError(resources.getString(R.string.error_state))
    else if (checkStringNull(zipCode))
        showAlerterError(resources.getString(R.string.error_zipcode))
    else
        check = true
    return check
}

fun Activity.isServiceRegionValid(addressFull: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(addressFull))
        showAlerterError(resources.getString(R.string.error_service_region))
    else
        check = true
    return check
}

fun Activity.isHearAboutValid(learnAbout: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(learnAbout))
        showAlerterError(resources.getString(R.string.error_hearabout))
    else
        check = true
    return check
}

fun Activity.isTransportValid(transportMode: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(transportMode))
        showAlerterError(resources.getString(R.string.error_transportmode))
    else
        check = true
    return check
}

fun Activity.isIndustryExperienceValid(totalExp: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(totalExp))
        showAlerterError(resources.getString(R.string.error_totalexp))
    else
        check = true
    return check
}

fun Activity.isSpecialServiceValid(website: String, experience: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(website))
        showAlerterError(resources.getString(R.string.error_website))
    else if (checkStringNull(experience))
        showAlerterError(resources.getString(R.string.error_experience))
    else
        check = true
    return check
}

fun Activity.isSSNValid(ssn: String, dob: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(ssn))
        showAlerterError(resources.getString(R.string.error_ssn))
    else if (checkStringNull(dob))
        showAlerterError(resources.getString(R.string.error_dob))
    else
        check = true
    return check
}

fun Activity.isAddIdentificationValid(
    text1: String,
    text2: String,
    text3: String,
    text4: String
): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(text1) || checkStringNull(text2) || checkStringNull(text3) || checkStringNull(
            text4
        )
    )
        check = false
    else
        check = true
    return check
}

fun Activity.isAddIdentification2Valid(text1: String,
                                       text2: String,
                                       text3: String,errorMessage:String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(text1))
        showAlerterError(errorMessage)
    else if (checkStringNull(text2))
        showAlerterError(resources.getString(R.string.error_expirydate))
    else if (checkStringNull(text3))
        showAlerterError(resources.getString(R.string.error_state))
    else
        check = true
    return check
}

fun Activity.isSetNewPasswordValid(password: String, newPassword: String): Boolean {
    var check = false
    if (!isInternetAvailable())
        showAlerterError(resources.getString(R.string.no_internet))
    else if (checkStringNull(password))
        showAlerterError(resources.getString(R.string.error_new_password))
    else if (password.length < 8)
        showAlerterError(resources.getString(R.string.error_new_password_length))
    else if (checkStringNull(newPassword))
        showAlerterError(resources.getString(R.string.error_renew_password))
    else if (!password.equals(newPassword))
        showAlerterError(resources.getString(R.string.error_password_not_matched))
    else
        check = true
    return check
}*/
