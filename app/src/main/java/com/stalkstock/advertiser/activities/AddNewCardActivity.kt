package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_add_new_card.btn_save
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class AddNewCardActivity : AppCompatActivity(), View.OnClickListener {
    val mContext:Context=this

    var current_year: Int = 0
    var future_year = 40
    lateinit var yearArray: Array<String?>
    internal var items = arrayOf<CharSequence>(
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_add_new_card)

        tv_heading.text = "Add a New Card"
        iv_back.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        monthsss.setOnClickListener(this)
        yearsss.setOnClickListener(this)

        current_year = Calendar.getInstance().get(Calendar.YEAR)

        yearArray = arrayOfNulls<String>(future_year)

        for (i in 0 until future_year) {
            yearArray[i] = (current_year + i).toString()
        }
    }

    private fun openMonth() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(resources.getString(R.string.expiry_month))
        builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->
            // Do something with the selection
            monthsss.setText(items[item])
        })
        val alert = builder.create()
        alert.show()
    }

    private fun openYear() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(resources.getString(R.string.expiry_year))
        builder.setItems(
            yearArray,
            DialogInterface.OnClickListener { dialog, item -> yearsss.setText(yearArray!![item]) })
        val alert = builder.create()
        alert.show()
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                finish()
            } R.id.monthsss -> {
            openMonth()
            }R.id.yearsss -> {
            openYear()
            }
            R.id.btn_save -> {
                val intent = Intent(mContext, PaymentActivity::class.java)
                startActivity(intent)
            }
        }
    }
}