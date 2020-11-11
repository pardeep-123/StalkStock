package com.stalkstock.vender.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.stalkstock.R;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password2);
        ImageView backarrow=findViewById(R.id.changepasswordbackarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button btn=findViewById(R.id.change_updatebutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater= LayoutInflater.from(ChangePassword.this);
                View v= inflater.inflate(R.layout.changepasswordalertbox,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(ChangePassword.this).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                deleteDialog.setView(v);
                Button btncontinue= v.findViewById(R.id.changepasswordalert_button);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChangePassword.this, AccountFragment.class);
//                                        startActivity(intent);
                      // startActivity(new Intent(ChangePassword.this, AccountFragment.class));
                        Intent i=new Intent(ChangePassword.this,BottomnavigationScreen.class);
                        i.putExtra("type","my");
                   startActivity(i);



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();


            }
        });
    }
}