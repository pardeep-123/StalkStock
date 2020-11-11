package stalkstockcommercial.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innovattic.rangeseekbar.RangeSeekBar
import com.stalkstock.R
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.fragment.HomeCounsumerFragment
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), RangeSeekBar.SeekBarChangeListener {

    var arrayList = ArrayList<FilterData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        arrayList.add(FilterData("Kosher"))
        arrayList.add(FilterData("Halal",true))
        arrayList.add(FilterData("Organic"))
        arrayList.add(FilterData("Seasonal"))
        recy_filter.adapter = FilterAdapter(arrayList)
        rangeSeekBar.setMaxThumbValue(250)
        rangeSeekBar.seekBarChangeListener = this

        btn_apply.setOnClickListener {
            val intent = Intent(applicationContext,MainConsumerActivity::class.java)
            startActivity(intent)
        }
        tvSortBy.setOnClickListener {
            val intent = Intent(applicationContext,ProductActivity::class.java)
            startActivity(intent)
        }
        rlCost.setOnClickListener {
            val intent = Intent(applicationContext,ProductActivity::class.java)
            startActivity(intent)
        }

    }

     data class  FilterData(var name :String , var isBoolen : Boolean = false)

    override fun onStartedSeeking() {

    }

    override fun onStoppedSeeking() {

    }

    override fun onValueChanged(minThumbValue: Int, maxThumbValue: Int) {
        tv_start.text = "$$minThumbValue.00"
        tv_end.text = "$$maxThumbValue.00"
    }
}
