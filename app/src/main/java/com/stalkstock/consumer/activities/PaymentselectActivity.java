package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stalkstock.R;


public class PaymentselectActivity extends AppCompatActivity {
    PaymentselectActivity context;
    ImageView oneone,onetwo;
    LinearLayout firstclick,secondclick;
    Button btn_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentselect);
        context=this;
        oneone=findViewById(R.id.oneone);
        onetwo=findViewById(R.id.onetwo);
        btn_preview=findViewById(R.id.btn_preview);

        firstclick=findViewById(R.id.firstclick);
        secondclick=findViewById(R.id.secondclick);

        firstclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneone.setImageResource(R.drawable.radio_fill);
                onetwo.setImageResource(R.drawable.radio_circle);
            }
        });
        secondclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onetwo.setImageResource(R.drawable.radio_fill);
                oneone.setImageResource(R.drawable.radio_circle);
            }
        });

        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,AddcardpaymentActivity.class);
                startActivity(intent);

               /* if(AppController.getInstance().getString("usertype").equals("3")){
                    val intent = Intent(mContext, ThanksActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(mContext, ThankyouActivity::class.java)
                    startActivity(intent)
                }*/
            }
        });
    }
}
