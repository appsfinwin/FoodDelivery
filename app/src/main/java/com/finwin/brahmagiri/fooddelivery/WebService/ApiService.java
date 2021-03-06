
package com.finwin.brahmagiri.fooddelivery.WebService;

import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.ResponseFetchCategory;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.ResponseHomePage;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.ResponseFetchitem;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseAddcart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCancelOrder;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCheckVersion;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCreateBill;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseDistricts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchAddress;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchOutlet;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProducts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProfile;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchZone;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseInvoiceGen;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseLogin;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseMyOrder;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseOrderDetails;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponsePay;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseRemove;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseStates;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseToken;
import com.finwin.brahmagiri.fooddelivery.Responses.Response_Signup;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponsenumUpdate;
import com.finwin.brahmagiri.fooddelivery.Responses.Signup_Zone;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {







    //  @GET("outlet/products?")
    // Call<ResponseFetchProducts>fetchproducts(@Query("outlet_id") String outlet_id, @Header("Access-Token") String auth);




    @FormUrlEncoded
    @POST("authtoken/get_token")
    Call<ResponseLogin> dologin
            (@Field("db") String db,
             @Field("login") String login,
             @Field("password") String password,
             @Field("app_type") String app_type);

    @FormUrlEncoded
    @POST("outlet_stock/stock_available")
    Call<ResponseFetchProducts> fetchproducts
            (@Field("outlet_id") String outlet_id,
             @Header("Access-Token") String auth,
             @Header("database") String database);


    @Headers({"Content-type: application/json",
            "Accept: */*"})
    @POST("cart/product_list")
    Call<ResponseCreateBill> postRawJSON(@Header("Access-Token") String Access_Token,
                                         @Header("database") String database, @Body JsonObject locationPost);

    @Headers({"Content-type: application/json",
            "Accept: */*"})
    @POST("cart/product_list")
    Call<ResponseInvoiceGen> dogenerateinvoice(@Header("Access-Token") String Access_Token,
                                               @Header("database") String database, @Body JsonObject locationPost);


    @GET("zone_new/outlets_new?")
    Call<ResponseFetchOutlet> fetchoutletbuzone(@Header("Access-Token") String auth, @Header("database") String database,
                                                @Query("page") String page,@Query("longitude")String longitude,@Query("latitude")String lattitude);

    @GET("all_zone/get_zone")
    Call<ResponseFetchZone> fetchzone(@Header("Access-Token") String auth, @Header("database") String database);

    @POST("sign_up/zone_list")
    Call<Signup_Zone> fetchzonesignup(@Header("database") String database);

    @FormUrlEncoded
    @POST("register/sign_up")
    Call<Response_Signup> dosignup(@Header("database") String database,
                                   @Field("name") String name,
                                   @Field("mobile") String mobile,
                                   @Field("address") String address,
                                   @Field("pincode") int pincode,
                                   @Field("username") String username,
                                   @Field("password") String password,
                                   @Field("street") String street,
                                   @Field("city") String city,
                                   @Field("landmark") String landmark,
                                   @Field("state") int stateid,
                                   @Field("district") int districtid,
                                   @Field("email") String email,
                                   @Field("zone") String zoneid);


    // @FormUrlEncoded
    @POST("temporary/add_cart")
    Call<ResponseAddcart> doAddtiocart(@Header("Access-Token") String Access_Token,
                                       @Header("database") String database,
                                       @Body JsonObject jsonObject);


    //  @FormUrlEncoded
    @POST("cart_list/get_cart")
    Call<ResponseBrahmaCart> FetchCart(@Header("Access-Token") String Access_Token,
                                       @Header("database") String database,
                                       @Body JsonObject cartbody);

    @POST("remove/remove_cart")
    Call<ResponseRemove> doremove(@Header("Access-Token") String Access_Token,
                                  @Header("database") String database,
                                  @Body JsonObject cartbody);


    @POST("consumer_sales_new/get_order_new")
    Call<ResponseMyOrder> doFetchMyOrder(@Header("Access-Token") String Access_Token,
                                         @Header("database") String database,
                                        @Body JsonObject jsonObject);

    @POST("send/otp")
    Call<JsonObject> doSendOtp(@Header("database") String database,
                               @Body JsonObject cartbody);

    @POST("otp/verification")
    Call<JsonObject> Verifyotp(@Header("database") String database,
                               @Body JsonObject cartbody);

    @POST("change/password")
    Call<JsonObject> doChangepwd(@Header("database") String database,
                                 @Body JsonObject cartbody);

    @POST("state/state_list")
    Call<ResponseStates> doFetchStates(@Header("database") String database);

    @POST("district/district_list")
    Call<ResponseDistricts> doFetchDistricts(@Header("database") String database, @Body JsonObject cartbody);

    @POST("consumer/profile")
    Call<ResponseFetchProfile> doFetchProfile(@Header("database") String database,
                                              @Header("Access-Token") String Access_Token, @Body JsonObject cartbody);
    @POST("profile/edit")
    Call<JsonObject> doUpdateProfile(@Header("database") String database,
                                     @Header("Access-Token") String Access_Token, @Body JsonObject cartbody);
    @POST("version/controller")
    Call<ResponseCheckVersion> doFetchVersionControl(@Header("database") String database, @Body JsonObject cartbody);

    @POST("payment/process")
    Call<ResponsePay> doFetchpayment(@Header("database") String database, @Header("Access-Token") String Access_Token, @Body JsonObject cartbody);
    @POST("invoice_customer/get_details_customer")
    Call<ResponseOrderDetails> dofetchorderdetails(@Header("Access-Token") String Access_Token,
                                                   @Header("database") String database, @Body JsonObject locationPost);



    @POST("firebase/token_save")
    Call<ResponseToken> dupushToken(@Header("Access-Token") String Access_Token,
                                    @Header("database") String database, @Body JsonObject locationPost);



    @POST("get_cost/delivery_cost")
    Call<ResponseFetchAddress> doUpdatedeliverycost(@Header("Access-Token") String Access_Token,
                                                    @Header("database") String database, @Body JsonObject locationPost);



    @Headers({"Content-type: application/json",
            "Accept: */*"})
    @POST("outlet_cancel/cancel_order")
    Call<ResponseCancelOrder> docancelOrder(@Header("Access-Token") String Access_Token,
                                            @Header("database") String database, @Body JsonObject locationPost);
    @POST("mobile_otp/updation_otp")
    Call<ResponsenumUpdate> doSendOtpforUpdation(@Header("Access-Token") String Access_Token, @Header("database") String database,
                                                 @Body JsonObject cartbody);


    @POST("number/verification")
    Call<JsonObject> Verifyotpformobilenum(@Header("Access-Token") String Access_Token,@Header("database") String database,
                               @Body JsonObject cartbody);
}

