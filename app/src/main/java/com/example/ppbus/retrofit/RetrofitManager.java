package com.example.ppbus.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final RetrofitManager mInstance = new RetrofitManager();

    private final TDXAPIService tdxapiService;

    private TokenInterceptor tokenInterceptor = new TokenInterceptor();

    private RetrofitManager() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)   // 設置連線Timeout
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tdx.transportdata.tw/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tdxapiService = retrofit.create(TDXAPIService.class);
    }

    public static RetrofitManager getInstance() {
        return mInstance;
    }

    public TDXAPIService getAPI() {
        return tdxapiService;
    }
}
