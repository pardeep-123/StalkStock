package com.stalkstock.driver.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.AddBankAccount
import com.stalkstock.driver.adapter.CardAdapter
import com.stalkstock.driver.models.BankBody
import com.stalkstock.driver.models.BankDataList
import com.stalkstock.driver.models.TransferFundsData
import com.stalkstock.driver.models.WalletData
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.payment_popup.*
import java.util.HashMap

class PaymentFragment : Fragment(), Observer<RestObservable> {

    lateinit var ctx:Context
    lateinit var cardAdapter:CardAdapter
    val viewModel: DriverViewModel by viewModels()
    var amount = ""
    var balanceAmount = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container!!.context
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_addnew.setOnClickListener { requireContext().startActivity(Intent(requireContext(),
            AddBankAccount::class.java))
        }
        btnTransfer.setOnClickListener {

            if(valid()) {

                val map = HashMap<String, String>()
                map["amount"] = etEnterAmount.text.toString()
                map["bankAccountId"] = cardAdapter.defaultCardId
                amount = etEnterAmount.text.toString()
                balanceAmount = (balanceAmount.toFloat()-amount.toFloat()).toString()
                tvBalanceAmount.text = "$ $balanceAmount"
                etEnterAmount.text!!.clear()
                viewModel.transferFunds(ctx as Activity, true, map)
            }
        }

        setCardData(listOf())
        if(MyApplication.instance.getString("usertype").equals("3")){
            rl_toos.visibility=View.VISIBLE
        }
        //api

    }

    override fun onResume() {
        super.onResume()
        viewModel.checkWalletBalance(ctx as Activity, true)
        viewModel.bankAccountList(ctx as Activity, true)
        viewModel.mResponse.observe(viewLifecycleOwner, this)
    }
    private fun valid(): Boolean {

        return when{
            etEnterAmount.text.toString().isBlank() ->{
                AppUtils.showErrorAlert(ctx as Activity, getString(R.string.enter_account_amount))
                false
            }
            etEnterAmount.text.toString().toFloat()>balanceAmount.toFloat() ->{
                AppUtils.showErrorAlert(ctx as Activity, getString(R.string.insufficient_balance))
                false
            }
          cardAdapter.defaultCardId.isNullOrEmpty() ->{
                AppUtils.showErrorAlert(ctx as Activity,"Please add your bank account")
                false
            }
            else -> true
        }
    }

    private fun setCardData(list: List<BankBody>) {
        cardAdapter = activity?.let { CardAdapter(it,list) }!!
        rvCards.adapter = cardAdapter
    }

    private fun dialo() {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.payment_popup)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.btn_ok11.setOnClickListener{
            dialog.dismiss()
        }
        dialog.tvAmount.text = "$ $amount"
        dialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is WalletData) {
                    val mResponse: WalletData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        tvBalanceAmount.text = "$ ${mResponse.body.walletAmount}"
                        balanceAmount = mResponse.body.walletAmount

                        btnTransfer.visibility = if(mResponse.body.walletAmount=="0.00") View.GONE else View.VISIBLE
                        rlEnterAmount.visibility = if(mResponse.body.walletAmount=="0.00") View.GONE else View.VISIBLE
                    } }
                if (it.data is BankDataList) {
                    val mResponse: BankDataList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if(mResponse.body.isEmpty())
                        {
                            Toast.makeText(ctx,"No Bank Added yet, Please add at-least one bank to proceed",Toast.LENGTH_LONG).show()
                        }
                        else{
                            setCardData(mResponse.body)
                        }
                    } }
                if (it.data is TransferFundsData) {
                    val mResponse: TransferFundsData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        dialo()
                    } }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(ctx, it.data as String, Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(ctx, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                } }
            it.status == Status.LOADING -> {
            }
        }
    }
}
