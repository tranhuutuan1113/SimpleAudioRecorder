package com.ph.audiorecorder.app.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ph.audiorecorder.ARApplication;
import com.ph.audiorecorder.ColorMap;
import com.ph.audiorecorder.R;
import com.ph.audiorecorder.app.retrofit.ApiCallback;
import com.ph.audiorecorder.app.retrofit.ApiClient;
import com.ph.audiorecorder.app.retrofit.ApiInterface;
import com.ph.audiorecorder.util.TokenUtils;

import retrofit2.Call;
import timber.log.Timber;

public class LoginActivity extends Activity implements LoginContract.View, View.OnClickListener {
    private static final String tag = LoginActivity.class.getSimpleName();

    private ColorMap colorMap;
    private View loginView;
    private View processingView;
    private ApiInterface apiInterface;
    private EditText txtUserName;
    private EditText txtPassword;
    private Button btnSubmit;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        colorMap = ARApplication.getInjector().provideColorMap(getApplicationContext());
        setTheme(colorMap.getAppThemeResource());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginView = findViewById(R.id.layout_login);
        processingView = findViewById(R.id.layout_processing);

        txtUserName = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        apiInterface = ApiClient.retrofit(this).create(ApiInterface.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // check session
        showProgress();
        // assume session is expired or empty
        // show login
        hideProgress();

    }

    @Override
    public void showProgress() {
        loginView.setVisibility(View.INVISIBLE);
        processingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginView.setVisibility(View.VISIBLE);
        processingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.btn_submit) {
            showProgress();

            LoginInfo loginInfo = new LoginInfo("dev", "test");
            Call<LoginResponse> call = apiInterface.login(loginInfo);
            call.enqueue(new ApiCallback<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse model) {
                    // save token
                    TokenUtils.getInstance(LoginActivity.this).saveToken(model);
                    // refresh api client
                    ApiClient.retrofit(true, LoginActivity.this);

                    showMessage(R.string.login_success);
                    onFinish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    // show err message
                    showMessage(R.string.login_fail);
                    onFinish();
                }

                @Override
                public void onThrowable(Throwable t) {
                    // log
                    Timber.tag(tag).e(t);
                }

                @Override
                public void onFinish() {
                    hideProgress();
                }
            });
        }
    }
}
