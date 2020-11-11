package com.live.stalkstockcommercial.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_parent.*

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        when(intent.extras!!.getString("fragment")){
            "0"->{
               // replaceFragment(OrderDetailFragment())
                tvStatus.text = "Pending"
            }
            "1"->{
                tvStatus.text = "Delivered"
                tvStatus.setTextColor(resources.getColor(R.color.themeColor))

            }
        }


        ivBackDetail.setOnClickListener { onBackPressed() }
    }

    // function for replacing any fragment over this activity
}