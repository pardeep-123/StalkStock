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
import com.stalkstock.consumer.adapter.ProductsAdapter;


public class ProductActivity extends AppCompatActivity {
    ProductActivity context;
    RecyclerView product_recycle;
    ProductsAdapter adapter;
    ImageView back,notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        context=this;
        product_recycle=findViewById(R.id.product_recycle);
        back=findViewById(R.id.back);
        notification=findViewById(R.id.notification);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Notification_firstActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new ProductsAdapter(context);
        product_recycle.setLayoutManager(new LinearLayoutManager(context));
        product_recycle.setAdapter(adapter);
    }
}
