package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stalkstock.R;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        TextView forgot=findViewById(R.id.loginforgotpassword);
        Button sign = findViewById(R.id.loginbutton);
        TextView signup= findViewById(R.id.loginsignuptext);
        sign.setOnClickListener(this);
        forgot.setOnClickListener(this);
        signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){

            case R.id.loginforgotpassword:
                startActivity(new Intent(LoginScreen.this, ForgotPassword.class));
                break;


            case R.id.loginbutton:
                startActivity(new Intent(LoginScreen.this, BottomnavigationScreen.class));
                finishAffinity();
                break;
            case R.id.loginsignuptext:
                startActivity(new Intent(LoginScreen.this, SignUp.class));
                break;

        }


    }
}