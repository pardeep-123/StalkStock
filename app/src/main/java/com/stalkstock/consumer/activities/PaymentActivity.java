package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stalkstock.R;


public class PaymentActivity extends AppCompatActivity {
    LinearLayout firstclick,secondclick;
    ImageView oneone,onetwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        firstclick=findViewById(R.id.firstclick);
        secondclick=findViewById(R.id.secondclick);
        oneone=findViewById(R.id.oneone);
        onetwo=findViewById(R.id.onetwo);

        firstclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneone.setImageResource(R.drawable.radio_fill);
                onetwo.setImageResource(R.drawable.radio_circle);

            }
        }); secondclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onetwo.setImageResource(R.drawable.radio_fill);
                oneone.setImageResource(R.drawable.radio_circle);

            }
        });
    }
}
