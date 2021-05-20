package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit2
import com.stalkstock.R
import com.stalkstock.utils.ProductUnitData
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import java.util.*

class AddProduct : AppCompatActivity(), View.OnClickListener {
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    var firstimage = ""
    lateinit var backarrow: ImageView
    lateinit var ivImg: ImageView
    lateinit var cameropen: ImageView
    lateinit var visibaleimage: ImageView
    lateinit var detailDialog: Dialog
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()
    lateinit var imagethree: RelativeLayout
    lateinit var relativeLayout: RelativeLayout
    lateinit var setimage: RelativeLayout
    lateinit var measurement: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        val button = findViewById<Button>(R.id.addproductsubmitbutton)
        backarrow = findViewById(R.id.addproduct_backarrow)
        cameropen = findViewById(R.id.add_uploadimages)
        ivImg = findViewById(R.id.iv_Img)
        setimage = findViewById(R.id.add_product)
        relativeLayout = findViewById(R.id.add_images)
        visibaleimage = findViewById(R.id.add_deleteicon)
        measurement = findViewById(R.id.addproductmasurement)
        imagethree = findViewById(R.id.imagethree)
        button.setOnClickListener(this)
        backarrow.setOnClickListener(this)
        cameropen.setOnClickListener(this)
        setimage.setOnClickListener(this)
        relativeLayout.setOnClickListener(this)
        ivImg.setOnClickListener(this)
        measurement.setOnClickListener(this)
        imagethree.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.addproduct_backarrow -> onBackPressed()
            R.id.addproductsubmitbutton -> {
                val logoutUpdatedDialogs = Dialog(this)
                logoutUpdatedDialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
                logoutUpdatedDialogs.setContentView(R.layout.addproductalertbox)
                logoutUpdatedDialogs.window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                logoutUpdatedDialogs.setCancelable(true)
                logoutUpdatedDialogs.setCanceledOnTouchOutside(false)
                logoutUpdatedDialogs.window!!.setGravity(Gravity.CENTER)
                logoutUpdatedDialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val btncontinue =
                    logoutUpdatedDialogs.findViewById<Button>(R.id.gotomyproducts_button)
                btncontinue.setOnClickListener { //                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                    startActivity(Intent(this@AddProduct, BottomnavigationScreen::class.java))
                    finishAffinity()
                    logoutUpdatedDialogs.dismiss()
                }
                logoutUpdatedDialogs.show()
            }
            R.id.add_uploadimages -> askCameraPermissons()
            R.id.imagethree -> {
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
                val logoutUpdatedDialog = Dialog(this)
                logoutUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                logoutUpdatedDialog.setContentView(R.layout.upgrade_alert_box)
                logoutUpdatedDialog.window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                logoutUpdatedDialog.setCancelable(true)
                logoutUpdatedDialog.setCanceledOnTouchOutside(false)
                logoutUpdatedDialog.window!!.setGravity(Gravity.CENTER)
                logoutUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val button1 = logoutUpdatedDialog.findViewById<Button>(R.id.upgrade_yes)
                val button2 = logoutUpdatedDialog.findViewById<Button>(R.id.upgrade_cancel)
                button1.setOnClickListener {
                    startActivity(Intent(this@AddProduct, Subscription::class.java))
                    logoutUpdatedDialog.dismiss()
                }
                button2.setOnClickListener { logoutUpdatedDialog.dismiss() }
                logoutUpdatedDialog.show()
            }
            R.id.addproductmasurement ->                /* LayoutInflater inf= LayoutInflater.from(AddProduct.this);
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
                setUnitList()
        }
    }

    private fun setUnitList() {
        listProductUnit.clear()
        //        listProductUnit.add(new ProductUnitData("Volume", "Teaspoon (t or tsp.)", false));
        listProductUnit.add(ProductUnitData("", "Teaspoon (t or tsp.)", false))
        listProductUnit.add(ProductUnitData("", "Tablespoon ( T, tbl.,tbs., or tbsp.)", false))
        listProductUnit.add(ProductUnitData("", "Fluid ounce ( fl oz )", false))
        listProductUnit.add(ProductUnitData("", "Gill (about 1/2 cup)", false))
        listProductUnit.add(ProductUnitData("", "Cup (c)", false))
        listProductUnit.add(ProductUnitData("", "Pint (p,pt or ft pt)", false))
        listProductUnit.add(ProductUnitData("", "Quart (q, qt, or fl qt)", false))
        listProductUnit.add(ProductUnitData("", "Gallon (g or gal)", false))
        listProductUnit.add(ProductUnitData("", "Milliliter, milliliter (cc, mL, ml)", false))
        listProductUnit.add(ProductUnitData("", "Liter, liter (L, l)", false))
        listProductUnit.add(ProductUnitData("", "Deciliter,deciliter (dL, dl)", false))
        listProductUnit.add(ProductUnitData("", "Pound (lb or #)", false))
        listProductUnit.add(ProductUnitData("", "Ounce (oz)", false))
        listProductUnit.add(ProductUnitData("", "Milligram/milligramme (mg)", false))
        listProductUnit.add(ProductUnitData("", "Gram/gramme (g)", false))
        listProductUnit.add(ProductUnitData("", "Kilogram/kilogramme (kg)", false))
        listProductUnit.add(ProductUnitData("", "Individual Units", true))
        setDialog(listProductUnit)
    }

    private fun setDialog(listProductUnit: ArrayList<ProductUnitData>) {
        detailDialog = Dialog(this)
        detailDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        detailDialog!!.setContentView(R.layout.dialog_home)
        detailDialog!!.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
        detailDialog!!.setCancelable(true)
        detailDialog!!.setCanceledOnTouchOutside(true)
        detailDialog.window!!.setGravity(Gravity.CENTER)
        detailDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rvProductUnit: RecyclerView = detailDialog!!.findViewById(R.id.rvProductUnit)
        val llDialog = detailDialog!!.findViewById<LinearLayout>(R.id.llDialog)
        rvProductUnit.adapter = AdapterProductUnit2(listProductUnit)
        llDialog.setOnClickListener { detailDialog!!.dismiss() }
        detailDialog!!.show()
    }

    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList()
        mAlbumFiles.clear()
        selectImage(ivImg, "1")
    }

    private fun selectImage(ivImg: ImageView?, s: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this@AddProduct).load(result[0].path).into(ivImg!!)
                if (s == "1") {
                    firstimage = result[0].path
                    relativeLayout!!.visibility = View.VISIBLE
                    visibaleimage!!.visibility = View.VISIBLE
                    visibaleimage!!.setOnClickListener { }
                }
            }
            .onCancel { }
            .start()
    }
}