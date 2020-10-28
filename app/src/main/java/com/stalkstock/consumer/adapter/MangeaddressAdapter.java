package com.stalkstock.consumer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;


import com.stalkstock.R;

import java.util.Objects;


public class MangeaddressAdapter extends RecyclerView.Adapter<MangeaddressAdapter.RecyclerViewHolder> {
    Context context;
    LayoutInflater inflater;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout delete,edit;

        public RecyclerViewHolder(View view) {
            super(view);
            delete=view.findViewById(R.id.delete);
            edit=view.findViewById(R.id.edit);
        }
    }

    public MangeaddressAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.row_manageaddress, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartInfoApp();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(context, EditAddressActivity.class);
                //intent.putExtra("key","edit");
              // context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return 3 ;
    }

    public void openStartInfoApp() {
        final Dialog dialogSuccessful = new Dialog(Objects.requireNonNull(context), R.style.Theme_Dialog);
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccessful.setContentView(R.layout.delete_successfully_alertaddress);
        dialogSuccessful.setCancelable(false);
        Objects.requireNonNull(dialogSuccessful.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSuccessful.setCanceledOnTouchOutside(false);
        dialogSuccessful.getWindow().setGravity(Gravity.CENTER);

        Button btn_yes = dialogSuccessful.findViewById(R.id.btn_yes);
        Button btn_no = dialogSuccessful.findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
            }
        });

        dialogSuccessful.show();
    }
}


