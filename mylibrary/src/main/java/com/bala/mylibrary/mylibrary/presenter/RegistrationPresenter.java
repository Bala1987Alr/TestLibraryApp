package com.bala.mylibrary.mylibrary.presenter;

import android.os.Handler;
import android.os.Looper;

import com.bala.mylibrary.mylibrary.model.IUser;
import com.bala.mylibrary.mylibrary.model.UserModel;
import com.bala.mylibrary.mylibrary.view.IRegistrationView;


public class RegistrationPresenter implements IRegistrationPresenter{

    IRegistrationView iRegistrationView;
    IUser user;
    Handler registrationHandler;

    public RegistrationPresenter(IRegistrationView iRegistrationView)
    {
        this.iRegistrationView=iRegistrationView;
        registrationHandler=new Handler(Looper.getMainLooper());
    }

    @Override
    public void doRegistration(String name, String password){

        Boolean isLoginSuccess = true;
        user=new UserModel(name, password);
        final int code = user.isValidEntry(name,password);
        if (code!=0)
            isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        registrationHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        iRegistrationView.onRegistrationResult(result, code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }}, 2000);
    }





}
