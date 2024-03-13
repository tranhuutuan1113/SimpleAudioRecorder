package com.ph.audiorecorder.app.login;

import com.ph.audiorecorder.Contract;

public interface LoginContract {
    interface View extends Contract.View {
    }

    interface UserActionsListener extends Contract.UserActionsListener<LoginContract.View> {

        void checkSession();

        void submitLogin(LoginInfo loginInfo);
    }
}
