
package com.finwin.brahmagiri.fooddelivery.WebService;

import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.ResponseFetchCategory;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.ResponseHomePage;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.ResponseFetchitem;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseAddcart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseBrahmaCart;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseCreateBill;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchOutlet;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchProducts;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseFetchZone;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseInvoiceGen;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseLogin;
import com.finwin.brahmagiri.fooddelivery.Responses.ResponseRemove;
import com.finwin.brahmagiri.fooddelivery.Responses.Response_Signup;
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
    @FormUrlEncoded
    @POST("MobileApp_Item_Api")
    Call<ResponseFetchitem> doFetchItembyCategory(@Field("ZoneId") String ZoneId,
                                                  @Field("AuthKey") String AuthKey,
                                                  @Field("Flag") String Flag,
                                                  @Field("Category") String Category,
                                                  @Field("PageNumber") int PageNumber, @Field("CustId") String CustId,
                                                  @Field("SearchKey") String SearchKey);

    @FormUrlEncoded
    @POST("MobileApp_Item_Api")
    Call<ResponseFetchCategory> doFetchCategory(@Field("ZoneId") String ZoneId,
                                                @Field("AuthKey") String AuthKey,
                                                @Field("Flag") String Flag,
                                                @Field("PageNumber") int PageNumber);


    @FormUrlEncoded
    @POST("MobileApp_Cart_Api")
    Call<ResponseAddtoCart> doCartManagment(@Field("ZoneId") String ZoneId,
                                            @Field("AuthKey") String AuthKey,
                                            @Field("Flag") String Flag,
                                            @Field("PageNumber") int PageNumber,
                                            @Field("Category") String Category,
                                            @Field("ItemCode") String ItemCode,
                                            @Field("Quantity") String Quantity,
                                            @Field("CustId") String CustId,
                                            @Field("PaymentType") String PaymentType);

    @FormUrlEncoded
    @POST("MobileApp_Cart_Api")
    Call<ResponseFetchCart> doCartSummary(@Field("ZoneId") String ZoneId,
                                          @Field("AuthKey") String AuthKey,
                                          @Field("Flag") String Flag,
                                          @Field("PageNumber") int PageNumber,
                                          @Field("Category") String Category,
                                          @Field("ItemCode") String ItemCode,
                                          @Field("Quantity") String Quantity,
                                          @Field("CustId") String CustId);

    //  @GET("outlet/products?")
    // Call<ResponseFetchProducts>fetchproducts(@Query("outlet_id") String outlet_id, @Header("Access-Token") String auth);


    @FormUrlEncoded
    @POST("MobileApp_Item_Api")
    Call<ResponseHomePage> doFetchhomePage(@Field("ZoneId") String ZoneId,
                                           @Field("AuthKey") String AuthKey,
                                           @Field("Flag") String Flag,
                                           @Field("PageNumber") int PageNumber,
                                           @Field("Category") String Category,
                                           @Field("ItemCode") String ItemCode,
                                           @Field("Quantity") String Quantity,
                                           @Field("CustId") String CustId,
                                           @Field("PaymentType") String PaymentType);


    @FormUrlEncoded
    @POST("authtoken/get_token")
    Call<ResponseLogin> dologinoutlet(@Field("db") String db, @Field("login") String login, @Field("password") String password);


    @GET("outlet/products?")
    Call<ResponseFetchProducts> fetchproducts(@Query("outlet_id") String outlet_id, @Header("Access-Token") String auth, @Header("database") String database);


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


    @GET("zone/outlets?")
    Call<ResponseFetchOutlet> fetchoutletbuzone(@Header("Access-Token") String auth, @Header("database") String database, @Query("zone_id")String zone_id);

    @GET("all_zone/get_zone")
    Call<ResponseFetchZone> fetchzone(@Header("Access-Token") String auth, @Header("database") String database);

    @POST("sign_up/zone_list")
    Call<Signup_Zone> fetchzonesignup(@Header("database") String database);

@FormUrlEncoded
    @POST("register/sign_up")
    Call<Response_Signup> dosignup(@Header("database") String database,
                                   @Field("name") String name,
                                   @Field("mobile") String  mobile,
                                   @Field("address") String address,
                                   @Field("pincode") int pincode,
                                   @Field("username") String username,
                                   @Field("password") String password,
                                   @Field("zoneid")String zoneid);




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

}

