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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.live.stalkstockcommercial.ui.view.fragments.home.AdapterProductUnit2
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.ProductUnitData
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelAddProduct
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_product.*
import okhttp3.RequestBody
import kotlin.collections.ArrayList

class AddProduct : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private var curreMeasurementId = ""
    private lateinit var adapterMeasurements: AdapterProductUnit2
    private var currentModelMeasurements: ArrayList<ModelMeasurementList.Body> = ArrayList()
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()
    private var currentCatId = ""
    private var currentSubCatId = ""
    private var currentTags = ""
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    var firstimage = ""
    lateinit var backarrow: ImageView
    lateinit var ivImg: ImageView
    lateinit var cameropen: ImageView
    lateinit var visibaleimage: ImageView
    lateinit var detailDialog: Dialog
    lateinit var imagethree: RelativeLayout
    lateinit var relativeLayout: RelativeLayout
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

        try {
            currentCatId = intent.getStringExtra("catId")!!
            currentSubCatId = intent.getStringExtra("subCatId")!!
            currentTags = intent.getStringExtra("tags")!!
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
            R.id.add_uploadimages -> askCameraPermissons()
            R.id.imagethree -> {
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
            R.id.addproductmasurement ->
                setUnitList()
        }
    }

    private fun addproductAPI() {
        var map = HashMap<String, RequestBody>()
        map["categoryId"] = mUtils.createPartFromString(currentCatId)
        map["subCategoryId"] = mUtils.createPartFromString(currentSubCatId)
        map["measurementId"] = mUtils.createPartFromString(curreMeasurementId)
        map["tag"] = mUtils.createPartFromString(currentTags)
        map["name"] = mUtils.createPartFromString(addproductname.text.toString())
        map["description"] = mUtils.createPartFromString(addproductdescription.text.toString())
        map["brandName"] = mUtils.createPartFromString(addproductbrand.text.toString())
        map["mrp"] = mUtils.createPartFromString(addproductprice.text.toString())
        map["country"] = mUtils.createPartFromString(addproductorigin.text.toString())
        map["country"] = mUtils.createPartFromString(addproductorigin.text.toString())
        var avail = 0
        if (spinner.selectedItemPosition == 0)
            avail = 1
        map["availability"] = mUtils.createPartFromString(avail.toString())
        var arrayList: ArrayList<String> = ArrayList()
        for (i in mAlbumFiles) {
            arrayList.add(i!!.path)
        }
        viewModel.vendorAddProductAPI(this, true, map, arrayList, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    private fun validations(): Boolean {
        if (firstimage.equals("")) {
            AppUtils.showErrorAlert(this, "Please select an image for the product")
            return false
        } else if (addproductbrand.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter brand")
            return false
        } else if (addproductname.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter product name")
            return false
        } else if (addproductorigin.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter country")
            return false
        } else if (curreMeasurementId.trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please select measurement")
            return false
        } else if (addproductprice.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter price")
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