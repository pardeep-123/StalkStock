package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.stalkstock.R;

import java.util.Objects;

public class ManagmentPaymentActivity extends AppCompatActivity {
    ManagmentPaymentActivity context;
    LinearLayout one,two,layout_delete,layout_delete1;
    ImageView item_address_rb,item_address_rb1;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment_payment);
        context=this;
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        item_address_rb=findViewById(R.id.item_address_rb);
        item_address_rb1=findViewById(R.id.item_address_rb1);
        btn_add=findViewById(R.id.btn_add);

        layout_delete=findViewById(R.id.layout_delete);
        layout_delete1=findViewById(R.id.layout_delete1);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_address_rb.setImageResource(R.drawable.radio_fill);
                item_address_rb1.setImageResource(R.drawable.radio_circle);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_address_rb1.setImageResource(R.drawable.radio_fill);
                item_address_rb.setImageResource(R.drawable.radio_circle);
            }
        });

        layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartInfoApp();
            }
        });
        layout_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartInfoApp();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PaymentselectActivity.class);
                startActivity(intent);
            }
        });

    }

    public void openStartInfoApp() {
        final Dialog dialogSuccessful = new Dialog(Objects.requireNonNull(context), R.style.Theme_Dialog);
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccessful.setContentView(R.layout.delete_successfully_alert);
        dialogSuccessful.setCancelable(false);
        Objects.requireNonNull(dialogSuccessful.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSuccessful.setCanceledOnTouchOutside(false);
        dialogSuccessful.getWindow().setGravity(Gravity.CENTER);

        Button btn_yes = dialogSuccessful.findViewById(R.id.btn_yes);
        Button btn_no = dialogSuccessful.findViewById(R.id.btn_no);


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
                finish();
            }
        }); btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
                finish();
            }
        });

        dialogSuccessful.show();
    }
}
