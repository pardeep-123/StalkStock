package com.stalkstock.advertiser.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.AddCardData
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class AddNewCardActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {

    val mContext:Context=this
    var current_year: Int = 0
    var future_year = 40
    lateinit var yearArray: Array<String?>
    internal var items = arrayOf<CharSequence>("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    val viewModel: DriverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)
        tv_heading.text = "Add A New Card"
        iv_back.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        monthsss.setOnClickListener(this)
        yearsss.setOnClickListener(this)
        current_year = Calendar.getInstance().get(Calendar.YEAR)
        yearArray = arrayOfNulls(future_year)

        for (i in 0 until future_year) { yearArray[i] = (current_year + i).toString() }
    }

    private fun openMonth() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(resources.getString(R.string.expiry_month))
        builder.setItems(items) { dialog, item ->
            monthsss.text = items[item] }
        val alert = builder.create()
        alert.show()
    }

    private fun openYear() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(resources.getString(R.string.expiry_year))
        builder.setItems(
            yearArray
        ) { dialog, item -> yearsss.text = yearArray[item] }
        val alert = builder.create()
        alert.show()
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.monthsss -> { openMonth() }
            R.id.yearsss -> {
            openYear()
            }
            R.id.btn_save -> { if(valid()) callAddCard() }
        }
    }

    private fun valid(): Boolean {

        when{
            etName.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_card_holder_name))
                return false
            }

            etCardNumber.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_card_name))
                return false
            }
            etCardNumber.text.toString().length<16 ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_char_card_name))
                return false
            }
            monthsss.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.select_expiry_month))
                return false
            }
            yearsss.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.select_expiry_year))
                return false
            }
            tvCvv.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_cvv_number))
                return false
            }
            tvCvv.text.toString().length<3 ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_valid_cvv))
                return false
            }
            else ->return true
        }
    }

    private fun callAddCard() {
        val map = HashMap<String, String>()
        map["name"] = etName.text.toString()
        map["cardNumber"] = etCardNumber.text.toString()
        map["month"] = monthsss.text.toString()
        map["year"] = yearsss.text.toString()
        map["cvv"] = tvCvv.text.toString()
        map["cardType"] =  "1"
        viewModel.addUserCards(this, true, map)
        viewModel.mResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is AddCardData) {
                    val mResponse: AddCardData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        onBackPressed()
                    } }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) { Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show() }
                else
                { Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show() } }
            it.status == Status.LOADING -> { }
        }
    }
}