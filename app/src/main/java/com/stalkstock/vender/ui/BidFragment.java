package com.stalkstock.vender.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.stalkstock.R;
import com.stalkstock.vender.adapter.AccpetAdapter;
import com.stalkstock.vender.adapter.RequestAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFragment extends Fragment {

    public BidFragment() {
        // Required empty public constructor
    }

    Context mContext;
    RequestAdapter requestAdapter;
    AccpetAdapter accpetAdapter;
    RecyclerView bidrecyclerview1;


    View  views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        views= inflater.inflate(R.layout.activity_bid_product, container, false);

        ImageView bidproductbackarrow=views.findViewById(R.id.bidproductbackarrow);
        bidproductbackarrow.setVisibility(View.GONE);


        bidrecyclerview1 = views.findViewById(R.id.bidrecyclerview);
        mContext = requireActivity();
        requestAdapter = new RequestAdapter(mContext);
        bidrecyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
        bidrecyclerview1.setAdapter(requestAdapter);
        ImageView backarrow =views. findViewById(R.id.bidproductbackarrow);
        final Button btnRequest = views.findViewById(R.id.btnrequest);
        final Button btnAccpet =views. findViewById(R.id.btnaccpet);

//        backarrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRequest.setBackground(getResources().getDrawable(R.drawable.current_button));
                btnAccpet.setBackground(getResources().getDrawable(R.drawable.past_button2));
                btnRequest.setTextColor(getResources().getColor(R.color.white));
                btnAccpet.setTextColor(getResources().getColor(R.color.balck));
                requestAdapter = new RequestAdapter(mContext);
                bidrecyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
                bidrecyclerview1.setAdapter(requestAdapter);

            }
        });
        btnAccpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAccpet.setBackground(getResources().getDrawable(R.drawable.current_button));
                btnRequest.setBackground(getResources().getDrawable(R.drawable.past_button2));
                btnAccpet.setTextColor(getResources().getColor(R.color.white));
                btnRequest.setTextColor(getResources().getColor(R.color.balck));
                accpetAdapter = new AccpetAdapter(mContext);
                bidrecyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
                bidrecyclerview1.setAdapter(accpetAdapter);
            }
        });

        return  views;
    }
}
