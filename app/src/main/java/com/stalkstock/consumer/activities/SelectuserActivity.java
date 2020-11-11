package com.stalkstock.consumer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.stalkstock.R;
import com.stalkstock.advertiser.activities.LoginActivity;
import com.stalkstock.commercial.view.activities.MainCommercialActivity;
import com.stalkstock.utils.others.AppController;
import com.stalkstock.vender.ui.LoginScreen;


public class SelectuserActivity extends AppCompatActivity {
    Button user,btn_advertiser, commercial,vendor,driver;
    SelectuserActivity context;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectuser);

        context = this;
        user= findViewById(R.id.user);
       // back= findViewById(R.id.back);
        btn_advertiser= findViewById(R.id.btn_advertiser);
        commercial= findViewById(R.id.commercial);
        vendor= findViewById(R.id.vendor);
        driver= findViewById(R.id.driver);



        btn_advertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store value in preference (select user)
                AppController.getInstance().setString("usertype","1");
                Intent intent=new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setString("usertype","2");
                Intent intent=new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });



        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store value in preference (select user)
                AppController.getInstance().setString("usertype","3");
                Intent intent=new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });


        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setString("usertype","4");
                Intent intent=new Intent(context, LoginScreen.class);
                startActivity(intent);
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setString("usertype","5");
                Intent intent=new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });*/
    }
}
