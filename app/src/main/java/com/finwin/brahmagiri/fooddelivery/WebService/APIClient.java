package com.finwin.brahmagiri.fooddelivery.WebService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

 // public  static String baseurl="http://bds.finwintechnologies.com:8069";//production
    private static Retrofit retrofit = null;

    public  static String baseurl="http://45.114.245.117:8069";//production
 //   private static  String baseurl="http://45.114.245.117:7059/";
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
