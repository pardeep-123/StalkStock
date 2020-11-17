package com.stalkstock.vender.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.stalkstock.R;
import com.stalkstock.vender.ui.AddProduct;
import com.stalkstock.vender.ui.BidProduct;
import com.stalkstock.vender.ui.BussinessProfile;
import com.stalkstock.vender.ui.ChangePassword;
import com.stalkstock.vender.ui.EditProfile;
import com.stalkstock.vender.ui.Help;
import com.stalkstock.vender.ui.LoginScreen;
import com.stalkstock.vender.ui.PaymentAccounts;
import com.stalkstock.vender.ui.Subscription;

public class AccountFragment extends Fragment implements View.OnClickListener {
    Context context;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.account_fragment, null);
        ImageView imageView = view.findViewById(R.id.profileediticon);
         LinearLayout business= view.findViewById(R.id.businessprofile);
        LinearLayout  addproduct= view.findViewById(R.id.addproductaccounts);
        LinearLayout bidProduct = view.findViewById(R.id.bidproduct);
        LinearLayout subscription = view.findViewById(R.id.subscription);
        LinearLayout payment=view.findViewById(R.id.payments);
        LinearLayout changepassword = view.findViewById(R.id.chnagepassword);
        LinearLayout help = view.findViewById(R.id.help);
        LinearLayout logout= view.findViewById(R.id.ll_1);

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


        imageView.setOnClickListener(this);
        business.setOnClickListener(this);
        addproduct.setOnClickListener(this);
        bidProduct.setOnClickListener(this);
        subscription.setOnClickListener(this);
        payment.setOnClickListener(this);
        changepassword.setOnClickListener(this);
        help.setOnClickListener(this);
        logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();



        switch (id) {

            case R.id.profileediticon:
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
                break;

            case R.id.businessprofile:
               Intent intent2 = new Intent(getActivity(), BussinessProfile.class);
                startActivity(intent2);
                break;
            case R.id.addproductaccounts:

                Intent intent3 = new Intent(getActivity(), AddProduct.class);
                startActivity(intent3);
                break;
            case R.id.bidproduct:

                Intent intent4 = new Intent(getActivity(), BidProduct.class);
                startActivity(intent4);

                break;
            case R.id.subscription:

              Intent intent5 = new Intent(getActivity(), Subscription.class);
               startActivity(intent5);

                break;
            case R.id.payments:

                Intent intent6 = new Intent(getActivity(), PaymentAccounts.class);
               startActivity(intent6);

                break;
            case R.id.chnagepassword:

                Intent intent7 = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent7);






                break;
            case R.id.help:

                Intent intent8 = new Intent(getActivity(), Help.class);
                startActivity(intent8);

                break;


            case R.id.ll_1:
                //showDialog()

                LayoutInflater inflate= LayoutInflater.from(getActivity());
                View v= inflate.inflate(R.layout.logout_alert_box,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(getActivity()).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteDialog.setView(v);
                final Button btnyes= v.findViewById(R.id.logout_yesbtn);
                final Button buttonno=v.findViewById(R.id.logout_nobtn);
                btnyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnyes.setBackground(getResources().getDrawable(R.drawable.logoutshape));
                        buttonno.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        btnyes.setTextColor(getResources().getColor(R.color.white));
                        buttonno.setTextColor(getResources().getColor(R.color.balck));

                        deleteDialog.dismiss();
                    }
                });
                buttonno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonno.setBackground(getResources().getDrawable(R.drawable.current_button));
                        btnyes.setBackground(getResources().getDrawable(R.drawable.past_button));
                        buttonno.setTextColor(getResources().getColor(R.color.white));
                        btnyes.setTextColor(getResources().getColor(R.color.balck));
                        Intent intent9 = new Intent(getActivity(), LoginScreen.class);
                        startActivity(intent9);
                        requireActivity().finish();
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();


                break;
        }

    }

//    private void showDialog() {
//        dialog= new Dialog(context);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        dialog.setContentView(R.layout.logout_alert_box);
//        dialog.setCancelable(true);
//        final Button btnyes,button;
//         btnyes= dialog.findViewById(R.id.logout_yesbtn);
//         final Button buttonno=dialog.findViewById(R.id.logout_nobtn);
//        btnyes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnyes.setBackground(getResources().getDrawable(R.drawable.logoutshape));
//                buttonno.setBackground(getResources().getDrawable(R.drawable.buttonshape));
//                btnyes.setTextColor(getResources().getColor(R.color.white));
//                buttonno.setTextColor(getResources().getColor(R.color.balck));
//                Intent intent9 = new Intent(getActivity(), LoginScreen.class);
//                startActivity(intent9);
//
//                dialog.dismiss();
//            }
//        });
//        buttonno.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buttonno.setBackground(getResources().getDrawable(R.drawable.current_button));
//                btnyes.setBackground(getResources().getDrawable(R.drawable.past_button));
//                buttonno.setTextColor(getResources().getColor(R.color.white));
//                btnyes.setTextColor(getResources().getColor(R.color.balck));
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
}
