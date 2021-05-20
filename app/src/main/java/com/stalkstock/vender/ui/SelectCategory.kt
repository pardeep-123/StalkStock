package com.stalkstock.vender.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.common.model.ModelSubCategoriesList
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_select_category.*
import okhttp3.RequestBody


class SelectCategory : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private lateinit var subCatAdapter: ArrayAdapter<String>
    private var list: ArrayList<String> = ArrayList()
    private var listCategoryBody: ArrayList<ModelCategoryList.Body> = ArrayList()
    private var listSub: ArrayList<String> = ArrayList()
    private var listSubCategoryBody: ArrayList<ModelSubCategoriesList.Body> = ArrayList()
    var spinner: Spinner? = null
    lateinit var textView: TextView
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    override fun getContentId(): Int {
        return R.layout.activity_select_category
    }

    override fun onResume() {
        super.onResume()
        getCategories()
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
        textView = findViewById(R.id.text)
        textView1 = findViewById(R.id.textone)
        textView2 = findViewById(R.id.texttwo)
        textView3 = findViewById(R.id.textthree)
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
        spinner = findViewById<View>(R.id.spinner) as Spinner
        spinner!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val get = listCategoryBody.get(spinner!!.selectedItemPosition)
                val id1 = get.id.toString()
                getSubCategoryAPI(id1)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        subCatAdapter = ArrayAdapter(this, R.layout.spinner_item_text, listSub)
        spinnerSubCategory!!.adapter = subCatAdapter

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
            R.id.selectctgbackarrow -> onBackPressed()
            R.id.enteritembutton -> {

                if (spinnerSubCategory.selectedItemPosition == -1) {
                    AppUtils.showErrorAlert(
                        this,
                        "Change category , Sub category not found for this category "
                    )
                } else {

                    val tags = ltTagContainer.tags
                    val join = TextUtils.join(",", tags)
                    Log.e("Joinedss>>>,", join)

                    val selectedItemPosition = spinner!!.selectedItemPosition
                    val id1 = listCategoryBody.get(selectedItemPosition).id.toString()

                    val selectedItemPosition1 = spinnerSubCategory.selectedItemPosition
                    val subCat = listSubCategoryBody.get(selectedItemPosition1).id.toString()

                    var intent = Intent(this@SelectCategory, AddProduct::class.java)
                    intent.putExtra("catId", id1)
                    intent.putExtra("subCatId", subCat)
                    intent.putExtra("tags", join)
                    startActivity(intent)
                }
            }
        }
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

                if (it.data is ModelSubCategoriesList) {
                    val mResponse: ModelSubCategoriesList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setAdapterSpinnerSub(mResponse)
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

    private fun setAdapterSpinnerSub(mResponse: ModelSubCategoriesList) {
        listSubCategoryBody.clear()
        listSubCategoryBody.addAll(mResponse.body)
        listSub.clear()
        for (i in listSubCategoryBody) {
            listSub.add(i.name)
        }

        subCatAdapter.notifyDataSetChanged()
//        list = ArrayList()
    }

    private fun setAdapterSpinner(mResponse: ModelCategoryList) {
        listCategoryBody.clear()
        listCategoryBody.addAll(mResponse.body)
        list.clear()
        for (i in listCategoryBody) {
            list.add(i.name)
        }
//        list = ArrayList()
        spinner!!.adapter = ArrayAdapter(this, R.layout.spinner_item_text, list)
    }
}