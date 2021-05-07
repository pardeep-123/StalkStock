package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.consumer.activities.ThanksActivity
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.toolbar.*

class PaymentActivity : AppCompatActivity(), View.OnClickListener {
    val mContext:Context = this
    var click=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_payment)

        tv_heading.text = "Payment"
        iv_back.setOnClickListener(this)
        btn_checkout.setOnClickListener(this)
        btn_preview.setOnClickListener(this)
        firstclick.setOnClickListener(this)
        secondclick.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.btn_checkout -> {
                if (click==0){
                    click=1;
                    btn_checkout.setText("Pay Now")
                }else{
                    if (MyApplication.instance.getString("usertype").equals("3")){
                        val intent = Intent(mContext, ThanksActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(mContext, ThankyouActivity::class.java)
                        startActivity(intent)
                    }
                }

            } R.id.btn_preview -> {
                val intent = Intent(mContext, AfteraddActivity::class.java)
                startActivity(intent)
            }
            R.id.firstclick -> {
                oneone.setImageResource(R.drawable.radio_fill)
                onetwo.setImageResource(R.drawable.radio_circle)
            }R.id.secondclick -> {
            oneone.setImageResource(R.drawable.radio_circle)
            onetwo.setImageResource(R.drawable.radio_fill)
            }
        }
    }
}