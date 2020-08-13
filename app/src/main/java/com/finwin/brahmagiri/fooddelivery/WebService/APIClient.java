package com.finwin.brahmagiri.fooddelivery.WebService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    /*http://45.114.245.117:7059/*/
   /* http://52.253.94.14:8069/*///live
    private static Retrofit retrofit = null;
    private static  String baseurl="http://45.114.245.117:7059/";
   /* "http://192.168.0.221:212/"*/

  public   static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;

    }
}
