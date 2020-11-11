package com.stalkstock.vender.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.stalkstock.R;

public class ChatBox extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_GALLERY = 1;

    int[] userimage = {R.drawable.chat_img_1};
    int[] senderimage = {R.drawable.chat_img_2};
    String[] userchat = {"Whats Up", "Hello"};
    String[] usertime = {"12.15 PM", "12.17 PM"};
    String[] senderchatbox = {"Hello", "WhatsUp"};
    String[] sendertime = {"12.16 PM", "12.18 PM"};
    ImageView  attachment;
    LinearLayout sideclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        ImageView imgbackarrow = findViewById(R.id.chat_backarrow);
        sideclick = findViewById(R.id.chat_moreverticon);
        attachment = findViewById(R.id.attachment);

        ListView listitem = findViewById(R.id.chatitem);
        ChatAdapter chatAdapter = new ChatAdapter();
        listitem.setAdapter(chatAdapter);
        sideclick.setOnClickListener(this);
        imgbackarrow.setOnClickListener(this);
        attachment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.chat_backarrow:
                onBackPressed();
                break;

            case R.id.chat_moreverticon:
                LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = layoutInflater.inflate(R.layout.chatboxpopup, null);
                final PopupWindow changeSortPopUp = new PopupWindow(this);
                changeSortPopUp.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                changeSortPopUp.setOutsideTouchable(true);
                changeSortPopUp.setFocusable(true);

                changeSortPopUp.setContentView(layout);

                //    changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                // changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                changeSortPopUp.showAtLocation(layout, Gravity.TOP, 350, 200);
                TextView side = layout.findViewById(R.id.chat_moreverticon);

                final TextView report = layout.findViewById(R.id.clearchat);
                final ImageView toggle = layout.findViewById(R.id.toggle);
                final ImageView toggle_off = layout.findViewById(R.id.toggle_off);

                toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggle_off.setVisibility(View.VISIBLE);
                        toggle.setVisibility(View.GONE);

                    }
                });
                toggle_off.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggle_off.setVisibility(View.GONE);
                        toggle.setVisibility(View.VISIBLE);

                    }
                });

                report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeSortPopUp.dismiss();
                        int id = view.getId();

                        switch (id) {
                            case R.id.clearchat:
                                LayoutInflater inflater = LayoutInflater.from(ChatBox.this);
                                View v = inflater.inflate(R.layout.clearchatalert, null);
                                final AlertDialog deleteDialog = new AlertDialog.Builder(ChatBox.this).create();
                                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                deleteDialog.setView(v);
                                final Button btndel = v.findViewById(R.id.reportokbtn);
                                final Button btncancel = v.findViewById(R.id.reportcanclebtn);


                                btndel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btndel.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                                        btncancel.setBackground(getResources().getDrawable(R.drawable.edittextshape));
                                        btndel.setTextColor(getResources().getColor(R.color.white));
                                        btncancel.setTextColor(getResources().getColor(R.color.green));

                                        Intent intent = new Intent(ChatBox.this, BottomnavigationScreen.class);
                                        intent.putExtra("data", "m");
                                        startActivity(intent);
                                        //startActivity(new Intent(ChatBox.this, MessageFragment.class));


                                        //  deleteDialog.dismiss();

                                    }
                                });
                                btncancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btncancel.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                                        btndel.setBackground(getResources().getDrawable(R.drawable.edittextshape));
                                        btncancel.setTextColor(getResources().getColor(R.color.white));
                                        btndel.setTextColor(getResources().getColor(R.color.green));
                                          deleteDialog.dismiss();

                                    }
                                });
                                deleteDialog.show();
                                break;


                            default:
                        }


                    }
                });
                break;
            case R.id.attachment:
                click();
                break;


        }

    }

    private void click() {
        try {
            if (ActivityCompat.checkSelfPermission(ChatBox.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ChatBox.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

        class ChatAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return userchat.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View v, ViewGroup viewGroup) {
                View view = getLayoutInflater().inflate(R.layout.chatboxlist, null);
                ImageView usrimage = view.findViewById(R.id.user_imge);
                ImageView senderimag = view.findViewById(R.id.sender_image);
                TextView usertext = view.findViewById(R.id.chattext);
                TextView usertimer = view.findViewById(R.id.userchattime);
                TextView sendchat = view.findViewById(R.id.chatsendtext);
                TextView sendtimer = view.findViewById(R.id.sendertimer);
                usrimage.setImageResource(R.drawable.chat_img_1);
                senderimag.setImageResource(R.drawable.chat_img_2);
                usertext.setText(userchat[i]);
                usertimer.setText(usertime[i]);
                sendchat.setText(senderchatbox[i]);
                sendtimer.setText(sendertime[i]);


                return view;
            }
        }
    }
