package com.isthmusit.isthgreen.isthgreenapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit.Builder retrofit = null;

    private final static String API_BASE_URL = "https://isth-green-app-web.herokuapp.com/";

    public static Retrofit.Builder getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
        }
        retrofit.baseUrl(API_BASE_URL);
        return retrofit;
    }

    public static Retrofit.Builder getInstance(String url){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create());
        }
        retrofit.baseUrl(url);
        return retrofit;
    }
}
