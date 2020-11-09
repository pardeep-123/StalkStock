package com.stalkstock.consumer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stalkstock.R;
import com.stalkstock.consumer.adapter.MyordersAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

  View view;
  MyordersAdapter adapter;
  RecyclerView myorder_recycle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_list, container, false);

        myorder_recycle=view.findViewById(R.id.myorder_recycle);
        adapter = new MyordersAdapter(getActivity());
        myorder_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        myorder_recycle.setAdapter(adapter) ;
        return view;
    }
}
