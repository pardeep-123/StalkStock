package com.stalkstock.vender.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stalkstock.R;
import com.stalkstock.vender.ui.NewOrderList;

public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    String message ="New Orders";
    String message1 ="In Progress";
    String message2 ="Ready for Pickup";


    public  CurrentAdapter(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.currentorderlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         RelativeLayout button1,button2,button3;
         TextView textView,textView2,textView3;
         ImageView imageone,imagetwo,imgethree;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              button1=itemView.findViewById(R.id.neworder);
              button2=itemView.findViewById(R.id.inprogress);
              button3=itemView.findViewById(R.id.redypickup);
              textView=itemView.findViewById(R.id.neworder_text);
              textView2=itemView.findViewById(R.id.inprogress_text);
              textView3=itemView.findViewById(R.id.redypickup_text);
              imageone=itemView.findViewById(R.id.neworder_img);
              imagetwo=itemView.findViewById(R.id.inprogress_img);
              imgethree=itemView.findViewById(R.id.redy_img);

            button1.setOnClickListener(view -> {
                button1.setBackground(context.getResources().getDrawable(R.drawable.current_button));
                button2.setBackground(context.getResources().getDrawable(R.drawable.cureentbackground));
                button3.setBackground(context.getResources().getDrawable(R.drawable.cureentbackground));
                imageone.setImageResource(R.drawable.right_arrow_icon);
                imagetwo.setImageResource(R.drawable.right_grey_icon);
                imgethree.setImageResource(R.drawable.right_grey_icon);
                textView.setTextColor(context.getResources().getColor(R.color.white));
                textView2.setTextColor(context.getResources().getColor(R.color.balck));
                textView3.setTextColor(context.getResources().getColor(R.color.balck));
                Intent intent4 = new Intent(context, NewOrderList.class);
                intent4.putExtra("key",message);
                context.startActivity(intent4);
            });
            button2.setOnClickListener(view -> {
                button2.setBackground(context.getResources().getDrawable(R.drawable.current_button));
                button3.setBackground(context.getResources().getDrawable(R.drawable.cureentbackground));
                button1.setBackground(context.getResources().getDrawable(R.drawable.cureentbackground));
                imagetwo.setImageResource(R.drawable.right_arrow_icon);
                imgethree.setImageResource(R.drawable.right_grey_icon);
                imageone.setImageResource(R.drawable.right_grey_icon);
                textView2.setTextColor(context.getResources().getColor(R.color.white));
                textView3.setTextColor(context.getResources().getColor(R.color.balck));
                textView.setTextColor(context.getResources().getColor(R.color.balck));
                Intent intent5 = new Intent(context, NewOrderList.class);
                intent5.putExtra("key",message1);
                context.startActivity(intent5);

            });
            button3.setOnClickListener(view -> {
                button3.setBackground(context.getResources().getDrawable(R.drawable.current_button));
                button2.setBackground(context.getResources().getDrawable(R.drawable.cureentbackground));
                button1.setBackground(context.getResources().getDrawable(R.drawable.cureentbackground));
                imgethree.setImageResource(R.drawable.right_arrow_icon);
                imagetwo.setImageResource(R.drawable.right_grey_icon);
                imageone.setImageResource(R.drawable.right_grey_icon);
                textView3.setTextColor(context.getResources().getColor(R.color.white));
                textView2.setTextColor(context.getResources().getColor(R.color.balck));
                textView.setTextColor(context.getResources().getColor(R.color.balck));
                Intent intent6 = new Intent(context, NewOrderList.class);
                intent6.putExtra("key",message2);
                context.startActivity(intent6);
            });

        }
    }
}
