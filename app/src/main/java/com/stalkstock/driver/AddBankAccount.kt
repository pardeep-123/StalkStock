package com.stalkstock.driver;

import android.graphics.Color
import android.os.Build
import android.os.Bundle;
import android.view.View

import androidx.appcompat.app.AppCompatActivity;

import com.stalkstock.R;
import kotlinx.android.synthetic.main.activity_add_bank_account.*
import kotlinx.android.synthetic.main.activity_add_bank_account2.*

public class AddBankAccount : AppCompatActivity() {


     override fun  onCreate( savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
             getWindow().setStatusBarColor(Color.WHITE);
         }
        setContentView(R.layout.activity_add_bank_account2);
         iv_back.setOnClickListener {
             finish()
         }
         btn_Save.setOnClickListener {
             finish()
         }
    }
}
