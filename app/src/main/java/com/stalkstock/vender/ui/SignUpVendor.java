package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class SignUpVendor extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";

    ImageView camera, imageView,imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ImageView backarrow= findViewById(R.id.signupbackarrow);
        imageView=findViewById(R.id.signup_imageset);
       // imageView1=findViewById(R.id.ivImg);
        camera = findViewById(R.id.signup_imgcamera);
        Button signupbtn= findViewById(R.id.signupbutton);
        TextView signtext= findViewById(R.id.losignuptext);

        backarrow.setOnClickListener(this);
        signupbtn.setOnClickListener(this);
        signtext.setOnClickListener(this);
        camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){

            case R.id.signupbackarrow:
                onBackPressed();
                break;
            case R.id.signupbutton:
                startActivity(new Intent(SignUpVendor.this, BottomnavigationScreen.class));
//                startActivity(new Intent(SignUpVendor.this, Verification.class));
                break;
            case R.id.losignuptext:
                onBackPressed();
                break;
            case R.id.signup_imgcamera:
                askCameraPermissons();
        }
    }

    private void askCameraPermissons() {
        mAlbumFiles = new ArrayList();
        mAlbumFiles.clear();
        selectImage(imageView,"1");
    }

    private void selectImage(final ImageView imageView, final String s) {
        Album.image(this)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles.addAll(result);
                        Glide.with(SignUpVendor.this).load(result.get(0).getPath()).into(imageView);
                        if (s.equals("1"))
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
