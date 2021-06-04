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
import com.stalkstock.utils.others.GlobalVariables
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

        arrayList.add(FilterData("Kosher"))
        arrayList.add(FilterData("Halal", true))
        arrayList.add(FilterData("Organic"))
        arrayList.add(FilterData("Seasonal"))
        recy_filter.adapter = FilterAdapter(arrayList)
        rangeSeekBar.setMaxThumbValue(100000)
        rangeSeekBar.seekBarChangeListener = this


        fromWhichActivity = intent.getStringExtra("from")

        btn_apply.setOnClickListener {
            /* val intent = Intent(applicationContext,MainConsumerActivity::class.java)
             intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
             startActivity(intent)*/
//            onBackPressed()

            var returnIntent = Intent()
            if (fromWhichActivity.equals("HomeCounsumerFragment"))
                returnIntent = Intent(this, HomeCounsumerFragment::class.java)
            if (fromWhichActivity.equals("HomedetailsActivity"))
                returnIntent = Intent(this, HomedetailsActivity::class.java)
            if (fromWhichActivity.equals("ProductdetailsActivity"))
                returnIntent = Intent(this, ProductdetailsActivity::class.java)
            if (fromWhichActivity.equals("ProductActivity"))
                returnIntent = Intent(this, ProductActivity::class.java)
            if (!fromWhichActivity.isEmpty()) {
                returnIntent.putExtra("lowPrice", currentLowPrice.toString())
                returnIntent.putExtra("highPrice", currentHighPrice.toString())
                returnIntent.putExtra("sortBy", currentSortBy)
                setResult(Activity.RESULT_OK, returnIntent)
            }

            finish()

        }
        btn_clear.setOnClickListener {
            /* val intent = Intent(applicationContext,MainConsumerActivity::class.java)
             intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
             startActivity(intent)*/
            currentLowPrice = 0
            currentHighPrice = 100000
            currentSortBy = ""
            var returnIntent = Intent()
            if (fromWhichActivity.equals("HomeCounsumerFragment"))
                returnIntent = Intent(this, HomeCounsumerFragment::class.java)
            if (fromWhichActivity.equals("HomedetailsActivity"))
                returnIntent = Intent(this, HomedetailsActivity::class.java)
            if (!fromWhichActivity.isEmpty()) {
                returnIntent.putExtra("lowPrice", currentLowPrice.toString())
                returnIntent.putExtra("highPrice", currentHighPrice.toString())
                returnIntent.putExtra("sortBy", currentSortBy.toString())
                setResult(Activity.RESULT_OK, returnIntent)
            }

            finish()

        }
        tvSortBy.setOnClickListener {
/*
            if (MyApplication.instance.getString("usertype").equals("3")) {
                onBackPressed()
            } else {
                val intent = Intent(applicationContext, ProductActivity::class.java)
                startActivity(intent)
            }
*/

/*
            currentSortBy = "high_to_low"
            imgLowToHigh.visibility = View.GONE
            imgHighToLOw.visibility = View.VISIBLE
            txtHighToLow.setTextColor(getColor(R.color.green_colour))
            txtLowToHigh.setTextColor(getColor(R.color.dark_gray))
*/
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
/*
            if (MyApplication.instance.getString("usertype").equals("3")) {

                onBackPressed()
            } else {
                val intent = Intent(applicationContext, ProductActivity::class.java)
                startActivity(intent)
            }
*/


        }

        //vender
        if (MyApplication.instance.getString("usertype").equals("3")) {
            tv_sort_cate.visibility = View.VISIBLE
            rl_sort_cate.visibility = View.VISIBLE
        } else {

        }

        setStaticData()
    }

    private fun setStaticData() {
        Log.e("Thumnbs>>","$currentLowPrice --- $currentHighPrice --- $currentSortBy")
        rangeSeekBar.setMinThumbValue(currentLowPrice)
        rangeSeekBar.setMaxThumbValue(currentHighPrice)
        tv_start.text = "$$currentLowPrice"
        tv_end.text = "$$currentHighPrice"

        if (currentSortBy.equals("low_to_high")) {
            ltLowToHigh.performClick()
        } else {
            rlCost.performClick()
        }
    }

    data class FilterData(var name: String, var isBoolen: Boolean = false)

    override fun onStartedSeeking() {

    }

    override fun onStoppedSeeking() {

    }

    override fun onValueChanged(minThumbValue: Int, maxThumbValue: Int) {
        tv_start.text = "$$minThumbValue"
        currentLowPrice = minThumbValue
        tv_end.text = "$$maxThumbValue"
        currentHighPrice = maxThumbValue
    }
}
