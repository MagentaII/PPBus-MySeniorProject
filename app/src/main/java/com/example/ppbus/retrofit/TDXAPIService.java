package com.example.ppbus.retrofit;

import com.example.ppbus.data.realTimeNearStop.RealTimeNearStop;
import com.example.ppbus.data.stopOfRoute.StopOfRoute;
import com.example.ppbus.data.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TDXAPIService {

    @Headers("content-type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("auth/realms/TDXConnect/protocol/openid-connect/token")
    Call<Token> getToken(@Field("grant_type") String grantType,
                             @Field("client_id") String clientId,
                             @Field("client_secret") String clientSecret);

    @GET("api/basic/v2/Bus/StopOfRoute/City/Kaohsiung/7C?%24top=30&%24format=JSON")
    Call<List<StopOfRoute>> getStopOfRoute();

    @GET("api/basic/v2/Bus/RealTimeNearStop/City/Kaohsiung/7C?%24top=30&%24format=JSON")
    Call<List<RealTimeNearStop>> getRealTimeNearStop();
}
