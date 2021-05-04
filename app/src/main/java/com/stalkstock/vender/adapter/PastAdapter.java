package com.stalkstock.vender.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.vender.ui.OrderDetails;

public class PastAdapter extends RecyclerView.Adapter<PastAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    String message4="Delivered";


    public  PastAdapter(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pastbtn, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout pastorder;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pastorder=itemView.findViewById(R.id.past_details);
            pastorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent4 = new Intent(context, OrderDetails.class);
                    intent4.putExtra("valu",message4);
                    intent4.putExtra("show","delivered_by");
                    context.startActivity(intent4);
                }
            });

        }
    }
}
