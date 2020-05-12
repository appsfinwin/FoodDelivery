package com.finwintechnologies.brahmagirioutlet.WebService;

import com.finwintechnologies.brahmagirioutlet.Responses.ResponseFetchProducts;
import com.finwintechnologies.brahmagirioutlet.Responses.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("authtoken/get_token")
    Call<ResponseLogin>dologinoutlet(@Field("db")String db,@Field("login")String login,@Field("password")String password);


    @GET("outlet/products?")
    Call<ResponseFetchProducts>fetchproducts(@Query("outlet_id") String outlet_id, @Header("Access-Token") String auth);




}
