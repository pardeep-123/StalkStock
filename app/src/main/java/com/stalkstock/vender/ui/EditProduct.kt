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
import androidx.core.content.ContextCompat
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
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelEditProduct
import com.stalkstock.vender.Model.ModelProductDetail
import com.stalkstock.vender.adapter.AdapterMultipleFiles
import com.stalkstock.vender.adapter.AddEditImageModel
import com.stalkstock.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_edit_business_profile.*
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.activity_edit_product.spinner
import kotlinx.android.synthetic.main.activity_edit_product.spinnerCountry
import kotlinx.android.synthetic.main.activity_edit_product.spinnerSubCategory
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.added_product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditProduct : BaseActivity(), View.OnClickListener, Observer<RestObservable>,
    AdapterMultipleFiles.MultipleFilesInterface {
    private lateinit var currentProductModel: ModelProductDetail
    private lateinit var adapterMultipleFiles: AdapterMultipleFiles
    private var arrStringMultipleImages: ArrayList<AddEditImageModel> = ArrayList()
    private var arrStringMultipleImagesUploadable: ArrayList<String> = ArrayList()
    private var haveSubscription = false
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    private var mAlbumFilesMultiple = ArrayList<AlbumFile>()
    var firstimage = ""
    var categoryId = "0"
    var subCategoryId = "0"
    private var listProduct: ArrayList<String> = ArrayList()
    var deleteImageArrayId = ArrayList<String>()

    private var curreMeasurementId = ""
    private var curreMeasurementName= ""
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
        getCategories()

        adapterMultipleFiles = AdapterMultipleFiles(this, arrStringMultipleImages)
        adapterMultipleFiles.multipleFileInterface = this
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
                if(position==0){
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@EditProduct, R.color.black_color
                        )
                    )
                }else{
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@EditProduct, R.color.black
                        )
                    )
                    val get = listCategoryBody[position]
                    categoryId = get.id.toString()
                    getSubCategoryAPI(categoryId)
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
        subCatAdapter = ArrayAdapter(this, R.layout.spinner_item_text, listSub)
        spinnerSubCategory!!.adapter = subCatAdapter

        spinnerSubCategory!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if(position==0){
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@EditProduct, R.color.black_color
                        )
                    )
                }else{
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@EditProduct, R.color.black
                        )
                    )
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }


        currentProductModel = intent.getSerializableExtra("body") as ModelProductDetail

        val countryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        countryAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinnerCountry.adapter = countryAdapter


        spinnerCountry!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if(position==0){
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@EditProduct, R.color.black_color
                    ) )
                }else{
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@EditProduct, R.color.black
                        )
                    )
                }


            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        getmeasurementsAPI()
    }

    private fun setAdapterSpinnerSub(
        position: String,
        mResponse: List<ModelSubCategoriesList.Body>
    ) {
        listSubCategoryBody.clear()
        listSubCategoryBody.add(
            0,
            ModelSubCategoriesList.Body(0, 0, "Select sub category", categoryId.toInt(), 0)
        )

        if (position != "0") {
            listSubCategoryBody.addAll(mResponse)
        }

        listSub.clear()
        for (i in listSubCategoryBody) {
            listSub.add(i.name)
        }

        subCatAdapter.notifyDataSetChanged()
        spinnerSubCategory.setSelection(listSub.indexOf(currentProductModel.body.subCategoryId.toString()))


//        list = ArrayList()
    }

    private fun getSubCategoryAPI(id1: String) {
        listSubCategoryBody.clear()
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
        subCategoryId= currentProductModel.body.subCategoryId.toString()

        val obj= listCategoryBody.find { it.id==currentProductModel.body.categoryId }
        spinnerCategory.setSelection(listCategoryBody.indexOf(obj))
        getSubCategoryAPI(currentProductModel.body.categoryId.toString())

//        categoryId = currentProductModel.body.categoryId.toString()
//        getSubCategoryAPI(categoryId)
        //  subCategoryId=currentProductModel.body.subCategoryId.toString()
        /*for (i in 0 until list.size) {
            if (lis[i] == currentProductModel.body.categoryId.toString()) {
                spinnerCategory.setSelection(list.indexOf(currentProductModel.body.categoryId.toString()))
               // getSubCategoryAPI(categoryId)
            }
        }

        for (i in 0 until listSub.size) {
            if (listSub[i] == currentProductModel.body.subCategoryId.toString()) {
                spinnerSubCategory.setSelection(list.indexOf(currentProductModel.body.subCategoryId.toString()))
                // getSubCategoryAPI(categoryId)
            }
        }*/
        //  spinnerCategory.setSelection(list.indexOf(currentProductModel.body.categoryId.toString()))
        addproduct_unitmeasurement.text = currentProductModel.body.productMeasurement.name
        curreMeasurementId = currentProductModel.body.measurementId.toString()
        curreMeasurementName = currentProductModel.body.productMeasurement.name

        if (currentProductModel.body.availability == 1) {
            spinner.setSelection(1)
        } else
            spinner.setSelection(2)


        if (currentProductModel.body.productType == 1) {
            spinnerType.setSelection(2)
        } else
            spinnerType.setSelection(1)

        val productTag = currentProductModel.body.productTag
        var stTag = ""
        val ars: ArrayList<String> = ArrayList()
        for (i in productTag) {
            ars.add(i.tag)
        }
        stTag = TextUtils.join(",", ars)
        addproduct_tag.setText(stTag)

        addproduct_Brand.setText(currentProductModel.body.brandName)
        addproduct_productname.setText(currentProductModel.body.name)
        val countryName: Array<String> = resources.getStringArray(R.array.Select_country)
        for (i in countryName.indices) {
            if (currentProductModel.body.country == countryName[i]) {
                spinnerCountry.setSelection(countryName.indexOf(countryName[i]))
            }
        }
        addproduct_addprice.setText(currentProductModel.body.mrp)
        addproduct_description.setText(currentProductModel.body.description)

        val productImage = currentProductModel.body.productImage
        if (productImage.size > 0) {
//            edituploadimages.loadImage(productImage[0].image)
//            firstimage = productImage[0].image
            setmultipleImages(productImage)
        } else if (productImage.isNotEmpty()) {
            edituploadimages.loadImage(productImage[0].image)
            firstimage = productImage[0].image
        }
    }

    private fun setmultipleImages(productImage: List<ModelProductDetail.Body.ProductImage>) {
        arrStringMultipleImages.clear()
        for (i in productImage.indices) {
            val data = AddEditImageModel()
            data.id = productImage[i].id.toString()
            data.name = productImage[i].image
            data.type = "edit"
            arrStringMultipleImages.add(data)
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
                if (arrStringMultipleImages.size == 2) {
                    Toast.makeText(
                        this,
                        "You can't upload more than 2 photos!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    askCameraPermissonsMultiple()
                }

            }
            R.id.addSingleImage -> {
                askCameraPermissons()
            }

            R.id.deleteicon -> {

            }
        }
    }

    val viewModel: HomeViewModel by viewModels()

    private fun validations(): Boolean {
        when {
            arrStringMultipleImages.size == 0 -> {
                AppUtils.showErrorAlert(this, "Please upload atleast one photo of the product")
                return false
            }

            spinnerSubCategory.selectedItemPosition==0 ->{
                AppUtils.showErrorAlert(
                    this,
                    "Please select sub category"
                )
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
           /* spinnerType.selectedItemPosition == 0 -> {
                AppUtils.showErrorAlert(this, "Please select product type")
                return false
            }*/
            spinnerCountry.selectedItemPosition == 0 -> {
                AppUtils.showErrorAlert(this, "Please select country")
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
            addproduct_addprice.text.toString().trim() == "0" -> {
                AppUtils.showErrorAlert(this, "Price should be greater than 0")
                return false
            }
            addproduct_description.text.toString().trim().isEmpty() -> {
                AppUtils.showErrorAlert(this, "Please enter description")
                return false
            }
            spinner.selectedItemPosition == 0 -> {
                AppUtils.showErrorAlert(this, "Please select product availability")
                return false
            }
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
        // if (!firstimage.contains(GlobalVariables.URL.IMAGE_URL)) { arrStringMultipleImagesUploadable.add(firstimage) }

        for (i in 0 until arrStringMultipleImages.size) {
            if (arrStringMultipleImages[i].name?.contains(GlobalVariables.URL.IMAGE_URL)!!) {
                arrStringMultipleImagesUploadable.remove(arrStringMultipleImages[i].name)
                val removePrefix = arrStringMultipleImages[i].name?.removePrefix("localStalk")
            } else {
                arrStringMultipleImagesUploadable.add(arrStringMultipleImages[i].name!!)

            }
        }


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
        //   map["deleteImageArrayId"] = deleteImageArrayId

        var avail=0
        var productType=0
        if(spinner.selectedItemPosition==1){
            avail=1
        }else{
            avail=0
        }

        if(spinnerType.selectedItemPosition==1){
            productType=0
        }else{
            productType=1
        }

        map["availability"] = mUtils.createPartFromString(avail.toString())
   //     map["productType"] = mUtils.createPartFromString(productType.toString())
        Log.i("==Ids", deleteImageArrayId.size.toString())


        val deleteImageIds: ArrayList<MultipartBody.Part> = ArrayList()
        for (i in 0 until deleteImageArrayId.size) {
            deleteImageIds.add(
                MultipartBody.Part.createFormData(
                    "deleteImageArrayId",
                    deleteImageArrayId[i]
                )
            )

        }

        // var deleteImageIds=createPartFromArray(deleteImageArrayId)
        viewModel.editProductAPI(
            this,
            true,
            map,
            deleteImageIds,
            arrStringMultipleImagesUploadable,
            mUtils
        )
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
                .selectCount(2)
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
                    }
                }
                .onCancel { }
                .start()
        }
    }

    private fun setAdapter(mAlbumFilesMultiple: java.util.ArrayList<AlbumFile>) {
        if (mAlbumFilesMultiple.size > 0) {
            arrStringMultipleImages.clear()
            for (i in mAlbumFilesMultiple) {
                var data = AddEditImageModel()
                data.name = i.path
                data.type = "add"
                arrStringMultipleImages.add(data)
                Log.e("PathMulti,", i.path)
            }
        }

        adapterMultipleFiles.notifyDataSetChanged()
    }

    private fun setUnitList() {
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
                        getSubCategoryAPI(mResponse.body[0].id.toString())
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

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
                        setDataMeasurements(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }

                if (it.data is ModelSubCategoriesList) {
                    val mResponse: ModelSubCategoriesList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        val obj1= listSubCategoryBody.find { it.id==subCategoryId.toInt() }
                        spinnerSubCategory.setSelection(listSubCategoryBody.indexOf(obj1))


                        setAdapterSpinnerSub("1", mResponse.body)

                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                setAdapterSpinnerSub("0", listSubCategoryBody)
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
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
        setData(currentProductModel)
    }

    /*private fun setAdapterSpinnerSub(mResponse: ModelSubCategoriesList) {
        listSubCategoryBody.clear()
        listSubCategoryBody.addAll(mResponse.body)
        listSub.clear()
        for (i in listSubCategoryBody) {
            listSub.add(i.name)
        }
        subCatAdapter.notifyDataSetChanged()
    }*/

    private fun setAdapterSpinner(mResponse: ModelCategoryList) {
        listCategoryBody.clear()
        listCategoryBody.addAll(mResponse.body)
        list.clear()
        for (i in listCategoryBody) {
            list.add(i.name)
        }
//        list = ArrayList()
        spinnerCategory!!.adapter = ArrayAdapter(this, R.layout.spinner_item_text, list)

    }

    fun setSelectedMeasurement(position: Int, productUnitData: ProductUnitData) {
        for (i in 0 until currentModelMeasurements.size) {
            listProductUnit[i] = ProductUnitData("", currentModelMeasurements[i].name, false)
        }
        val productUnitData1 = ProductUnitData("", productUnitData.unit, true)
        listProductUnit[position] = productUnitData1
        adapterMeasurements.notifyDataSetChanged()
        addproduct_unitmeasurement.text = productUnitData.unit
        detailDialog!!.dismiss()
        curreMeasurementId = currentModelMeasurements[position].id.toString()
        curreMeasurementName = currentModelMeasurements[position].name.toString()
    }

    private fun getmeasurementsAPI() {
        viewModel.measurementListAPI(this, true)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onItemClick(position: Int, data: AddEditImageModel) {
        if (data.type == "add") {
            if (mAlbumFilesMultiple.size > 0) {
                mAlbumFilesMultiple.removeAt(position)
            }
            arrStringMultipleImages.removeAt(position)
            adapterMultipleFiles.notifyDataSetChanged()
        } else {
            deleteImageArrayId.add(data.id)
            arrStringMultipleImages.removeAt(position)
            adapterMultipleFiles.notifyDataSetChanged()

        }
    }
}