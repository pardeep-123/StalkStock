package com.stalkstock.vender.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class EditBussinessProfile extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";

    ImageView opencamera, setimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bussiness_profile);

        ImageView imageView = findViewById(R.id.edit_businessbackarrow);
        Button button = findViewById(R.id.businessupdatebutton);
        setimage=findViewById(R.id.business_imageset);
        opencamera = findViewById(R.id.business_imgcamera);
        opencamera.setOnClickListener(this);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater= LayoutInflater.from(EditBussinessProfile.this);
                View v= inflater.inflate(R.layout.businessprofilealertbox,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(EditBussinessProfile.this).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteDialog.setView(v);
                Button btncontinue= v.findViewById(R.id.done_button);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        startActivity(new Intent(EditBussinessProfile.this, BussinessProfile.class));



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        askCameraPermissons();

    }

    private void askCameraPermissons() {
        mAlbumFiles = new ArrayList();
        mAlbumFiles.clear();
        selectImage(setimage,"1");
    }

    private void selectImage(final ImageView setimage, final String s) {
        Album.image(this)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles.addAll(result);
                        Glide.with(EditBussinessProfile.this).load(result.get(0).getPath()).into(setimage);
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