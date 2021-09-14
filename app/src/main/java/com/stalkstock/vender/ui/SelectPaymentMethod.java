package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.stalkstock.R;

public class SelectPaymentMethod extends AppCompatActivity {
    Context mcontext;
    RelativeLayout rl_1, rl2;
    ImageView radio_empty, radio_fill;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);
        mcontext = this;
        ImageView backarrow = findViewById(R.id.spm_backarrow);

        final Button nextbutton = findViewById(R.id.spm_btnNext);
        btn_add = findViewById(R.id.btn_add);
        rl_1 = findViewById(R.id.rl_1);
        rl2 = findViewById(R.id.rl2);
        final LinearLayout visb = findViewById(R.id.spm_cardshow);
        final LinearLayout visb1 = findViewById(R.id.spm_cardpaypalshow);
        radio_empty = findViewById(R.id.radio_empty);
        radio_fill = findViewById(R.id.radio_fill);


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        rl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_fill.setImageResource(R.drawable.radio_fill);
                radio_empty.setImageResource(R.drawable.radio_circle);

            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_fill.setImageResource(R.drawable.radio_circle);
                radio_empty.setImageResource(R.drawable.radio_fill);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPaymentMethod.this, AddNewCard.class);
                startActivity(intent);
                visb.setVisibility(View.VISIBLE);
                // visb1.setVisibility(View.VISIBLE);


            }
        });

        nextbutton.setTag(1);
        nextbutton.setText("Next");


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextbutton.getText().toString().equals("Next")) {
                    nextbutton.setText("Checkout");
                    btn_add.setVisibility(View.VISIBLE);
                    radio_fill.setImageResource(R.drawable.radio_fill);
                    radio_empty.setImageResource(R.drawable.radio_circle);


//openNewActivity();
                    view.setTag(1); //pause
                } else {
                    final int status = (Integer) view.getTag();
                    if (status == 1) {
                        //  nextbutton.setText("Checkout");
                        //btn_add.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(SelectPaymentMethod.this, ThankYou.class);
                        startActivity(intent);


                        view.setTag(0); //pause
                    } else {
                        nextbutton.setText("Checkout");
                        btn_add.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(SelectPaymentMethod.this, ThankYou.class);
                        startActivity(intent);
//                         Intent intent = new Intent(SelectPaymentMethod.this, AddNewCard.class);
//                         startActivity(intent);


//openNewActivity();
                        view.setTag(1); //pause
                    }
                }
            }
        });
    }


}