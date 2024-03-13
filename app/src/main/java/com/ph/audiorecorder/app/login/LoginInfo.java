package com.ph.audiorecorder.app.login;

import com.google.gson.annotations.SerializedName;

public class LoginInfo {
    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String password;

    public LoginInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
