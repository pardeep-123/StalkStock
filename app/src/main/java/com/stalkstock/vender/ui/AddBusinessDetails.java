package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.stalkstock.advertiser.activities.LoginActivity;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class AddBusinessDetails extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
    ImageView setimage,cameraopen,backarrow;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_details);
         backarrow= findViewById(R.id.addbusinessbackarrow);
          button=findViewById(R.id.addbusinessavebutton);
        setimage=findViewById(R.id.add_imageset);
        cameraopen=findViewById(R.id.add_imgcamera);


        button.setOnClickListener(this);
        backarrow.setOnClickListener(this);
        cameraopen.setOnClickListener(this);
        setimage.setOnClickListener(this);

        Spinner spinner=findViewById(R.id.spinner);

        Spinner spinner_type=findViewById(R.id.spinner_type);

        ArrayAdapter foodadapter = ArrayAdapter.createFromResource(this, R.array.Select_country, R.layout.spinner_layout_for_vehicle);
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text);
        spinner.setAdapter(foodadapter);

        ArrayAdapter foodadapter2 = ArrayAdapter.createFromResource(this, R.array.Select_business_type, R.layout.spinner_layout_for_vehicle);
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text);
        spinner_type.setAdapter(foodadapter2);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){

            case R.id.addbusinessbackarrow:
                onBackPressed();
                break;


            case R.id.addbusinessavebutton:
                LayoutInflater inflater= LayoutInflater.from(AddBusinessDetails.this);
                View v= inflater.inflate(R.layout.addbussinessalertbox,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(AddBusinessDetails.this).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                deleteDialog.setView(v);
                Button btncontinue= v.findViewById(R.id.close_button);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        startActivity(new Intent(AddBusinessDetails.this, LoginActivity.class));



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();
                break;




            default:
                break;
            case R.id.add_imgcamera:
                askCameraPermissons();
                break;


        }


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
                        Glide.with(AddBusinessDetails.this).load(result.get(0).getPath()).into(setimage);
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