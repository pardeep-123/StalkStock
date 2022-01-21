package com.stalkstock.vender.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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
import com.stalkstock.utils.custom.TitiliumBoldTextView
import com.stalkstock.utils.custom.TitiliumRegularTextView
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.GridItemDecoration
import com.stalkstock.vender.Model.ModelEditProduct
import com.stalkstock.vender.Model.ModelProductDetail
import com.stalkstock.vender.adapter.AdapterMultipleFiles
import com.stalkstock.vender.adapter.AddEditImageModel
import com.stalkstock.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import de.hdodenhof.circleimageview.CircleImageView
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
    var adapterPosition=0

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
    var ivImg: CircleImageView? = null
    var ivDelete: ImageView? = null
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
        ivDelete = findViewById(R.id.ivDelete)
        ivImg?.setOnClickListener(this)
        ivDelete?.setOnClickListener(this)

        val ss = SpannableString(   upgrade_desc.text )

        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                startActivity(Intent(this@EditProduct, Subscription::class.java))
            }
        }
        ss.setSpan(clickableSpan, 77, 92, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        upgrade_desc.text = ss
        upgrade_desc.movementMethod = LinkMovementMethod.getInstance()
        upgrade_desc.highlightColor = Color.TRANSPARENT

        button.setOnClickListener(this)
        imageView.setOnClickListener(this)
        ivImg?.setOnClickListener(this)

        addproduct_unitmeasurement.setOnClickListener { setUnitList() }
        getCategories()

        adapterMultipleFiles = AdapterMultipleFiles(this, arrStringMultipleImages,firstimage)
        adapterMultipleFiles.multipleFileInterface = this
        recyclerviewSubImages.layoutManager = GridLayoutManager(this, 3)
        recyclerviewSubImages.addItemDecoration(GridItemDecoration(this))
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
        subCategoryId = currentProductModel.body.subCategoryId.toString()

        val obj = listCategoryBody.find { it.id == currentProductModel.body.categoryId }
        spinnerCategory.setSelection(listCategoryBody.indexOf(obj))
        getSubCategoryAPI(currentProductModel.body.categoryId.toString())

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

            if (firstimage.isEmpty()){

                firstimage=productImage[0].image
                Glide.with(this).load(firstimage).into(ivImg!!)
                ivDelete?.visibility=View.VISIBLE

                if (productImage.size>=1){


                    var tempList = ArrayList<ModelProductDetail.Body.ProductImage>()

                    tempList.addAll(productImage)
                    tempList.removeAt(0)
//                            mAlbumFilesMultiple.removeAt(0)
                    setmultipleImages(tempList)
                }

            }else{


                var tempList = ArrayList<ModelProductDetail.Body.ProductImage>()

                tempList.addAll(productImage)
                tempList.removeAt(0)

                //.addAll(result)
                setmultipleImages(tempList)
            }

        }
    }

    private fun setmultipleImages(productImage: List<ModelProductDetail.Body.ProductImage>) {

       // Glide.with(this).load(productImage[0].image).into(adduploadimages)
        arrStringMultipleImages.clear()
        for (i in productImage.indices) {
            val data = AddEditImageModel()
            data.id = productImage[i].id.toString()
            data.name = productImage[i].image
            data.type = "edit"
            arrStringMultipleImages.add(data)
        }
        adapterMultipleFiles.firstImageUpdate(firstimage,arrStringMultipleImages)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivDelete ->{
                ivDelete?.visibility=View.GONE
                ivImg?.setImageResource(R.drawable.camera_green)
                firstimage=""

            }
            R.id.addproduct_backarrow -> onBackPressed()
            R.id.addproduct_updatebutton -> {
                if (validations())
                    alertDailogConfirmEdit()
            }
            R.id.ivImg -> {
                askCameraPermissonsMultiple()
               /* if (arrStringMultipleImages.size == 2) {
                    updateSubscriptionDialog()
                } else {
                    askCameraPermissonsMultiple()
                }*/

            }
            R.id.addSingleImage -> {
                askCameraPermissons()
            }

            R.id.deleteicon -> {

            }
            R.id.uploadimagesthree ->{
                if (arrStringMultipleImages.size == 2) {
                    updateSubscriptionDialog()
                } else {
                    askCameraPermissonsMultiple()
                }
            }
        }
    }

    val viewModel: HomeViewModel by viewModels()

    private fun updateSubscriptionDialog() {
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
        btncontinue.setOnClickListener {
            startActivity(Intent(this@EditProduct, Subscription::class.java))
            finishAffinity()
            logoutUpdatedDialogs.dismiss()
        }
        upgrade_cancel.setOnClickListener {
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
        val tvDesc = logoutUpdatedDialogs.findViewById<TitiliumRegularTextView>(R.id.tvDesc)
        val tvTitle = logoutUpdatedDialogs.findViewById<TitiliumBoldTextView>(R.id.tvTitle)

        tvTitle.text= getString(R.string.time_to_upgrade_your_account)
        tvDesc.text= getString(R.string.reached_your_limit_description)

        btncontinue.setOnClickListener {
            startActivity(Intent(this@EditProduct, Subscription::class.java))
            finishAffinity()
            logoutUpdatedDialogs.dismiss()
        }
        upgrade_cancel.setOnClickListener {
            logoutUpdatedDialogs.dismiss()
        }
        logoutUpdatedDialogs.show()

    }

    private fun validations(): Boolean {
//        if (firstimage.isEmpty()){
//            AppUtils.showErrorAlert(this, "Please upload atleast one photo of the product")
//            return false
//        }else{
            when {
                firstimage.isEmpty()-> {
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
       // }


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

//        if (firstimage.isNotEmpty()){
//            arrStringMultipleImagesUploadable.add(firstimage)
//        }
        for (i in 0 until arrStringMultipleImages.size) {
            if (arrStringMultipleImages[i].name.contains(GlobalVariables.URL.IMAGE_URL)) {
                arrStringMultipleImagesUploadable.remove(arrStringMultipleImages[i].name)
                val removePrefix = arrStringMultipleImages[i].name.removePrefix("localStalk")
            } else {
                arrStringMultipleImagesUploadable.add(arrStringMultipleImages[i].name)

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
        if(spinner.selectedItemPosition==1){
            avail=1
        }else{
            avail=0
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

                    if (firstimage.isEmpty()){

                        firstimage=result[0].path
                        Glide.with(this).load(firstimage).into(ivImg!!)

                        if (result.size>=1){

                            var tempList = ArrayList<AlbumFile>()

                            tempList.addAll(result)
                            tempList.removeAt(0)
//                            mAlbumFilesMultiple.removeAt(0)
                            setAdapter(tempList)
                        }

                    }else{

                        val tempList = ArrayList<AlbumFile>()
                        tempList.addAll(result)
                        tempList.removeAt(0)

                        //.addAll(result)
                        setAdapter(tempList)
                    }

                   // setAdapter(mAlbumFilesMultiple)
                }
                .onCancel { }
                .start()

        } /*else {
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
        }*/
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
        adapterMultipleFiles.firstImageUpdate(firstimage,arrStringMultipleImages)

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

              /*  if(it.error!!.toString()=="Please upgrade your Subscription Plan"){
                    upgradeSubscriptionDialog()
                }else{*/
                    setAdapterSpinnerSub("0", listSubCategoryBody)
                    if (it.data != null) {
                        Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.error.toString(), Toast.LENGTH_SHORT).show()
                    }
               // }
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
            if (data.id.isNotEmpty()){
                deleteImageArrayId.add(data.id)
            }
            arrStringMultipleImages.removeAt(position)
            adapterMultipleFiles.notifyDataSetChanged()

        }
    }

    override fun onImageClick(position: Int) {
        adapterPosition=position
        if(position==0){
            askCameraPermissonsMultiple()
        }else{
            startActivity(Intent(this@EditProduct, Subscription::class.java))
        }
    }
}