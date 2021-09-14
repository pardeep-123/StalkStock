package com.stalkstock.vender.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.stalkstock.R;

public class Verification extends AppCompatActivity implements View.OnClickListener {
    LinearLayout otp_layout;
    EditText otp1,otp2,otp3,otp4;
    Button  verfiy;
    ImageView verfy_backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            try {
                if(getIntent().getStringExtra("type").equals("my"))
                {
                    /*LayoutInflater inflater= LayoutInflater.from(Verification.this);
                    View v= inflater.inflate(R.layout.verficationalertdialog,null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(Verification.this).create();
                    deleteDialog.setView(v);
                    Button btncontinue= v.findViewById(R.id.verify_continuebutton);

                    btncontinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                            startActivity(new Intent(Verification.this, AddBusinessDetails.class));



                            deleteDialog.dismiss();

                        }
                    });
                    deleteDialog.show();*/
                    final Dialog logoutUpdatedDialog = new  Dialog(this, R.style.Theme_Dialog);
                    logoutUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    logoutUpdatedDialog.setContentView(R.layout.verficationalertdialog);

                    logoutUpdatedDialog.getWindow().setLayout(
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT
                    );
                    logoutUpdatedDialog.setCancelable(true);
                    logoutUpdatedDialog.setCanceledOnTouchOutside(false);
                    logoutUpdatedDialog.getWindow().setGravity(Gravity.CENTER);

                    logoutUpdatedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button btncontinue= logoutUpdatedDialog.findViewById(R.id.verify_continuebutton);

                    btncontinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                            startActivity(new Intent(Verification.this, AddBusinessDetails.class));



                            logoutUpdatedDialog.dismiss();

                        }
                    });


                    logoutUpdatedDialog.show();
                }
            }catch (Exception e)
            {

                // loadFragment( fragment);
            }
            // change to whichever id should be default
        }catch (Exception e)
        {
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        verfy_backarrow = findViewById(R.id.verify_backarrow);
        otp_layout=findViewById(R.id.otp_layout);
        otp1=findViewById(R.id.otp_edit_box1);
        otp2=findViewById(R.id.otp_edit_box2);
        otp3=findViewById(R.id.otp_edit_box3);
        otp4=findViewById(R.id.otp_edit_box4);
//        otp1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(otp1.getText().toString().length()==1)     //size as per your requirement
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        otp1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(otp1.getText().toString().length()==1)
                    otp2.requestFocus();
                    return false;
            }
        });
        otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(otp2.getText().toString().length()==1)
                    otp3.requestFocus();
                    return false;
            }
        });
        otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(otp3.getText().toString().length()==1)
                    otp4.requestFocus();
                    return false;
            }
        });
        otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(otp4.getText().toString().length()==1)
                    otp4.requestFocus();
                return false;
            }
        });

        verfiy=findViewById(R.id.verifybutton);
        verfiy.setOnClickListener(this);
        verfy_backarrow.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.verify_backarrow:
                onBackPressed();
                break;
            case R.id.verifybutton:


                verifyloDailogMethod();


               /* LayoutInflater inflater= LayoutInflater.from(Verification.this);
                View v= inflater.inflate(R.layout.verficationalertdialog,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(Verification.this).create();
                deleteDialog.setView(v);
                deleteDialog.create();


                    Button btncontinue= v.findViewById(R.id.verify_continuebutton);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        startActivity(new Intent(Verification.this, AddBusinessDetails.class));



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();*/
                break;




            default:
        }
    }

    private void verifyloDailogMethod() {
        final Dialog logoutUpdatedDialog = new  Dialog(this, R.style.Theme_Dialog);
        logoutUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutUpdatedDialog.setContentView(R.layout.verficationalertdialog);

        logoutUpdatedDialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        logoutUpdatedDialog.setCancelable(true);
        logoutUpdatedDialog.setCanceledOnTouchOutside(false);
        logoutUpdatedDialog.getWindow().setGravity(Gravity.CENTER);

        logoutUpdatedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btncontinue= logoutUpdatedDialog.findViewById(R.id.verify_continuebutton);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                startActivity(new Intent(Verification.this, AddBusinessDetails.class));



                logoutUpdatedDialog.dismiss();

            }
        });


        logoutUpdatedDialog.show();
    }
}