package com.stalkstock.vender.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
ImageView  backarrow ,ivImg,cameropen,visibaleimage,imagethree;
RelativeLayout relativeLayout,setimage;
TextView measurement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Button button=findViewById(R.id.addproductsubmitbutton);
        backarrow=findViewById(R.id.addproduct_backarrow);

        cameropen=findViewById(R.id.add_uploadimages);
        ivImg=findViewById(R.id.iv_Img);
        setimage=findViewById(R.id.add_product);

        relativeLayout= findViewById(R.id.add_images);
         visibaleimage = findViewById(R.id.add_deleteicon);
         imagethree=findViewById(R.id.add_uploadimagesthree);
         imagethree.setOnClickListener(this);
         measurement=findViewById(R.id.addproductmasurement);

        button.setOnClickListener(this);
        backarrow.setOnClickListener(this);
        cameropen.setOnClickListener(this);
        setimage.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        imagethree.setOnClickListener(this);
        ivImg.setOnClickListener(this);
        measurement.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.addproduct_backarrow:
                onBackPressed();
                break;


            case R.id.addproductsubmitbutton:
                LayoutInflater inflater= LayoutInflater.from(AddProduct.this);
                View v= inflater.inflate(R.layout.addproductalertbox,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(AddProduct.this).create();
                deleteDialog.setView(v);
                Button btncontinue= v.findViewById(R.id.gotomyproducts_button);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        startActivity(new Intent(AddProduct.this, BottomnavigationScreen.class));



                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();
             //   startActivity(new Intent(AddProduct.this, BottomnavigationScreen.class));
                break;
            case  R.id.add_uploadimages:

                askCameraPermissons();
                break;
            case R.id.add_uploadimagesthree:
                LayoutInflater inflat= LayoutInflater.from(AddProduct.this);
                View view1= inflat.inflate(R.layout.upgrade_alert_box,null);

                final AlertDialog delete = new AlertDialog.Builder(AddProduct.this).create();
                delete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                delete.setView(view1);
                Button button1 = view1.findViewById(R.id.upgrade_yes);
                Button button2=view1.findViewById(R.id.upgrade_cancel);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(AddProduct.this, Subscription.class));

                       delete.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delete.dismiss();
                    }
                });

                delete.show();
//                relativeLayout.setVisibility(View.VISIBLE);
//                visibaleimage.setVisibility(View.VISIBLE);
//                visibaleimage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//
//
//                    }
//                });

                break;
            case R.id.addproductmasurement:
                LayoutInflater inf= LayoutInflater.from(AddProduct.this);
                View view2= inf.inflate(R.layout.masurement_alert_box,null);

                final AlertDialog showdialog = new AlertDialog.Builder(AddProduct.this).create();
                showdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                showdialog.setView(view2);
                LinearLayout linearLayout =view2.findViewById(R.id.linear_measure);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showdialog.dismiss();
                    }
                });


                showdialog.show();





        }
    }

    private void askCameraPermissons() {
        mAlbumFiles = new ArrayList();
        mAlbumFiles.clear();
        selectImage(ivImg,"1");
    }

    private void selectImage(final ImageView ivImg, final String s) {
        Album.image(this)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles.addAll(result);
                        Glide.with(AddProduct.this).load(result.get(0).getPath()).into(ivImg);
                        if (s.equals("1"))
                        {
                            firstimage =result.get(0).getPath();
                            relativeLayout.setVisibility(View.VISIBLE);
                visibaleimage.setVisibility(View.VISIBLE);
                visibaleimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                    }
                });
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