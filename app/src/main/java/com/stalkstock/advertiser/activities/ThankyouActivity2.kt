package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_thankyou.*

class ThankyouActivity2 : AppCompatActivity(), View.OnClickListener {
    val mContext:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou2)
        btn_view.setOnClickListener(this)
//        tv_heading.visibility= View.GONE
        back_img.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_view->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                 finishAffinity()


            }
            R.id.back_img->{
                finish()
            }
        }
    }
}