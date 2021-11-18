package com.stalkstock.api

import com.stalkstock.advertiser.model.*
import com.stalkstock.commercial.view.model.*
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.consumer.TermsData
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
import retrofit2.http.Body


interface RestApiInterface {


    /*
    vendor:-business@yopmail.com    123456   //promote my goods and services
    consumer:-anikaesash@mailinator.com 123456   // purchase items for my household
    consumer:-user01@yopmail.com 123456   // purchase items for my household
    driver:-testdriver1@yopmail.com 123456      // offer delivery services
    */

    /*
1=>user
2=>driver
3=>vendor
4=>commercial
5=>advertiser
*/

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

    @GET(URL.terms)
    fun termsAndCondition(): Observable<TermsData>

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

    @Multipart
    @POST(URL.VENDORBIDDINGLIST)
    fun vendorBiddingList(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<VendorBiddingListResponse>

  @Multipart
    @POST(URL.VENDORBIDDINGDETAIL)
    fun vendorBiddingDetail(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<VendorBidDetailResponse>

  @Multipart
    @POST(URL.VENDORACCEPTBID)
    fun vendorAcceptBid(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<CommonResponseModel>


    /*-------------------------------------Driver API's-----------------------------*/


    @Multipart
    @PUT(URL.onlineOffline)
    fun driveronlineOffline(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @Multipart
    @PUT(URL.acceptRejectOrder)
    fun driverAcceptRejectOrder(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<UserCommonModel>

    @Multipart
    @POST(URL.orderHistoryDriver)
    fun orderHistoryDriver(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<OrderHistoryData>

    @Multipart
    @PUT(URL.changeDiverOrder)
    fun changeDiverOrder(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<DefaultDataModel>

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

    @Multipart
    @POST(URL.checkEmailMobileExist)
    fun checkEmailMobileExist(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<CheckEmailResponse>

    @Multipart
    @PUT(URL.editDriverProfileDetail)
    fun editDriverProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part profileImage: MultipartBody.Part?
    ): Observable<EditDriverResponse>


    @POST(URL.walletBalance)
    fun checkWalletBalance(): Observable<WalletData>

    @POST(URL.bankAccountList)
    fun checkBankList(): Observable<BankDataList>


    @FormUrlEncoded
    @POST(URL.addBankAccount)
    fun addBankAccount(@FieldMap map: HashMap<String, String>): Observable<AddBankData>

    @FormUrlEncoded
    @POST(URL.transferFunds)
    fun transferFunds(@FieldMap map: HashMap<String, String>): Observable<TransferFundsData>

    @FormUrlEncoded
    @POST(URL.addUserCards)
    fun addUserCards(@FieldMap map: HashMap<String, String>): Observable<AddCardData>

    @FormUrlEncoded
    @POST(URL.getCardList)
    fun getCardList(@FieldMap map: HashMap<String, String>): Observable<UserCardList>

    @FormUrlEncoded
    @POST(URL.getSuggestedProduct)
    fun getSuggestedProduct(@FieldMap map: HashMap<String, String>): Observable<SuggestedDataListed>

    @FormUrlEncoded
    @POST(URL.getNotificationList)
    fun getNotificationList(@FieldMap map: HashMap<String, String>): Observable<NotificationListData>

   // @DELETE(URL.deleteCard)


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = URL.deleteCard, hasBody = true)
    fun deleteCard(@FieldMap map: HashMap<String, String>): Observable<DeleteCardData>

    /*-------------------------------------Advertiser API's-----------------------------*/

    @Multipart
    @POST(URL.ADVERTISERSIGNUP)
    fun advertiserSignUp(
        @PartMap map: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): Observable<AdvertiserSignUpResponse>

    @FormUrlEncoded
    @POST(URL.advertiserProfile)
    fun getUserProfile(
        @FieldMap map: HashMap<String, String>): Observable<AdvertiserProfileDetailResponse>

    @Multipart
    @PUT(URL.editAdvertiserProfileDetail)
    fun editAdvertiserProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part?
    ): Observable<EditProfileResponse>

    @FormUrlEncoded
    @POST(URL.getBuisnessDetail)
    fun getBusinessProfile(
        @FieldMap map: HashMap<String, String>): Observable<BuisnessDetailResponse>

    @Multipart
    @PUT(URL.editAdvertiserBuisnessDetail)
    fun editAdvertiserBuisnessDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part buisnessLogo: MultipartBody.Part?
    ): Observable<EditBuisnessDetail>

    @GET(URL.buisnessType)
    fun getBuisnessType(): Observable<BusinessTypeResponse>

    @FormUrlEncoded
    @PUT(URL.notification_on_off)
    fun notificationOnOffAPI(
       @FieldMap map: HashMap<String, String>
    ): Observable<NotificationResponse>

    @Multipart
    @POST(URL.buisnessAdList)
    fun getAddList(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<BusinessAdsList>


    @Multipart
    @POST(URL.deleteBuisnessAd)
    fun deleteBuisnessAd(
        @PartMap map: HashMap<String, RequestBody>
    ): Observable<DeleteAdsResponse>

    @Multipart
    @PUT(URL.editBuisnessAd)
    fun editBuisnessAd(
//        @PartMap map: HashMap<String, RequestBody>, @Part image: ArrayList<MultipartBody.Part>
//    ): Observable<EditAdsResponse>
        @PartMap map: HashMap<String, RequestBody>, @Part image:  MultipartBody.Part?
    ): Observable<EditAdsResponse>

    @Multipart
    @POST(URL.addBuisnessAd)
    fun addBusinessAds(
//        @PartMap map: HashMap<String, RequestBody>, @Part image: ArrayList<MultipartBody.Part>
//    ): Observable<AddBusinesssAdsResponse>
        @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part?
    ): Observable<AddBusinesssAdsResponse>

    /*-------------------------------------Commercial API's-----------------------------*/

    @Multipart
    @POST(URL.COMMERCIALSIGNUP)
    fun commercialSignUp(
        @PartMap map: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): Observable<CommercialSignUpResponse>

    @FormUrlEncoded
    @POST(URL.getCommercialProfileDetail)
    fun getCommercialUserProfile(
        @FieldMap map: HashMap<String, String>): Observable<CommercialProfileDetailResponse>

    @Multipart
    @PUT(URL.editCommercialProfileDetail)
    fun editCommercialProfileDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part?
    ): Observable<EditCommercialProfileResponse>

    @FormUrlEncoded
    @POST(URL.getCommercialBuisnessDetail)
    fun getCommercialBusinessDetail(
        @FieldMap map: HashMap<String, String>): Observable<GetCommercialBuisnessDetail>

    @Multipart
    @PUT(URL.editCommercialBuisnessDetail)
    fun editCommercialBusinessDetail(
        @PartMap map: HashMap<String, RequestBody>, @Part buisnessLogo: MultipartBody.Part?
    ): Observable<EditCommercialBuisnessDetail>

    @FormUrlEncoded
    @POST(URL.bidinglist)
    fun getBidinglist(
        @FieldMap map: HashMap<String, String>): Observable<BidingListResponse>

    @Multipart
    @POST(URL.bidingDetail)
    fun getBidingDetail(
        @PartMap map: HashMap<String, Int>
    ): Observable<BidingDetailResponse>


    @POST(URL.sendBidingRequest)
    fun sendBidingRequest(@Body body: SendRequestData): Observable<Sendbidresponse>

    @POST(URL.orderPlace)
    fun orderPlace(@Body body: HashMap<String,Any>): Observable<CommericalOrderPlaceResponse>


}