package com.stalkstock.vender.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stalkstock.R;
import com.stalkstock.driver.fragment.PaymentFragment;
import com.stalkstock.utils.BaseActivity;
import com.stalkstock.vender.fragment.AccountFragment;
import com.stalkstock.vender.fragment.MainHomeFragment;
import com.stalkstock.vender.fragment.MessageFragment;
import com.stalkstock.vender.fragment.OrdersFragment;

public class BottomnavigationScreen extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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


   public MutableLiveData<String> muteClick =new MutableLiveData<String>("");
    LiveData<String> liveClick = muteClick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
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


            home_tab.setOnClickListener(view -> {
                iv_homeTab.setImageResource(R.drawable.home_green_icon1);
                iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                textColorChange(tv_home,tv_order,tv_bid,tv_payment,tv_account);

                loadFragment(new MainHomeFragment());

            });
            order_tab.setOnClickListener(view -> {
                iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                iv_orderTab.setImageResource(R.drawable.list_green_icon1);
                iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                textColorChange(tv_order,tv_home,tv_bid,tv_payment,tv_account);

                loadFragment(new OrdersFragment());

            });


            bid_tab.setOnClickListener(view -> {
               bidClick();

            });

            payment_tab.setOnClickListener(view -> {
                iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                iv_paymentTab.setImageResource(R.drawable.a13doller1);
                iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                iv_accountTab.setImageResource(R.drawable.user_black_icon1);

                textColorChange(tv_payment,tv_bid,tv_order,tv_home,tv_account);


                loadFragment(new PaymentFragment());

            });
            account_tab.setOnClickListener(view -> {
                iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                iv_accountTab.setImageResource(R.drawable.user_green_icon1);
                textColorChange(tv_account,tv_bid,tv_order,tv_payment,tv_home);

                loadFragment(new AccountFragment());

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            try {
                if(getIntent().getStringExtra("type").equals("my"))
                {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_accountTab.setImageResource(R.drawable.user_green_icon1);


                    textColorChange(tv_account,tv_bid,tv_order,tv_payment,tv_home);

                    loadFragment(new AccountFragment());
                }
            }catch (Exception e)
            {

            }
        }catch (Exception e)
        {
        }

        try {

            try {
                if(getIntent().getStringExtra("data").equals("m"))
                {
                    iv_homeTab.setImageResource(R.drawable.home_black_icon1);
                    iv_orderTab.setImageResource(R.drawable.list_black_icon1);
                    iv_paymentTab.setImageResource(R.drawable.a14dooler1);
                    iv_bidTab.setImageResource(R.drawable.ic_bid_gray);
                    iv_accountTab.setImageResource(R.drawable.user_black_icon1);
                    textColorChange(tv_bid,tv_order,tv_home,tv_payment,tv_account);

                    loadFragment(new MessageFragment());


                }
            }catch (Exception e)
            {

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        liveClick.observe(this, s -> {
            if(s.equals("payments"))
            {
                payment_tab.performClick();
            }
            if(s.equals("bids"))
            {
                bid_tab.performClick();
            }
        });

    }

    private void bidClick() {
        iv_homeTab.setImageResource(R.drawable.home_black_icon1);
        iv_orderTab.setImageResource(R.drawable.list_black_icon1);
        iv_paymentTab.setImageResource(R.drawable.a14dooler1);
        iv_bidTab.setImageResource(R.drawable.ic_bid_green);
        iv_accountTab.setImageResource(R.drawable.user_black_icon1);

        textColorChange(tv_bid,tv_order,tv_home,tv_payment,tv_account);
        loadFragment(new BidFragment());
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

    @Override
    protected int getContentId() {
        return R.layout.activity_bottomnavigation_screen;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent().getBooleanExtra("notificationClick",false)){
            bidClick();
        }
    }
}

