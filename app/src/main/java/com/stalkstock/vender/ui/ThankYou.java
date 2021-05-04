package com.stalkstock.vender.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.stalkstock.R;

public class ThankYou extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        ImageView backarrow=findViewById(R.id.congo_backArrow);
        Button vieworder= findViewById(R.id.vieworder);

        backarrow.setOnClickListener(this);
        vieworder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.congo_backArrow:
                onBackPressed();
                break;
            case R.id.vieworder:
               /* Intent intent = new Intent(this, Verification.class);
            //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             //   intent.putExtra("t","m");
                intent.putExtra("type","my");

                startActivity(intent);*/

                Intent intent = new Intent(this, BottomnavigationScreen.class);
                startActivity(intent);
                finishAffinity();

                break;


        }
    }
}