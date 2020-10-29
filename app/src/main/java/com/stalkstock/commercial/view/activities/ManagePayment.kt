package com.live.stalkstockcommercial.ui.paymnet

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stalkstock.R
import kotlinx.android.synthetic.main.manage_payment.*

class ManagePayment : AppCompatActivity() {


    var list  : ArrayList<PaymentMethodData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_payment)



        ivBackPayment.setOnClickListener { onBackPressed() }
        btnAddNew.setOnClickListener { startActivity(Intent(this,AddNewCard::class.java)) }


        list.add(PaymentMethodData("VISA CARD",resources.getDrawable(R.drawable.visa_img),"**** **** **** 1234"
            ,"MM/YY 12/24 CVV ***","Added on jan 10,2020",true))

        list.add(PaymentMethodData("MASTER CARD",resources.getDrawable(R.drawable.master_card),"**** **** **** 1234"
            ,"MM/YY 12/24 CVV ***","Added on jan 10,2020",false))

        list.add(PaymentMethodData("PAYPAL",resources.getDrawable(R.drawable.paypal),"**** **** **** 1234"
            ,"MM/YY 12/24 CVV ***","Added on jan 10,2020",false))
        rvPaymentMethods.adapter = PaymentMethodAdapter(list)

    }

    data class PaymentMethodData(var type: String = "",var icon:Drawable,
                                 var cardNumber:String = "",var monthData:String = "",var date: String = "",var default: Boolean = false)
}
