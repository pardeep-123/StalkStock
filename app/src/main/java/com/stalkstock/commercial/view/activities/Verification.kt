package com.stalkstock.commercial.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.verification.*

class Verification : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.verification)

        verifybutton.setOnClickListener {
           // startActivity(Intent(this, LoginActivity::class.java))
            onBackPressed()
        }
        verify_backarrow.setOnClickListener { onBackPressed() }

        otp_edit_box1.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if(s!!.length==1)
                {
                    otp_edit_box2.requestFocus();
                }
                else if (s != null) {
                    if(s.length ==0) {
                        otp_edit_box1.clearFocus();
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        otp_edit_box2.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if(s!!.length ==1)
                {
                    otp_edit_box3.requestFocus();
                }
                else if(s.length ==0)
                {
                    otp_edit_box1.requestFocus();
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



            }

        })
        otp_edit_box3.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if(s!!.length ==1)
                {
                    otp_edit_box4.requestFocus();
                }
                else if(s.length ==0)
                {
                    otp_edit_box2.requestFocus();
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        otp_edit_box4.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if(s!!.length ==1)
                {
                    otp_edit_box1.clearFocus();
                }
                else if(s.length ==0)
                {
                    otp_edit_box3.requestFocus();
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })


    }

    override fun onBackPressed() {
        //super.onBackPressed()
        //  super.onBackPressed();
        val returnIntent = Intent()
        returnIntent.putExtra("status", "login")
         setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


}
