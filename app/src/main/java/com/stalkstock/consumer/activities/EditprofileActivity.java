package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.stalkstock.R;
import java.util.Objects;

public class EditprofileActivity extends AppCompatActivity {
    EditprofileActivity context;
    Button btn_update_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        context=this;
        btn_update_profile=findViewById(R.id.btn_update_profile);
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartInfoApp();
            }
        });
    }

    public void openStartInfoApp() {
        final Dialog dialogSuccessful = new Dialog(Objects.requireNonNull(context), R.style.Theme_Dialog);
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccessful.setContentView(R.layout.update_successfully_alert);
        dialogSuccessful.setCancelable(false);
        Objects.requireNonNull(dialogSuccessful.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSuccessful.setCanceledOnTouchOutside(false);
        dialogSuccessful.getWindow().setGravity(Gravity.CENTER);

        Button btn_ok = dialogSuccessful.findViewById(R.id.btn_ok);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
                finish();
            }
        });

        dialogSuccessful.show();
    }
}
