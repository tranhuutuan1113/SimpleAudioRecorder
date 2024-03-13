package com.ph.audiorecorder.app.retrofit;

import com.ph.audiorecorder.app.login.LoginInfo;
import com.ph.audiorecorder.app.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    //baseUrl
    String API_SERVER_URL = "https://ph2024.org/";

    @POST("auth/login")
    Call<LoginResponse>login(@Body LoginInfo loginInfo);
}
