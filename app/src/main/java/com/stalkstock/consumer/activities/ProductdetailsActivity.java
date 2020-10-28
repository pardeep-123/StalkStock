package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stalkstock.R;
import com.stalkstock.consumer.adapter.ProductsdetailsAdapter;


public class ProductdetailsActivity extends AppCompatActivity {
    ProductdetailsActivity context;
    ProductsdetailsAdapter adapter;
    RecyclerView productdetails_recycle;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        context=this;
        productdetails_recycle=findViewById(R.id.productdetails_recycle);
        back=findViewById(R.id.back);
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
