package com.stalkstock.consumer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stalkstock.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    View views;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        views= inflater.inflate(R.layout.activity_search_screen, container, false);

        ImageView id_backarrow=views.findViewById(R.id.id_backarrow);
        id_backarrow.setVisibility(View.GONE);
        return views;

    }
}
