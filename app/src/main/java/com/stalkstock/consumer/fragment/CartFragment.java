package com.stalkstock.consumer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.stalkstock.commercial.view.activities.ManageAddress;
import com.stalkstock.R;
import com.stalkstock.advertiser.activities.PaymentActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
TextView tvChange;
   View view;
    Button btnPaymentMethod;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_cart, container, false);

        btnPaymentMethod = view.findViewById(R.id.btnPaymentMethod);
        tvChange = view.findViewById(R.id.tvChange);
        btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ManageAddress.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
