package com.stalkstock.commercial.view.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.stalkstock.commercial.view.adapters.AdapterProductUnit
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.adapters.CategoryCommercialAdapter
import com.stalkstock.commercial.view.adapters.RequestProductHomeAdapter
import com.stalkstock.commercial.view.model.BidingDetailResponse
import com.stalkstock.commercial.view.model.CategoryList
import com.stalkstock.commercial.view.model.Sendbidresponse
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelMeasurementList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.consumer.model.ModelUserAddressList
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.added_product.*
import kotlinx.android.synthetic.main.dialog_home.*
import kotlinx.android.synthetic.main.added_product.back
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class AddedProduct : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private val homeModel: HomeViewModel by viewModels()

    private var reset = false
    private var currentOffsets = 0
    private var currentOffset = 0
    private var currentModel: ArrayList<ModelProductListAsPerSubCat.Body> = ArrayList()
    private var listProduct: ArrayList<String> = ArrayList()
    val listC: ArrayList<CategoryList> = ArrayList()
    private var currentCatId = ""
    var currentDeliveryType = "0"
    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"
    var measurementId = ""

    private var address: ArrayList<ModelUserAddressList.Body> = ArrayList()
    var addressId: Int = 0

    var productCategoryList: ArrayList<ModelCategoryList.Body> = ArrayList()
    private var listSubCategoryBody: ArrayList<ModelSubCategoriesList.Body> = ArrayList()
    private var listSub: ArrayList<String> = ArrayList()
    private lateinit var subCatAdapter: ArrayAdapter<String>
    private lateinit var productAdapter: ArrayAdapter<String>
    private lateinit var adapterMeasurements: AdapterProductUnit
    var currentMeasurementId = ""
    var currentMeasurementName = ""
    var quantity: String = ""
    var type: String = ""
    var categoryId: String = ""
    var subCategoryId: String = ""
    var productId: String = ""
    lateinit var category: CategoryList

    var list: ArrayList<RequestProductData> = ArrayList()
    var orderList: ArrayList<BidingDetailResponse.OrderItem> = ArrayList()

    lateinit var detailDialog: Dialog
    var listProductUnit: ArrayList<ProductUnitData> = ArrayList()
    private var currentModelMeasurements: ArrayList<ModelMeasurementList.Body> = ArrayList()

    override fun getContentId(): Int {
        return R.layout.added_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAddressApi()

        getCategoryListApi()

        rlUnitMesurment.setOnClickListener {
            setUnitList()
        }

        etUnitMeasurement.setOnClickListener {
            setUnitList()
        }

        tvUnitMeasurement.setOnClickListener {
            setUnitList()
        }

        btnSubmit.setOnClickListener {
            addBidingRequestApi()
        }

        back.setOnClickListener {
            onBackPressed()
        }

        spinnerProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddedProduct, R.color.black_color
                        )
                    )
                    val categories = productCategoryList[spinnerProduct!!.selectedItemPosition - 1]
                    categoryId = categories.id.toString()
                    getProductList()
                } else {
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddedProduct, R.color.black_color
                        )
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        listSub.add("Select Sub Category")
        spinnerSubProduct.isEnabled = false
        subCatAdapter = ArrayAdapter(this, R.layout.spiner_layout_text, listSub)
        spinnerSubProduct!!.adapter = subCatAdapter


        listProduct.add("Select Product")
        spinnerGetProduct.isEnabled = false
        productAdapter = ArrayAdapter(this, R.layout.spiner_layout_text, listProduct)
        spinnerGetProduct!!.adapter = productAdapter

        spinnerSubProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if (position != 0) {
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddedProduct, R.color.black_color
                        )
                    )
                    val products = listSubCategoryBody[spinnerSubProduct.selectedItemPosition - 1]
                    subCategoryId = products.id.toString()
                    getProductAsSubCategory()
                } else {
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddedProduct, R.color.black_color
                        )
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerGetProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if (position != 0) {
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddedProduct, R.color.black_color
                        )
                    )
                    val product = currentModel[spinnerGetProduct.selectedItemPosition - 1]
                    productId = product.id.toString()
                } else {
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@AddedProduct, R.color.black_color
                        )
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        adapterMeasurements = AdapterProductUnit(this, listProductUnit)
        getMeasurementApi()

        tvAddMore.setOnClickListener {
            type = productId
            quantity = etEnterQuantity.text.toString()
            showDataList()

            btnSave.visibility = VISIBLE
            btnSubmit.visibility = GONE


        }

        btnSave.setOnClickListener {

            if (list.size == 0) {
                showDataList()
            } else if (spinnerProduct.selectedItemPosition == 0 && spinnerSubProduct.selectedItemPosition == 0
                && spinnerGetProduct.selectedItemPosition == 0 && etEnterQuantity.text!!.isEmpty()
                && etUnitMeasurement.text!!.isEmpty()
            ) {
                btnSave.visibility = GONE
                btnSubmit.visibility = VISIBLE

            } else {
                btnSave.visibility = GONE
                btnSubmit.visibility = VISIBLE
                addBidingRequestApi()
            }
        }
    }

    private fun getAddressApi() {
        if (reset) {
            currentOffsets = 0
            address.clear()
        }
        val map = java.util.HashMap<String, RequestBody>()
        map["offset"] = mUtils.createPartFromString(currentOffsets.toString())
        map["limit"] = mUtils.createPartFromString("5")
        homeModel.getUserAddressListAPI(this, true, map)
        homeModel.homeResponse.observe(this, this)
    }

    private fun getProductAsSubCategory() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val map = java.util.HashMap<String, RequestBody>()
        map["subCategoryId"] = mUtils.createPartFromString(subCategoryId)
        map["categoryId"] = mUtils.createPartFromString(categoryId)
        homeModel.getProductAccToCategoryAPI(this, true, map)
        homeModel.homeResponse.observe(this, this)
    }

    private fun showDataList() {


        when {
            spinnerProduct.selectedItemPosition == 0 -> {
                spinnerProduct.requestFocus()
                AppUtils.showErrorAlert(this, getString(R.string.please_select_category))
            }

            spinnerSubProduct.selectedItemPosition == 0 -> {
                spinnerSubProduct.requestFocus()
                AppUtils.showErrorAlert(this, getString(R.string.please_select_sub_category))
            }
            spinnerGetProduct.selectedItemPosition == 0 -> {
                spinnerGetProduct.requestFocus()
                AppUtils.showErrorAlert(this, getString(R.string.please_select_product))
            }

            etEnterQuantity.text!!.isEmpty() -> {
                etEnterQuantity.requestFocus()
                etEnterQuantity.error = resources.getString(R.string.please_enter_quantity)
            }

            etUnitMeasurement.text!!.isEmpty() -> {
                etUnitMeasurement.requestFocus()
                etUnitMeasurement.error = resources.getString(R.string.please_select_quantity)
            }
            else -> {
                list.add(
                    RequestProductData(
                        spinnerGetProduct.selectedItem.toString(),
                        type,
                        productId,
                        currentMeasurementName,
                        quantity,
                        measurementId,
                        edit = true,
                        delete = true
                    )
                )
                val adapter = RequestProductHomeAdapter(list, orderList)
                rvRequestProducts.adapter = adapter
                adapter.notifyDataSetChanged()

                if (list.size == 0) {
                    card_added.visibility = View.GONE
                } else {
                    card_added.visibility = View.VISIBLE
                }
                spinnerSubProduct.setSelection(0)
                spinnerProduct.setSelection(0)
                spinnerGetProduct.setSelection(0)

                etEnterQuantity.setText("")
                etUnitMeasurement.setText("")
            }
        }
    }

    private fun setUnitList() {
        setDialog()
    }

    private fun getCategoryListApi() {
        homeModel.getCategoryListAPI(this, true)
        homeModel.homeResponse.observe(this, this)
    }

    private fun getProductList() {
        val map = HashMap<String, RequestBody>()
        map["category_id"] = mUtils.createPartFromString(categoryId)
        homeModel.getSubCategoryListAPI(this, true, map)
    }

    private fun getMeasurementApi() {
        homeModel.measurementListAPI(this, true)
        homeModel.homeResponse.observe(this, this)
    }

    private fun addBidingRequestApi() {
        val jsonArray = JSONArray()
        val student1 = JSONObject()
        val quantity = etEnterQuantity.text.toString()
        val hashMap = HashMap<String, RequestBody>()

        if (list.size > 0) {

            for (i in 0 until list.size) {
                val student1 = JSONObject()
                student1.put("productId", list[i].type)
                student1.put("qty", list[i].quantity)
                student1.put("measurementId", list[i].unit)
                jsonArray.put(student1)
            }

            Log.i("list", list.toString())

            homeModel.sendBidingRequest(this, addressId, jsonArray, true)
            homeModel.homeResponse.observe(this, this)
        } else {
            if (spinnerProduct.selectedItemPosition == 0) {
                spinnerProduct.requestFocus()
                AppUtils.showErrorAlert(this, getString(R.string.please_select_category))
            } else if (spinnerSubProduct.selectedItemPosition == 0) {
                spinnerSubProduct.requestFocus()
                AppUtils.showErrorAlert(this, getString(R.string.please_select_sub_category))
            } else if (spinnerGetProduct.selectedItemPosition == 0) {
                spinnerGetProduct.requestFocus()
                AppUtils.showErrorAlert(this, getString(R.string.please_select_product))
            } else if (etEnterQuantity.text.toString().trim().isEmpty()) {
                etEnterQuantity.requestFocus()
                etEnterQuantity.error = resources.getString(R.string.please_enter_quantity)
            } else if (etEnterQuantity.text.toString().trim() == "0") {
                etEnterQuantity.requestFocus()
                etEnterQuantity.error = getString(R.string.qty_greater_zero)
            } else if (etUnitMeasurement.text.toString().trim().isEmpty()) {
                etUnitMeasurement.requestFocus()
                etUnitMeasurement.error = resources.getString(R.string.please_select_quantity)
            } else {

                if (list.size == 0) {
                    student1.put("productId", productId)
                    student1.put("qty", quantity.toInt())
                    student1.put("measurementId", measurementId)
                    jsonArray.put(student1)
                }


                homeModel.sendBidingRequest(this, addressId, jsonArray, true)
                homeModel.homeResponse.observe(this, this)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
        detailDialog.rvProductUnit.adapter = adapterMeasurements
        detailDialog.llDialog.setOnClickListener { detailDialog.dismiss() }
        detailDialog.show()
    }


    data class RequestProductData(
        var name: String = "",
        var type: String = "",
        val productId: String = "",
        val unitName: String = "",
        var quantity: String = "",
        var unit: String = "",
        var edit: Boolean = false,
        var delete: Boolean = false
    )

    data class ProductUnitData(var id: Int = 0, var name: String = "", var status: Int = 0)

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is Sendbidresponse) {
                    val mResponse: Sendbidresponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        val intent = Intent(this, AddThanks::class.java)
                        intent.putExtra("requestNo", it.data.body.requestNo)
                        startActivity(intent)
                    }
                }/*else {
                    AppUtils.showErrorAlert(this,"Please add your address first before sending the request.")
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 2000)
                }*/

                if (it.data is ModelUserAddressList) {
                    val mResponse: ModelUserAddressList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        if (mResponse.body.size == 0) {
                            AppUtils.showErrorAlert(
                                this,
                                getString(R.string.add_address_first)
                            )
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 2000)
                        } else {
                            currentOffset += 5
                            address.addAll(mResponse.body)
                            addressId = address[0].id
                        }

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

                if (it.data is ModelCategoryList) {
                    val mResponse: ModelCategoryList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        productCategoryList.clear()
                        productCategoryList.addAll(mResponse.body)

                        listC.clear()
                        listC.add(CategoryList(0, 0, "Select category", ""))
                        if (productCategoryList.isNotEmpty()) {
                            for (i in 0 until productCategoryList.size) {
                                listC.add(
                                    CategoryList(
                                        productCategoryList[i].id, productCategoryList[i].status,
                                        productCategoryList[i].name, productCategoryList[i].image
                                    )
                                )
                            }
                        }

                        val categoryList = CategoryCommercialAdapter(this, "Select category", listC)
                        spinnerProduct.adapter = categoryList
                    }
                }

                if (it.data is ModelSubCategoriesList) {
                    val mResponse: ModelSubCategoriesList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        spinnerSubProduct.isEnabled = true
                        listSubCategoryBody.clear()
                        listSubCategoryBody.addAll(mResponse.body)
                        listSub.clear()
                        listSub.add("Select Sub Category")
                        if (listSubCategoryBody.isNotEmpty()) {
                            for (i in 0 until listSubCategoryBody.size) {
                                listSub.add(listSubCategoryBody[i].name)
                            }
                            subCatAdapter.notifyDataSetChanged()
                        }

                    } else AppUtils.showErrorAlert(this, mResponse.message)
                }

                if (it.data is ModelMeasurementList) {
                    val mResponse: ModelMeasurementList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setDataMeasurements(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
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

    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.clear()
        currentModel.addAll(mResponse.body)
        reset = false
        spinnerGetProduct.isEnabled = true

        listProduct.clear()
        listProduct.add("Select Product")
        for (i in 0 until currentModel.size) {
            if (!currentModel[i].title.isNullOrEmpty()) {
                listProduct.add(currentModel[i].title + " " + currentModel[i].name)

            } else {
                listProduct.add(currentModel[i].name)

            }
        }

        productAdapter.notifyDataSetChanged()
    }

    private fun setDataMeasurements(mResponse: ModelMeasurementList) {
        listProductUnit.clear()
        currentModelMeasurements.clear()
        currentModelMeasurements = mResponse.body as ArrayList<ModelMeasurementList.Body>

        for (i in currentModelMeasurements) {
            listProductUnit.add(ProductUnitData(i.id, i.name, i.status))
        }
        adapterMeasurements.notifyDataSetChanged()
    }

    fun setSelectedMeasurement(position: Int, productUnitData: ProductUnitData) {
        measurementId = productUnitData.id.toString()
        currentMeasurementName = productUnitData.name.toString()
        for (i in 0 until currentModelMeasurements.size) {
            listProductUnit[i] = ProductUnitData(
                currentModelMeasurements[i].id,
                currentModelMeasurements[i].name,
                currentModelMeasurements[i].status
            )
        }
        val productUnitData1 =
            ProductUnitData(productUnitData.id, productUnitData.name, productUnitData.status)
        listProductUnit[position] = productUnitData1
        adapterMeasurements.notifyDataSetChanged()
        etUnitMeasurement.setText(productUnitData.name)
        detailDialog.dismiss()
        currentMeasurementId = currentModelMeasurements[position].id.toString()

    }

    fun createRequestBody(param: String): RequestBody {
        val request = RequestBody.create(
            MediaType.parse("text/plain"),
            param
        )
        return request
    }


    override fun onClick(v: View?) {
    }
}
