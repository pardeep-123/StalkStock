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
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.adapter.RequestAdapter
import com.stalkstock.driver.models.OrderHistoryData
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.completed_popup.*
import kotlinx.android.synthetic.main.current_popup.*
import kotlinx.android.synthetic.main.current_popup.btn_signup
import kotlinx.android.synthetic.main.fragment_h_ome.*
import kotlinx.android.synthetic.main.fragment_my_request.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap


class MyRequestFragment : Fragment(), RequestAdapter.OnClick, Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()

    lateinit var ctx : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container!!.context
        return inflater.inflate(R.layout.fragment_my_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recy_req.adapter = RequestAdapter( "1",this)

        btn_current.setOnClickListener {
            btn_current.setTextColor(resources.getColor(R.color.white))
            btn_current.setBackgroundColor(resources.getColor(R.color.green_colour))
            btn_completed.setTextColor(resources.getColor(R.color.black))
            btn_completed.setBackgroundColor(resources.getColor(R.color.light_grey))
            recy_req.adapter = RequestAdapter( "1",this)
            callCurrentOrders("0")
        }
        btn_completed.setOnClickListener {
            btn_completed.setTextColor(resources.getColor(R.color.white))
            btn_completed.setBackgroundColor(resources.getColor(R.color.green_colour))
            btn_current.setTextColor(resources.getColor(R.color.black))
            btn_current.setBackgroundColor(resources.getColor(R.color.light_grey))
            recy_req.adapter = RequestAdapter( "2",this)
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
    private fun dialogConfirmation() {
        val  dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.current_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.iv_cross.setOnClickListener {
            dialog.dismiss()

        }
        dialog.btn_signup.setOnClickListener{
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    override fun onClick(type: String) {
        if (type == "2")
            dialogCompleted()
            else
            dialogConfirmation()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderHistoryData) {
                    val mResponse: OrderHistoryData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        Toast.makeText(ctx, mResponse.body.size.toString(), Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(ctx, mResponse.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
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
