package com.stalkstock.api

import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.consumer.model.*
import com.stalkstock.driver.models.*
import com.stalkstock.response_models.common.forgot.ForgotPasswordResponse
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.utils.others.GlobalVariables.URL
import com.stalkstock.vender.Model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface RestApiInterface {

    /*
    vendor:-business@yopmail.com    123456
    consumer:-anikaesash@mailinator.com 123456
    driver:-testdriver1@yopmail.com 123456

    */
    /*
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
    @PUT(URL.editProduct)
    fun editProductAPI(
        @PartMap map: HashMap<String, RequestBody>,
        @Part image: ArrayList<MultipartBody.Part>
    ): Observable<ModelEditProduct>

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

    @Multipart
    @POST(URL.getProductAccToCategorySubcategory)
    fun getProductAccToCategorySubcategoryAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelProductListAsPerSubCat>

    @FormUrlEncoded
    @POST(URL.addRecentSearch)
    fun addRecentSearchAPI(
        @FieldMap map: HashMap<String, String>
    ): Observable<AddRecentSearchResponse>

    @POST(URL.recentSearchList)
    fun getRecentSearchListAPI(): Observable<RecentSearchListResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = URL.deleteRecentSearch, hasBody = true)
    fun deleteSearchListAPI(@FieldMap map: HashMap<String, String>): Observable<DeleteRecentSearchResponse>

    @Multipart
    @POST(URL.userBannerList)
    fun userBannerListAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserBannerModel>


    @Multipart
    @POST(URL.userGetVendorAsPerProduct)
    fun userGetVendorAsPerProductAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelProductVendorList>

    @Multipart
    @POST(URL.userGetVendorProductList)
    fun userGetVendorProductListAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserVendorsProductList>

    @Multipart
    @POST(URL.userAddUpdateToCart)
    fun userAddUpdateToCartAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelAddToCart>

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


    @GET(URL.getUserCardData)
    fun getUserCardDataAPI(): Observable<ModelCartData>

    @POST(URL.ORDERPLACE)
    fun userOrderPlace(
        @Body map: PlaceOrderModel
    ): Observable<OrderPlaceResponse>

    @Multipart
    @POST(URL.getUserAddressList)
    fun getUserAddressListAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelUserAddressList>

    @Multipart
    @POST(URL.useraddUserAddress)
    fun useraddUserAddressAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

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
    @PUT(URL.editUserProfileDetail)
    fun editUserProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?
    ): Observable<ModelUpdateProfile>

    @FormUrlEncoded
    @POST(URL.userOrderList)
    fun getOrderList(
        @FieldMap map: HashMap<String, String>
    ): Observable<OrderListModel>

    @FormUrlEncoded
    @POST(URL.userOrderDetail)
    fun getOrderDetail(
        @FieldMap map: HashMap<String, String>
    ): Observable<OrderDetailResponse>

    /*-------------------------------------Vendor API's-----------------------------*/

    @Multipart
    @POST(URL.getVendorProductList)
    fun getVendorProductListAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelVendorProductList>

    @FormUrlEncoded
    @POST(URL.getProfileDetailVendor)
    fun getProfileDetailVendor(
        @FieldMap map: HashMap<String, String>
    ): Observable<VendorProfileDetail>

    @FormUrlEncoded
    @POST(URL.vendorChangeOrderStatus)
    fun vendorChangeOrderStatus(
        @FieldMap map: HashMap<String, String>
    ): Observable<VendorCommonModel>

    @Multipart
    @POST(URL.getVendorProductDetails)
    fun getVendorProductDetailsAPI(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<ModelProductDetail>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = URL.deleteVendorProduct, hasBody = true)
    fun deleteVendorProductAPI(
        @Field("product_id") postId: Int
    ): Observable<UserCommonModel>

    @Multipart
    @POST(URL.vendorAddProduct)
    fun vendorAddProductAPI(
        @PartMap map: HashMap<String, RequestBody>,
        @Part profileImage: ArrayList<MultipartBody.Part>
    ): Observable<ModelAddProduct>

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

    @POST(URL.VENDORBUSINESSDETAIL)
    fun vendorBusinessDetail(): Observable<VendorBusinessDetailResponse>

    @Multipart
    @PUT(URL.VENDOREDITBUSINESSDETAIL)
    fun vendorUpdateBusinessDetail(
        @PartMap map: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): Observable<VendorBusinessDetailResponse>

    @FormUrlEncoded
    @POST(URL.VENDORORDERLISTDETAIL)
    fun vendorOrderList(
        @FieldMap map: HashMap<String, String>
    ): Observable<VendorOrderListResponse>

    @FormUrlEncoded
    @POST(URL.VENDORORDERDETAIL)
    fun vendorOrderDetail(
        @FieldMap map: HashMap<String, String>
    ): Observable<OrderDetailVendorResponse>


    /*-------------------------------------Driver API's-----------------------------*/


    @Multipart
    @PUT(URL.onlineOffline)
    fun driveronlineOffline(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @POST(URL.driverOrderRequestAPI)
    fun driverOrderRequestAPI(): Observable<NewOrderResponse>

    @Multipart
    @POST(URL.DRIVERSIGNUP)
    fun driverSignup(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?,
        @Part licenseImage1: MultipartBody.Part?, @Part licenseImage2: MultipartBody.Part?,
        @Part registrationImage: MultipartBody.Part?, @Part insuranceImage: MultipartBody.Part?
    ): Observable<DriverSignUpResponse>

    @Multipart
    @PUT(URL.editDriverDocumentDetail)
    fun editDriverDocumentDetail(
        @PartMap map: HashMap<String, RequestBody>,
        @Part licenseImage1: MultipartBody.Part?, @Part licenseImage2: MultipartBody.Part?,
        @Part registrationImage: MultipartBody.Part?, @Part insuranceImage: MultipartBody.Part?
    ): Observable<DriverDocResponse>

    @FormUrlEncoded
    @POST(URL.getDriverProfileDetail)
    fun getDriverProfileDetail(
        @FieldMap map: HashMap<String, String>
    ): Observable<DriverProfileDetailResponse>

    @FormUrlEncoded
    @POST(URL.getDocumentDetail)
    fun getDocumentDetail(
        @FieldMap map: HashMap<String, String>
    ): Observable<DriverDocResponse>

    @FormUrlEncoded
    @POST(URL.checkEmailMobileExist)
    fun checkEmailMobileExist(
        @FieldMap map: HashMap<String, RequestBody>
    ): Observable<CheckEmailResponse>

    @Multipart
    @PUT(URL.editDriverProfileDetail)
    fun editDriverProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?
    ): Observable<EditDriverResponse>

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