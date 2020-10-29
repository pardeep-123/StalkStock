package com.live.stalkstockcommercial.ui.product

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
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


        list.add(RequestProductData("Meat","Bacon Grill","10","Kg", edit = true, delete = true))
        list.add(RequestProductData("Meat","Bacon Normal","8","Kg", edit = true, delete = true))

        rvRequestProducts.adapter = RequestProductAdapter(list)


        rlUnitMesurment.setOnClickListener {
            setUnitList()
        }

        btnSubmit.setOnClickListener {
            startActivity(Intent(this,AddThanks::class.java))
            finish()

        }
        etUnitMeasurement.setOnClickListener {
            setUnitList()
        }
        tvUnitMeasurement.setOnClickListener {
            setUnitList()
        }

    }

    private fun setUnitList() {

        listProductUnit.clear()
        listProductUnit.add(ProductUnitData("Volume","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("","Teaspoon(t or tsp.)",false))
        listProductUnit.add(ProductUnitData("Mass and Weight","Pound(lb or #)",false))
        listProductUnit.add(ProductUnitData("","Pound(lb or #)",false))
        listProductUnit.add(ProductUnitData("","Pound(lb or #)",false))
        listProductUnit.add(ProductUnitData("","Pound(lb or #)",false))
        listProductUnit.add(ProductUnitData("","Pound(lb or #)",false))
        listProductUnit.add(ProductUnitData("","Pound(lb or #)",false))
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
            WindowManager.LayoutParams.MATCH_PARENT,
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
