package com.stalkstock.vender.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.vender.adapter.TestAdapter;
import com.stalkstock.vender.ui.FilterScreen;
import com.stalkstock.vender.ui.MessageActivity;
import com.stalkstock.vender.ui.NotificationScreen;
import com.stalkstock.vender.ui.SearchScreen;
import com.stalkstock.vender.ui.SelectCategory;

import stalkstockcommercial.ui.view.activities.FilterActivity;


public class MainHomeFragment extends Fragment implements View.OnClickListener {
    Context mcontext;
    private TestAdapter testAdapter;
    RecyclerView recyclerview;

    int clickMsg=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_two_fragment, container, false);
        mcontext = getActivity();
        recyclerview = view.findViewById(R.id.recyclerview);
        testAdapter = new TestAdapter(mcontext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerview.setAdapter(testAdapter);


        ImageView imageView = view.findViewById(R.id.notification);
        ImageView imageView1 = view.findViewById(R.id.filter);
        ImageView iv_msg = view.findViewById(R.id.iv_msg);
        RelativeLayout editText = view.findViewById(R.id.edit_search);
        Button button = view.findViewById(R.id.addproductbutton);
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        editText.setOnClickListener(this);

        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (clickMsg==0){
                    clickMsg=1;
                    Intent intent=new Intent(requireActivity(), MessageActivity.class);
                    startActivity(intent);
                }else {

                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clickMsg=0;

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.notification:
                Intent intent = new Intent(getActivity(), NotificationScreen.class);
                startActivity(intent);

                break;
            case R.id.filter:
                Intent intent2 = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent2);
                //Intent intent2= new Intent(getActivity(), FilterScreen.class);
                //   startActivity(intent2);

                break;

            case R.id.edit_search:
                Intent intent1 = new Intent(getActivity(), SearchScreen.class);
                startActivity(intent1);

                break;


            case R.id.addproductbutton:
                Intent i = new Intent(getActivity(), SelectCategory.class);
                startActivity(i);
                break;


        }
    }
}
