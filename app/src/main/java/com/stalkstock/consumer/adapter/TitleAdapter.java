package com.stalkstock.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.consumer.activities.ProductActivity;
import com.stalkstock.utils.SliderItemTitleModel;

import java.util.ArrayList;


public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<SliderItemTitleModel> arrayList;


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_title,tv_item_title_bold;
        View view_simple,view_bold;

        public RecyclerViewHolder(View view) {
            super(view);
            tv_item_title=view.findViewById(R.id.tv_item_title);
            tv_item_title_bold=view.findViewById(R.id.tv_item_title_bold);
            view_simple=view.findViewById(R.id.view_simple);
            view_bold=view.findViewById(R.id.view_bold);

        }
    }

    public TitleAdapter(Context context, ArrayList<SliderItemTitleModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_title, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {


        holder.tv_item_title.setText(arrayList.get(position).getTitle());
        holder.tv_item_title_bold.setText(arrayList.get(position).getTitle());

        if (arrayList.get(position).getIsSelected().equals("false")){

            holder.tv_item_title_bold.setVisibility(View.GONE);
            holder.view_bold.setVisibility(View.GONE);

            holder.tv_item_title.setVisibility(View.VISIBLE);
            holder.view_simple.setVisibility(View.VISIBLE);

        }else if (arrayList.get(position).getIsSelected().equals("true")){

            holder.tv_item_title_bold.setVisibility(View.VISIBLE);
            holder.view_bold.setVisibility(View.VISIBLE);

            holder.tv_item_title.setVisibility(View.GONE);
            holder.view_simple.setVisibility(View.GONE);

        }else {

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i =0; i<arrayList.size();i++){
                    arrayList.get(i).setIsSelected("false");

                }
                arrayList.get(position).setIsSelected("true");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }


}


