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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.stalkstock.R;
import com.stalkstock.advertiser.activities.ChangePasswordActivity;
import com.stalkstock.advertiser.activities.HelpActivity;
import com.stalkstock.advertiser.activities.LoginActivity;
import com.stalkstock.advertiser.activities.ManagePaymentsActivity;
import com.stalkstock.consumer.activities.EditprofileActivity;
import com.stalkstock.consumer.activities.MainConsumerActivity;
import com.stalkstock.consumer.activities.ManagmentPaymentActivity;
import com.stalkstock.utils.custom.TitiliumBoldButton;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileConsumerFragment extends Fragment {

   View view;
   ImageView edi_icon;
   LinearLayout profile;
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
                Intent intent=new Intent(getActivity(), EditprofileActivity.class);
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
            public void onClick(View view) {Intent intent=new Intent(getActivity(), ManagePaymentsActivity.class);
                startActivity(intent);
            }
        });tv_business_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(getActivity(), ManageaddressActivity.class);
               // startActivity(intent);
            }
        });

        return view;
    }

    public void LogoutAlert() {
        final Dialog dialogSuccessful = new Dialog(Objects.requireNonNull(getActivity()), R.style.Theme_Dialog);
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
                intent.putExtra("is_open","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                dialogSuccessful.dismiss();
            }
        });

        dialogSuccessful.show();
    }
}
