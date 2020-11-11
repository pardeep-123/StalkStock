package com.stalkstock.consumer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stalkstock.R;
import com.stalkstock.consumer.adapter.MangeaddressAdapter;


public class ManageaddressActivity extends AppCompatActivity {
    ManageaddressActivity context;
    RecyclerView mangeaddress_recycle;
    MangeaddressAdapter adapter;
    Button btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageaddress);
        context=this;
        mangeaddress_recycle=findViewById(R.id.mangeaddress_recycle);
        btn_signup=findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent=new Intent(context,EditAddressActivity.class);
                intent.putExtra("key","add");
                startActivity(intent);*/
            }
        });


        // tbnAdd.setOnClickListener {
        //            val intent = Intent(this, AddAddress::class.java)
        //            intent.putExtra("key","add")
        //            startActivity(intent)
        //        }
        //
        //        btnEdit.setOnClickListener {
        //            val intent = Intent(this, AddAddress::class.java)
        //            intent.putExtra("key","edit")
        //            startActivity(intent)
        //        }



        adapter = new MangeaddressAdapter(context);
        mangeaddress_recycle.setLayoutManager(new LinearLayoutManager(context));
        mangeaddress_recycle.setAdapter(adapter);
    }
}
