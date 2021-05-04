package com.stalkstock.vender.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.stalkstock.utils.others.CommonMethods;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class EditBussinessProfile extends AppCompatActivity  {
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

        setimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissons();
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Spinner spinner=findViewById(R.id.spinner);
        Spinner spinner_type=findViewById(R.id.spinner_type);

        ArrayAdapter foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_country, R.layout.spinner_layout_for_vehicle);
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text);
        spinner.setAdapter(foodadapter);


        ArrayAdapter foodadapter2 = ArrayAdapter.createFromResource(this, R.array.Select_business_type, R.layout.spinner_layout_for_vehicle);
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text);
        spinner_type.setAdapter(foodadapter2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  LayoutInflater inflater= LayoutInflater.from(EditBussinessProfile.this);
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
                      onBackPressed();



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();*/

                final Dialog logoutUpdatedDialog2 = new  Dialog(EditBussinessProfile.this);
                logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutUpdatedDialog2.setContentView(R.layout.businessprofilealertbox);

                logoutUpdatedDialog2.getWindow().setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                logoutUpdatedDialog2.setCancelable(true);
                logoutUpdatedDialog2.setCanceledOnTouchOutside(false);
                logoutUpdatedDialog2.getWindow().setGravity(Gravity.CENTER);

                logoutUpdatedDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                Button btncontinue= logoutUpdatedDialog2.findViewById(R.id.done_button);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        onBackPressed();



                        logoutUpdatedDialog2.dismiss();

                    }
                });

                logoutUpdatedDialog2.show();
            }
        });

        CommonMethods.hideKeyboard(this,opencamera);
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