package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.stalkstock.R;
import com.stalkstock.consumer.adapter.MyordersAdapter;

public class MyOrderActivity extends AppCompatActivity {

    View view;
    ImageView ivBackButton;
    MyordersAdapter adapter;
    RecyclerView myorder_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        myorder_recycle=findViewById(R.id.myorder_recycle);
        ivBackButton = findViewById(R.id.ivBackButton);
       /* ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
onBackPressed();            }
        });*/


        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        adapter = new MyordersAdapter(this);
        myorder_recycle.setLayoutManager(new LinearLayoutManager(this));
        myorder_recycle.setAdapter(adapter) ;
    }
}