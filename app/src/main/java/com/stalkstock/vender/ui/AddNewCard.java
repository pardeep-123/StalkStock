package com.stalkstock.vender.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stalkstock.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

public class AddNewCard extends AppCompatActivity {
    EditText mExpiryDate;
    String mLastInput ="";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card2);
        mExpiryDate= findViewById(R.id.date);
        ImageView imageView=findViewById(R.id.sp_backarrow);
        Button addvcardbutton=findViewById(R.id.addcard_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        addvcardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
finish();

            }
        });

        mExpiryDate.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


           }

           @Override
           public void onTextChanged(CharSequence s, int before, int i1, int i2) {
               String input = s.toString();
               SimpleDateFormat formatter = new SimpleDateFormat("MM/yy", Locale.GERMANY);
               Calendar expiryDateDate = Calendar.getInstance();
               try {
                   expiryDateDate.setTime(formatter.parse(input));
               } catch (ParseException e) {

               } catch (Exception e) {
                   if (s.length() == 2 && !mLastInput.endsWith("/")) {
                       int month = Integer.parseInt(input);
                       if (month <= 12) {
                           mExpiryDate.setText(mExpiryDate.getText().toString() + "/");
                           mExpiryDate.setSelection(mExpiryDate.getText().toString().length());
                       }
                   } else if (s.length() == 2 && mLastInput.endsWith("/")) {
                       int month = Integer.parseInt(input);
                       if (month <= 12) {
                           mExpiryDate.setText(mExpiryDate.getText().toString().substring(0, 1));
                           mExpiryDate.setSelection(mExpiryDate.getText().toString().length());
                       } else {
                           mExpiryDate.setText("");
                           mExpiryDate.setSelection(mExpiryDate.getText().toString().length());
                           Toast.makeText(getApplicationContext(), "Enter a valid month", Toast.LENGTH_LONG).show();
                       }
                   } else if (s.length() == 1) {
                       int month = Integer.parseInt(input);
                       if (month > 1) {
                           mExpiryDate.setText("0" + mExpiryDate.getText().toString() + "/");
                           mExpiryDate.setSelection(mExpiryDate.getText().toString().length());
                       }
                   } else {

                   }
                   mLastInput = mExpiryDate.getText().toString();
                   return;
               }



           }

           @Override
           public void afterTextChanged(Editable editable) {
               if (editable.length() > 0 && (editable.length() % 3) == 0) {
                   final char c = editable.charAt(editable.length() - 1);
                   if ('/' == c) {
                       editable.delete(editable.length() - 1, editable.length());
                   }
               }
               if (editable.length() > 0 && (editable.length() % 3) == 0) {
                   char c = editable.charAt(editable.length() - 1);
                   if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                       editable.insert(editable.length() - 1, String.valueOf("/"));
                   }
               }

           }

       });
    }
        }