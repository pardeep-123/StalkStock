package com.stalkstock.consumer.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.slider.RangeSlider
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.consumer.fragment.HomeCounsumerFragment
import com.stalkstock.utils.others.GlobalVariables.FilterVariables.Companion.currentHighPrice
import com.stalkstock.utils.others.GlobalVariables.FilterVariables.Companion.currentLowPrice
import com.stalkstock.utils.others.GlobalVariables.FilterVariables.Companion.currentSortBy
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.activity_select_category.*

class FilterActivity : AppCompatActivity() {

    private var fromWhichActivity = ""
    var arrayList = ArrayList<FilterData>()
    var categoryId = ""
    var productType=0 // 1- Non veg, 0- veg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        rangePrice.addOnChangeListener(object : RangeSlider.OnChangeListener {

            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                val start_values = rangePrice.values[0].toInt()
                val end_values = rangePrice.values[1].toInt()

                tv_start.text = "$$start_values"
                currentLowPrice = start_values
                tv_end.text = "$$end_values"
                currentHighPrice = end_values

            }
        })

        fromWhichActivity = intent.getStringExtra("from")!!

        spinner2!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@FilterActivity, R.color.sort_popup_gray_color
                        )
                    )
                    categoryId = ""
                } else {
                    (selectedItemView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@FilterActivity, R.color.black
                        )
                    )
                    categoryId = spinner2.selectedItemPosition.toString()

                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        btn_apply.setOnClickListener {

            var returnIntent = Intent()
            if (fromWhichActivity == "HomeCounsumerFragment") {
                val returnIntent = Intent()
                returnIntent.putExtra("lowPrice", currentLowPrice.toString())
                returnIntent.putExtra("highPrice", currentHighPrice.toString())
                returnIntent.putExtra("sortBy", currentSortBy)
                returnIntent.putExtra("productType", productType)
                setResult(RESULT_OK, returnIntent)
            }
            if (fromWhichActivity == "MainHomeFragment") {
                val returnIntent = Intent()
                returnIntent.putExtra("lowPrice", currentLowPrice.toString())
                returnIntent.putExtra("highPrice", currentHighPrice.toString())
                returnIntent.putExtra("sortBy", currentSortBy)
                returnIntent.putExtra("productType", productType)
                setResult(RESULT_OK, returnIntent)
            }
            if (fromWhichActivity == "HomedetailsActivity")
                returnIntent = Intent(this, HomedetailsActivity::class.java)
            if (fromWhichActivity == "ProductdetailsActivity")
                returnIntent = Intent(this, ProductDetailsActivity::class.java)
            if (fromWhichActivity == "ProductActivity")
                returnIntent = Intent(this, ProductActivity::class.java)
            if (fromWhichActivity.isNotEmpty()) {
                returnIntent.putExtra("lowPrice", currentLowPrice.toString())
                returnIntent.putExtra("highPrice", currentHighPrice.toString())
                returnIntent.putExtra("sortBy", currentSortBy)
                returnIntent.putExtra("productType", productType.toString())
                setResult(Activity.RESULT_OK, returnIntent)
            }
            finish()
        }
        btn_clear.setOnClickListener {

            currentLowPrice = 0
            currentHighPrice = 1000
            currentSortBy = ""
            var returnIntent = Intent()
            if (fromWhichActivity == "HomeCounsumerFragment")
                returnIntent = Intent(this, HomeCounsumerFragment::class.java)
            if (fromWhichActivity == "HomedetailsActivity")
                returnIntent = Intent(this, HomedetailsActivity::class.java)
            if (fromWhichActivity.isNotEmpty()) {
                returnIntent.putExtra("lowPrice", currentLowPrice.toString())
                returnIntent.putExtra("highPrice", currentHighPrice.toString())
                returnIntent.putExtra("sortBy", currentSortBy)
                setResult(Activity.RESULT_OK, returnIntent)
            }
            finish()
        }
        ltLowToHigh.setOnClickListener {
            currentSortBy = "low_to_high"
            imgLowToHigh.visibility = View.VISIBLE
            imgHighToLOw.visibility = View.GONE
            imgRating.visibility = View.GONE
            imgPopular.visibility = View.GONE
            txtHighToLow.setTextColor(getColor(R.color.dark_gray))
            txtLowToHigh.setTextColor(getColor(R.color.green_colour))
            txtPopular.setTextColor(getColor(R.color.dark_gray))
            txtRating.setTextColor(getColor(R.color.dark_gray))
        }
        rlCost.setOnClickListener {
            currentSortBy = "high_to_low"
            imgLowToHigh.visibility = View.GONE
            imgRating.visibility = View.GONE
            imgPopular.visibility = View.GONE
            imgHighToLOw.visibility = View.VISIBLE
            txtHighToLow.setTextColor(getColor(R.color.green_colour))
            txtLowToHigh.setTextColor(getColor(R.color.dark_gray))
            txtPopular.setTextColor(getColor(R.color.dark_gray))
            txtRating.setTextColor(getColor(R.color.dark_gray))
        }


        rlPopular.setOnClickListener {
            currentSortBy = "popular"
            imgLowToHigh.visibility = View.GONE
            imgHighToLOw.visibility = View.GONE
            imgPopular.visibility = View.VISIBLE
            imgRating.visibility = View.GONE
            txtHighToLow.setTextColor(getColor(R.color.dark_gray))
            txtLowToHigh.setTextColor(getColor(R.color.dark_gray))
            txtPopular.setTextColor(getColor(R.color.green_colour))
            txtRating.setTextColor(getColor(R.color.dark_gray))
        }
        rlRating.setOnClickListener {
            currentSortBy = "rating"
            imgLowToHigh.visibility = View.GONE
            imgHighToLOw.visibility = View.GONE
            imgPopular.visibility = View.GONE
            imgRating.visibility = View.VISIBLE
            txtHighToLow.setTextColor(getColor(R.color.dark_gray))
            txtLowToHigh.setTextColor(getColor(R.color.dark_gray))
            txtPopular.setTextColor(getColor(R.color.dark_gray))
            txtRating.setTextColor(getColor(R.color.green_colour))
        }


        if (MyApplication.instance.getString("usertype").equals("3")) {
            tv_sort_cate.visibility = View.VISIBLE
            rl_sort_cate.visibility = View.VISIBLE
        } else {
        }
        setStaticData()
    }

    private fun setStaticData() {
//        Log.e("Thumnbs>>", "$currentLowPrice --- $currentHighPrice --- $currentSortBy")
//        rangeSeekBar.setMinThumbValue(currentLowPrice)
//        rangeSeekBar.setMaxThumbValue(currentHighPrice)
//        tv_start.text = "$$currentLowPrice"
//        tv_end.text = "$$currentHighPrice"

        if (currentSortBy == "low_to_high") {
            ltLowToHigh.performClick()
        } else {
            rlCost.performClick()
        }
    }

    data class FilterData(var name: String, var isBoolen: Boolean = false)


}
