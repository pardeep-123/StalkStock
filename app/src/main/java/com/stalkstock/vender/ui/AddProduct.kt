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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit2
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.ProductUnitData
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelAddProduct
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.vender.adapter.AdapterMultipleFiles
import com.stalkstock.vender.adapter.AddEditImageModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product.spinner
import kotlinx.android.synthetic.main.activity_add_product.spinnerCountry
import kotlinx.android.synthetic.main.activity_add_product.spinnerGetProduct
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.added_product.*
import okhttp3.RequestBody
import kotlin.collections.ArrayList

class AddProduct : BaseActivity(), View.OnClickListener, Observer<RestObservable>,
    AdapterMultipleFiles.MultipleFilesInterface {
    private var productId: String=""
    private var curreMeasurementId = ""
    private lateinit var adapterMeasurements: AdapterProductUnit2
    private lateinit var adapterMultipleFiles: AdapterMultipleFiles
    private var currentModelMeasurements: ArrayList<ModelMeasurementList.Body> = ArrayList()
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()
    private var currentCatId = ""
    private var currentSubCatId = ""
    private var currentTags = ""
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    private var mAlbumFilesMultiple = ArrayList<AlbumFile>()

    private var currentModel: ArrayList<ModelProductListAsPerSubCat.Body> = ArrayList()
    private var listProduct: ArrayList<String> = ArrayList()
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
    private lateinit var productAdapter: ArrayAdapter<String>
    private var arrStringMultipleImages: ArrayList<AddEditImageModel> = ArrayList()

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

        adapterMultipleFiles = AdapterMultipleFiles(this, arrStringMultipleImages)
        adapterMultipleFiles.multipleFileInterface=this
        rvSubImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        rvSubImages.adapter = adapterMultipleFiles


       // imagethree.setOnClickListener(this)
        button.setOnClickListener(this)
        backarrow.setOnClickListener(this)
        cameropen.setOnClickListener(this)
        setimage.setOnClickListener(this)
        ivUpload.setOnClickListener(this)
//        relativeLayout.setOnClickListener(this)
        measurement.setOnClickListener(this)

        val countryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        countryAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinnerCountry.adapter = countryAdapter

        listProduct.add("Select Product")
        spinnerGetProduct.isEnabled = false
        productAdapter = ArrayAdapter(this, R.layout.spiner_layout_text, listProduct)
        spinnerGetProduct!!.adapter = productAdapter


        spinnerGetProduct.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position!==0){
                    val product = currentModel[spinnerGetProduct.selectedItemPosition-1]
                    productId = product.id.toString()
                }
                else{ }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        try {
            currentCatId = intent.getStringExtra("catId")!!
            currentSubCatId = intent.getStringExtra("subCatId")!!
            currentTags = intent.getStringExtra("tags")!!

            getProductAsSubCategory()
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

    private fun getProductAsSubCategory() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = java.util.HashMap<String, RequestBody>()
        map["subCategoryId"] = mUtils.createPartFromString(currentSubCatId)
        map["categoryId"] = mUtils.createPartFromString(currentCatId)
        viewModel.getProductAccToCategoryAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {

            R.id.ivUpload -> {
                if (arrStringMultipleImages.size == 2) {
                    Toast.makeText(
                        this,
                        "You cannot upload more than 2 photos!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    askCameraPermissonsMultiple()
                }
            }
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
            R.id.add_uploadimages -> askCameraPermissons()
           /* R.id.imagethree -> {
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
            }*/
            R.id.addproductmasurement ->
                setUnitList()
        }
    }

    private fun addproductAPI() {

        if(mAlbumFilesMultiple.size==0){
            AppUtils.showErrorAlert(this, "Please upload atleast one photo of the product")
            return
        }
        var map = HashMap<String, RequestBody>()
        map["categoryId"] = mUtils.createPartFromString(currentCatId)
        map["subCategoryId"] = mUtils.createPartFromString(currentSubCatId)
        map["measurementId"] = mUtils.createPartFromString(curreMeasurementId)
        map["tag"] = mUtils.createPartFromString(currentTags)
        map["productId"] = mUtils.createPartFromString(productId)
        map["description"] = mUtils.createPartFromString(addproductdescription.text.toString())
        map["brandName"] = mUtils.createPartFromString(addproductbrand.text.toString())
        map["mrp"] = mUtils.createPartFromString(addproductprice.text.toString())
        map["country"] = mUtils.createPartFromString(spinnerCountry.selectedItem.toString())

        //productType(0=>veg 1=>non veg)
        var avail = 0
        if (spinner.selectedItemPosition == 0)
            avail = 1

        var productType = 0
        if (spinnerProdType.selectedItemPosition == 0)
            productType = 1

        Log.e("product_type",productType.toString())

        map["availability"] = mUtils.createPartFromString(avail.toString())
        map["productType"] = mUtils.createPartFromString(productType.toString())
        var arrayList: ArrayList<String> = ArrayList()
        for (i in arrStringMultipleImages) {
            arrayList.add(i.name!!)
        }
        viewModel.vendorAddProductAPI(this, true, map, arrayList, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    private fun validations(): Boolean {
        if (mAlbumFilesMultiple.size==0) {
            AppUtils.showErrorAlert(this, "Please select an image for the product")
            return false
        } else if (addproductbrand.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter brand")
            return false
        } else if(spinnerGetProduct.selectedItemPosition==0) {
            spinnerGetProduct.requestFocus()
            AppUtils.showErrorAlert(this, getString(R.string.please_select_product))
            return false
        } else if(spinnerCountry.selectedItemPosition==0) {
            AppUtils.showErrorAlert(this, "Please select country")
            return false
        } else if (curreMeasurementId.trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please select measurement")
            return false
        } else if (addproductprice.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter price")
            return false
        }else if (addproductprice.text.toString().trim()=="0") {
            AppUtils.showErrorAlert(this, "Price should be greater than 0")
            return false
        } else if (addproductdescription.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter description")
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

    private fun askCameraPermissonsMultiple() {
        selectImage("2")
    }
    private fun selectImage(s: String) {
        if (s == "2") {
            Album.image(this)
                .multipleChoice()
                .checkedList(mAlbumFilesMultiple)
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .selectCount(2)
                .columnCount(4)
                .onResult { result ->
                    mAlbumFilesMultiple.clear()
                    mAlbumFilesMultiple.addAll(result)
                    setAdapter(mAlbumFilesMultiple)
                }
                .onCancel { }
                .start()

        }
    }

    private fun setAdapter(mAlbumFilesMultiple:ArrayList<AlbumFile>) {
        if (mAlbumFilesMultiple.size > 0) {
            arrStringMultipleImages.clear()
            for (i in mAlbumFilesMultiple) {
                val data= AddEditImageModel()
                data.name= i.path
                data.type="add"
                arrStringMultipleImages.add(data)
                Log.e("PathMulti,", i.path)
            } }

        adapterMultipleFiles.notifyDataSetChanged()
    }
    private fun setUnitList() {
        //        listProductUnit.add(new ProductUnitData("Volume", "Teaspoon (t or tsp.)", false));
        setDialog()
    }

    private fun setDialog() {
        detailDialog = Dialog(this)
        detailDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        detailDialog!!.setContentView(R.layout.dialog_home)
        detailDialog!!.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        detailDialog!!.setCancelable(true)
        detailDialog!!.setCanceledOnTouchOutside(true)
        detailDialog.window!!.setGravity(Gravity.CENTER)
        detailDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rvProductUnit: RecyclerView = detailDialog!!.findViewById(R.id.rvProductUnit)
        val llDialog = detailDialog!!.findViewById<LinearLayout>(R.id.llDialog)
        rvProductUnit.adapter = adapterMeasurements
        llDialog.setOnClickListener { detailDialog!!.dismiss() }
        detailDialog!!.show()
    }

    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList()
        mAlbumFiles.clear()
       // selectImage(ivImg, "1")
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
//                    relativeLayout!!.visibility = View.VISIBLE
//                    visibaleimage!!.visibility = View.VISIBLE
                    visibaleimage!!.setOnClickListener { }
                }
            }
            .onCancel { }
            .start()
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

                if (it.data is ModelProductListAsPerSubCat) {
                    val mResponse: ModelProductListAsPerSubCat = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
                        setData(mResponse)
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
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.clear()
        currentModel.addAll(mResponse.body)
        reset = false
        spinnerGetProduct.isEnabled = true

        listProduct.clear()
        listProduct.add("Select Product")
        for (i in 0 until currentModel.size) {
            listProduct.add(currentModel[i].name) }

        productAdapter.notifyDataSetChanged()
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

    override fun onItemClick(position: Int, data: AddEditImageModel) {
        if(data.type=="add"){
            mAlbumFilesMultiple.removeAt(position)
            arrStringMultipleImages.removeAt(position)
            adapterMultipleFiles.notifyDataSetChanged()
            Log.i("===",arrStringMultipleImages.size.toString())
        }
    }
}