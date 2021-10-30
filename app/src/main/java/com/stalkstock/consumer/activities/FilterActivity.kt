package com.stalkstock.consumer.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.innovattic.rangeseekbar.RangeSeekBar
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.consumer.adapter.FilterAdapter
import com.stalkstock.consumer.fragment.HomeCounsumerFragment
import com.stalkstock.utils.others.GlobalVariables.FilterVariables.Companion.currentHighPrice
import com.stalkstock.utils.others.GlobalVariables.FilterVariables.Companion.currentLowPrice
import com.stalkstock.utils.others.GlobalVariables.FilterVariables.Companion.currentSortBy
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), RangeSeekBar.SeekBarChangeListener {

    private var fromWhichActivity = ""
    var arrayList = ArrayList<FilterData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        rangeSeekBar.setMaxThumbValue(1000)
        rangeSeekBar.seekBarChangeListener = this
        fromWhichActivity = intent.getStringExtra("from")!!

        btn_apply.setOnClickListener {

            var returnIntent = Intent()
            if (fromWhichActivity == "HomeCounsumerFragment")
                returnIntent = Intent(this, HomeCounsumerFragment::class.java)
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
            txtHighToLow.setTextColor(getColor(R.color.dark_gray))
            txtLowToHigh.setTextColor(getColor(R.color.green_colour))
        }
        rlCost.setOnClickListener {
            currentSortBy = "high_to_low"
            imgLowToHigh.visibility = View.GONE
            imgHighToLOw.visibility = View.VISIBLE
            txtHighToLow.setTextColor(getColor(R.color.green_colour))
            txtLowToHigh.setTextColor(getColor(R.color.dark_gray))
        }

        if (MyApplication.instance.getString("usertype").equals("3")) {
            tv_sort_cate.visibility = View.VISIBLE
            rl_sort_cate.visibility = View.VISIBLE
        }
        else { }
        setStaticData()
    }

    private fun setStaticData() {
        Log.e("Thumnbs>>","$currentLowPrice --- $currentHighPrice --- $currentSortBy")
        rangeSeekBar.setMinThumbValue(currentLowPrice)
        rangeSeekBar.setMaxThumbValue(currentHighPrice)
        tv_start.text = "$$currentLowPrice"
        tv_end.text = "$$currentHighPrice"

        if (currentSortBy == "low_to_high") {
            ltLowToHigh.performClick()
        }
        else {
            rlCost.performClick()
        }
    }

    data class FilterData(var name: String, var isBoolen: Boolean = false)

    override fun onStartedSeeking() {}

    override fun onStoppedSeeking() {}

    override fun onValueChanged(minThumbValue: Int, maxThumbValue: Int) {
        tv_start.text = "$$minThumbValue"
        currentLowPrice = minThumbValue
        tv_end.text = "$$maxThumbValue"
        currentHighPrice = maxThumbValue
    }
}
