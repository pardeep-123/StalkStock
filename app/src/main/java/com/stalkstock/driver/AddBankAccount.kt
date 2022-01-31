package com.stalkstock.driver

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.AddBankData
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_add_bank_account.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap

class AddBankAccount : AppCompatActivity(), Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()

    override fun  onCreate( savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_add_bank_account)
         ivBack.setOnClickListener { finish() }
         btnSave.setOnClickListener { if(valid()) callAddBankApi() }
    }

    private fun valid(): Boolean {

        when{
            etAccountName.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_account_name))
                return false
            }
            etBankName.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_bank_name))
                return false
            }
            etRoutingNumber.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_routing_number))
                return false
            }
            etRoutingNumber.text.toString().length<9 ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_minimum_routing_number))
                return false
            }
            etBankAccount.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_bank_account_number))
                return false
            }
            etBankAccount.text.toString().length<6 ->{
                AppUtils.showErrorAlert(this, getString(R.string.enter_minimum_bank_account_number))
                return false
            }
            etCnfBankAccount.text.toString() != etBankAccount.text.toString() ->{
                AppUtils.showErrorAlert(this, getString(R.string.please_confirm_bank_account_number))
                return false
            }

            else ->return true
        }
    }

    private fun callAddBankApi() {
        val map = HashMap<String, String>()
        map["name"] =  etAccountName.text.toString()
        map["bankName"] = etBankName.text.toString()
        map["routingNumber"] =  etRoutingNumber.text.toString()
        map["bankAccount"] =  etBankAccount.text.toString()
        viewModel.addBankAccount(this, true,map)
        viewModel.mResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is AddBankData) {
                    val mResponse: AddBankData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        onBackPressed()
                    } }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                } }
            it.status == Status.LOADING -> {
            }
        }
    }
}
