package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stalkstock.R;
import com.stalkstock.vender.Model.MessageList;
import com.stalkstock.vender.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    Context mcontext;
    private MessageAdapter messageAdapter;
    RecyclerView messagerecyclerview;
    private List<MessageList> messageLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_fragment);

        mcontext=this;

        ImageView iv_back=findViewById(R.id.iv_back);

        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        messagerecyclerview=findViewById(R.id.messagerecyclerview);
        messageAdapter = new MessageAdapter(mcontext);
        messagerecyclerview.setLayoutManager(new LinearLayoutManager(mcontext));
        messagerecyclerview.setAdapter(messageAdapter) ;
        messageLists = new ArrayList<>();
        messageLists.add(new MessageList(R.drawable.chat_img,"John","11.02 PM","Hello , How are you"));
        messageLists.add(new MessageList(R.drawable.chat_img_2,"John","11.02 PM","Hello , How are you"));
        messageLists.add(new MessageList(R.drawable.chat_img_3,"John","11.02 PM","Hello , How are you"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
