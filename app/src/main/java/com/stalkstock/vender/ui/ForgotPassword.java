package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.stalkstock.R;
import com.stalkstock.advertiser.activities.LoginActivity;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
        ImageView backarrow=findViewById(R.id.forgot_backarrow);
        Button forgotbtn=findViewById(R.id.forgotbutton);

        forgotbtn.setOnClickListener(this);
        backarrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){

            case R.id.forgot_backarrow:
                onBackPressed();
                break;


            case R.id.forgotbutton:
                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                break;


        }

    }
}