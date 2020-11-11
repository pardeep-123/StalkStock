package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stalkstock.R;
import com.stalkstock.vender.fragment.AccountFragment;
import com.stalkstock.vender.fragment.MainHomeFragment;
import com.stalkstock.vender.fragment.MessageFragment;
import com.stalkstock.vender.fragment.OrdersFragment;

public class BottomnavigationScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation_screen);
        loadFragment(new MainHomeFragment());

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomnavigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
        }

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    }

