package com.bala.mylibrary.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bala.mylibrary.R;
import com.bala.mylibrary.mylibrary.Security.Preferences;
import com.bala.mylibrary.mylibrary.apikey.APIBuilder;
import com.bala.mylibrary.mylibrary.apikey.APIKey;
import com.bala.mylibrary.mylibrary.presenter.IRegistrationPresenter;
import com.bala.mylibrary.mylibrary.presenter.RegistrationPresenter;
import com.bala.mylibrary.mylibrary.view.IRegistrationView;


public class RegistrationScreen extends AppCompatActivity implements IRegistrationView, View.OnClickListener {

    private EditText editText_UserName;
    private EditText editText_Password;
    private Button btnRegister;
    private IRegistrationPresenter registrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        editText_UserName = (EditText) this.findViewById(R.id.edit_username);
        editText_Password = (EditText) this.findViewById(R.id.edit_password);
        btnRegister = (Button) this.findViewById(R.id.button_register);

        btnRegister.setOnClickListener(this);
        registrationPresenter = new RegistrationPresenter(this);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        registrationPresenter=null;

    }

    @Override
    public void onClick(View view) {

        registrationPresenter.doRegistration(editText_UserName.getText().toString(), editText_Password.getText().toString());
    }

    @Override
    public void onRegistrationResult(Boolean result, int code) throws Exception {

        if (result)
        {
            Preferences preferences=new Preferences(this);
            preferences.storeUserCredentials(editText_UserName.getText().toString(), editText_Password.getText().toString());
            startActivity(new Intent(this, LoginScreen.class));
            this.finish();
        }
        else
            Toast.makeText(this,"Invalid entry",Toast.LENGTH_SHORT).show();
    }


}
