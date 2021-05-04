package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stalkstock.R;
import com.stalkstock.driver.fragment.PaymentFragment;
import com.stalkstock.vender.fragment.AccountFragment;
import com.stalkstock.vender.fragment.MainHomeFragment;
import com.stalkstock.vender.fragment.MessageFragment;
import com.stalkstock.vender.fragment.OrdersFragment;

public class BottomnavigationScreen extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {

    RelativeLayout home_tab;
     ImageView iv_homeTab;

    RelativeLayout order_tab;
     ImageView iv_orderTab;

    RelativeLayout message_tab;
    ImageView iv_messageTab;

      RelativeLayout bid_tab;
    ImageView iv_bidTab;

     RelativeLayout payment_tab;
    ImageView iv_paymentTab;

    RelativeLayout account_tab;
     ImageView iv_accountTab;

    TextView tv_home,tv_order,tv_cart,tv_payment,tv_account,tv_bid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation_screen);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomnavigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        tv_home=findViewById(R.id.tv_home);
         tv_order=findViewById(R.id.tv_order);
        tv_cart=findViewById(R.id.tv_cart);
        tv_payment=findViewById(R.id.tv_payment);
        tv_account=findViewById(R.id.tv_account);
        bid_tab=findViewById(R.id.bid_tab);

        tv_home.setText("HOME");
        tv_order.setText("ORDERS");
        tv_cart.setText("MESSAGES");
        tv_account.setText("ACCOUNT");


      /*  loadFragment(new MainHomeFragment());

//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottomnavigation);
//        bottomNavigationView.setItemIconTintList(null);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);




        try {

            try {
                if(getIntent().getStringExtra("type").equals("my"))
                {
                    Fragment fragment = null;
                    fragment = new AccountFragment();
                 //   loadFragment( fragment);
                    bottomNavigationView.setSelectedItemId(R.id.usericon);
                }
            }catch (Exception e)
            {
                Fragment fragment = null;
                fragment = new AccountFragment();
               // loadFragment( fragment);
            }
            // change to whichever id should be default
        }catch (Exception e)
        {
        }

        try {

            try {
                if(getIntent().getStringExtra("data").equals("m"))
                {
                    Fragment fragment = null;
                    fragment = new MessageFragment();
                 //   loadFragment( fragment);
                    bottomNavigationView.setSelectedItemId(R.id.action_chat);
                }
            }catch (Exception e)
            {
                Fragment fragment = null;
                fragment = new MessageFragment();
                //loadFragment( fragment);
            }
            // change to whichever id should be default
        }catch (Exception e)
        {
        }*/


//        if (savedInstanceState == null) {
//            Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//
//            if (f instanceof MainHomeFragment) {
//               // switchFragment(R.id.rl_content_frame, new MainHomeFragment());
//                loadFragment(new MainHomeFragment());
//            } else {
//            }
//
//        }
        loadFragment(new MainHomeFragment());
        try {
            home_tab=findViewById(R.id.home_tab);
            iv_homeTab=findViewById(R.id.iv_homeTab);

            order_tab=findViewById(R.id.order_tab);
            iv_orderTab=findViewById(R.id.iv_orderTab);

            message_tab=findViewById(R.id.message_tab);
            iv_messageTab=findViewById(R.id.iv_messageTab);

            payment_tab=findViewById(R.id.payment_tab);
            iv_paymentTab=findViewById(R.id.iv_paymentTab);

            account_tab=findViewById(R.id.account_tab);
            iv_accountTab=findViewById(R.id.iv_accountTab);

            iv_bidTab=findViewById(R.id.iv_bidTab);
            tv_bid=findViewById(R.id.tv_bid);


            home_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_homeTab.setImageResource(R.drawable.home_green_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                    //iv_messageTab.setImageResource(R.drawable.chat_grey_icon1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                    textColorChange(tv_home,tv_order,tv_bid,tv_payment,tv_account);

//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof MainHomeFragment) {
//                        switchFragment(R.id.rl_content_frame, new MainHomeFragment());
//                    } else {
//                    }

                    loadFragment(new MainHomeFragment());

                }
            });
            order_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_green_icon1);
                 //   iv_messageTab.setImageResource(R.drawable.chat_grey_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);
//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof OrdersFragment) {
//                        switchFragment(R.id.rl_content_frame, new OrdersFragment());
//                    } else {
//                    }
                    textColorChange(tv_order,tv_home,tv_bid,tv_payment,tv_account);

                    loadFragment(new OrdersFragment());

                }
            });
          /*  message_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                    iv_messageTab.setImageResource(R.drawable.chat_green_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                    textColorChange(tv_bid,tv_order,tv_home,tv_payment,tv_account);

//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof MessageFragment) {
//                        switchFragment(R.id.rl_content_frame, new MessageFragment());
//                    } else {
//                    }

                    loadFragment(new MessageFragment());

                }
            });*/

            bid_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                //    iv_messageTab.setImageResource(R.drawable.chat_grey_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_green);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                    textColorChange(tv_bid,tv_order,tv_home,tv_payment,tv_account);

//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof MessageFragment) {
//                        switchFragment(R.id.rl_content_frame, new MessageFragment());
//                    } else {
//                    }

                    loadFragment(new BidFragment());

                }
            });

            payment_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                //    iv_messageTab.setImageResource(R.drawable.chat_grey_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a13doller1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                    textColorChange(tv_payment,tv_bid,tv_order,tv_home,tv_account);

//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof MessageFragment) {
//                        switchFragment(R.id.rl_content_frame, new MessageFragment());
//                    } else {
//                    }

                    loadFragment(new PaymentFragment());

                }
            });
            account_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                 //   iv_messageTab.setImageResource(R.drawable.chat_grey_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_accountTab.setImageResource(R.drawable.user_green_icon1);

//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof AccountFragment) {
//                        switchFragment(R.id.rl_content_frame, new AccountFragment());
//                    } else {
//                    }
                    textColorChange(tv_account,tv_bid,tv_order,tv_payment,tv_home);

                    loadFragment(new AccountFragment());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            try {
                if(getIntent().getStringExtra("type").equals("my"))
                {
//                    Fragment fragment = null;
//                    fragment = new AccountFragment();
//                    //   loadFragment( fragment);
//                    bottomNavigationView.setSelectedItemId(R.id.usericon);

                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                  //  iv_messageTab.setImageResource(R.drawable.chat_grey_icon1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_accountTab.setImageResource(R.drawable.user_green_icon1);

//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof AccountFragment) {
//                        switchFragment(R.id.rl_content_frame, new AccountFragment());
//                    } else {
//                    }
                    textColorChange(tv_account,tv_bid,tv_order,tv_payment,tv_home);

                    loadFragment(new AccountFragment());
                }
            }catch (Exception e)
            {
//                Fragment fragment = null;
//                fragment = new AccountFragment();
                // loadFragment( fragment);
            }
            // change to whichever id should be default
        }catch (Exception e)
        {
        }

        try {

            try {
                if(getIntent().getStringExtra("data").equals("m"))
                {
//                    Fragment fragment = null;
//                    fragment = new MessageFragment();
//                    //   loadFragment( fragment);
//                    bottomNavigationView.setSelectedItemId(R.id.action_chat);
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                 //   iv_messageTab.setImageResource(R.drawable.chat_green_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);


//                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.rl_content_frame);
//                    if (f instanceof MessageFragment) {
//                        switchFragment(R.id.rl_content_frame, new MessageFragment());
//                    } else {
//                    }
                    textColorChange(tv_bid,tv_order,tv_home,tv_payment,tv_account);

                    loadFragment(new MessageFragment());


                }
            }catch (Exception e)
            {
//                Fragment fragment = null;
//                fragment = new MessageFragment();
                //loadFragment( fragment);
            }
            // change to whichever id should be default
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    protected void switchFragment(Integer main_frame, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(main_frame, fragment);
        fragmentTransaction.commit();
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;

    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = new MainHomeFragment();
                break;
            case R.id.listicon:
                fragment = new OrdersFragment();
              //  Intent intent2= new Intent(BottomnavigationScreen.this, Orders.class);
              //  startActivity(intent2);

                break;

            case R.id.action_chat:
                fragment = new MessageFragment();
                break;

            case R.id.usericon:
                fragment = new AccountFragment();
                break;
        }

        return loadFragment(fragment);
    }

    public  void textColorChange(TextView tv1,TextView tv3,TextView tv4,TextView tv5,TextView tv6){

        tv1.setTextColor(getResources().getColor(R.color.green));
        tv3.setTextColor(getResources().getColor(R.color.colorIcon));
        tv4.setTextColor(getResources().getColor(R.color.colorIcon));
        tv5.setTextColor(getResources().getColor(R.color.colorIcon));
        tv6.setTextColor(getResources().getColor(R.color.colorIcon));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    }

