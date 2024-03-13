package com.ph.audiorecorder.app.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("user_id")
    public String userID;
    @SerializedName("auth_token")
    public String authToken;
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("expired_time")
    public long expiredTime;
    @SerializedName("logged_time")
    public long loggedTime;
}
