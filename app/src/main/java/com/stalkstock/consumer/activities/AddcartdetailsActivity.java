package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.stalkstock.R;
import com.stalkstock.consumer.adapter.ProductsdetailsAdapter;

import java.util.Objects;

public class AddcartdetailsActivity extends AppCompatActivity {
    AddcartdetailsActivity context;
TextView minus,plus,count;
RecyclerView productdetails_recycle;
    ProductsdetailsAdapter adapter;
    RelativeLayout all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcartdetails);
        context=this;
        minus=findViewById(R.id.minus);
        plus=findViewById(R.id.plus);
        count=findViewById(R.id.count);
        all=findViewById(R.id.all);
        productdetails_recycle=findViewById(R.id.productdetails_recycle);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inc = Integer.parseInt(count.getText().toString()) + 1;
                count.setText(String.valueOf(inc));
            }
        });minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!count.getText().toString().equals("1")) {
                    int decrease = Integer.parseInt(count.getText().toString()) - 1;
                    count.setText(String.valueOf(decrease));
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartInfoApp();
            }
        });

        adapter = new ProductsdetailsAdapter(context);
        productdetails_recycle.setLayoutManager(new LinearLayoutManager(context));
        productdetails_recycle.setAdapter(adapter);

    }

    public void openStartInfoApp() {
        final Dialog dialogSuccessful = new Dialog(Objects.requireNonNull(context), R.style.Theme_Dialog);
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccessful.setContentView(R.layout.delivery_successfully_alert);
        dialogSuccessful.setCancelable(false);
        Objects.requireNonNull(dialogSuccessful.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSuccessful.setCanceledOnTouchOutside(false);
        dialogSuccessful.getWindow().setGravity(Gravity.CENTER);

        Button btn_ok = dialogSuccessful.findViewById(R.id.btn_ok);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
                Intent intent=new Intent(context, MainConsumerActivity.class);
                intent.putExtra("is_open","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogSuccessful.show();
    }
}
