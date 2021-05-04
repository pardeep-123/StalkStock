package com.live.stalkstockcommercial.ui.paymnet

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.add_new_card.*
import kotlinx.android.synthetic.main.add_new_card.btn_save
import kotlinx.android.synthetic.main.add_new_card.monthsss
import kotlinx.android.synthetic.main.add_new_card.yearsss
import java.util.*

class AddNewCard : AppCompatActivity(){
    val mContext: Context =this

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
        setContentView(R.layout.add_new_card)

        ivBackAddNewCard.setOnClickListener { onBackPressed() }
        btn_save.setOnClickListener {

           /* val intent = Intent(this,SelectPayment::class.java)
            intent.putExtra("card","yes")
            startActivity(intent)*/
        onBackPressed()
        }

        current_year = Calendar.getInstance().get(Calendar.YEAR)

        yearArray = arrayOfNulls<String>(future_year)

        for (i in 0 until future_year) {
            yearArray[i] = (current_year + i).toString()
        }

        monthsss.setOnClickListener{
            openMonth()
        }
        yearsss.setOnClickListener{
            openYear()
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
}
