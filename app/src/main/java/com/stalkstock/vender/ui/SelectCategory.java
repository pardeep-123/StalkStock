package com.stalkstock.vender.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stalkstock.R;

import java.util.ArrayList;
import java.util.List;

public class SelectCategory extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    TextView textView ,textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        ImageView backarrow = findViewById(R.id.selectctgbackarrow);
        Button button = findViewById(R.id.enteritembutton);
        button.setOnClickListener(this);
        backarrow.setOnClickListener(this);
        textView = findViewById(R.id.text);
        textView1= findViewById(R.id.textone);
        textView2 = findViewById(R.id.texttwo);
        textView3= findViewById(R.id.textthree);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackground(getResources().getDrawable(R.drawable.edit_background));
                textView1.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView2.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView3.setBackground(getResources().getDrawable(R.drawable.textbackground));

            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView1.setBackground(getResources().getDrawable(R.drawable.edit_background));
                textView2.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView3.setBackground(getResources().getDrawable(R.drawable.textbackground));

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView1.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView2.setBackground(getResources().getDrawable(R.drawable.edit_background));
                textView3.setBackground(getResources().getDrawable(R.drawable.textbackground));
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView1.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView2.setBackground(getResources().getDrawable(R.drawable.textbackground));
                textView3.setBackground(getResources().getDrawable(R.drawable.edit_background));
            }
        });

       // addItemsOnSpinner2();




        spinner= (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Select Category");
        list.add("Vegetables");
        list.add("Fruits");
        list.add("Grains, Beans and Nuts");
        list.add("Meat and Poultry");
        list.add("Fish and Seafood");
        list.add("Dairy");
        list.add("Other (Please Specify Below)");




        // spinner.setAdapter(new ArrayAdapter(this, R.layout.spinner_item_text,list));

        ArrayAdapter<CharSequence> foodadapter =  ArrayAdapter.createFromResource(this, R.array.Select_Category, R.layout.spinner_layout);
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text);
        spinner.setAdapter(foodadapter);


    }

//    private void addItemsOnSpinner2() {
//        List<String> list = new ArrayList<String>();
//        list.add("Vegetables");
//        list.add("Fruits");
//        list.add("Grains, Beans and Nuts");
//        list.add("Meat and Poultry");
//        list.add("Fish and Seafood");
//        list.add("Dairy");
//        list.add("Other(Please Specify Below)");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case  R.id.selectctgbackarrow:
                onBackPressed();
                break;
            case R.id.enteritembutton:
                startActivity(new Intent(SelectCategory.this, AddProduct.class));
                break;


        }

    }
}