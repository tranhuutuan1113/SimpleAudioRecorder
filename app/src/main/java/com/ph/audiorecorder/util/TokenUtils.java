package com.ph.audiorecorder.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ph.audiorecorder.app.login.LoginResponse;

public class TokenUtils {
    private static final String PREFS_NAME = "prefs";
    private static final int PREFS_MODE = Activity.MODE_PRIVATE;
    private static final String USER_ID  = "USER_ID";
    private static final String AUTH_TOKEN  = "AUTH_TOKEN";
    private static final String REFRESH_TOKEN  = "REFRESH_TOKEN";
    private static final String EXPIRED_TIME  = "EXPIRED_TIME";
    private static final String LOGGED_TIME  = "LOGGED_TIME";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static TokenUtils INSTANCE = null;

    private TokenUtils() {}

    private TokenUtils(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        this.editor = prefs.edit();
    }

    public static synchronized TokenUtils getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TokenUtils(context);
        }

        return INSTANCE;
    }

    public void saveToken(LoginResponse loginResponse) {
        editor.putString(USER_ID, loginResponse.userID);
        editor.putString(AUTH_TOKEN, loginResponse.authToken);
        editor.putString(REFRESH_TOKEN, loginResponse.refreshToken);
        editor.putLong(LOGGED_TIME, loginResponse.loggedTime);
        editor.putLong(EXPIRED_TIME, loginResponse.expiredTime);

        editor.commit();
    }

    public void deleteToken() {
        editor.remove(USER_ID);
        editor.remove(AUTH_TOKEN);
        editor.remove(REFRESH_TOKEN);
        editor.remove(LOGGED_TIME);
        editor.remove(EXPIRED_TIME);

        editor.commit();
    }

    public boolean isTokenExpired() {
        long now = System.currentTimeMillis();
        return now >= prefs.getLong(EXPIRED_TIME, now);
    }

    public String getAuthToken() {
        return prefs.getString(AUTH_TOKEN, "null");
    }

    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN, "null");
    }
}
