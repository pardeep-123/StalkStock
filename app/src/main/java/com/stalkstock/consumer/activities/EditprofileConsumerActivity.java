package com.stalkstock.consumer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stalkstock.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Objects;

public class EditprofileConsumerActivity extends AppCompatActivity {

    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    String firstimage="";
    EditprofileConsumerActivity context;
    Button btn_update_profile;
    ImageView image,back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        context=this;
        btn_update_profile=findViewById(R.id.btn_update_profile);
        image = findViewById(R.id.image);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartInfoApp();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(image,"1") ;
            }
        });
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
                        Glide.with(EditprofileConsumerActivity.this).load(result.get(0).getPath()).into(ivProduct);
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

    public void openStartInfoApp() {
        final Dialog dialogSuccessful = new Dialog(Objects.requireNonNull(context), R.style.Theme_Dialog);
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccessful.setContentView(R.layout.update_successfully_alert2);
        dialogSuccessful.setCancelable(false);
        Objects.requireNonNull(dialogSuccessful.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogSuccessful.setCanceledOnTouchOutside(false);
        dialogSuccessful.getWindow().setGravity(Gravity.CENTER);

        Button btn_ok = dialogSuccessful.findViewById(R.id.btn_ok);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccessful.dismiss();
                finish();
            }
        });

        dialogSuccessful.show();
    }
}
