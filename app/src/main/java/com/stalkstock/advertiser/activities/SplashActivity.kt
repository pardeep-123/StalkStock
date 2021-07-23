package com.stalkstock.advertiser.activities

 import android.content.Context
 import android.content.Intent
 import android.os.Bundle
 import android.os.CountDownTimer
 import android.view.WindowManager
 import androidx.appcompat.app.AppCompatActivity
 import com.google.android.gms.wallet.WalletConstants
 import com.stalkstock.MyApplication
 import com.stalkstock.R
 import com.stalkstock.commercial.view.activities.MainCommercialActivity
 import com.stalkstock.consumer.activities.MainConsumerActivity
 import com.stalkstock.consumer.activities.SelectuserActivity
 import com.stalkstock.driver.HomeActivity
 import com.stalkstock.utils.others.GlobalVariables
 import com.stalkstock.utils.others.getPrefrence
 import com.stalkstock.vender.ui.BottomnavigationScreen
 import com.stripe.android.Stripe
 import com.stripe.android.model.PaymentMethodCreateParams


class SplashActivity : AppCompatActivity() {
    val mContext : Context = this
    //var value="17.9500005"

    val PUBLISHABLE_KEY = "pk_test_TYooMQauvdEDq54NiTphI7jx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                selectLoginOrHome()
            }
        }.start()



//        val card: MutableMap<String, Any> = HashMap()
//        card["number"] = "4242424242424242"
//        card["exp_month"] = 1
//        card["exp_year"] = 2022
//        card["cvc"] = "314"
//        val params: MutableMap<String, Any> = HashMap()
//        params["type"] = "card"
//        params["card"] = card
//
//
//
//         val paymentMethod: PaymentMethodCreateParams = PaymentMethodCreateParams..create(params)


    }

    private fun selectLoginOrHome() {
        if(!getPrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, "").equals(""))
        {
//            val userType = getPrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "")
            val userType = MyApplication.instance.getString("usertype")
            if (userType.equals("5")) {
                startActivity(Intent(mContext, MainActivity::class.java))
                finishAffinity()
            } else if (userType.equals("1")) {
                startActivity(Intent(mContext, MainConsumerActivity::class.java))
                finishAffinity()
            } else if (userType.equals("4")) {
                startActivity(Intent(mContext, MainCommercialActivity::class.java))
                finishAffinity()
            } else if (userType.equals("3")) {
                startActivity(Intent(mContext, BottomnavigationScreen::class.java))
                finishAffinity()
            } else if (userType.equals("2")) {
                startActivity(Intent(mContext, HomeActivity::class.java))
                finishAffinity()
            }

        }
        else{
            startActivity(Intent(mContext, SelectuserActivity::class.java))
            //startActivity(Intent(mContext, GetStartedPageActivity::class.java))
            finishAffinity()
        }
    }


//        val df = DecimalFormat("0.00")
//       Log.e("precision",df.format(value.toDouble()))
 //
//
//
//       Log.e("valueCheck", DecimalFormat("##.00").format(i2.toFloat()).toString());

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

//        if (Build.VERSION.SDK_INT > 9) {
//            val policy = StrictMode.ThreadPolicy.Builder()
//                .permitAll().build()
//            StrictMode.setThreadPolicy(policy)
//        }
//
//        saveCreditCard()
//    }

//    fun saveCreditCard() {
//        val card = Card(
//            "4242424242424242", 11,
//            2025, "123"
//        )
//        Log.e("card ", ""+card)
//        val validation: Boolean = card.validateCard()
//        Log.e("Validation", ""+validation)
//        if (validation) {
//            startProgress()
//            Stripe().createToken(card, PUBLISHABLE_KEY,
//                object : TokenCallback {
//                    override fun onSuccess(token: Token?) {
//                        try {
//
//                            Log.e("Token_Json",  token.toString());
//
//                        }catch (e:Exception){
//                            //toast print
//                            e.localizedMessage
//                        }
//
//                        }
//
//                    override fun onError(error: Exception?) {
//                        handleError(error!!.localizedMessage)
////                        finishProgress()
//
//                    }
//
//
//                })
//        } else if (!card.validateNumber()) {
//            handleError("The card number that you entered is invalid")
//        } else if (!card.validateExpiryDate()) {
//            handleError("The expiration date that you entered is invalid")
//        } else if (!card.validateCVC()) {
//            handleError("The CVC code that you entered is invalid")
//        } else {
//            handleError("The card details that you entered are invalid")
//        }
//    }
//
//    private fun startProgress() {
//       // progressFragment.show(supportFragmentManager, "progress")
//    }
//
//    private fun finishProgress() {
//        //progressFragment.dismiss()
//    }
//
//    private fun handleError(error: String) {
//       /* val fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error)
//        fragment.show(fragmentManager, "error")*/
//
//        //toast print
//
//        // Log.e("validationErrors",error)
//    }

}