package com.stalkstock.consumer.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stalkstock.R;
import com.stalkstock.consumer.fragment.CartFragment;
import com.stalkstock.consumer.fragment.HomeCounsumerFragment;
import com.stalkstock.consumer.fragment.ListFragment;
import com.stalkstock.consumer.fragment.ProfileConsumerFragment;
import com.stalkstock.consumer.fragment.SearchFragment;


public class MainConsumerActivity extends AppCompatActivity {

    MainConsumerActivity context;
    RelativeLayout home,list,cart,profile,search_home;
String type ="1";
ImageView home_img,list_img,cart_img,profile_img,search_img;

TextView tv_home,tv_search,tv_order,tv_cart,tv_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_consumer);
        context=this;
        home=findViewById(R.id.home);
        list=findViewById(R.id.list);
        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.profile);
        search_home=findViewById(R.id.search_home);

        home_img=findViewById(R.id.home_img);
        search_img=findViewById(R.id.search_img);
        list_img=findViewById(R.id.list_img);
        cart_img=findViewById(R.id.cart_img);
        profile_img=findViewById(R.id.profile_img);

        tv_home=findViewById(R.id.tv_home);
        tv_search=findViewById(R.id.tv_search);
        tv_order=findViewById(R.id.tv_order);
        tv_cart=findViewById(R.id.tv_cart);
        tv_account=findViewById(R.id.tv_account);

        if (savedInstanceState == null) {
           // backgroundChange(home,list,cart,profile);
            switchFragment(new HomeCounsumerFragment());
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_green_icon1);
                search_img.setImageResource(R.drawable.search_icon_new);
                list_img.setImageResource(R.drawable.list_black_icon1);
                cart_img.setImageResource(R.drawable.cart_black_icon1);
                profile_img.setImageResource(R.drawable.user_black_icon1);

                textColorChange(tv_home,tv_search,tv_order,tv_cart,tv_account);
                switchFragment(new HomeCounsumerFragment());
            }
        });


        search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon1);
                search_img.setImageResource(R.drawable.ic_search_green_new);
                list_img.setImageResource(R.drawable.list_black_icon1);
                cart_img.setImageResource(R.drawable.cart_black_icon1);
                profile_img.setImageResource(R.drawable.user_black_icon1);

                textColorChange(tv_search,tv_home,tv_order,tv_cart,tv_account);

                switchFragment(new SearchFragment());
            }
        });


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon1);
                search_img.setImageResource(R.drawable.search_icon_new);

                list_img.setImageResource(R.drawable.list_green_icon1);
                cart_img.setImageResource(R.drawable.cart_black_icon1);
                profile_img.setImageResource(R.drawable.user_black_icon1);
                textColorChange(tv_order,tv_home,tv_search,tv_cart,tv_account);

                switchFragment(new ListFragment());
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon1);
                search_img.setImageResource(R.drawable.search_icon_new);

                list_img.setImageResource(R.drawable.list_black_icon1);
                cart_img.setImageResource(R.drawable.cart_green_icon1);
                profile_img.setImageResource(R.drawable.user_black_icon1);

                textColorChange(tv_cart,tv_home,tv_search,tv_order,tv_account);

                switchFragment(new CartFragment());
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_img.setImageResource(R.drawable.home_black_icon1);
                search_img.setImageResource(R.drawable.search_icon_new);

                list_img.setImageResource(R.drawable.list_black_icon1);
                cart_img.setImageResource(R.drawable.cart_black_icon1);
                profile_img.setImageResource(R.drawable.user_green_icon1);
                textColorChange(tv_account,tv_home,tv_search,tv_order,tv_cart);

                switchFragment(new ProfileConsumerFragment());
            }
        });

        try {
            if(getIntent().getStringExtra("is_open")!=null){
             if (getIntent().getStringExtra("is_open").equals("1")){
               /*  home_img.setImageResource(R.drawable.home_black_icon);
                 list_img.setImageResource(R.drawable.list_black_icon);
                 cart_img.setImageResource(R.drawable.cart_green_icon);
                 profile_img.setImageResource(R.drawable.user_black_icon);*/

                 home_img.setImageResource(R.drawable.home_black_icon1);
                 search_img.setImageResource(R.drawable.search_icon_new);

                 list_img.setImageResource(R.drawable.list_black_icon1);
                 cart_img.setImageResource(R.drawable.cart_green_icon1);
                 profile_img.setImageResource(R.drawable.user_black_icon1);
                 textColorChange(tv_cart,tv_home,tv_search,tv_order,tv_account);

                 switchFragment(new CartFragment());
                // switchFragment(new CartFragment());
              }
             else if (getIntent().getStringExtra("is_open").equals("2")){
                 home_img.setImageResource(R.drawable.home_black_icon1);
                 search_img.setImageResource(R.drawable.search_icon_new);

                 list_img.setImageResource(R.drawable.list_green_icon1);
                 cart_img.setImageResource(R.drawable.cart_black_icon1);
                 profile_img.setImageResource(R.drawable.user_black_icon1);
                 textColorChange(tv_order,tv_home,tv_search,tv_cart,tv_account);

                 //  switchFragment(new ListFragment());
             }else if (getIntent().getStringExtra("is_open").equals("3")){

                 home_img.setImageResource(R.drawable.home_black_icon1);
                 search_img.setImageResource(R.drawable.search_icon_new);

                 list_img.setImageResource(R.drawable.list_green_icon1);
                 cart_img.setImageResource(R.drawable.cart_black_icon1);
                 profile_img.setImageResource(R.drawable.user_black_icon1);
                 textColorChange(tv_order,tv_home,tv_search,tv_cart,tv_account);

                 switchFragment(new ListFragment());
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

    public  void textColorChange(TextView tv1,TextView tv2,TextView tv3,TextView tv4,TextView tv5){

        tv1.setTextColor(getResources().getColor(R.color.green));
        tv2.setTextColor(getResources().getColor(R.color.colorIcon));
        tv3.setTextColor(getResources().getColor(R.color.colorIcon));
        tv4.setTextColor(getResources().getColor(R.color.colorIcon));
        tv5.setTextColor(getResources().getColor(R.color.colorIcon));

    }

    protected void switchFragment(Fragment fragment ) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
