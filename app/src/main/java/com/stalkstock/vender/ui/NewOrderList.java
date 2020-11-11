package com.stalkstock.vender.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.vender.adapter.NewOrderAdapter;

public class NewOrderList extends AppCompatActivity {
    Context mcontext;
     NewOrderAdapter newOrderAdapter;
    RecyclerView orderrecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_list);
        ImageView backarrow= findViewById(R.id.orderlist_backarrow);
        Intent intent = getIntent();
        String text = intent.getStringExtra("key");
        TextView appbar=findViewById(R.id.appbar_name);

        appbar.setText(text);
        mcontext=NewOrderList.this;

        orderrecyclerview= findViewById(R.id.neworder_recyclerview);
        newOrderAdapter = new NewOrderAdapter(mcontext);
        orderrecyclerview.setLayoutManager(new LinearLayoutManager(mcontext));
        orderrecyclerview.setAdapter(newOrderAdapter) ;

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
}