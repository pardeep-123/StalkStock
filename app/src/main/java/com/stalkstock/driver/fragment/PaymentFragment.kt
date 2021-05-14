package com.stalkstock.driver.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.driver.adapter.CardAdapter
import com.stalkstock.driver.models.CardModel
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.payment_popup.*


class PaymentFragment : Fragment() {

    var cardAdapter:CardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_addnew.setOnClickListener {
            //requireContext().startActivity(Intent(requireContext(), AddBankAccount::class.java))
            requireContext().startActivity(Intent(requireContext(), com.stalkstock.vender.ui.AddBankAccount::class.java))
        }
        btn_update.setOnClickListener {
            dialo()
        }

        val arrayList = ArrayList<CardModel>()
        arrayList.add(CardModel(R.drawable.a11,"**** **** **** 3456", true))
        //arrayList.add(CardModel(R.drawable.a11,"**** **** **** 3456", false))

        cardAdapter = activity?.let { CardAdapter(it,arrayList) }
        rvCards.layoutManager = LinearLayoutManager(activity)
        rvCards.adapter = cardAdapter

        if(MyApplication.instance.getString("usertype").equals("3")){
            rl_toos.visibility=View.VISIBLE
        }
    }



    private fun dialo() {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.payment_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_ok11.setOnClickListener{
            dialog.dismiss()
        }


        dialog.show()
    }



}
