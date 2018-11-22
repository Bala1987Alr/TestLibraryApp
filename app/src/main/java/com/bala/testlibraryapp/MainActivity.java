package com.bala.testlibraryapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bala.mylibrary.mylibrary.HomeScreen;
import com.bala.mylibrary.mylibrary.LoginScreen;
import com.bala.mylibrary.mylibrary.RegistrationScreen;
import com.bala.mylibrary.mylibrary.Security.Preferences;


public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences(Preferences.SHARED_PREFENCE_NAME,Context.MODE_PRIVATE);

    }

    public void trigger_reg(View v)
    {

        boolean isUserRegistered=preferences.getBoolean(Preferences.IS_REGISTERED,false);
        if (isUserRegistered) {
            startActivity(new Intent(this, LoginScreen.class));
        } else {
            startActivity(new Intent(this, RegistrationScreen.class));
        }
    }

    public void payment(View v)
    {
        if(!preferences.getBoolean(Preferences.IS_REGISTERED,false)) {
            startActivity(new Intent(this, RegistrationScreen.class));
        }
        else if(!preferences.getBoolean(Preferences.IS_LOGIN,false)){
            startActivity(new Intent(this, LoginScreen.class));
        }
        else {
            startActivity(new Intent(this, HomeScreen.class));
        }
    }



}
