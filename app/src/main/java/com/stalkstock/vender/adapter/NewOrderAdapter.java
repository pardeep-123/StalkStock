package com.stalkstock.vender.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.vender.ui.OrderDetails;


public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    //String message="New Orders";
//    String message2="In Progress";
//
//    String message3="Ready for Pickup";
    String text="";



    public  NewOrderAdapter(Context context, String text){
        this.context=context;
        this.text=text;
       inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.neworderlist, parent, false);
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
        LinearLayout orderlistone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderlistone=itemView.findViewById(R.id.orderlistone);
            orderlistone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent4 = new Intent(context, OrderDetails.class);

                    if (text.equals("New Orders")){
                        intent4.putExtra("key","New");

                    }else  if (text.equals("In Progress")){
                        intent4.putExtra("value","In Progress");

                    }else  if (text.equals("Ready for Pickup")){
                        intent4.putExtra("val","Ready For Pickup");

                    }else {
                    }
//                    intent4.putExtra("value",message2);
//                    intent4.putExtra("val",message3);
                    context.startActivity(intent4);
                }
            });
        }
    }
}
