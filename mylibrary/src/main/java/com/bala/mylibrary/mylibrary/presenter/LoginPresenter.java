package com.bala.mylibrary.mylibrary.presenter;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.bala.mylibrary.mylibrary.model.IUser;
import com.bala.mylibrary.mylibrary.model.UserModel;
import com.bala.mylibrary.mylibrary.view.ILoginView;


public class LoginPresenter implements ILoginPresenter, View.OnClickListener {

    ILoginView iLoginView;
    IUser user;
    Handler loginHandler;

    public LoginPresenter(ILoginView iLoginView)
    {
        this.iLoginView=iLoginView;
        loginHandler=new Handler(Looper.getMainLooper());
    }

    @Override
    public void doLogin(String name, String password){

        Boolean isLoginSuccess = true;
        user=new UserModel(name, password);
        final int code = user.isValidEntry(name,password);
        if (code!=0)
            isLoginSuccess = false;
        final Boolean result = isLoginSuccess;

        try {
            iLoginView.onLoginResult(result, code);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void showDialog() {

    }


    @Override
    public void onClick(View view) {

    }
}
