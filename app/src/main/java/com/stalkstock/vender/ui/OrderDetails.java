package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stalkstock.R;

public class OrderDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent intent = getIntent();
        String text = intent.getStringExtra("ke");
        TextView settext=findViewById(R.id.text_details);
        settext.setText(text);

        Intent intent1 = getIntent();
        String text1=intent1.getStringExtra("value");
        TextView settext2=findViewById(R.id.text_detailes2);
        settext2.setText(text1);

        Intent intent2 = getIntent();
        String text2=intent2.getStringExtra("val");
        TextView settext3=findViewById(R.id.text_detailes3);
        settext3.setText(text2);


        Intent intent3 = getIntent();
        String text4=intent3.getStringExtra("valu");
        TextView settext4=findViewById(R.id.text_detailes4);
        settext4.setText(text4);
        ImageView backarrow=findViewById(R.id.order_details_backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}