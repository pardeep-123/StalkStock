package com.stalkstock.consumer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.live.stalkstockcommercial.ui.view.fragments.account.ManageAddress;
import com.stalkstock.R;
import com.stalkstock.advertiser.activities.ChangePasswordActivity;
import com.stalkstock.advertiser.activities.HelpActivity;
import com.stalkstock.advertiser.activities.LoginActivity;
import com.stalkstock.advertiser.activities.ManagePaymentsActivity;
import com.stalkstock.advertiser.activities.PaymentActivity;
import com.stalkstock.consumer.activities.EditprofileConsumerActivity;
import com.stalkstock.utils.custom.TitiliumBoldButton;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileConsumerFragment extends Fragment {

   View view;
   ImageView edi_icon;
   RelativeLayout profile;
   TextView tv_changepass,tv_help,tv_manage,tv_business_profile,tv_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_account_consumer, container, false);
        profile=view.findViewById(R.id.profile);
        tv_changepass=view.findViewById(R.id.tv_changepass);
        tv_logout=view.findViewById(R.id.tv_logout);
        edi_icon = view.findViewById(R.id.ivEdit);
        tv_help=view.findViewById(R.id.tv_help);
        tv_manage=view.findViewById(R.id.tv_manage);
        tv_business_profile=view.findViewById(R.id.tv_business_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), EditprofileConsumerActivity.class);
                startActivity(intent);
            }
        }); tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
        tv_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAlert();
            }
        });

        tv_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(getActivity(), ManagePaymentsActivity.class);
                startActivity(intent);

//                Intent intent=new Intent(getActivity(), PaymentActivity.class);
//                startActivity(intent);
            }
        });tv_business_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ManageAddress.class);
                startActivity(intent);

            }
        });


        final ImageView toggle1=view.findViewById(R.id.toggle1);
        final ImageView toggle_off2=view.findViewById(R.id.toggle_off2);

        toggle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_off2.setVisibility(View.VISIBLE);
                toggle1.setVisibility(View.GONE);

            }
        });
        toggle_off2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_off2.setVisibility(View.GONE);
                toggle1.setVisibility(View.VISIBLE);

            }
        });



        return view;
    }

    public void LogoutAlert() {
        final Dialog dialogSuccessful = new Dialog(requireActivity(), R.style.Theme_Dialog);
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccessful.setContentView(R.layout.logout_alert);
        dialogSuccessful.setCancelable(false);
        Objects.requireNonNull(dialogSuccessful.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSuccessful.setCanceledOnTouchOutside(false);
        dialogSuccessful.getWindow().setGravity(Gravity.CENTER);

        TitiliumBoldButton btn_no = dialogSuccessful.findViewById(R.id.btn_no);
        TitiliumBoldButton btn_yes = dialogSuccessful.findViewById(R.id.btn_yes);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), LoginActivity.class);
               // intent.putExtra("is_open","1");
                startActivity(intent);
                dialogSuccessful.dismiss();
                getActivity().finishAffinity();

            }
        });

        dialogSuccessful.show();
    }
}
