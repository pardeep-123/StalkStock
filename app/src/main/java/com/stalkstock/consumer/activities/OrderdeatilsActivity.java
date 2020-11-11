package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stalkstock.R;
import com.stalkstock.advertiser.activities.ManagePaymentsActivity;
import com.stalkstock.advertiser.activities.PaymentActivity;


public class OrderdeatilsActivity extends AppCompatActivity {

    TextView tvPending;
    ImageView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdeatils);

        tvPending= findViewById(R.id.tvPending);
        arrowBack= findViewById(R.id.arrowBack);

        tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ManagePaymentsActivity.class);
                startActivity(intent);
            }
        });


        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}
