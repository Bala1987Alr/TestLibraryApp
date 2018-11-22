package com.bala.mylibrary.mylibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bala.mylibrary.R;
import com.bala.mylibrary.mylibrary.Security.Preferences;
import com.bala.mylibrary.mylibrary.presenter.ILoginPresenter;
import com.bala.mylibrary.mylibrary.presenter.LoginPresenter;
import com.bala.mylibrary.mylibrary.view.ILoginView;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private SharedPreferences sharedPreferences;
    private Preferences preferences;
    private byte[] userByte,passByte;
    private EditText editText_UserName;
    private EditText editText_Password;
    private Button btnRegister;
    private AlertDialog alertDialog;
    private AlertDialog.Builder aleBuilder;
    private ILoginPresenter iLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        iLoginPresenter=new LoginPresenter(this);


        editText_UserName = (EditText) this.findViewById(R.id.edit_username);
        editText_Password = (EditText) this.findViewById(R.id.edit_password);
        btnRegister = (Button) this.findViewById(R.id.button_login);

        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        iLoginPresenter.doLogin(editText_UserName.getText().toString(),editText_Password.getText().toString());

    }

    @Override
    public void onLoginResult(Boolean result, int code) throws Exception {

        preferences=new Preferences(this);
        sharedPreferences=getSharedPreferences(Preferences.SHARED_PREFENCE_NAME,Context.MODE_PRIVATE);
        String userName=sharedPreferences.getString(Preferences.USER_NAME,null);
        String password=sharedPreferences.getString(Preferences.USER_PASSWORD,null);
        byte[] decoded_username=Base64.decode(userName,Base64.DEFAULT);
        byte[] decoded_password=Base64.decode(password,Base64.DEFAULT);
        try
        {
            userByte=preferences.decrypt(this, decoded_username);
            passByte=preferences.decrypt(this, decoded_password);
            System.out.println("User Name: : "+ new String(userByte));
            System.out.println("Password: : "+ new String(userByte));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (new String(userByte).equals(editText_UserName.getText().toString()) && new String(passByte).equals(editText_Password.getText().toString()))
        {
            SharedPreferences preferences=getSharedPreferences(Preferences.SHARED_PREFENCE_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean(Preferences.IS_LOGIN, true);
            editor.commit();
            startActivity(new Intent(this,HomeScreen.class));
            this.finish();
        }
        else
        {
            aleBuilder=new AlertDialog.Builder(this).
                    setCancelable(false)
                    .setMessage("Invalid username or password")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog=aleBuilder.create();
            alertDialog.show();

        }
    }
}
