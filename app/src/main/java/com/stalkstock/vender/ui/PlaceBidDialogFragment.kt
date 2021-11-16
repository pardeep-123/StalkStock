package com.stalkstock.vender.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.stalkstock.R
import com.stalkstock.utils.others.Util
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import okhttp3.RequestBody


class PlaceBidDialogFragment(var bidDetail: BidDetail) : DialogFragment() {

    var mUtil = Util()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.biddetailsalertbox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)

        submitbutton.setOnClickListener { v ->

            if (dialog?.edtSellingPrice!!.text.toString().isEmpty()) {
                dialog?.edtSellingPrice!!.error = resources.getString(R.string.please_enter_sale_price)
            } else if (dialog?.edtSellngDesc!!.text.toString().isEmpty()) {
                dialog?.edtSellngDesc!!.error = resources.getString(R.string.please_enter_sale_terms)
            } else {
                dismiss()
                var hashMap = HashMap<String, RequestBody>()
                hashMap["bidId"] = mUtil.createPartFromString(bidDetail.bidId)
                hashMap["amount"] = mUtil.createPartFromString(dialog?.edtSellingPrice!!.text.toString())
                hashMap["description"] = mUtil.createPartFromString(dialog?.edtSellngDesc!!.text.toString())
                bidDetail.placeBid(hashMap)
            }

        }
    }
}
