package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.live.stalkstockcommercial.ui.product.AddedProduct;
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit;
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit2;
import com.stalkstock.R;
import com.stalkstock.utils.ProductUnitData;
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

    Dialog detailDialog;
    ArrayList<ProductUnitData> listProductUnit  = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        TextView textView= findViewById(R.id.editappbar);
        TextView addproduct_unitmeasurement= findViewById(R.id.addproduct_unitmeasurement);


        ImageView imageView=findViewById(R.id.addproduct_backarrow);

        Button button = findViewById(R.id.addproduct_updatebutton);
        relativeLayout=findViewById(R.id.relative_imagesthree);
        ivImg=findViewById(R.id.uploadimagesthree);

        adduploadimages=findViewById(R.id.imagesthree);


        button.setOnClickListener(this);
        imageView.setOnClickListener(this);
        adduploadimages.setOnClickListener(this);

        addproduct_unitmeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUnitList();
            }
        });

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

    public  void unitMessurement(){
        final Dialog logoutUpdatedDialog2 = new  Dialog(this);
        logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutUpdatedDialog2.setContentView(R.layout.masurement_alert_box);

        logoutUpdatedDialog2.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        logoutUpdatedDialog2.setCancelable(true);
        logoutUpdatedDialog2.setCanceledOnTouchOutside(false);
        logoutUpdatedDialog2.getWindow().setGravity(Gravity.CENTER);

        logoutUpdatedDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout linearLayout =logoutUpdatedDialog2.findViewById(R.id.linear_measure);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUpdatedDialog2.dismiss();
            }
        });





        logoutUpdatedDialog2.show();
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


}