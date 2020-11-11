package com.stalkstock.consumer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stalkstock.R
import com.stalkstock.consumer.adapter.MyordersAdapter
import kotlinx.android.synthetic.main.activity_my_request.*

class MyRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_request)

        myorder_recycle.adapter = MyordersAdapter(this)
        backArrow.setOnClickListener {
            finish()
        }
    }
}