package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stalkstock.R;
import com.stalkstock.consumer.activities.SelectuserActivity;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ImageView back=findViewById(R.id.back);
        TextView forgot=findViewById(R.id.loginforgotpassword);
        Button sign = findViewById(R.id.loginbutton);
        TextView signup= findViewById(R.id.loginsignuptext);

        ImageView iv_fb= findViewById(R.id.iv_fb);
        ImageView iv_gmail= findViewById(R.id.iv_gmail);
        ImageView iv_twitter= findViewById(R.id.iv_twitter);
        sign.setOnClickListener(this);
        forgot.setOnClickListener(this);
        signup.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, SelectuserActivity.class));
                finishAffinity();
            }
        });

        iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, BottomnavigationScreen.class));
                finishAffinity();
            }
        });

        iv_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, BottomnavigationScreen.class));
                finishAffinity();
            }
        });

        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, BottomnavigationScreen.class));
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        startActivity(new Intent(LoginScreen.this, SelectuserActivity.class));
        finishAffinity();
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
                startActivity(new Intent(LoginScreen.this, SignUpVendor.class));
                break;

        }


    }
}