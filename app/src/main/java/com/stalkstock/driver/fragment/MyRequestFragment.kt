package com.stalkstock.driver.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.stalkstock.R
import com.stalkstock.driver.adapter.RequestAdapter
import kotlinx.android.synthetic.main.completed_popup.*
import kotlinx.android.synthetic.main.current_popup.*
import kotlinx.android.synthetic.main.fragment_my_request.*


class MyRequestFragment : Fragment(), RequestAdapter.OnClick {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        }
        btn_completed.setOnClickListener {
            btn_completed.setTextColor(resources.getColor(R.color.white))
            btn_completed.setBackgroundColor(resources.getColor(R.color.green_colour))
            btn_current.setTextColor(resources.getColor(R.color.black))
            btn_current.setBackgroundColor(resources.getColor(R.color.light_grey))
            recy_req.adapter = RequestAdapter( "2",this)
        }
    }

    private fun dialogcompleted() {
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
    private fun dialogconfirmation() {
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
        if (type.equals("2"))
            dialogcompleted()
            else
            dialogconfirmation()

    }

}
