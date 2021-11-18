package com.stalkstock.vender.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.*
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
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.ProductUnitData
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelEditProduct
import com.stalkstock.vender.Model.ModelProductDetail
import com.stalkstock.vender.adapter.AdapterMultipleFiles
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_business_profile.*
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.activity_edit_product.spinner
import okhttp3.RequestBody

class EditProduct : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private lateinit var currentProductModel: ModelProductDetail
    private lateinit var adapterMultipleFiles: AdapterMultipleFiles
    private var arrStringMultipleImages: ArrayList<String> = ArrayList()
    private var arrStringMultipleImagesUploadable: ArrayList<String> = ArrayList()
    private var haveSubscription = true
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    private var mAlbumFilesMultiple = ArrayList<AlbumFile>()
    var firstimage = ""

    private var curreMeasurementId = ""
    private lateinit var adapterMeasurements: AdapterProductUnit2
    private var currentModelMeasurements: ArrayList<ModelMeasurementList.Body> = ArrayList()
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()

    private lateinit var subCatAdapter: ArrayAdapter<String>
    private var list: ArrayList<String> = ArrayList()
    private var listCategoryBody: ArrayList<ModelCategoryList.Body> = ArrayList()
    private var listSub: ArrayList<String> = ArrayList()
    private var listSubCategoryBody: ArrayList<ModelSubCategoriesList.Body> = ArrayList()


    var m: Context? = null
    var ivImg: ImageView? = null
    lateinit var adduploadimages: ImageView
    var relativeLayout: RelativeLayout? = null
    var detailDialog: Dialog? = null
    override fun getContentId(): Int {
        return R.layout.activity_edit_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addproduct_unitmeasurement = findViewById<TextView>(R.id.addproduct_unitmeasurement)
        val imageView = findViewById<ImageView>(R.id.addproduct_backarrow)
        val button = findViewById<Button>(R.id.addproduct_updatebutton)
        relativeLayout = findViewById(R.id.relative_imagesthree)
        ivImg = findViewById(R.id.uploadimagesthree)
        adduploadimages = findViewById(R.id.imagesthree)
        button.setOnClickListener(this)
        imageView.setOnClickListener(this)
        adduploadimages.setOnClickListener(this)
        addSingleImage.setOnClickListener(this)
        deleteicon.setOnClickListener(this)
        addproduct_unitmeasurement.setOnClickListener { setUnitList() }

        adapterMultipleFiles = AdapterMultipleFiles(this, arrStringMultipleImages)
        recyclerviewSubImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        recyclerviewSubImages.adapter = adapterMultipleFiles

        adapterMeasurements = AdapterProductUnit2(this, listProductUnit)

        spinnerCategory!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val get = listCategoryBody[spinnerCategory!!.selectedItemPosition]
                val id1 = get.id.toString()
                getSubCategoryAPI(id1)
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
            } }
        subCatAdapter = ArrayAdapter(this, R.layout.spinner_item_text, listSub)
        spinnerSubCategory!!.adapter = subCatAdapter

        getCategories()
        currentProductModel = intent.getSerializableExtra("body") as ModelProductDetail

        val countryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        countryAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinnerCountry.adapter = countryAdapter

    }

    private fun getSubCategoryAPI(id1: String) {
        listSubCategoryBody.clear()
        listSub.clear()
        subCatAdapter.notifyDataSetChanged()
        val hashMap = HashMap<String, RequestBody>()
        hashMap["category_id"] = mUtils.createPartFromString(id1)
        viewModel.getSubCategoryListAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)
    }

    private fun getCategories() {
        viewModel.getCategoryListAPI(this, true)
        viewModel.homeResponse.observe(this, this)
    }

    private fun setData(currentProductModel: ModelProductDetail) {
        spinnerCategory.setSelection(list.indexOf(currentProductModel.body.categoryId.toString()))
        spinnerSubCategory.setSelection(listSub.indexOf(currentProductModel.body.subCategoryId.toString()))
        addproduct_unitmeasurement.text = currentProductModel.body.productMeasurement.name
        curreMeasurementId = currentProductModel.body.measurementId.toString()

        if (currentProductModel.body.availability == 1) {
            spinner.setSelection(0)
        } else
            spinner.setSelection(1)

        val productTag = currentProductModel.body.productTag
        var stTag = ""
        val ars: ArrayList<String> = ArrayList()
        for (i in productTag) { ars.add(i.tag) }
        stTag = TextUtils.join(",", ars)
        addproduct_tag.setText(stTag)

        addproduct_Brand.setText(currentProductModel.body.brandName)
        addproduct_productname.setText(currentProductModel.body.name)
        val countryName: Array<String> = resources.getStringArray(R.array.Select_country)
        for(i in countryName.indices)
        {
            if(currentProductModel.body.country == countryName[i])
            {
                spinnerCountry.setSelection(countryName.indexOf(countryName[i]))
            }
        }
        addproduct_addprice.setText(currentProductModel.body.mrp)
        addproduct_description.setText(currentProductModel.body.description)

        val productImage = currentProductModel.body.productImage
        if (productImage.size > 1) {
            edituploadimages.loadImage(productImage[0].image)
            firstimage = productImage[0].image
            setmultipleImages(productImage)
        } else if (productImage.isNotEmpty()) {
            edituploadimages.loadImage(productImage[0].image)
            firstimage = productImage[0].image
        }
    }

    private fun setmultipleImages(productImage: List<ModelProductDetail.Body.ProductImage>) {
        arrStringMultipleImages.clear()
        for (i in productImage.indices) {
            if (i == 0) { }
            else arrStringMultipleImages.add(productImage[i].image)
        }
        adapterMultipleFiles.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.addproduct_backarrow -> onBackPressed()
            R.id.addproduct_updatebutton -> {
                if (validations())
                    alertDailogConfirmEdit()
            }
            R.id.imagesthree -> {
                if (haveSubscription)
                    askCameraPermissonsMultiple()
            }
            R.id.addSingleImage -> {
                askCameraPermissons()
            }

            R.id.deleteicon ->{

            }
        }
    }

    val viewModel: HomeViewModel by viewModels()

    private fun validations(): Boolean {
        when {
            firstimage == "" -> {
                AppUtils.showErrorAlert(this, "Please select an image for the product")
                return false
            }
            addproduct_Brand.text.toString().trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please enter brand")
                return false
            }
            addproduct_productname.text.toString().trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please enter product name")
                return false
            }
            spinnerCountry.selectedItem.toString().trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please enter country")
                return false
            }
            curreMeasurementId.trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please select measurement")
                return false
            }
            addproduct_addprice.text.toString().trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please enter price")
                return false
            }
            addproduct_description.text.toString().trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please enter description")
                return false }
            else -> return true
        }
    }

    private fun alertDailogConfirmEdit() {
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
            updateProductAPI()
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

    private fun updateProductAPI() {
        arrStringMultipleImagesUploadable.clear()
        if (!firstimage.contains(GlobalVariables.URL.IMAGE_URL)) { arrStringMultipleImagesUploadable.add(firstimage) }

        for (i in arrStringMultipleImages) {
            if (!i.contains(GlobalVariables.URL.IMAGE_URL)) {
                val removePrefix = i.removePrefix("localStalk")
                arrStringMultipleImagesUploadable.add(removePrefix)
            } }

        val map = HashMap<String, RequestBody>()
        map["categoryId"] =
            mUtils.createPartFromString(listCategoryBody[spinnerCategory.selectedItemPosition].id.toString())
        map["subCategoryId"] =
            mUtils.createPartFromString(listSubCategoryBody[spinnerSubCategory.selectedItemPosition].id.toString())
        map["measurementId"] = mUtils.createPartFromString(curreMeasurementId)
        map["tag"] = mUtils.createPartFromString(addproduct_tag.text.toString())
        map["name"] = mUtils.createPartFromString(addproduct_productname.text.toString())
        map["description"] = mUtils.createPartFromString(addproduct_description.text.toString())
        map["brandName"] = mUtils.createPartFromString(addproduct_Brand.text.toString())
        map["mrp"] = mUtils.createPartFromString(addproduct_addprice.text.toString())
        map["country"] = mUtils.createPartFromString(spinnerCountry.selectedItem.toString())
        map["product_id"] = mUtils.createPartFromString(currentProductModel.body.id.toString())

        var avail = 0
        if (spinner.selectedItemPosition == 0) avail = 1
        map["availability"] = mUtils.createPartFromString(avail.toString())
        viewModel.editProductAPI(this, true, map, arrStringMultipleImagesUploadable, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList()
        mAlbumFiles.clear()
        selectImage("1")
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
                .columnCount(4)
                .onResult { result ->
                    mAlbumFilesMultiple.clear()
                    mAlbumFilesMultiple.addAll(result)
                    setAdapter(mAlbumFilesMultiple)
                }
                .onCancel { }
                .start()

        } else {
            Album.image(this)
                .singleChoice()
                .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
                .camera(true)
                .columnCount(4)
                .onResult { result ->
                    mAlbumFiles.addAll(result)
                    Glide.with(this@EditProduct).load(result[0].path).into(edituploadimages!!)
                    if (s == "1") {
                        firstimage = result[0].path
                    } }
                .onCancel { }
                .start()
        }
    }

    private fun setAdapter(mAlbumFilesMultiple: java.util.ArrayList<AlbumFile>) {
        if (mAlbumFilesMultiple.size > 0) {
            arrStringMultipleImages.clear()
            for (i in mAlbumFilesMultiple) {
                arrStringMultipleImages.add("localStalk" + i.path)
                Log.e("PathMulti,", i.path)
            } }

        adapterMultipleFiles.notifyDataSetChanged()
    }

    private fun setUnitList() {
        detailDialog = Dialog(this)
        detailDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        detailDialog!!.setContentView(R.layout.dialog_home)
        detailDialog!!.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        detailDialog!!.setCancelable(true)
        detailDialog!!.setCanceledOnTouchOutside(true)
        detailDialog!!.window!!.setGravity(Gravity.CENTER)
        detailDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val rvProductUnit: RecyclerView = detailDialog!!.findViewById(R.id.rvProductUnit)
        val llDialog = detailDialog!!.findViewById<LinearLayout>(R.id.llDialog)
        rvProductUnit.adapter = adapterMeasurements
        llDialog.setOnClickListener { detailDialog!!.dismiss() }
        detailDialog!!.show()
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelCategoryList) {
                    val mResponse: ModelCategoryList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setAdapterSpinner(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    } }

                if (it.data is ModelEditProduct) {
                    val mResponse: ModelEditProduct = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, "Product updated successfully.")
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 2000)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }

                if (it.data is ModelMeasurementList) {
                    val mResponse: ModelMeasurementList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setDataMeasurements(mResponse) }
                    else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }

                if (it.data is ModelSubCategoriesList) {
                    val mResponse: ModelSubCategoriesList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) { setAdapterSpinnerSub(mResponse) }
                    else { AppUtils.showErrorAlert(this, mResponse.message.toString()) } }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) { Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show() }
                else { Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show() } }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setDataMeasurements(mResponse: ModelMeasurementList) {
        listProductUnit.clear()
        currentModelMeasurements.clear()
        currentModelMeasurements = mResponse.body as ArrayList<ModelMeasurementList.Body>
        for (i in currentModelMeasurements) { listProductUnit.add(ProductUnitData("", i.name, false)) }
        adapterMeasurements.notifyDataSetChanged()
        setData(currentProductModel)
    }

    private fun setAdapterSpinnerSub(mResponse: ModelSubCategoriesList) {
        listSubCategoryBody.clear()
        listSubCategoryBody.addAll(mResponse.body)
        listSub.clear()
        for (i in listSubCategoryBody) { listSub.add(i.name) }
        subCatAdapter.notifyDataSetChanged()
    }

    private fun setAdapterSpinner(mResponse: ModelCategoryList) {
        listCategoryBody.clear()
        listCategoryBody.addAll(mResponse.body)
        list.clear()
        for (i in listCategoryBody) { list.add(i.name) }
        spinnerCategory!!.adapter = ArrayAdapter(this, R.layout.spinner_item_text, list)

        for(i in list.indices) {
            if(currentProductModel.body.productCategory.name == list[i]) {
                spinnerCategory.setSelection(list.indexOf(list[i]))
            }
        }
        getmeasurementsAPI()
    }

    fun setSelectedMeasurement(position: Int, productUnitData: ProductUnitData) {
        for (i in 0 until currentModelMeasurements.size) { listProductUnit[i] = ProductUnitData("", currentModelMeasurements[i].name, false) }
        val productUnitData1 = ProductUnitData("", productUnitData.unit, true)
        listProductUnit[position] = productUnitData1
        adapterMeasurements.notifyDataSetChanged()
        addproduct_unitmeasurement.text = productUnitData.unit
        detailDialog!!.dismiss()
        curreMeasurementId = currentModelMeasurements[position].id.toString()
    }

    private fun getmeasurementsAPI() {
        viewModel.measurementListAPI(this, true)
        viewModel.homeResponse.observe(this, this)
    }
}