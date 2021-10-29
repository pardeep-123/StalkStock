package com.stalkstock.vender.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stalkstock.R;
import com.stalkstock.driver.AddBankAccount;

public class PaymentAccounts extends AppCompatActivity {
    ImageView radio_fillone, radio_emptyone, radio_emptytwo, mastercardelete;
    RelativeLayout rl1, rl2, rl3;

    ImageView visadelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_accounts);
        ImageView imageView = findViewById(R.id.paymentsbackarrow);
        mastercardelete = findViewById(R.id.mastercarddelete);

        rl1 = findViewById(R.id.rlone);
        rl2 = findViewById(R.id.rltwo);
        rl3 = findViewById(R.id.rlthree);

        radio_fillone = findViewById(R.id.radio_fillone);
        radio_emptyone = findViewById(R.id.radio_emptyone);
        radio_emptytwo = findViewById(R.id.radio_emptytwo);
        visadelete = findViewById(R.id.visadelete);
        Button button = findViewById(R.id.paymentsaccbutton);

        imageView.setOnClickListener(view -> onBackPressed());
        rl1.setOnClickListener(view -> {
            radio_fillone.setImageResource(R.drawable.radio_fill);
            radio_emptyone.setImageResource(R.drawable.radio_circle);

        });
        rl2.setOnClickListener(view -> {
            radio_emptyone.setImageResource(R.drawable.radio_fill);
            radio_fillone.setImageResource(R.drawable.radio_circle);

        });
        rl3.setOnClickListener(view -> {
            radio_emptytwo.setImageResource(R.drawable.radio_fill);
            radio_fillone.setImageResource(R.drawable.radio_circle);
        });
        visadelete.setOnClickListener(view -> {
            final Dialog logoutUpdatedDialog2 = new  Dialog(PaymentAccounts.this);
            logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            logoutUpdatedDialog2.setContentView(R.layout.deletecardalert);

            logoutUpdatedDialog2.getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            logoutUpdatedDialog2.setCancelable(true);
            logoutUpdatedDialog2.setCanceledOnTouchOutside(false);
            logoutUpdatedDialog2.getWindow().setGravity(Gravity.CENTER);
            logoutUpdatedDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btncontinue = logoutUpdatedDialog2.findViewById(R.id.yesbtn);
            Button btnno = logoutUpdatedDialog2.findViewById(R.id.nobtn);
            TextView tv_msg=logoutUpdatedDialog2.findViewById(R.id.tv_msg);
            tv_msg.setText("Are you sure you want to remove this payment method?");
            btncontinue.setOnClickListener(v -> logoutUpdatedDialog2.dismiss());
            btnno.setOnClickListener(view1 -> logoutUpdatedDialog2.dismiss());
            logoutUpdatedDialog2.show();
        });

        button.setOnClickListener(view -> startActivity(new Intent(PaymentAccounts.this, AddBankAccount.class)));

    }
}