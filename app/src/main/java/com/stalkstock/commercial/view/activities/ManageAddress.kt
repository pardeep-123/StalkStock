package com.live.stalkstockcommercial.ui.view.fragments.account

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.live.stalkstockcommercial.ui.address.AddAddress
import com.stalkstock.R
import kotlinx.android.synthetic.main.manage_address.*
import java.util.*

class ManageAddress : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(R.layout.manage_address)

        ivBackManage.setOnClickListener {
            onBackPressed()
        }

        btnDelete.setOnClickListener {
            openStartInfoApp()
        }

        tbnAdd.setOnClickListener {
            val intent = Intent(this, AddAddress::class.java)
            intent.putExtra("key","add")
            startActivity(intent)
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, AddAddress::class.java)
            intent.putExtra("key","edit")
            startActivity(intent)
        }
    }


    fun openStartInfoApp() {
        val dialogSuccessful = Dialog(this, R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.delete_successfully_alertaddress)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull<Window>(dialogSuccessful.window).setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)

        val btn_yes = dialogSuccessful.findViewById<Button>(R.id.btn_yes)
        val btn_no = dialogSuccessful.findViewById<Button>(R.id.btn_no)

        btn_yes.setOnClickListener { dialogSuccessful.dismiss() }

        btn_no.setOnClickListener { dialogSuccessful.dismiss() }

        dialogSuccessful.show()
    }


}
