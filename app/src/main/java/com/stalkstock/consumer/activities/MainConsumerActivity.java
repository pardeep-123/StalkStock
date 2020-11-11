package com.stalkstock.consumer.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stalkstock.R;
import com.stalkstock.consumer.fragment.CartFragment;
import com.stalkstock.consumer.fragment.HomeCounsumerFragment;
import com.stalkstock.consumer.fragment.ListFragment;
import com.stalkstock.consumer.fragment.ProfileConsumerFragment;


public class MainConsumerActivity extends AppCompatActivity {

    MainConsumerActivity context;
    RelativeLayout home,list,cart,profile;
String type ="1";
ImageView home_img,list_img,cart_img,profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_consumer);
        context=this;
        home=findViewById(R.id.home);
        list=findViewById(R.id.list);
        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.profile);

        home_img=findViewById(R.id.home_img);
        list_img=findViewById(R.id.list_img);
        cart_img=findViewById(R.id.cart_img);
        profile_img=findViewById(R.id.profile_img);

        if (savedInstanceState == null) {
           // backgroundChange(home,list,cart,profile);
            switchFragment(new HomeCounsumerFragment());
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_green_icon);
                list_img.setImageResource(R.drawable.list_black_icon);
                cart_img.setImageResource(R.drawable.cart_black_icon);
                profile_img.setImageResource(R.drawable.user_black_icon);
                switchFragment(new HomeCounsumerFragment());
            }
        }); list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon);
                list_img.setImageResource(R.drawable.list_green_icon);
                cart_img.setImageResource(R.drawable.cart_black_icon);
                profile_img.setImageResource(R.drawable.user_black_icon);
                switchFragment(new ListFragment());
            }
        }); cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon);
                list_img.setImageResource(R.drawable.list_black_icon);
                cart_img.setImageResource(R.drawable.cart_green_icon);
                profile_img.setImageResource(R.drawable.user_black_icon);
               switchFragment(new CartFragment());
            }
        });profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon);
                list_img.setImageResource(R.drawable.list_black_icon);
                cart_img.setImageResource(R.drawable.cart_black_icon);
                profile_img.setImageResource(R.drawable.user_green_icon);
               switchFragment(new ProfileConsumerFragment());
            }
        });

        try {
            if(getIntent().getStringExtra("is_open")!=null){
             if (getIntent().getStringExtra("is_open").equals("1")){
                 home_img.setImageResource(R.drawable.home_black_icon);
                 list_img.setImageResource(R.drawable.list_black_icon);
                 cart_img.setImageResource(R.drawable.cart_green_icon);
                 profile_img.setImageResource(R.drawable.user_black_icon);
                // switchFragment(new CartFragment());
              }
             else if (getIntent().getStringExtra("is_open").equals("2")){
                 home_img.setImageResource(R.drawable.home_black_icon);
                 list_img.setImageResource(R.drawable.list_green_icon);
                 cart_img.setImageResource(R.drawable.cart_black_icon);
                 profile_img.setImageResource(R.drawable.user_black_icon);
               //  switchFragment(new ListFragment());
             }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void backgroundChange(ImageView rl1, ImageView rl2, ImageView rl3 , ImageView rl4) {
//        rl1.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
////        rl2.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
////        rl3.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
////        rl4.setBackground(getResources().getDrawable(R.drawable.tab_background_for_profile));
//
//    }

    protected void switchFragment(Fragment fragment ) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
