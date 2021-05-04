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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit2;
import com.stalkstock.R;
import com.stalkstock.utils.ProductUnitData;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
ImageView  backarrow ,ivImg,cameropen,visibaleimage;

    Dialog detailDialog;
    ArrayList<ProductUnitData> listProductUnit  = new ArrayList();


RelativeLayout imagethree;


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

         measurement=findViewById(R.id.addproductmasurement);
        imagethree=findViewById(R.id.imagethree);

        button.setOnClickListener(this);
        backarrow.setOnClickListener(this);
        cameropen.setOnClickListener(this);
        setimage.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
         ivImg.setOnClickListener(this);
        measurement.setOnClickListener(this);
        imagethree.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.addproduct_backarrow:
                onBackPressed();
                break;


            case R.id.addproductsubmitbutton:
                final Dialog logoutUpdatedDialogs = new  Dialog(this);
                logoutUpdatedDialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutUpdatedDialogs.setContentView(R.layout.addproductalertbox);

                logoutUpdatedDialogs.getWindow().setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                logoutUpdatedDialogs.setCancelable(true);
                logoutUpdatedDialogs.setCanceledOnTouchOutside(false);
                logoutUpdatedDialogs.getWindow().setGravity(Gravity.CENTER);

                logoutUpdatedDialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btncontinue= logoutUpdatedDialogs.findViewById(R.id.gotomyproducts_button);

                btncontinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                        startActivity(new Intent(AddProduct.this, BottomnavigationScreen.class));

                        finishAffinity();


                        logoutUpdatedDialogs.dismiss();

                    }
                });



                logoutUpdatedDialogs.show();


  /*  LayoutInflater inflater= LayoutInflater.from(AddProduct.this);
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

                        finishAffinity();


                        deleteDialog.dismiss();

                    }
                });

                deleteDialog.show();*/
             //   startActivity(new Intent(AddProduct.this, BottomnavigationScreen.class));
                break;
            case  R.id.add_uploadimages:

              askCameraPermissons();
                break;
            case R.id.imagethree:
//                LayoutInflater inflat= LayoutInflater.from(AddProduct.this);
//                View view1= inflat.inflate(R.layout.upgrade_alert_box,null);
//
//                final AlertDialog delete = new AlertDialog.Builder(AddProduct.this).create();
//                delete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                delete.setView(view1);
//                Button button1 = view1.findViewById(R.id.upgrade_yes);
//                Button button2=view1.findViewById(R.id.upgrade_cancel);
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        startActivity(new Intent(AddProduct.this, Subscription.class));
//
//                       delete.dismiss();
//                    }
//                });
//                button2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        delete.dismiss();
//                    }
//                });
//
//                delete.show();

                final Dialog logoutUpdatedDialog = new  Dialog(this);
                logoutUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutUpdatedDialog.setContentView(R.layout.upgrade_alert_box);

                logoutUpdatedDialog.getWindow().setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                logoutUpdatedDialog.setCancelable(true);
                logoutUpdatedDialog.setCanceledOnTouchOutside(false);
                logoutUpdatedDialog.getWindow().setGravity(Gravity.CENTER);

                logoutUpdatedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button button1 = logoutUpdatedDialog.findViewById(R.id.upgrade_yes);
                Button button2=logoutUpdatedDialog.findViewById(R.id.upgrade_cancel);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(AddProduct.this, Subscription.class));


                        logoutUpdatedDialog.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logoutUpdatedDialog.dismiss();
                    }
                });


                logoutUpdatedDialog.show();

                break;
            case R.id.addproductmasurement:
               /* LayoutInflater inf= LayoutInflater.from(AddProduct.this);
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
*/

//                final Dialog logoutUpdatedDialog2 = new  Dialog(this, R.style.Theme_Dialog);
//                logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                logoutUpdatedDialog2.setContentView(R.layout.masurement_alert_box);
//
//                logoutUpdatedDialog2.getWindow().setLayout(
//                        WindowManager.LayoutParams.MATCH_PARENT,
//                        WindowManager.LayoutParams.WRAP_CONTENT
//                );
//                logoutUpdatedDialog2.setCancelable(true);
//                logoutUpdatedDialog2.setCanceledOnTouchOutside(false);
//                logoutUpdatedDialog2.getWindow().setGravity(Gravity.CENTER);
//
//                logoutUpdatedDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                LinearLayout linearLayout =logoutUpdatedDialog2.findViewById(R.id.linear_measure);
//                linearLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        logoutUpdatedDialog2.dismiss();
//                    }
//                });
//
//                logoutUpdatedDialog2.show();

                setUnitList();

        }
    }


    private void  setUnitList() {

        listProductUnit.clear();
        listProductUnit.add(new ProductUnitData("Volume","Teaspoon (t or tsp.)",false));
        listProductUnit.add(new ProductUnitData("","Tablespoon ( T, tbl.,tbs., or tbsp.)",false));
        listProductUnit.add( new ProductUnitData("","Fluid ounce ( fl oz )",false));
        listProductUnit.add(new ProductUnitData("","Gill (about 1/2 cup)",false));
        listProductUnit.add( new ProductUnitData("","Cup (c)",false));
        listProductUnit.add(new ProductUnitData("","Pint (p,pt or ft pt)",false));
        listProductUnit.add(new ProductUnitData("","Quart (q, qt, or fl qt)",false));
        listProductUnit.add(new ProductUnitData("","Gallon (g or gal)",false));
        listProductUnit.add(new ProductUnitData("","Milliliter, milliliter (cc, mL, ml)",false));
        listProductUnit.add(new ProductUnitData("","Liter, liter (L, l)",false));
        listProductUnit.add(new ProductUnitData("","Deciliter,deciliter (dL, dl)",false));
        listProductUnit.add(new ProductUnitData("Mass and Weight","Pound (lb or #)",false));
        listProductUnit.add(new ProductUnitData("","Ounce (oz)",false));
        listProductUnit.add(new ProductUnitData("","Milligram/milligramme (mg)",false));
        listProductUnit.add(new ProductUnitData("","Gram/gramme (g)",false));
        listProductUnit.add(new ProductUnitData("","Kilogram/kilogramme (kg)",false));
        listProductUnit.add(new ProductUnitData("Other","Individual Units",true));

        setDialog(listProductUnit);
    }

    private void setDialog(ArrayList<ProductUnitData> listProductUnit ) {

        detailDialog =new  Dialog(this);
        detailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        detailDialog.setContentView(R.layout.dialog_home);

        detailDialog.getWindow().setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        detailDialog.setCancelable(true);
        detailDialog.setCanceledOnTouchOutside(true);
        detailDialog.getWindow().setGravity(Gravity.CENTER);

        detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        RecyclerView rvProductUnit=detailDialog.findViewById(R.id.rvProductUnit);
        LinearLayout llDialog=detailDialog.findViewById(R.id.llDialog);

        rvProductUnit.setAdapter( new AdapterProductUnit2(listProductUnit));
        llDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailDialog.dismiss();
            }
        });


        detailDialog.show();
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