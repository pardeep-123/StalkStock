package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stalkstock.MyApplication;
import com.stalkstock.R;

public class OrderDetails extends AppCompatActivity {

    TextView tv_deli_by,tv_deli_to,tv_deli_by_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        tv_deli_by=findViewById(R.id.tv_deli_by);
        tv_deli_to=findViewById(R.id.tv_deli_to);
        tv_deli_by_value=findViewById(R.id.tv_deli_by_value);


        Intent intent = getIntent();
        String text = intent.getStringExtra("ke");
        TextView settext=findViewById(R.id.text_details);
        if (intent.getStringExtra("ke")!=null){
            settext.setText(text);
            settext.setVisibility(View.VISIBLE);
        }

        Intent intent1 = getIntent();
        String text1=intent1.getStringExtra("value");
        TextView settext2=findViewById(R.id.text_detailes2);

        if (intent1.getStringExtra("value")!=null){
            settext2.setText(text1);
            settext2.setVisibility(View.VISIBLE);


        }

        Intent intent2 = getIntent();
        String text2=intent2.getStringExtra("val");
        TextView settext3=findViewById(R.id.text_detailes3);


        if (intent2.getStringExtra("val")!=null){
            settext3.setText(text2);
            settext3.setVisibility(View.VISIBLE);

        }


        Intent intent3 = getIntent();
        String text4=intent3.getStringExtra("valu");
        TextView settext4=findViewById(R.id.text_detailes4);

        if (intent3.getStringExtra("valu")!=null){
            settext4.setText(text4);
            settext4.setVisibility(View.VISIBLE);

        }





        try {
            Intent intent5 = getIntent();
            String text5=intent5.getStringExtra("key");
            TextView settext5=findViewById(R.id.text_detailes4);

            if (intent5.getStringExtra("key")!=null){
                settext5.setText(text5);
                settext5.setTextColor(getResources().getColor(R.color.red_bid));
                settext5.setVisibility(View.VISIBLE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageView backarrow=findViewById(R.id.order_details_backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(MyApplication.instance.getString("usertype").equals("4")){

            if (getIntent().getStringExtra("show")!=null){

                tv_deli_by.setVisibility(View.VISIBLE);
                tv_deli_by_value.setVisibility(View.VISIBLE);
                tv_deli_by.setText("DELIVERED BY");
                tv_deli_to.setText("DELIVER TO");


            }else {
                tv_deli_by.setVisibility(View.GONE);
                tv_deli_by_value.setVisibility(View.GONE);
                tv_deli_to.setText("DELIVER TO");

            }

        }
    }
}