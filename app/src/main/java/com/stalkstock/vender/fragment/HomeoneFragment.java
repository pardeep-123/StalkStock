package com.stalkstock.vender.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stalkstock.R;
import com.stalkstock.vender.ui.SelectCategory;

public class HomeoneFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view= inflater.inflate(R.layout.homeonefragment, null);

        Button button =view.findViewById(R.id.homeadditembutton);

        button.setOnClickListener(this);
        return  view;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){



            case R.id.homeadditembutton:
                Intent i = new Intent(getActivity(), SelectCategory.class);
                startActivity(i);


                break;


        }
    }
}
