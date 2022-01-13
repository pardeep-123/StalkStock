package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.vender.adapter.AdapterMultipleFiles
import com.stalkstock.vender.adapter.AddEditImageModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.activity_select_category.ivUpload
import kotlinx.android.synthetic.main.activity_select_category.spinner
import kotlinx.android.synthetic.main.activity_select_category.spinnerGetProduct
import kotlinx.android.synthetic.main.added_product.*
import okhttp3.RequestBody


class SelectCategory : BaseActivity(), View.OnClickListener, Observer<RestObservable>,AdapterMultipleFiles.MultipleFilesInterface {
    private lateinit var subCatAdapter: ArrayAdapter<String>
    private var list: ArrayList<String> = ArrayList()
    private var listCategoryBody: ArrayList<ModelCategoryList.Body> = ArrayList()
    private var listSub: ArrayList<String> = ArrayList()
    private var listSubCategoryBody: ArrayList<ModelSubCategoriesList.Body> = ArrayList()
    var mSpinner: Spinner? = null
    lateinit var textView: TextView
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    var categoryId = "0"
    private var productId: String=""
    var subCategoryId = "0"
    private var currentModel: ArrayList<ModelProductListAsPerSubCat.Body> = ArrayList()
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    private var mAlbumFilesMultiple = ArrayList<AlbumFile>()
    private lateinit var adapterMultipleFiles: AdapterMultipleFiles
    private var arrStringMultipleImages: ArrayList<AddEditImageModel> = ArrayList()

    private lateinit var productAdapter: ArrayAdapter<String>
    private var listProduct: ArrayList<String> = ArrayList()
    override fun getContentId(): Int {
        return R.layout.activity_select_category
    }

    override fun onResume() {
        super.onResume()
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getCategories() {
        viewModel.getCategoryListAPI(this, true)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backarrow = findViewById<ImageView>(R.id.selectctgbackarrow)
        val button = findViewById<Button>(R.id.enteritembutton)

        button.setOnClickListener(this)
        backarrow.setOnClickListener(this)
        ivUpload.setOnClickListener(this)
        textView = findViewById(R.id.text)
        textView1 = findViewById(R.id.textone)
        textView2 = findViewById(R.id.texttwo)
        textView3 = findViewById(R.id.textthree)

        adapterMultipleFiles = AdapterMultipleFiles(this, arrStringMultipleImages)
        adapterMultipleFiles.multipleFileInterface=this
        rvSubImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        rvSubImages.adapter = adapterMultipleFiles
        textView.setOnClickListener(View.OnClickListener {
            textView.setBackground(resources.getDrawable(R.drawable.edit_background))
            textView1.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView2.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView3.setBackground(resources.getDrawable(R.drawable.textbackground))
        })
        textView1.setOnClickListener(View.OnClickListener {
            textView.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView1.setBackground(resources.getDrawable(R.drawable.edit_background))
            textView2.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView3.setBackground(resources.getDrawable(R.drawable.textbackground))
        })
        textView2.setOnClickListener(View.OnClickListener {
            textView.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView1.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView2.setBackground(resources.getDrawable(R.drawable.edit_background))
            textView3.setBackground(resources.getDrawable(R.drawable.textbackground))
        })
        textView3.setOnClickListener(View.OnClickListener {
            textView.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView1.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView2.setBackground(resources.getDrawable(R.drawable.textbackground))
            textView3.setBackground(resources.getDrawable(R.drawable.edit_background))
        })

        txtAddTag.setOnClickListener { addSingleTag() }
        imgPlus.setOnClickListener { addSingleTag() }

        ltTagContainer.setOnTagClickListener(object : OnTagClickListener {
            override fun onTagClick(position: Int, text: String) {
                // ...
            }

            override fun onTagLongClick(position: Int, text: String) {
                // ...
            }

            override fun onSelectedTagDrag(position: Int, text: String) {
                // ...
            }

            override fun onTagCrossClick(position: Int) {
                // ...
                ltTagContainer.removeTag(position)
            }
        })
        mSpinner = findViewById<View>(R.id.spinner) as Spinner

        spinner!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@SelectCategory, R.color.black_color
                        )
                    )
                    setAdapterSpinnerSub("0", listSubCategoryBody)
                } else {
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@SelectCategory, R.color.black
                        )
                    )

                    val get = listCategoryBody.get(spinner!!.selectedItemPosition)
                    categoryId = get.id.toString()
                    getSubCategoryAPI(categoryId)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        subCatAdapter = ArrayAdapter(this, R.layout.spinner_item_text, listSub)
        spinnerSubCategory!!.adapter = subCatAdapter

        spinnerSubCategory!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@SelectCategory, R.color.black_color
                        )
                    )
                    setAdapterSpinnerSub("0", listSubCategoryBody)
                } else {
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@SelectCategory, R.color.black
                        )
                    )
                    val selectedItemPosition1 = spinnerSubCategory.selectedItemPosition
                    subCategoryId = listSubCategoryBody.get(selectedItemPosition1).id.toString()

                    getProductAsSubCategory()
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        listProduct.add("Select Product")
        spinnerGetProduct.isEnabled = false
        productAdapter = ArrayAdapter(this, R.layout.spiner_layout_text, listProduct)
        spinnerGetProduct!!.adapter = productAdapter

        spinnerGetProduct.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position!=0){
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@SelectCategory, R.color.black_color
                        )
                    )
                    val product = currentModel[spinnerGetProduct.selectedItemPosition-1]
                    productId = product.id.toString()
                }
                else{
                    (view as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@SelectCategory, R.color.sort_popup_gray_color
                        )
                    )
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        getCategories()

    }
    private fun getProductAsSubCategory() {

            currentModel.clear()

        val map = java.util.HashMap<String, RequestBody>()
        map["subCategoryId"] = mUtils.createPartFromString(subCategoryId)
        map["categoryId"] = mUtils.createPartFromString(categoryId)
        viewModel.getProductAccToCategoryAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)
    }
    private fun setData(mResponse: ModelProductListAsPerSubCat) {
        currentModel.clear()
        currentModel.addAll(mResponse.body)
        spinnerGetProduct.isEnabled = true

        listProduct.clear()
        listProduct.add("Select Product")
        for (i in 0 until currentModel.size) {
            listProduct.add(currentModel[i].name) }

        productAdapter.notifyDataSetChanged()
    }
    private fun getSubCategoryAPI(id1: String) {
        listSubCategoryBody.clear()
        subCatAdapter.notifyDataSetChanged()
        val hashMap = HashMap<String, RequestBody>()
        hashMap["category_id"] = mUtils.createPartFromString(id1)
        viewModel.getSubCategoryListAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)

    }


    private fun addSingleTag() {
        if (addproductTag.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter tag name")
        } else {
            ltTagContainer.addTag(addproductTag.text.toString())
            addproductTag.setText("")
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.ivUpload -> {
                if (arrStringMultipleImages.size == 2) {
                    updateSubscriptionDialog()
                } else {
                    askCameraPermissonsMultiple()
                }
            }
            R.id.selectctgbackarrow -> onBackPressed()
            R.id.enteritembutton -> {
                val tags = ltTagContainer.tags
                if (mAlbumFilesMultiple.size==0) {
                    AppUtils.showErrorAlert(this, "Please select an image for the product")

                }
               else if (spinner.selectedItemPosition == 0) {
                    AppUtils.showErrorAlert(
                        this,
                        "Please select category"
                    )
                } else if (spinnerSubCategory.selectedItemPosition == 0) {
                    AppUtils.showErrorAlert(
                        this,
                        "Please select sub category"
                    )
                } else if (spinnerSubCategory.selectedItemPosition == -1) {
                    AppUtils.showErrorAlert(
                        this,
                        "Change category , Sub category not found for this category "
                    )
                }else if(spinnerGetProduct.selectedItemPosition==0) {
                    spinnerGetProduct.requestFocus()
                    AppUtils.showErrorAlert(this, getString(R.string.please_select_product))
                }else if (addproductprice.text.toString().trim().isEmpty()) {
                    AppUtils.showErrorAlert(this, "Please enter price")

                }else if (addproductprice.text.toString().trim()=="0") {
                    AppUtils.showErrorAlert(this, "Price should be greater than 0")

                }
                else if (tags.size < 1) {
                    AppUtils.showErrorAlert(
                        this,
                        "Add atleast one tag!"
                    )
                } else {
                    val join = TextUtils.join(",", tags)
                    Log.e("Joinedss>>>,", join)

                    val intent = Intent(this@SelectCategory, AddProduct::class.java)
                    intent.putExtra("catId", categoryId)
                    intent.putExtra("price", addproductprice.text.toString())
                    intent.putExtra("productId", productId)
                    intent.putExtra("subCatId", subCategoryId)
                    intent.putExtra("tags", join)
                    val args = Bundle()
                    args.putSerializable("ARRAYLIST", arrStringMultipleImages)
                    intent.putExtra("images",args)
                    startActivity(intent)
                }
            }
        }
    }

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
            startActivity(Intent(this@SelectCategory, Subscription::class.java))
            finishAffinity()
            logoutUpdatedDialogs.dismiss()
        }
        upgrade_cancel.setOnClickListener {
            logoutUpdatedDialogs.dismiss()
        }
        logoutUpdatedDialogs.show()

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
                    }
                }
                if (it.data is ModelProductListAsPerSubCat) {
                    val mResponse: ModelProductListAsPerSubCat = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is ModelSubCategoriesList) {
                    val mResponse: ModelSubCategoriesList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setAdapterSpinnerSub("1", mResponse.body!!)

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
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
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
//        list = ArrayList()
    }

    private fun setAdapterSpinner(mResponse: ModelCategoryList) {
        listCategoryBody.clear()
        listCategoryBody.add(0, ModelCategoryList.Body(0, "", "Select Category", 0))
        listCategoryBody.addAll(mResponse.body)
        list.clear()
        for (i in listCategoryBody) {
            list.add(i.name)
        }
//        list = ArrayList()
        spinner!!.adapter = ArrayAdapter(this, R.layout.spinner_item_text, list)
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