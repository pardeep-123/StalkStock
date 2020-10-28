package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stalkstock.R;


public class AddcardpaymentActivity extends AppCompatActivity {
    AddcardpaymentActivity context;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcardpayment);
        context=this;
        btn_save=findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PaymentselectActivity.class);
                startActivity(intent);
            }
        });
    }
}
