package com.stalkstock.vender.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.innovattic.rangeseekbar.RangeSeekBar;
import com.stalkstock.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.*;

public class FilterScreen extends AppCompatActivity implements RangeSeekBar.SeekBarChangeListener {
    Button bt1 , bt2;
    TextView textView,textView2,textView3,textView4,filterone,filtertwo,filterthree ,filterfour;
    RelativeLayout relativeLayout,relativeLayout1,relativeLayout3,relativeLayout4;
    ImageView imageView, imageView1;
    RangeSeekBar rangeSeekBar;
    Spinner spinner;
    Context context;
 //   private String[] catg= {"Select Category","Vegetables","Fruits","Grains, Beans and Nuts","Meat and Poultry","Fish and Seafood","Dairy","Other(Please Specify Below)"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter2);

        bt1=findViewById(R.id.btn_clear);
        bt2=findViewById(R.id.btn_apply);


        textView= findViewById(R.id.setprice);
        textView2=findViewById(R.id.setpricetwo);
        textView3=findViewById(R.id.tv_start);
        textView4=findViewById(R.id.tv_end);

        relativeLayout=findViewById(R.id.relativeone);
        relativeLayout1=findViewById(R.id.relativetwo);

        relativeLayout3=findViewById(R.id.checkone);
        relativeLayout4=findViewById(R.id.checktwo);
   //     rangeSeekBar.setMaxThumbValue(250);
//        rangeSeekBar.setSeekBarChangeListener(this);

        spinner = findViewById(R.id.spinner2);

//        String[] plants = new String[]{
//                "Select Category","Vegetables","Fruits",
//                        "Grains, Beans and Nuts","Meat and Poultry",
//                        "Fish and Seafood","Dairy","Other(Please Specify Below)"
//
//        };
//
//        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
//
//        // Initializing an ArrayAdapter
//        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                this,R.layout.spiner_item,plantsList){
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position==0){
//                    // Set the hint text color gray
//                    tv.setTextColor(getResources().getColor(R.color.gry));
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spiner_item);
//        spinner.setAdapter(spinnerArrayAdapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if(position > 0){
//                    // Notify the selected item text
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//ArrayAdapter<String>   arrayAdapter= new ArrayAdapter<String>(FilterScreen.this,R.layout.support_simple_spinner_dropdown_item,catg);
//
//
//        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//
//
//
//        spinner.setAdapter(arrayAdapter);
//


        filterone=findViewById(R.id.filter_tagone);
        filtertwo=findViewById(R.id.filter_tagtwo);
        filterthree=findViewById(R.id.filter_tagthree);
        filterfour=findViewById(R.id.filter_tagfour);
        filterone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterone.setBackground(getResources().getDrawable(R.drawable.edit_background));
                filtertwo.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filterthree.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filterfour.setBackground(getResources().getDrawable(R.drawable.textbackground));
            }
        });
        filtertwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtertwo.setBackground(getResources().getDrawable(R.drawable.edit_background));
                filterthree.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filterfour.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filterone.setBackground(getResources().getDrawable(R.drawable.textbackground));
            }
        });
        filterthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterthree.setBackground(getResources().getDrawable(R.drawable.edit_background));
                filterfour.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filterone.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filtertwo.setBackground(getResources().getDrawable(R.drawable.textbackground));
            }
        });
        filterfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterfour.setBackground(getResources().getDrawable(R.drawable.edit_background));
                filterthree.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filtertwo.setBackground(getResources().getDrawable(R.drawable.textbackground));
                filterone.setBackground(getResources().getDrawable(R.drawable.textbackground));
            }
        });




        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setTextColor(getResources().getColor(R.color.green));
                textView2.setTextColor(getResources().getColor(R.color.grey));
                relativeLayout3.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.GONE);

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setTextColor(getResources().getColor(R.color.green));
                textView.setTextColor(getResources().getColor(R.color.grey));
                relativeLayout4.setVisibility(View.VISIBLE);
                relativeLayout3.setVisibility(View.GONE);

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterScreen.this, BottomnavigationScreen.class);
                startActivity(intent);
            }
        });









    }

    @Override
    public void onStartedSeeking() {

    }

    @Override
    public void onStoppedSeeking() {

    }

    @Override
    public void onValueChanged(int i, int i1) {
        textView3.setText(0);
        textView4.setText(0);

    }
}
