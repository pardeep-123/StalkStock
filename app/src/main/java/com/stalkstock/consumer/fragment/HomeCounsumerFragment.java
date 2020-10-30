package com.stalkstock.consumer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.stalkstock.R;
import com.stalkstock.advertiser.activities.Notification_firstActivity;
import com.stalkstock.consumer.activities.SearchActivity;
import com.stalkstock.consumer.adapter.CategoryAdapter;
import com.stalkstock.consumer.adapter.SuggestedAdapter;
import com.stalkstock.consumer.adapter.View_detailAdapter;
import com.stalkstock.consumer.model.CategoryModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import stalkstockcommercial.ui.view.activities.FilterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCounsumerFragment extends Fragment {
    View view;
    CategoryAdapter adapter;

    RecyclerView category_recycle,suggested_recycle;
    ViewPager viewPagerDetail;
    CirclePageIndicator indicator;
    View_detailAdapter detailAdapter;
    SuggestedAdapter adapter3;
    ImageView notification,fillter;
    RelativeLayout etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_consumer, container, false);

        category_recycle=view.findViewById(R.id.category_recycle);
        viewPagerDetail=view.findViewById(R.id.viewPagerDetail);
        suggested_recycle=view.findViewById(R.id.suggested_recycle);
        indicator=view.findViewById(R.id.indicator);
        notification=view.findViewById(R.id.notification);
        fillter=view.findViewById(R.id.fillter);
        etSearch=view.findViewById(R.id.etSearch);
        ArrayList<CategoryModel> arrayList=new ArrayList<>();

        arrayList.add(new CategoryModel("Vegetable",R.drawable.veg_icon));
        arrayList.add(new CategoryModel("Fruits",R.drawable.fruit_icon));
        arrayList.add(new CategoryModel("Grains Beans,Nutts",R.drawable.grains_icon));
        arrayList.add(new CategoryModel("Meat and Poultry",R.drawable.meat_ico));
        arrayList.add(new CategoryModel("Fish and Seafood",R.drawable.fish_icon1));
        arrayList.add(new CategoryModel("Dairy",R.drawable.dairy_ico));
        arrayList.add(new CategoryModel("More",R.drawable.more_ico));

        adapter = new CategoryAdapter(getActivity(),arrayList);
        category_recycle.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        category_recycle.setAdapter(adapter) ;

        detailAdapter = new View_detailAdapter(getActivity());
        viewPagerDetail.setAdapter(detailAdapter);
        indicator.setFillColor(getResources().getColor(R.color.theme_green));
        indicator.setViewPager(viewPagerDetail);

        adapter3 = new SuggestedAdapter(getActivity());
        suggested_recycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        suggested_recycle.setAdapter(adapter3);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Notification_firstActivity.class);
                startActivity(intent);
            }
        });
        fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
