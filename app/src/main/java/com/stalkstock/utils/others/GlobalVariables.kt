package com.stalkstock.utils.others

class GlobalVariables {

    class SHARED_PREF {
        companion object {
            const val SHARED_NAME = "i_dispatch"
            const val LOGIN_DETAILS = "login_details"
            const val IS_LOGIN = "is_login"
            const val AUTH_KEY = "auth_key"
            const val DEVICE_TOKEN = "device_token"
            const val USER_ID = "user_id"
            const val USER_TYPE = "user_TYpe"
            const val NOTI_ON_Off = "notificationonoff"
            const val USER_NAME = "user_name"
            const val USER_IMAGE = "user_image"
            const val USER_LOCATION = "user_location"
            const val UNREAD_NOTIFICATION = "unread_notification"
            const val JOB_STATE = "job_state"
            const val ADV_TOKEN = "auth_token"
        }
    }

    class FilterVariables {
        companion object{
            var currentLowPrice = 0
             var currentHighPrice = 1000
             var currentSortBy = ""
        }
    }
    class SHARED_PREF_USER {
        companion object {
            const val AUTH_KEY = "auth_key"
            const val id = "id"
            const val role = "role"
            const val verified = "verified"
            const val status = "status"
            const val email = "email"
            const val mobile = "mobile"
            const val deviceToken = "deviceToken"
            const val deviceType = "deviceType"
            const val notification = "notification"
            const val remember_token = "remember_token"
            const val created = "created"
            const val updated = "updated"
            const val createdAt = "createdAt"
            const val updatedAt = "updatedAt"
            const val token = "token"
        }
    }

    class SHARED_PREF_VENDOR {
        companion object {
            const val AUTH_KEY = "auth_key"
            const val id = "id"
            const val role = "role"
            const val verified = "verified"
            const val status = "status"
            const val email = "email"
            const val mobile = "mobile"
            const val deviceToken = "deviceToken"
            const val deviceType = "deviceType"
            const val notification = "notification"
            const val remember_token = "remember_token"
            const val created = "created"
            const val updated = "updated"
            const val createdAt = "createdAt"
            const val updatedAt = "updatedAt"
            const val vendorID = "vendorId"
            const val approvalStatus = "approvalStatus"
            const val approvalStatusReason = "approvalStatusReason"
            const val firstName = "firstName"
            const val lastName = "lastName"
            const val image = "image"
            const val buisnessPhone = "buisnessPhone"
            const val shopName = "shopName"
            const val shopLogo = "shopLogo"
            const val buisnessTypeId = "buisnessTypeId"
            const val buisnessLicense = "buisnessLicense"
            const val website = "website"
            const val city = "city"
            const val state = "state"
            const val country = "country"
            const val postalCode = "postalCode"
            const val latitude = "latitude"
            const val longitude = "longitude"
            const val geoLocation = "geoLocation"
            const val shopAddress = "shopAddress"
            const val addressLine2 = "addressLine2"
            const val shopDescription = "shopDescription"
            const val shopCharges = "shopCharges"
            const val deliveryTime = "deliveryTime"
            const val paymentPolicy = "paymentPolicy"
            const val deliveryPolicy = "deliveryPolicy"
            const val sellerInformation = "sellerInformation"
            const val taxInPercent = "taxInPercent"
            const val taxValue = "taxValue"
            const val bankName = "bankName"
            const val bankBranch = "bankBranch"
            const val accountHolderName = "accountHolderName"
            const val accountNumber = "accountNumber"
            const val bsbNumber = "bsbNumber"
            const val ifscSwiftCode = "ifscSwiftCode"
            const val bankAddress = "bankAddress"
            const val userId = "userId"
            const val token = "token"
        }
    }

    class SHARED_PREF_DRIVER {
        companion object {
            const val AUTH_KEY = "auth_key"
            const val id = "id"
            const val role = "role"
            const val verified = "verified"
            const val status = "status"
            const val email = "email"
            const val mobile = "mobile"
            const val deviceToken = "deviceToken"
            const val deviceType = "deviceType"
            const val notification = "notification"
            const val remember_token = "remember_token"
            const val created = "created"
            const val updated = "updated"
            const val createdAt = "createdAt"
            const val updatedAt = "updatedAt"
            const val token = "token"
            const val DRIVER_DATA = "driverLogindata"
        }
    }

    class SHARED_PREF_ADVERTISER{
        companion object{
            const val AUTH_KEY = "auth_key"
            const val id = "id"
            const val role = "role"
            const val verified = "verified"
            const val status = "status"
            const val email = "email"
            const val mobile = "mobile"
            const val deviceToken = "deviceToken"
            const val deviceType = "deviceType"
            const val notification = "notification"
            const val remember_token = "remember_token"
            const val created = "created"
            const val updated = "updated"
            const val createdAt = "createdAt"
            const val updatedAt = "updatedAt"
            const val firstName = "firstName"
            const val lastName = "lastName"
            const val image = "image"
            const val buisnessPhone = "buisnessPhone"
            const val buisnessName = "buisnessName"
            const val buisnessLogo = "buisnessLogo"
            const val buisnessTypeId = "buisnessTypeId"
            const val buisnessLicense = "buisnessLicense"
            const val website = "website"
            const val city = "city"
            const val state = "state"
            const val country = "country"
            const val postalCode = "postalCode"
            const val buisnessAddress = "buisnessAddress"
            const val addressLine2 = "addressLine2"
            const val buisnessDescription = "buisnessDescription"
            const val userId = "userId"
            const val token = "token"
            const val advertiserId = "advertiserId"
        }
    }

    class SHARED_PREF_COMMERCIAL{
        companion object{
            const val AUTH_KEY = "auth_key"
            const val id = "id"
            const val role = "role"
            const val verified = "verified"
            const val status = "status"
            const val email = "email"
            const val mobile = "mobile"
            const val deviceToken = "deviceToken"
            const val deviceType = "deviceType"
            const val notification = "notification"
            const val remember_token = "remember_token"
            const val created = "created"
            const val updated = "updated"
            const val createdAt = "createdAt"
            const val updatedAt = "updatedAt"
            const val firstName = "firstName"
            const val lastName = "lastName"
            const val image = "image"
            const val buisnessPhone = "buisnessPhone"
            const val buisnessName = "buisnessName"
            const val buisnessLogo = "buisnessLogo"
            const val buisnessTypeId = "buisnessTypeId"
            const val buisnessLicense = "buisnessLicense"
            const val website = "website"
            const val city = "city"
            const val state = "state"
            const val country = "country"
            const val postalCode = "postalCode"
            const val buisnessAddress = "buisnessAddress"
            const val addressLine2 = "addressLine2"
            const val buisnessDescription = "buisnessDescription"
            const val userId = "userId"
            const val token = "token"
            const val advertiserId = "advertiserId"
        }
    }


    object URL {

        const val BASE_URL: String = "http://3.13.214.27:8800/api/"
       // const val BASE_URL: String = "http://192.168.1.156:8800/api/"  //local
        const val IMAGE_URL: String = "http://3.13.214.27:8800/uploads/"
       // const val IMAGE_URL: String = "http://192.168.1.156:8800/uploads/"  // local

            const val SOCKET_URL: String = "http://3.13.214.27:8800"
          //  const val SOCKET_URL: String = "http://192.168.1.156:8800"  //local
         //   const val IMAGE_URL: String = "http://202.164.42.227:8992/images/users/"


        const val SECURITY_KEY: String = "stalkandstock1123"

        //    const val GETSTORES = "getStores"
        const val USERLOGIN = "user/login"
        const val terms = "termAndCondition"
        const val logout = "logout"
        const val getProfileDetail = "user/getProfileDetail"
        const val getProfileDetailVendor = "vendor/getProfileDetail"
        const val vendorChangeOrderStatus = "vendor/changeOrderStatus"
        const val useraddUserAddress = "user/addUserAddress"
        const val userOrderList = "user/order/list"
        const val userOrderDetail = "user/order/detail"
        const val getUserAddressList = "user/getUserAddressList"
        const val getVendorProductList = "vendor/getVendorProductList"
        const val getVendorProductDetails = "vendor/getVendorProductDetails"
        const val editUserAddress = "user/editUserAddress"
        const val deleteUserAddress = "user/deleteUserAddress"
        const val deleteVendorProduct = "vendor/deleteProduct"
        const val notification_on_off = "notification_on_off"
        const val editProduct = "vendor/editProduct"
        const val changePassword = "changePassword"
        const val getSubCategoryList = "getSubCategoryList"
        const val getProductAccToCategorySubcategory = "getProductAccToCategorySubcategory"
        const val getProductAccToCategory = "getProductAccToCategory"
        const val addRecentSearch = "user/addRecentSearch"
        const val recentSearchList = "user/recentSearchList"
        const val deleteRecentSearch = "user/deleteRecentSearch"
        const val userBannerList = "user/home/banner"
        const val userGetVendorAsPerProduct = "get/product/vendor"
        const val userGetVendorProductList = "get/vendor/product/list"
        const val userAddUpdateToCart = "user/add/cart"
        const val helpContent = "helpContent"
        const val getCategoryList = "getCategoryList"
        const val getUserCardData  = "user/cartDetail"
        const val measurementList = "measurementList"
        const val USERSIGNUP = "user/signup"
        const val checkEmailMobileExist = "user/checkEmailMobileExist"
        const val vendorAddProduct = "vendor/addProduct"
        const val editUserProfileDetail = "user/editUserProfileDetail"
        const val editVendorProfileDetail = "vendor/editVendorProfileDetail"
        const val VENDORSIGNUP = "vendor/signup"
        const val VENDORBUSINESSDETAIL = "vendor/getBuisnessDetail"
        const val VENDOREDITBUSINESSDETAIL = "vendor/editVendorBuisnessDetail"
        const val VENDORORDERLISTDETAIL = "vendor/orderList"
        const val VENDORORDERDETAIL = "vendor/orderDetail"
        const val VENDORBIDDINGLIST="bidingAcceptRequestList"
        const val VENDORBIDDINGDETAIL="vendor/biding/Detail"
        const val VENDORACCEPTBID="vendor/accept/biding"
        const val code = 200
        const val FORGOTPASSWORD = "forgotPassword"
        const val GETPROFILE = "get_profile"
        const val ORDERPLACE = "user/orderPlace"
        const val CHANGEPASSWORD = "change_password"
        const val LOGOUT = "logout"
        const val NOTIFICATIONSSTATUS = "notifications_status"
        const val GETNOTIFICATIONS = "get_notifications"
        const val DRIVERSIGNUP = "driver/signup"
        const val editDriverDocumentDetail = "driver/editDriverDocumentDetail"
        const val getDriverProfileDetail = "driver/getProfileDetail"
        const val onlineOffline = "driver/onlineOffline"
        const val driverOrderRequestAPI = "driver/driverOrderRequest"
        const val getDocumentDetail = "driver/getDocumentDetail"
        const val editDriverProfileDetail = "driver/editDriverProfileDetail"
        const val acceptRejectOrder = "driver/acceptRejectOrder"    //1=> accept 2=> reject
        const val walletBalance = "walletBalance"
        const val bankAccountList = "bankAccountList"
        const val addBankAccount = "add/bank/account"
        const val transferFunds = "transferFund"
        const val addUserCards = "user/add/Card"
        const val getCardList = "user/getUserCardList"
        const val getSuggestedProduct = "user/home/suggested/Product"    //default, rating,popular
        const val getNotificationList = "notificationList"
        const val deleteCard = "user/deleteUserCard"
        const val orderHistoryDriver = "driver/orderHistoryDriver"
        const val changeDiverOrder = "driver/changeOrderStatus"

        const val ADVERTISERSIGNUP = "advertiser/signup"
        const val advertiserProfile = "advertiser/getProfileDetail"
        const val editAdvertiserProfileDetail = "advertiser/editAdvertiserProfileDetail"
        const val getBuisnessDetail = "advertiser/getBuisnessDetail"
        const val editAdvertiserBuisnessDetail = "advertiser/editAdvertiserBuisnessDetail"
        const val buisnessType = "buisnessType"
        const val buisnessAdList = "advertiser/buisnessAdList"
        const val editBuisnessAd = "advertiser/editBuisnessAd"
        const val deleteBuisnessAd = "advertiser/deleteBuisnessAd"
        const val buisnessAdView = "advertiser/buisnessAdView"
        const val addBuisnessAd = "advertiser/addBuisnessAd"

        const val COMMERCIALSIGNUP = "commercial/signup"
        const val getCommercialProfileDetail = "commercial/getProfileDetail"
        const val editCommercialProfileDetail = "commercial/editCommercialProfileDetail"
        const val editCommercialBuisnessDetail = "commercial/editCommercialBuisnessDetail"
        const val getCommercialBuisnessDetail = "commercial/getBuisnessDetail"
        const val bidinglist = "commercial/biding/list"
        const val sendBidingRequest = "sendBidingRequest"
        const val orderPlace = "commercial/order/place"
        const val makeDefaultCard = "user/markAsPrimary"
        const val markAsPrimaryAddress = "markAsPrimaryAddress"

   /*     {
            "addressId": 42,
            "bidItem": [
            {
                "productId": 20,
                "qty": 10
            }
           ]
        }*/
        const val bidingDetail = "commercial/biding/Detail" //bidId

    }

    object PARAM {
        val email = "email"
        val password = "password"
        val device_type = "device_type"
        val device_token = "device_token"
        val android_device_type = "0"
        val phone = "phone"
        val image = "image"
        val firstname = "firstName"
        val lastname = "lastName"
        val mobile = "mobile"
        val businessPhone = "buisnessPhone"
        val shopName = "shopName"
        val buisnessTypeId = "buisnessTypeId"
        val deliveryType = "deliveryType"
        val buisnessDeliveryTypeId = "deliveryType"
        val buisnessLicense = "buisnessLicense"
        val website = "website"
        val city = "city"
        val state = "state"
        val country = "country"
        val postalCode = "postalCode"
        val shopAddress = "shopAddress"
        val shopDescription = "shopDescription"
        val latitude = "latitude"
        val longitude = "longitude"
        val geoLocation = "geoLocation"
        val addressLine2 = "addressLine2"
        val vehicleType = "vehicleType"
        val vehicleMake = "vehicleMake"
        val vehicleModel = "vehicleModel"
        val licenceNumber = "licenceNumber"
        val registrationNumber = "registrationNumber"
        val licenceExpiryDate = "licenceExpiryDate"
        val registrationExpiryDate = "registrationExpiryDate"
        val buisnessName = "buisnessName"
        val buisnessDescription = "buisnessDescription"
        val buisnessAddress = "buisnessAddress"
        val token = "token"

    }

    object DATEFORMAT
    {
        const val DateFormat = "MM/dd/yyyy"
        const val DateFormat1 = "dd MMM,yyyy"
        const val DateFormat2 = "yyyy-MM-dd"
        const val DateFormat3 = "EE, MMM dd"
        const val TimeFormat1 = "hh:mm a"
        const val DateTimeFormat = "dd MMM,yyyy hh:mm a"
        const val DateTimeFormat2 = "MMM dd,yyyy hh:mm a"
        const val DateTimeFormat1 = "yyyy-MM-dd'T'hh:mm"  //Fri Sep 17 01:40:00 GMT+05:30 2021
        const val DateTimeFormat3 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" // 2021-10-07T05:19:07.000Z
    }

    object Job_state {
        const val DIRECTION = "direction"
        const val START_JOBSTATUS = "start_job"
        const val TOKEN_PAYMENT = "token_payment"
        const val STOP_JOBSTATUS = "stop_job"
        const val RESTART_JOB = "restart_job"
    }

}