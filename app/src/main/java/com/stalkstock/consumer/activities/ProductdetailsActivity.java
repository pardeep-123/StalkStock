package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stalkstock.R;
import com.stalkstock.advertiser.activities.Notification_firstActivity;
import com.stalkstock.consumer.adapter.ProductsdetailsAdapter;

import stalkstockcommercial.ui.view.activities.FilterActivity;


public class ProductdetailsActivity extends AppCompatActivity {
    ProductdetailsActivity context;
    ProductsdetailsAdapter adapter;
    RecyclerView productdetails_recycle;
    ImageView back,notification,search,fillter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        context=this;
        productdetails_recycle=findViewById(R.id.productdetails_recycle);
        back=findViewById(R.id.back);
        notification = findViewById(R.id.notification);
        search = findViewById(R.id.search);
        fillter = findViewById(R.id.fillter);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Notification_firstActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
        fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FilterActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new ProductsdetailsAdapter(context);
        productdetails_recycle.setLayoutManager(new LinearLayoutManager(context));
        productdetails_recycle.setAdapter(adapter);
    }
}
