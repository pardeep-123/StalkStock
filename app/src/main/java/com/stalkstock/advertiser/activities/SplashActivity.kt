package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.stalkstock.R
import com.stalkstock.consumer.activities.SelectuserActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {
    val mContext : Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                startActivity(Intent(mContext, SelectuserActivity::class.java))
                finishAffinity()
            }
        }.start()



      /*  var jsonArray= JSONArray()

        for (i in 0 until cartItem.items!!.size) {
            val student1 = JSONObject()
            var addOnName = ""

            try {
                val studentArray = JSONArray()
                for (j in 0 until cartItem.items!!.get(i).categories.size) {

                    val student2 = JSONObject()

                    for (k in 0 until cartItem.items!!.get(i).categories.get(j).addOnArray.size) {
                        if (cartItem.items!!.get(i).categories.get(j).addOnArray.get(k).isSelected == true) {
                            addOnName = cartItem.items!!.get(i).categories.get(j).addOnArray.get(k).addon
                            student2.put("addonId","")
                            student2.put("addon","")
                            student2.put("itemId","")
                            student2.put("addonCategory","")
                            student2.put("quantity","")
                            studentArray.put(student2)
                         }
                    }


                }
                student1.put("restaurantId", cartItem.restaurantId)
                student1.put("itemDescription", cartItem.items!!.get(i).itemQuantity)
                student1.put("itemImagePath", cartItem.items!!.get(i).id)
                student1.put("quantity", cartItem.items!!.get(i).id)
                student1.put("itemMenuId", cartItem.items!!.get(i).id)
                student1.put("itemName", cartItem.items!!.get(i).id)
                student1.put("price", cartItem.items!!.get(i).id)
                student1.put("addons", studentArray)
              } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            jsonArray.put(student1)
        }
        itemArray = jsonArray.toString()



        println("jsonString: $jsonArray")*/
    }
}