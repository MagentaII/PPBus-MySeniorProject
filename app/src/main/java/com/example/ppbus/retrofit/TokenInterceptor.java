package com.example.ppbus.retrofit;

import android.text.TextUtils;

import com.example.ppbus.data.Token;
import com.example.ppbus.retrofit.TDXAPIService;

import java.io.IOException;

import kotlin.jvm.Synchronized;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenInterceptor implements Interceptor {
    private static final String grant_type = "client_credentials";
    private static final String client_id = "C109118107-adb8d07c-3af8-42aa";
    private static final String client_secret = "b777a1ae-437a-44b9-b608-a259c4e1ca5a";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response proceed = chain.proceed(chain.request());
        if(isTokenExpired(proceed)){
            String newToken = getNewToken();
            if(!TextUtils.isEmpty(newToken)){
                Request newAgainRequest = chain.request().newBuilder()
                        .addHeader("authorization", "Bearer " + newToken)
                        .build();
                proceed.close();
                return chain.proceed(newAgainRequest);
            }
        }
        return proceed;
    }

    private String getNewToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tdx.transportdata.tw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit2.Response<Token> response = retrofit.create(TDXAPIService.class).getToken(grant_type, client_id, client_secret).execute();
        return response.body().getAccess_token();
    }

    private static boolean isTokenExpired(Response response){
        return response.code() == 401;
    }
}
