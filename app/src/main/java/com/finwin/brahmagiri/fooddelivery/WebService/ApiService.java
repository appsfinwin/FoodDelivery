
package com.finwin.brahmagiri.fooddelivery.WebService;

import com.finwin.brahmagiri.fooddelivery.Responses.AddtoCart.ResponseAddtoCart;
import com.finwin.brahmagiri.fooddelivery.Responses.FetchCart.ResponseFetchCart;
import com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category.ResponseFetchCategory;
import com.finwin.brahmagiri.fooddelivery.Responses.HomePage.ResponseHomePage;
import com.finwin.brahmagiri.fooddelivery.Responses.Itemlisting.ResponseFetchitem;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

}

