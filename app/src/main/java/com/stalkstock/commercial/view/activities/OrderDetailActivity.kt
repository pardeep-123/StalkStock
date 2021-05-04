package com.live.stalkstockcommercial.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.utils.others.AppController
import kotlinx.android.synthetic.main.activity_parent.*

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        when(intent.extras!!.getString("fragment")){
            "0"->{
               // replaceFragment(OrderDetailFragment())
                tvStatus.text = "Pending"
                tv_delivered_by.visibility=View.GONE
                tv_delivered_by_value.visibility=View.GONE


                tv_delivered_to.visibility=View.VISIBLE
                tv_delivered_to_value.visibility=View.VISIBLE
                tv_delivered_to.setText("DELIVERED BY")
            }
            "1"->{
                tvStatus.text = "Delivered"
                tvStatus.setTextColor(resources.getColor(R.color.themeColor))

            }
        }



        ivBackDetail.setOnClickListener { onBackPressed() }
    }

    override fun onResume() {
        super.onResume()

        if(AppController.getInstance().getString("usertype").equals("2")){
            rl_visa.visibility= View.VISIBLE
            tv_rest_charges.visibility= View.GONE
            tv_free.text= "Fee"
            rv_hp_charges.visibility= View.GONE
            rl_charges.visibility= View.GONE
            tv_total.text= "$81.50".toString()

        }else{
            rl_visa.visibility= View.GONE
        }
    }

    // function for replacing any fragment over this activity
}