package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.stalkstock.utils.others.CommonMethods;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
    Context context;
    ImageView opencamera, setimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);
        ImageView imageView = findViewById(R.id.editprofile_backarrow);
        Button button = findViewById(R.id.editprofile_savebtn);
        setimage=findViewById(R.id.editprofile_imageset);
        opencamera=findViewById(R.id.editprofile_camera);
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
               /* LayoutInflater inflater= LayoutInflater.from(EditProfile.this);
                View v= inflater.inflate(R.layout.editprofilealertbox,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(EditProfile.this).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                deleteDialog.setView(v);
                Button btncontinue= v.findViewById(R.id.edit_donebtn);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                       Intent intent = new Intent(EditProfile.this, BottomnavigationScreen.class);
                        intent.putExtra("type","my");

                        startActivity(intent);
 //                      context.startActivity(new Intent(EditProfile.this, AccountFragment.class));



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();*/
                final Dialog logoutUpdatedDialog2 = new  Dialog(EditProfile.this);
                logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutUpdatedDialog2.setContentView(R.layout.editprofilealertbox);

                logoutUpdatedDialog2.getWindow().setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                logoutUpdatedDialog2.setCancelable(true);
                logoutUpdatedDialog2.setCanceledOnTouchOutside(false);
                logoutUpdatedDialog2.getWindow().setGravity(Gravity.CENTER);

                logoutUpdatedDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btncontinue= logoutUpdatedDialog2.findViewById(R.id.edit_donebtn);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Intent intent = new Intent(EditProfile.this, BottomnavigationScreen.class);
                        intent.putExtra("type","my");

                        startActivity(intent);*/
                       onBackPressed();
                        //                      context.startActivity(new Intent(EditProfile.this, AccountFragment.class));



                        logoutUpdatedDialog2.dismiss();

                    }
                });




                logoutUpdatedDialog2.show();
            }
        });

        CommonMethods.hideKeyboard(this,opencamera);
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
                        Glide.with(EditProfile.this).load(result.get(0).getPath()).into(setimage);
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