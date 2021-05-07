package com.stalkstock.consumer.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;


public class SignupConsumerActivity extends AppCompatActivity {

    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
    SignupConsumerActivity context;
    ImageView img,iv_back;
    TextView tv_signin;
    Button btn_signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_consumer);

        context=this;
        img=findViewById(R.id.img);
        btn_signup=findViewById(R.id.btn_signup);
        tv_signin=findViewById(R.id.tv_signin);
        iv_back=findViewById(R.id.iv_back);
        clicks();

    }

    private void clicks() {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlbumFiles = new ArrayList();
                mAlbumFiles.clear();
                selectImage(img,"1");
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              finish();
            }
        });


        tv_signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }

    private void callMethod() {
    }

    private void selectImage(final ImageView ivProduct, final String mediType) {
        Album.image(this)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles.addAll(result);
                        Glide.with(SignupConsumerActivity.this).load(result.get(0).getPath()).into(ivProduct);
                        if (mediType.equals("1"))
                        {
                            firstimage =result.get(0).getPath();
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }


}

