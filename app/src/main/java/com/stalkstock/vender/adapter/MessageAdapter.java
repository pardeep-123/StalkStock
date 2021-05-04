package com.stalkstock.vender.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.live.stalkstockcommercial.ui.view.activities.Chat;
import com.stalkstock.R;
import com.stalkstock.vender.Model.MessageList;
import com.stalkstock.vender.ui.ChatBox;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    int[] userimg ={R.drawable.chat_img, R.drawable.chat_img_2,R.drawable.chat_img_3,R.drawable.chat_img,R.drawable.chat_img_3, R.drawable.chat_img_2,R.drawable.chat_img, R.drawable.chat_img};
    Context context;
    LayoutInflater inflater;
    private List<MessageList> messageContacts;

    public MessageAdapter(List<MessageList> messageContacts) {
        this.messageContacts = messageContacts;
    }

    public  MessageAdapter(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.messagelist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context.startActivity(new Intent(context, ChatBox.class));
                context.startActivity(new Intent(context, Chat.class));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        MessageList messageList = messageContacts.get(position);
//        holder.imageview.setImageResource(messageList.getImage());
//        holder.textname.setText(messageList.getName());
//        holder.texttime.setText(messageList.getTime());
//        holder.textView.setText(messageList.getMessage());

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageview;
        TextView textname,texttime,textView;
        public ViewHolder(View itemView) {
            super(itemView);
             imageview= itemView.findViewById(R.id.imguser);
            // textname=itemView.findViewById(R.id.msgusername);
             texttime=itemView.findViewById(R.id.messagetime);
          //  textView=itemView.findViewById(R.id.messagerecive);

        }


    }
}
