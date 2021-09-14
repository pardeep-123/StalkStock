package com.stalkstock.vender.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.stalkstock.R;
import com.stalkstock.vender.Model.MessageList;
import com.stalkstock.vender.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    Context mcontext;
    private MessageAdapter messageAdapter;
    RecyclerView messagerecyclerview;
    private List<MessageList> messageLists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.chat_fragment, container,false);
        mcontext=getActivity();
        messagerecyclerview=view.findViewById(R.id.messagerecyclerview);
        messageAdapter = new MessageAdapter(mcontext);
        messagerecyclerview.setLayoutManager(new LinearLayoutManager(mcontext));
        messagerecyclerview.setAdapter(messageAdapter) ;
        messageLists = new ArrayList<>();
        messageLists.add(new MessageList(R.drawable.chat_img,"John","11.02 PM","Hello , How are you"));
        messageLists.add(new MessageList(R.drawable.chat_img_2,"John","11.02 PM","Hello , How are you"));
        messageLists.add(new MessageList(R.drawable.chat_img_3,"John","11.02 PM","Hello , How are you"));
        return view;


    }
}
