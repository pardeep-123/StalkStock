package com.tamam.utils.others

class GlobalVariables {

    class SHARED_PREF {
        companion object {
            const val SHARED_NAME = "i_dispatch"
            const val LOGIN_DETAILS = "login_details"
            const val IS_LOGIN = "is_login"
            const val AUTH_KEY = "auth_key"
            const val DEVICE_TOKEN = "device_token"
            const val USER_ID = "user_id"
            const val NOTI_ON_Off = "notificationonoff"
            const val USER_NAME = "user_name"
            const val USER_IMAGE = "user_image"
            const val USER_LOCATION = "user_location"
            const val UNREAD_NOTIFICATION = "unread_notification"
            const val JOB_STATE = "job_state"
        }
    }


    object URL {


            const val BASE_URL: String = "http://3.13.214.27:8800/api/"

//            const val SOCKET_URL: String = "http://202.164.42.227:8992/"
//            const val IMAGE_URL: String = "http://202.164.42.227:8992/images/users/"


        const val SECURITY_KEY: String = "stalkandstock1123"
            //            const val GETSTORES = "getStores"
            const val USERLOGIN = "user/login"
            const val USERSIGNUP = "user/signup"
            const val VENDORSIGNUP = "vendor/signup"
            const val FORGOTPASSWORD = "forgotPassword"
            const val GETPROFILE = "get_profile"
            const val EDITPROFILE = "edit_profile"
            const val CHANGEPASSWORD = "change_password"
            const val TERMCONDITION = "term_condition"
            const val PRIVACYPOLICY = "privacy_policy"
            const val LOGOUT = "logout"
            const val NOTIFICATIONSSTATUS = "notifications_status"
            const val GETNOTIFICATIONS = "get_notifications"
            const val DELETENOTIFICATION = "delete_notification"
            const val GETCURRENTORDERASSIGN = "get_current_order_assign"
            const val STARTJOB = "start_job"
            const val STOPJOB = "stop_job"
            const val JOBHISTORY = "job_history"
            const val RESTARTJOB = "restart_job"
            const val COMPLETEJOB = "complete_job"
            const val PAYMENT = "payment"


    }
    object PARAM{
         val email = "email"
         val password = "password"
         val device_type = "device_type"
         val device_token = "device_token"
         val android_device_type = "1"
         val phone = "phone"
         val image = "image"
         val firstname = "firstName"
         val lastname = "lastName"
         val mobile = "mobile"
         val businessPhone = "buisnessPhone"
         val shopName = "shopName"
         val buisnessTypeId = "buisnessTypeId"
         val buisnessLicense = "buisnessLicense"
         val website = "website"
         val city = "city"
         val state = "state"
         val country = "country"
         val postalCode = "postalCode"
         val shopAddress = "shopAddress"
         val shopDescription = "shopDescription"



    }

    object Job_state
    {
        const val DIRECTION = "direction"
        const val START_JOBSTATUS = "start_job"
        const val TOKEN_PAYMENT = "token_payment"
        const val STOP_JOBSTATUS = "stop_job"
        const val RESTART_JOB = "restart_job"

    }


}