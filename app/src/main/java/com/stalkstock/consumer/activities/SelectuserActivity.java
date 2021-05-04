package com.stalkstock.consumer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stalkstock.R;
import com.stalkstock.advertiser.GetStartedPageActivity;
import com.stalkstock.advertiser.activities.LoginActivity;
import com.stalkstock.commercial.view.activities.MainCommercialActivity;
import com.stalkstock.utils.TermsConditionActivity;
import com.stalkstock.utils.others.AppController;
import com.stalkstock.vender.ui.LoginScreen;


public class SelectuserActivity extends AppCompatActivity {
    Button user,btn_advertiser, commercial,vendor,driver;
    SelectuserActivity context;

    TextView tv_l;


    int click=0;

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
        tv_l= findViewById(R.id.tv_l);

       // SpannableString formattedSpan = formatStyles(getString(R.string.get_started_text), getString(R.string.text_sub0), R.style.style0, getString(R.string.main_text_sub1), R.style.style1);
       // tv_l.setText(formattedSpan, TextView.BufferType.SPANNABLE);
        btn_advertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store value in preference (select user)
                AppController.getInstance().setString("usertype","1");
                Intent intent=new Intent(context, GetStartedPageActivity.class);
                startActivity(intent);
            }
        });

        commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setString("usertype","2");
                Intent intent=new Intent(context, GetStartedPageActivity.class);
                startActivity(intent);
            }
        });



        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store value in preference (select user)
                AppController.getInstance().setString("usertype","3");
                Intent intent=new Intent(context, GetStartedPageActivity.class);
                startActivity(intent);
            }
        });


        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setString("usertype","4");
                Intent intent=new Intent(context, GetStartedPageActivity.class);
                startActivity(intent);
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().setString("usertype","5");
                Intent intent=new Intent(context, GetStartedPageActivity.class);
                startActivity(intent);
            }
        });




      /*  btn_advertiser.setOnClickListener(new View.OnClickListener() {
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
*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        click=0;

    }

    private SpannableString formatStyles(String value, String sub0, int style0, String sub1, int style1)
    {
        String tag0 = "{0}";
        int startLocation0 = value.indexOf(tag0);
        value = value.replace(tag0, sub0);

        String tag1 = "{1}";
        int startLocation1 = value.indexOf(tag1);
        if (sub1 != null && !sub1.equals(""))
        {
            value = value.replace(tag1, sub1);
        }

        SpannableString styledText = new SpannableString(value);
        styledText.setSpan(new TextAppearanceSpan(this, style0), startLocation0, startLocation0 + sub0.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (sub1 != null && !sub1.equals(""))
        {
            styledText.setSpan(new TextAppearanceSpan(this, style1), startLocation1, startLocation1 + sub1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return styledText;
    }
}
