package com.stalkstock.api

import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.consumer.model.*
import com.stalkstock.response_models.common.forgot.ForgotPasswordResponse
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.utils.others.GlobalVariables.URL
import com.stalkstock.vender.Model.ModelAddProduct
import com.stalkstock.vender.Model.UpdateVendorProfileModel
import com.stalkstock.vender.Model.VendorProfileDetail
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface RestApiInterface {
    /*OLD
    * 1-adv
    * 2-commerc
    * 3-consumer
    * 4-vendor
    * 5-driver
    *
    New
1=>user
2=>driver
3=>vendor
4=>commercial
5=>advertiser* */

    /*-------------------------------------Common API's-----------------------------*/

    @Multipart
    @PUT(URL.notification_on_off)
    fun notification_on_offAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @Multipart
    @POST(URL.changePassword)
    fun changePasswordAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @Multipart
    @POST(URL.getSubCategoryList)
    fun getSubCategoryListAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelSubCategoriesList>

    @GET(URL.helpContent)
    fun helpContentAPI(): Observable<ModelHelpContent>

    @GET(URL.getCategoryList)
    fun getCategoryListAPI(): Observable<ModelCategoryList>

    @GET(URL.measurementList)
    fun measurementListAPI(): Observable<ModelMeasurementList>

    @POST(URL.logout)
    fun logout(): Observable<UserCommonModel>

    /*-------------------------------------User API's-----------------------------*/
    @FormUrlEncoded
    @POST(URL.USERLOGIN)
    fun userlogin(
        @FieldMap map: HashMap<String, String>
    ): Observable<UserLoginResponse>


    @FormUrlEncoded
    @POST(URL.getProfileDetail)
    fun getProfileDetail(
        @FieldMap map: HashMap<String, String>
    ): Observable<ModelGetProfileDetail>

    @FormUrlEncoded
    @POST(URL.getProfileDetailVendor)
    fun getProfileDetailVendor(
        @FieldMap map: HashMap<String, String>
    ): Observable<VendorProfileDetail>

    @Multipart
    @POST(URL.useraddUserAddress)
    fun useraddUserAddressAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @Multipart
    @POST(URL.getUserAddressList)
    fun getUserAddressListAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelUserAddressList>

    @Multipart
    @PUT(URL.editUserAddress)
    fun editUserAddressAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = URL.deleteUserAddress, hasBody = true)
    fun deleteUserAddressAPI(
        @Field("address_id") postId: Int
    ): Observable<UserCommonModel>

    @Multipart
    @POST(URL.USERSIGNUP)
    fun usersignup(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?
    ): Observable<ModelSignupUser>

    @Multipart
    @POST(URL.vendorAddProduct)
    fun vendorAddProductAPI(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: ArrayList<MultipartBody.Part>
    ): Observable<ModelAddProduct>

    @Multipart
    @PUT(URL.editUserProfileDetail)
    fun editUserProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?
    ): Observable<ModelUpdateProfile>

    @Multipart
    @PUT(URL.editVendorProfileDetail)
    fun editVendorProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?
    ): Observable<UpdateVendorProfileModel>


    @Multipart
    @POST(URL.VENDORSIGNUP)
    fun vendorsignup(
        @PartMap map: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): Observable<VendorSignupResponse>


    @FormUrlEncoded
    @POST(URL.FORGOTPASSWORD)
    fun forgotpassword(
        @FieldMap map: HashMap<String, String>
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