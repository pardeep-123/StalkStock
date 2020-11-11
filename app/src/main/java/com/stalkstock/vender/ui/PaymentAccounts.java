package com.stalkstock.vender.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.stalkstock.R;

public class PaymentAccounts extends AppCompatActivity {
    ImageView radio_fillone, radio_emptyone,radio_emptytwo,mastercardelete;
    RelativeLayout rl1,rl2,rl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_accounts);
        ImageView imageView = findViewById(R.id.paymentsbackarrow);
        mastercardelete=findViewById(R.id.mastercarddelete);

        rl1=findViewById(R.id.rlone);
        rl2=findViewById(R.id.rltwo);
        rl3=findViewById(R.id.rlthree);

        radio_fillone=findViewById(R.id.radio_fillone);
        radio_emptyone=findViewById(R.id.radio_emptyone);
        radio_emptytwo=findViewById(R.id.radio_emptytwo);
        Button button =findViewById(R.id.paymentsaccbutton);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_fillone.setImageResource(R.drawable.radio_fill);
                radio_emptyone.setImageResource(R.drawable.radio_circle);


            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_emptyone.setImageResource(R.drawable.radio_fill);
                radio_fillone.setImageResource(R.drawable.radio_circle);


            }
        });
rl3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        radio_emptytwo.setImageResource(R.drawable.radio_fill);
        radio_fillone.setImageResource(R.drawable.radio_circle);


    }
});
mastercardelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        LayoutInflater inflater= LayoutInflater.from(PaymentAccounts.this);
        View v= inflater.inflate(R.layout.deletecardalert,null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(PaymentAccounts.this).create();
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.setView(v);
        Button btncontinue= v.findViewById(R.id.yesbtn);
        Button btnno=v.findViewById(R.id.nobtn);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
               // startActivity(new Intent(.this, LoginScreen.class));



                deleteDialog.dismiss();

            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();

    }
});


button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(PaymentAccounts.this, AddBankAccount.class));


    }
});

    }
}