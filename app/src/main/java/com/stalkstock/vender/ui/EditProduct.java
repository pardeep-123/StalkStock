package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
 import com.stalkstock.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class EditProduct extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
    Context m;
    ImageView ivImg,adduploadimages;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        TextView textView= findViewById(R.id.editappbar);


        ImageView imageView=findViewById(R.id.addproduct_backarrow);

        Button button = findViewById(R.id.addproduct_updatebutton);
        relativeLayout=findViewById(R.id.relative_imagesthree);
        ivImg=findViewById(R.id.uploadimagesthree);

        adduploadimages=findViewById(R.id.imagesthree);


        button.setOnClickListener(this);
        imageView.setOnClickListener(this);
        adduploadimages.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.addproduct_backarrow:
                onBackPressed();
                break;


            case R.id.addproduct_updatebutton:

                LayoutInflater inflater= LayoutInflater.from(EditProduct.this);
                View v= inflater.inflate(R.layout.editproductalertbox,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(EditProduct.this).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                deleteDialog.setView(v);
                final Button btnyes= v.findViewById(R.id.edit_Yesbtn);
                final Button btncno= v.findViewById(R.id.edit_Nobtn);


                btnyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnyes.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        btncno.setBackground(getResources().getDrawable(R.drawable.edittextshape));
                        btnyes.setTextColor(getResources().getColor(R.color.white));
                        btncno.setTextColor(getResources().getColor(R.color.balck));
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        startActivity(new Intent(EditProduct.this, BottomnavigationScreen.class));



                        deleteDialog.dismiss();

                    }
                });
                btncno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btncno.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        btnyes.setBackground(getResources().getDrawable(R.drawable.edittextshape));
                        btnyes.setTextColor(getResources().getColor(R.color.white));
                        btncno.setTextColor(getResources().getColor(R.color.balck));
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
                //   startActivity(new Intent(AddProduct.this, BottomnavigationScreen.class));
                break;
            case R.id.imagesthree:
                askCameraPermissons();



        }

    }

    private void askCameraPermissons() {
        mAlbumFiles = new ArrayList();
        mAlbumFiles.clear();
        selectImage(ivImg,"1");

    }

    private void selectImage(final ImageView adduploadimages, final String s) {
        Album.image(this)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles.addAll(result);
                        Glide.with(EditProduct.this).load(result.get(0).getPath()).into(adduploadimages);
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