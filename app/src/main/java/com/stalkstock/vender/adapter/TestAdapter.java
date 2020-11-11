package com.stalkstock.vender.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

 import com.stalkstock.R;
import com.stalkstock.vender.ui.ProductDetail;

import java.util.ArrayList;

public class TestAdapter  extends RecyclerView.Adapter<TestAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    ImageView rl_list;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View view) {
            super(view);

        }
    }

    public TestAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_home_two, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);

        LinearLayout linearLayout= v.findViewById(R.id.itemlayout);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetail.class));
            }
        });

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 3;
    }


}


