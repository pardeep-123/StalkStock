package com.net

import com.stalkstock.response_models.common.forgot.ForgotPasswordResponse
import com.stalkstock.response_models.user_response.user_login.UserLoginResponse
import com.stalkstock.response_models.user_response.user_signup.UserSignupResponse
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.tamam.utils.others.GlobalVariables.*
import io.reactivex.Observable
import retrofit2.http.*

interface RestApiInterface {

    @FormUrlEncoded
    @POST(URL.USERLOGIN)
    fun userlogin(@FieldMap map :HashMap<String,String>
    ): Observable<UserLoginResponse>

    @GET(URL.USERSIGNUP)
    fun usersignup(): Observable<UserSignupResponse>


    @FormUrlEncoded
    @POST(URL.VENDORSIGNUP)
    fun vendorsignup(@FieldMap map :HashMap<String,String>
    ): Observable<VendorSignupResponse>



    @FormUrlEncoded
    @POST(URL.FORGOTPASSWORD)
    fun    forgotpassword(@FieldMap map :HashMap<String,String>
    ): Observable<ForgotPasswordResponse>

//@GET(URL.GETPROFILE)
//fun getProfile(): Observable<GetProfileResponse>
//
//    @FormUrlEncoded
//    @POST(URL.EDITPROFILE)
//    fun    editprofile(@FieldMap map :HashMap<String,String>
//    ): Observable<EditProfileResponse>
//
//    @FormUrlEncoded
//    @PUT(URL.CHANGEPASSWORD)
//    fun    changepassword(@FieldMap map :HashMap<String,String>
//    ): Observable<ChangePasswordResponse>
//
//    @GET(URL.LOGOUT)
//    fun logout(): Observable<BaseResponse>
//
//    @GET(URL.TERMCONDITION)
//    fun termcondition(): Observable<TermConditionResponse>
//
//    @GET(URL.PRIVACYPOLICY)
//    fun privacypolicy(): Observable<PrivacyPolicyResponse>
//
//    @FormUrlEncoded
//    @POST(URL.NOTIFICATIONSSTATUS)
//    fun    notificationsstatus(@FieldMap map :HashMap<String,String>
//    ): Observable<NotificationsStatusResponse>
//
//    @GET(URL.GETNOTIFICATIONS)
//    fun getnotifications(): Observable<GetNotificationsResponse>
//
//    @FormUrlEncoded
//    @POST(URL.DELETENOTIFICATION)
//    fun    deletenotification(@Field("id") id: String
//    ): Observable<BaseResponse>
//
//    @GET(URL.GETCURRENTORDERASSIGN)
//    fun getcurrentorder(): Observable<CurrentOrderResponse>
//
//    @FormUrlEncoded
//    @POST(URL.STARTJOB)
//    fun    startjob(@FieldMap map :HashMap<String,String>
//    ): Observable<StartJobResponse>
//
//    @FormUrlEncoded
//    @POST(URL.STOPJOB)
//    fun    stopjob(@FieldMap map :HashMap<String,String>
//    ): Observable<StopJobResponse>
//
//    @FormUrlEncoded
//    @POST(URL.JOBHISTORY)
//    fun    jobhistory(@FieldMap map :HashMap<String,String>
//    ): Observable<JobHistoryResponse>
//
//    @FormUrlEncoded
//    @POST(URL.RESTARTJOB)
//    fun    restartjob(@FieldMap map :HashMap<String,String>
//    ): Observable<RestartJobResponse>
//
//    @FormUrlEncoded
//    @POST(URL.COMPLETEJOB)
//    fun    completejob(@FieldMap map :HashMap<String,String>
//    ): Observable<CompleteJobResponse>
//
//    @FormUrlEncoded
//    @POST(URL.PAYMENT)
//    fun    payment(@FieldMap map :HashMap<String,String>
//    ): Observable<PaymentResponse>
//
//
//
//
//    @Multipart
//    @POST("upload")
//    fun uploadImage(
//        @Part images: ArrayList<MultipartBody.Part?>
//    ): Observable<ImageResponse>




}