package com.stalkstock.driver.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.adapter.RequestAdapter
import com.stalkstock.driver.models.HistoryDataBody
import com.stalkstock.driver.models.OrderHistoryData
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import kotlinx.android.synthetic.main.completed_popup.*
import kotlinx.android.synthetic.main.current_popup.*
import kotlinx.android.synthetic.main.current_popup.btn_signup
import kotlinx.android.synthetic.main.fragment_h_ome.*
import kotlinx.android.synthetic.main.fragment_my_request.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap


class MyRequestFragment : Fragment(), Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()

    lateinit var ctx : Context
    lateinit var adapter : RequestAdapter
     var listRequest = mutableListOf<HistoryDataBody>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container!!.context
        return inflater.inflate(R.layout.fragment_my_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RequestAdapter(listRequest)


        adapter.onClickInterFace(object :RequestAdapter.OnClick{
            override fun onClick(position: Int) {
               /* if (type == "2")
                    dialogCompleted()
                else
                    dialogConfirmation()*/

                dialogConfirmation(listRequest[position])

            }
        })
        recy_req.adapter = adapter

        btn_current.setOnClickListener {
            btn_current.setTextColor(resources.getColor(R.color.white))
            btn_current.setBackgroundColor(resources.getColor(R.color.green_colour))
            btn_completed.setTextColor(resources.getColor(R.color.black))
            btn_completed.setBackgroundColor(resources.getColor(R.color.light_grey))
            callCurrentOrders("0")
        }
        btn_completed.setOnClickListener {
            btn_completed.setTextColor(resources.getColor(R.color.white))
            btn_completed.setBackgroundColor(resources.getColor(R.color.green_colour))
            btn_current.setTextColor(resources.getColor(R.color.black))
            btn_current.setBackgroundColor(resources.getColor(R.color.light_grey))
            callCurrentOrders("1")
        }
       callCurrentOrders("0")
    }

    private fun callCurrentOrders(s: String) {
        val map = HashMap<String, RequestBody>()
        map["offset"] = RequestBody.create(MultipartBody.FORM, "0")
        map["limit"] = RequestBody.create(MultipartBody.FORM, "10")
        map["type"] = RequestBody.create(MultipartBody.FORM, s)

        viewModel.orderHistoryDriver(ctx as Activity, true, map)
        viewModel.mResponse.observe(viewLifecycleOwner, this@MyRequestFragment)
    }

    private fun dialogCompleted() {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.completed_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.iv_crossi.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
    private fun dialogConfirmation(historyDataBody: HistoryDataBody) {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.current_popup)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.iv_cross.setOnClickListener { dialog.dismiss() }
        dialog.tvOrderID.text = "Order Id: ${historyDataBody.orderNo}"
        dialog.tvStatus.text = Util.orderStatus(historyDataBody.orderStatus,dialog.tvStatus.context)
        dialog.tvCharge.text = "$ ${historyDataBody.shippingCharges}"
        dialog.tvShopAddress.text = historyDataBody.vendorDetail.shopAddress
        dialog.tvShopAddress2.text = historyDataBody.vendorDetail.shopAddress
        dialog.tvName.text = historyDataBody.vendorDetail.shopName

        Glide.with(dialog.ivProfile.context).load(historyDataBody.vendorDetail.shopLogo).placeholder(dialog.ivProfile.context.getDrawable(R.drawable.place_holder)).into(dialog.ivProfile)

        dialog.tvDateTime.text = AppUtils.changeDateFormat(historyDataBody.createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat3,
            GlobalVariables.DATEFORMAT.DateTimeFormat2)

        dialog.btn_signup.setOnClickListener{ dialog.dismiss() }
        dialog.show()
    }
    


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderHistoryData) {
                    val mResponse: OrderHistoryData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        listRequest.clear()
                        listRequest.addAll(mResponse.body)
                        adapter.notifyDataSetChanged()
                    }
                    else {
                        Toast.makeText(ctx, mResponse.message, Toast.LENGTH_SHORT).show()
                    } }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(ctx, it.data as String, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        ctx,
                        it.error!!.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}
