package com.live.stalkstockcommercial.ui.product

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit
import com.stalkstock.R
import kotlinx.android.synthetic.main.added_product.*
import kotlinx.android.synthetic.main.dialog_home.*


class AddedProduct : AppCompatActivity() {


    var list : ArrayList<RequestProductData> = ArrayList()

    lateinit var detailDialog: Dialog
    var listProductUnit : ArrayList<ProductUnitData> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.added_product)


        list.add(RequestProductData("Meat", "Bacon Grill", "10", "Kg", edit = true, delete = true))
        list.add(RequestProductData("Meat", "Bacon Normal", "8", "Kg", edit = true, delete = true))

        rvRequestProducts.adapter = RequestProductAdapter(list)


        rlUnitMesurment.setOnClickListener {
            setUnitList()
        }



        btnSubmit.setOnClickListener {
            startActivity(Intent(this, AddThanks::class.java))
//            finish()

        }
        etUnitMeasurement.setOnClickListener {
            setUnitList()
        }
        tvUnitMeasurement.setOnClickListener {
            setUnitList()
        }

        back.setOnClickListener {
            onBackPressed()
        }

        tvAddMore.setOnClickListener {
            card_added.visibility = View.VISIBLE
        }

//
//        var  listAr=ArrayList<String>()
//       /* <item>Select Category</item>
//        <item>Vegetables</item>
//        <item>Fruits</item>
//        <item>Grains, Beans and Nuts</item>
//        <item>Meat and Poultry</item>
//        <item>Fish and Seafood</item>
//        <item>Dairy</item>
//        <item>Other (Please Specify Below)</item>*/
//        listAr.add("Select Category")
//         listAr.add("Vegetables")
//        listAr.add("Fruits")
//        listAr.add("Grains, Beans and Nuts")
//        listAr.add("Meat and Poultry")
//        listAr.add("Dairy")
//        listAr.add("Other (Please Specify Below)")
//
//        val adapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.Select_Category, R.layout.spinner_item_text
//        )
//        adapter.setDropDownViewResource(R.layout.spinner_item_text);
//       // var adapterss: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_item_text,listAr)
//       // adapterss.setDropDownViewResource(R.layout.spinner_item_text);
//        spinner.adapter=adapter


        val foodadapter = ArrayAdapter.createFromResource(
            this, R.array.Select_Category, R.layout.spinner_layout
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setUnitList() {

        listProductUnit.clear()
        listProductUnit.add(ProductUnitData("Volume","Teaspoon (t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Tablespoon ( T, tbl.,tbs., or tbsp.)",false))
        listProductUnit.add(ProductUnitData("","Fluid ounce ( fl oz )",false))
        listProductUnit.add(ProductUnitData("","Gill (about 1/2 cup)",false))
        listProductUnit.add(ProductUnitData("","Cup (c)",false))
        listProductUnit.add(ProductUnitData("","Pint (p,pt or ft pt)",false))
        listProductUnit.add(ProductUnitData("","Quart (q, qt, or fl qt)",false))
        listProductUnit.add(ProductUnitData("","Gallon (g or gal)",false))
        listProductUnit.add(ProductUnitData("","Milliliter, milliliter (cc, mL, ml)",false))
        listProductUnit.add(ProductUnitData("","Liter, liter (L, l)",false))
        listProductUnit.add(ProductUnitData("","Deciliter,deciliter (dL, dl)",false))
        listProductUnit.add(ProductUnitData("Mass and Weight","Pound (lb or #)",false))
         listProductUnit.add(ProductUnitData("","Ounce (oz)",false))
        listProductUnit.add(ProductUnitData("","Milligram/milligramme (mg)",false))
        listProductUnit.add(ProductUnitData("","Gram/gramme (g)",false))
        listProductUnit.add(ProductUnitData("","Kilogram/kilogramme (kg)",false))
        listProductUnit.add(ProductUnitData("Other","Individual Units",true))

        setDialog(listProductUnit)
    }

    private fun setDialog(

        listProductUnit: ArrayList<ProductUnitData>
    ) {

        detailDialog = Dialog(this)
        detailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        detailDialog.setContentView(R.layout.dialog_home)

        detailDialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        detailDialog.setCancelable(true)
        detailDialog.setCanceledOnTouchOutside(true)
        detailDialog.window!!.setGravity(Gravity.CENTER)

        detailDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        detailDialog.rvProductUnit.adapter = AdapterProductUnit(listProductUnit)
        detailDialog.llDialog.setOnClickListener { detailDialog.dismiss() }

        detailDialog.show()
    }

    data class RequestProductData(var name:String ="",var type:String ="",
                                  var quantity:String ="",var unit:String ="",var edit:Boolean =false,var delete:Boolean =false)

    data class ProductUnitData(var title:String =  "",var unit:String =  "",var selected:Boolean =  false)


}
