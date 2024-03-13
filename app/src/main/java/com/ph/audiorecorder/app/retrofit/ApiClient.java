package com.ph.audiorecorder.app.retrofit;

import android.content.Context;

import com.ph.audiorecorder.BuildConfig;
import com.ph.audiorecorder.util.TokenUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit mRetrofit = null;

    public static Retrofit retrofit(Context context) {
        return retrofit(false, context);
    }

    public static Retrofit retrofit(Boolean forceRefresh, Context context) {
        if (forceRefresh) {
            mRetrofit = null;
        }

        if (mRetrofit == null) {
            String authToken = TokenUtils.getInstance(context).getAuthToken();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(chain -> {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();

                requestBuilder.addHeader("Accept", "application/json");
                requestBuilder.addHeader("Authorization", String.format("Bearer %s", authToken));

                request = requestBuilder.build();

                return chain.proceed(request);
            });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            OkHttpClient okHttpClient = builder.build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return mRetrofit;
    }
}
