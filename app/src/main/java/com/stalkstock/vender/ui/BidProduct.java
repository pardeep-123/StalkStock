package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.stalkstock.R;
import com.stalkstock.vender.adapter.AccpetAdapter;
import com.stalkstock.vender.adapter.RequestAdapter;

public class BidProduct extends AppCompatActivity {
    Context mContext;
    RequestAdapter requestAdapter;
    AccpetAdapter accpetAdapter;
    RecyclerView bidrecyclerview1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_product);
        bidrecyclerview1 = findViewById(R.id.bidrecyclerview);
        mContext = BidProduct.this;
        requestAdapter = new RequestAdapter(mContext);
        bidrecyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
        bidrecyclerview1.setAdapter(requestAdapter);
        ImageView backarrow = findViewById(R.id.bidproductbackarrow);
        final Button btnRequest = findViewById(R.id.btnrequest);
        final Button btnAccpet = findViewById(R.id.btnaccpet);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRequest.setBackground(getResources().getDrawable(R.drawable.current_button));
                btnAccpet.setBackground(getResources().getDrawable(R.drawable.past_button));
                btnRequest.setTextColor(getResources().getColor(R.color.white));
                btnAccpet.setTextColor(getResources().getColor(R.color.balck));
                requestAdapter = new RequestAdapter(mContext);
                bidrecyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
                bidrecyclerview1.setAdapter(requestAdapter);

            }
        });
        btnAccpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAccpet.setBackground(getResources().getDrawable(R.drawable.current_button));
                btnRequest.setBackground(getResources().getDrawable(R.drawable.past_button));
                btnAccpet.setTextColor(getResources().getColor(R.color.white));
                btnRequest.setTextColor(getResources().getColor(R.color.balck));
                accpetAdapter = new AccpetAdapter(mContext);
                bidrecyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
                bidrecyclerview1.setAdapter(accpetAdapter);
            }
        });


    }
}