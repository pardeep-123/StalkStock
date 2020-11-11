package com.stalkstock.vender.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.stalkstock.R;

public class BidDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_detail);
        ImageView imageView = findViewById(R.id.biddetails_backarrow);
        TextView textView=findViewById(R.id.newchat);
        final Button button = findViewById(R.id.placebid_button);
        final RelativeLayout relativeLayout=findViewById(R.id.bidamt);
        final LinearLayout linearLayout = findViewById(R.id.biddisc);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BidDetail.this, ChatBox.class));


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater= LayoutInflater.from(BidDetail.this);
                View v= inflater.inflate(R.layout.biddetailsalertbox,null);

                final AlertDialog deleteDialog = new AlertDialog.Builder(BidDetail.this).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteDialog.setView(v);
                Button btncontinue= v.findViewById(R.id.submitbutton);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        button.setTag(1);
                        button.setText("Place Bid");
                        if(button.getText().toString().equals("Place Bid")){
                            button.setText("Edit Bid");





//openNewActivity();
                            v.setTag(1); //pause
                        }else {
                            final int status =(Integer) v.getTag();
                            if(status == 1) {
                                //  nextbutton.setText("Checkout");
                                //btn_add.setVisibility(View.VISIBLE);




                                v.setTag(0); //pause
                            } else {
                                button.setText("Edit Bid");

//                         Intent intent = new Intent(SelectPaymentMethod.this, AddNewCard.class);
//                         startActivity(intent);




//openNewActivity();
                                v.setTag(1); //pause
                            }
                        }

//


                        deleteDialog.dismiss();

                    }
                });


                deleteDialog.show();




    }
        });
    }
}