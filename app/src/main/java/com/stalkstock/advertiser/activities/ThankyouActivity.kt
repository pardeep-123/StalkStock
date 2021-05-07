package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.MainCommercialActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import kotlinx.android.synthetic.main.activity_thankyou.*

class ThankyouActivity : AppCompatActivity(), View.OnClickListener {
    val mContext:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)
        btn_view.setOnClickListener(this)
//        tv_heading.visibility= View.GONE
        back_img.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_view->{
                if(MyApplication.instance.getString("usertype").equals("1")){
                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }else if(MyApplication.instance.getString("usertype").equals("2")){
                    val intent = Intent(mContext, MainCommercialActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }else{
                    val intent = Intent(mContext, MainConsumerActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }


            }
            R.id.back_img->{
                finish()
            }
        }
    }
}