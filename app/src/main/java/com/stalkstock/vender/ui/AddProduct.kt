package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit2
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.ProductUnitData
import com.stalkstock.utils.custom.TitiliumBoldTextView
import com.stalkstock.utils.custom.TitiliumRegularTextView
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelAddProduct
import com.stalkstock.vender.adapter.AddEditImageModel
import com.stalkstock.viewmodel.HomeViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product.spinner
import kotlinx.android.synthetic.main.activity_add_product.spinnerCountry
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.added_product.*
import okhttp3.RequestBody

class AddProduct : BaseActivity(), View.OnClickListener, Observer<RestObservable>{

    private var curreMeasurementId = ""
    private lateinit var adapterMeasurements: AdapterProductUnit2
    private var arrStringMultipleImages: ArrayList<AddEditImageModel> = ArrayList()
    private var currentModelMeasurements: ArrayList<ModelMeasurementList.Body> = ArrayList()
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()
    private var currentCatId = ""
    private var currentSubCatId = ""
    private var productId = ""
    private var price = ""
    private var currentTags = ""

    private var reset = false
    private var currentOffsets = 0
    private var currentOffset = 0
    var firstimage = ""
    lateinit var backarrow: ImageView
    lateinit var ivImg: ImageView
    lateinit var cameropen: ImageView
    lateinit var visibaleimage: ImageView
    lateinit var detailDialog: Dialog
    //lateinit var imagethree: RelativeLayout
    //lateinit var relativeLayout: RelativeLayout
    lateinit var setimage: RelativeLayout
    lateinit var measurement: TextView



    override fun getContentId(): Int {
        return R.layout.activity_add_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = findViewById<Button>(R.id.addproductsubmitbutton)
        backarrow = findViewById(R.id.addproduct_backarrow)
        cameropen = findViewById(R.id.add_uploadimages)
        ivImg = findViewById(R.id.iv_Img)
        setimage = findViewById(R.id.add_product)
        visibaleimage = findViewById(R.id.add_deleteicon)
        measurement = findViewById(R.id.addproductmasurement)


       // imagethree.setOnClickListener(this)
        button.setOnClickListener(this)
        backarrow.setOnClickListener(this)
        cameropen.setOnClickListener(this)
        setimage.setOnClickListener(this)
       // ivUpload.setOnClickListener(this)
        measurement.setOnClickListener(this)

        val countryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        countryAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinnerCountry.adapter = countryAdapter

        spinnerCountry.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position!=0){
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddProduct, R.color.black_color
                        )
                    )
                }
                else{
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddProduct, R.color.black_color
                        )
                    )
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        val prodTypeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_product_type,
            R.layout.spinner_layout_for_vehicle
        )
        prodTypeAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinnerProdType.adapter = prodTypeAdapter

        spinnerProdType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position!=0){
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddProduct, R.color.black_color
                        )
                    )
                }
                else{
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddProduct, R.color.black_color
                        )
                    )
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


         val availAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_Category_available,
            R.layout.spinner_layout_for_vehicle
        )
        availAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = availAdapter

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position!=0){
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddProduct, R.color.black_color
                        )
                    )
                }
                else{
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddProduct, R.color.sort_popup_gray_color
                        )
                    )
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        try {
            val args = intent.getBundleExtra("images")
            arrStringMultipleImages = args!!.getSerializable("ARRAYLIST") as ArrayList<AddEditImageModel>
            currentCatId = intent.getStringExtra("catId")!!
            currentSubCatId = intent.getStringExtra("subCatId")!!
            currentTags = intent.getStringExtra("tags")!!
            productId = intent.getStringExtra("productId")!!


        } catch (e: Exception) {
            e.printStackTrace()
        }
        adapterMeasurements = AdapterProductUnit2(this, listProductUnit)
        getMeasurementAPI()
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getMeasurementAPI() {
        viewModel.measurementListAPI(this, true)
        viewModel.homeResponse.observe(this, this)
    }



    override fun onClick(view: View) {
        val id = view.id
        when (id) {

            R.id.addproduct_backarrow -> onBackPressed()
            R.id.addproductsubmitbutton -> {
                if (validations()) {
                    Log.e(
                        "currentadhhsa>>",
                        curreMeasurementId + "---" + spinner.selectedItemPosition.toString() + "cat:$currentCatId subcat::$currentSubCatId tags: $currentTags"
                    )
                    addproductAPI()
                }
//                    addProductAlertDialog()
            }
           // R.id.add_uploadimages ->
                //askCameraPermissons()

            R.id.addproductmasurement ->
                setUnitList()
        }
    }

    private fun addproductAPI() {

       /* if(mAlbumFilesMultiple.size==0){
            AppUtils.showErrorAlert(this, "Please upload atleast one photo of the product")
            return
        }*/
        var map = HashMap<String, RequestBody>()
        map["categoryId"] = mUtils.createPartFromString(currentCatId)
        map["subCategoryId"] = mUtils.createPartFromString(currentSubCatId)
        map["measurementId"] = mUtils.createPartFromString(curreMeasurementId)
        map["tag"] = mUtils.createPartFromString(currentTags)
        map["productId"] = mUtils.createPartFromString(productId)
        map["description"] = mUtils.createPartFromString(addproductdescription.text.toString())
        map["brandName"] = mUtils.createPartFromString(addproductbrand.text.toString())
        map["mrp"] = mUtils.createPartFromString(price)
        map["country"] = mUtils.createPartFromString(spinnerCountry.selectedItem.toString())

        //productType(0=>veg 1=>non veg)
        var avail=0
        var productType=0
        if(spinner.selectedItemPosition==1){
            avail=1
        }else{
            avail=0
        }

        if(spinnerProdType.selectedItemPosition==1){
            productType=0
        }else{
            productType=1
        }

        map["availability"] = mUtils.createPartFromString(avail.toString())
       // map["productType"] = mUtils.createPartFromString(productType.toString())
        var arrayList: ArrayList<String> = ArrayList()
        for (i in arrStringMultipleImages) {
            arrayList.add(i.name!!)
        }
        viewModel.vendorAddProductAPI(this, true, map, arrayList, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    private fun validations(): Boolean {
       if (addproductbrand.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter brand")
            return false
        }   /*else if(spinnerProdType.selectedItemPosition == 0) {
            spinnerProdType.requestFocus()
            AppUtils.showErrorAlert(this, getString(R.string.please_select_product_type))
            return false
        }*/
        else if(spinnerCountry.selectedItemPosition==0) {
            AppUtils.showErrorAlert(this, "Please select country")
            return false
        } else if (curreMeasurementId.trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please select measurement")
            return false
        }  else if (addproductdescription.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter description")
            return false
        }else if (spinner.selectedItemPosition==0) {
            AppUtils.showErrorAlert(this, "Please select product availability")
            return false
        } else
            return true
    }

    private fun addProductAlertDialog() {
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




    private fun upgradeSubscriptionDialog() {
        val logoutUpdatedDialogs = Dialog(this)
        logoutUpdatedDialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialogs.setContentView(R.layout.upgrade_alert_box)
        logoutUpdatedDialogs.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialogs.setCancelable(true)
        logoutUpdatedDialogs.setCanceledOnTouchOutside(false)
        logoutUpdatedDialogs.window!!.setGravity(Gravity.CENTER)
        logoutUpdatedDialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btncontinue = logoutUpdatedDialogs.findViewById<Button>(R.id.upgrade_yes)
        val upgrade_cancel = logoutUpdatedDialogs.findViewById<Button>(R.id.upgrade_cancel)
        val verifyset = logoutUpdatedDialogs.findViewById<CircleImageView>(R.id.verifyset)
        val tvDesc = logoutUpdatedDialogs.findViewById<TitiliumRegularTextView>(R.id.tvDesc)
        val tvTitle = logoutUpdatedDialogs.findViewById<TitiliumBoldTextView>(R.id.tvTitle)
        verifyset.setImageResource(R.drawable.warning_icon)

        tvTitle.text= getString(R.string.time_to_upgrade_your_account)
        tvDesc.text= getString(R.string.reached_your_limit_description)

        btncontinue.setOnClickListener {
            startActivity(Intent(this@AddProduct, Subscription::class.java))
            finishAffinity()
            logoutUpdatedDialogs.dismiss()
        }
        upgrade_cancel.setOnClickListener {
            startActivity(Intent(this@AddProduct, BottomnavigationScreen::class.java))
            finishAffinity()
            logoutUpdatedDialogs.dismiss()
        }
        logoutUpdatedDialogs.show()

    }


    private fun setUnitList() {
        //        listProductUnit.add(new ProductUnitData("Volume", "Teaspoon (t or tsp.)", false));
        setDialog()
    }

    private fun setDialog() {
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
        val rvProductUnit: RecyclerView = detailDialog.findViewById(R.id.rvProductUnit)
        val llDialog = detailDialog.findViewById<LinearLayout>(R.id.llDialog)
        rvProductUnit.adapter = adapterMeasurements
        llDialog.setOnClickListener { detailDialog.dismiss() }
        detailDialog.show()
    }





    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is ModelMeasurementList) {
                    val mResponse: ModelMeasurementList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setDataMeasurements(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }



                if (it.data is ModelAddProduct) {
                    val mResponse: ModelAddProduct = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        addProductAlertDialog()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if(it.error!!.toString()=="Please upgrade your Subscription Plan"){
                 upgradeSubscriptionDialog()
                }else{
                    if (it.data != null) {
                        Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
            it.status == Status.LOADING -> {
            }
        }
    }




    private fun setDataMeasurements(mResponse: ModelMeasurementList) {
        listProductUnit.clear()
        currentModelMeasurements.clear()
        currentModelMeasurements = mResponse.body as ArrayList<ModelMeasurementList.Body>

        for (i in currentModelMeasurements) {
            listProductUnit.add(ProductUnitData("", i.name, false))
        }
        adapterMeasurements.notifyDataSetChanged()
    }

    fun setSelectedMeasurement(position: Int, productUnitData: ProductUnitData) {
        for (i in 0 until currentModelMeasurements.size) {
            listProductUnit.set(i, ProductUnitData("", currentModelMeasurements.get(i).name, false))
        }
        val productUnitData1 = ProductUnitData("", productUnitData.unit, true)
        listProductUnit.set(position, productUnitData1)
        adapterMeasurements.notifyDataSetChanged()
        addproductmasurement.setText(productUnitData.unit)
        detailDialog.dismiss()
        curreMeasurementId = currentModelMeasurements.get(position).id.toString()
    }

}