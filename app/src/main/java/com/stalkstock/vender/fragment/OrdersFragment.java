package com.stalkstock.vender.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.vender.adapter.CurrentAdapter;
import com.stalkstock.vender.adapter.PastAdapter;

public class OrdersFragment extends Fragment {
    Context mContext;
    private CurrentAdapter currentAdapter;
    RecyclerView recyclerview1;
    private PastAdapter pastAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_orders, container,false);
        mContext = getActivity();
        final Button btnCurrent = view.findViewById(R.id.btnCurrent);
        final Button btnPast = view.findViewById(R.id.btnPast);
        recyclerview1 = view.findViewById(R.id.currentrecyclerview);
        currentAdapter = new CurrentAdapter(mContext);
        recyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview1.setAdapter(currentAdapter);




        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCurrent.setBackground(getResources().getDrawable(R.drawable.current_button));
                btnPast.setBackground(getResources().getDrawable(R.drawable.past_button2));
                btnCurrent.setTextColor(getResources().getColor(R.color.white));
                btnPast.setTextColor(getResources().getColor(R.color.balck));
                currentAdapter = new CurrentAdapter(mContext);
                recyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerview1.setAdapter(currentAdapter);

            }
        });
        btnPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPast.setBackground(getResources().getDrawable(R.drawable.current_button));
                btnCurrent.setBackground(getResources().getDrawable(R.drawable.past_button2));
                btnPast.setTextColor(getResources().getColor(R.color.white));
                btnCurrent.setTextColor(getResources().getColor(R.color.balck));
                pastAdapter = new PastAdapter(mContext);
                recyclerview1.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerview1.setAdapter(pastAdapter);
            }
        });
        return  view;


    }
}

