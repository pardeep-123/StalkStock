package com.stalkstock.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.consumer.activities.AddcartdetailsActivity;


public class ProductsdetailsAdapter extends RecyclerView.Adapter<ProductsdetailsAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
TextView add;
        public RecyclerViewHolder(View view) {
            super(view);
            add=view.findViewById(R.id.add);
        }
    }

    public ProductsdetailsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_productdetsils, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddcartdetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10 ;
    }


}


