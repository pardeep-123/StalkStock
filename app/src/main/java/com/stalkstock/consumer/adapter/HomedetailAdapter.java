package com.stalkstock.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.consumer.activities.ProductActivity;


public class HomedetailAdapter extends RecyclerView.Adapter<HomedetailAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View view) {
            super(view);

        }
    }

    public HomedetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_homedetails, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10 ;
    }


}


