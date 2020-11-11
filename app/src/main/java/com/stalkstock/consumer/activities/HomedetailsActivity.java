package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stalkstock.R;
import com.stalkstock.advertiser.activities.Notification_firstActivity;
import com.stalkstock.consumer.adapter.HomedetailAdapter;

import stalkstockcommercial.ui.view.activities.FilterActivity;


public class HomedetailsActivity extends AppCompatActivity {
    HomedetailsActivity context;
    RecyclerView detail_recycle;
    HomedetailAdapter adapter;
    RelativeLayout meat,dairy,fish,food;
    TextView chat,group,fish_text,food_text;
    ImageView ivNotification,search,fillter;
    View request_view,inprogress_view,inprogress_view1,inprogress_view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedetails);
        context=this;
        detail_recycle=findViewById(R.id.detail_recycle);

        meat=findViewById(R.id.meat);
        dairy=findViewById(R.id.dairy);
        fish=findViewById(R.id.fish);
        food=findViewById(R.id.food);

        chat=findViewById(R.id.chat);
        group=findViewById(R.id.group);
        fish_text=findViewById(R.id.fish_text);
        food_text=findViewById(R.id.food_text);

        request_view=findViewById(R.id.request_view);
        inprogress_view=findViewById(R.id.inprogress_view);
        inprogress_view1=findViewById(R.id.inprogress_view1);
        inprogress_view2=findViewById(R.id.inprogress_view2);
        ivNotification=findViewById(R.id.ivNotification);
        fillter=findViewById(R.id.fillter);
        search=findViewById(R.id.search);



        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setTextColor(getResources().getColor(R.color.theme_green));
                group.setTextColor(getResources().getColor(R.color.home_grey));
                fish_text.setTextColor(getResources().getColor(R.color.home_grey));
                food_text.setTextColor(getResources().getColor(R.color.home_grey));

                request_view.setBackgroundColor(getResources().getColor(R.color.theme_green));
                inprogress_view.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.home_grey));
            }
        });

        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setTextColor(getResources().getColor(R.color.home_grey));
                group.setTextColor(getResources().getColor(R.color.theme_green));
                fish_text.setTextColor(getResources().getColor(R.color.home_grey));
                food_text.setTextColor(getResources().getColor(R.color.home_grey));

                request_view.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view.setBackgroundColor(getResources().getColor(R.color.theme_green));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.home_grey));
            }
        });
        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setTextColor(getResources().getColor(R.color.home_grey));
                group.setTextColor(getResources().getColor(R.color.home_grey));
                fish_text.setTextColor(getResources().getColor(R.color.theme_green));
                food_text.setTextColor(getResources().getColor(R.color.home_grey));

                request_view.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.theme_green));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.home_grey));
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setTextColor(getResources().getColor(R.color.home_grey));
                group.setTextColor(getResources().getColor(R.color.home_grey));
                fish_text.setTextColor(getResources().getColor(R.color.home_grey));
                food_text.setTextColor(getResources().getColor(R.color.theme_green));

                request_view.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.home_grey));
                inprogress_view1.setBackgroundColor(getResources().getColor(R.color.theme_green));
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Notification_firstActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
        fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FilterActivity.class);
                startActivity(intent);
            }
        });

        adapter = new HomedetailAdapter(context);
        detail_recycle.setLayoutManager(new LinearLayoutManager(context));
        detail_recycle.setAdapter(adapter) ;
    }


}
