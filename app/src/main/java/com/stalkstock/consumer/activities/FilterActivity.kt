package stalkstockcommercial.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.innovattic.rangeseekbar.RangeSeekBar
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.adapter.FilterAdapter
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
           /* val intent = Intent(applicationContext,MainConsumerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)*/
            onBackPressed()
        }
        btn_clear.setOnClickListener {
           /* val intent = Intent(applicationContext,MainConsumerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)*/
            onBackPressed()
        }
        tvSortBy.setOnClickListener {
            if (MyApplication.instance.getString("usertype").equals("3")){
                    onBackPressed()
            } else{
                val intent = Intent(applicationContext,ProductActivity::class.java)
                startActivity(intent)
            }

        }
        rlCost.setOnClickListener {


            if (MyApplication.instance.getString("usertype").equals("3")){

                onBackPressed()
            } else{
                val intent = Intent(applicationContext,ProductActivity::class.java)
                startActivity(intent)
            }


        }

        //vender
        if (MyApplication.instance.getString("usertype").equals("3")){
            tv_sort_cate.visibility=View.VISIBLE
            rl_sort_cate.visibility=View.VISIBLE
        }else{

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
