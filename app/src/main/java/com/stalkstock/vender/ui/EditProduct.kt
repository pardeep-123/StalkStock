package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
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

class EditProduct : AppCompatActivity(), View.OnClickListener {
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    var firstimage = ""
    var m: Context? = null
    var ivImg: ImageView? = null
    lateinit var adduploadimages: ImageView
    var relativeLayout: RelativeLayout? = null
    var detailDialog: Dialog? = null
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)
        val textView = findViewById<TextView>(R.id.editappbar)
        val addproduct_unitmeasurement = findViewById<TextView>(R.id.addproduct_unitmeasurement)
        val imageView = findViewById<ImageView>(R.id.addproduct_backarrow)
        val button = findViewById<Button>(R.id.addproduct_updatebutton)
        relativeLayout = findViewById(R.id.relative_imagesthree)
        ivImg = findViewById(R.id.uploadimagesthree)
        adduploadimages = findViewById(R.id.imagesthree)
        button.setOnClickListener(this)
        imageView.setOnClickListener(this)
        adduploadimages.setOnClickListener(this)
        addproduct_unitmeasurement.setOnClickListener { setUnitList() }
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.addproduct_backarrow -> onBackPressed()
            R.id.addproduct_updatebutton -> {
                val inflater = LayoutInflater.from(this@EditProduct)
                val v = inflater.inflate(R.layout.editproductalertbox, null)
                val deleteDialog = AlertDialog.Builder(this@EditProduct).create()
                deleteDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                deleteDialog.setView(v)
                val btnyes = v.findViewById<Button>(R.id.edit_Yesbtn)
                val btncno = v.findViewById<Button>(R.id.edit_Nobtn)
                btnyes.setOnClickListener {
                    btnyes.background = resources.getDrawable(R.drawable.buttonshape)
                    btncno.background = resources.getDrawable(R.drawable.edittextshape)
                    btnyes.setTextColor(resources.getColor(R.color.white))
                    btncno.setTextColor(resources.getColor(R.color.balck))
                    //                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
                    startActivity(Intent(this@EditProduct, BottomnavigationScreen::class.java))
                    deleteDialog.dismiss()
                }
                btncno.setOnClickListener {
                    btncno.background = resources.getDrawable(R.drawable.buttonshape)
                    btnyes.background = resources.getDrawable(R.drawable.edittextshape)
                    btnyes.setTextColor(resources.getColor(R.color.white))
                    btncno.setTextColor(resources.getColor(R.color.balck))
                    deleteDialog.dismiss()
                }
                deleteDialog.show()
            }
            R.id.imagesthree -> askCameraPermissons()
        }
    }

    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList()
        mAlbumFiles.clear()
        selectImage(ivImg, "1")
    }

    private fun selectImage(adduploadimages: ImageView?, s: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this@EditProduct).load(result[0].path).into(adduploadimages!!)
                if (s == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel { }
            .start()
    }

    fun unitMessurement() {
        val logoutUpdatedDialog2 = Dialog(this)
        logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialog2.setContentView(R.layout.masurement_alert_box)
        logoutUpdatedDialog2.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialog2.setCancelable(true)
        logoutUpdatedDialog2.setCanceledOnTouchOutside(false)
        logoutUpdatedDialog2.window!!.setGravity(Gravity.CENTER)
        logoutUpdatedDialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val linearLayout = logoutUpdatedDialog2.findViewById<LinearLayout>(R.id.linear_measure)
        linearLayout.setOnClickListener { logoutUpdatedDialog2.dismiss() }
        logoutUpdatedDialog2.show()
    }

    private fun setUnitList() {
        listProductUnit.clear()
        listProductUnit.add(ProductUnitData("Volume", "Teaspoon (t or tsp.)", false))
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
        listProductUnit.add(ProductUnitData("Mass and Weight", "Pound (lb or #)", false))
        listProductUnit.add(ProductUnitData("", "Ounce (oz)", false))
        listProductUnit.add(ProductUnitData("", "Milligram/milligramme (mg)", false))
        listProductUnit.add(ProductUnitData("", "Gram/gramme (g)", false))
        listProductUnit.add(ProductUnitData("", "Kilogram/kilogramme (kg)", false))
        listProductUnit.add(ProductUnitData("Other", "Individual Units", true))
        setDialog(listProductUnit)
    }

    private fun setDialog(listProductUnit: ArrayList<ProductUnitData>) {
        detailDialog = Dialog(this)
        detailDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        detailDialog!!.setContentView(R.layout.dialog_home)
        detailDialog!!.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        detailDialog!!.setCancelable(true)
        detailDialog!!.setCanceledOnTouchOutside(true)
        detailDialog!!.window!!.setGravity(Gravity.CENTER)
        detailDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rvProductUnit: RecyclerView = detailDialog!!.findViewById(R.id.rvProductUnit)
        val llDialog = detailDialog!!.findViewById<LinearLayout>(R.id.llDialog)
        rvProductUnit.adapter = AdapterProductUnit2(listProductUnit)
        llDialog.setOnClickListener { detailDialog!!.dismiss() }
        detailDialog!!.show()
    }
}