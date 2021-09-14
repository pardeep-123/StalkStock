package com.stalkstock.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_terms_condition.*

class TermsConditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_condition)


        back_arrow.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
