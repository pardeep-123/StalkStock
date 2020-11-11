package com.stalkstock.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.stalkstock.R;
import com.stalkstock.consumer.activities.HomedetailsActivity;
import com.stalkstock.consumer.model.CategoryModel;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<CategoryModel> arrayList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        public RecyclerViewHolder(View view) {
            super(view);
            img=view.findViewById(R.id.img);
            name=view.findViewById(R.id.name);
        }
    }

    public CategoryAdapter(Context context, ArrayList<CategoryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_categrey, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {


        Glide.with(context).load(arrayList.get(position).getIcon())
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.img);

        holder.name.setText(arrayList.get(position).getAbc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HomedetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }


}


