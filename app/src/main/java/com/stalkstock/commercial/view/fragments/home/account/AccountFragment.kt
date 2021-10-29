package com.stalkstock.commercial.view.fragments.home.account

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.stalkstock.commercial.view.activities.BusinessProfile
import com.stalkstock.R
import com.stalkstock.advertiser.activities.*
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.commercial.view.activities.ManageAddress
import kotlinx.android.synthetic.main.account.*
import kotlinx.android.synthetic.main.logout_alert.*

class AccountFragment : Fragment(), View.OnClickListener {
    lateinit var mContext:Context
    lateinit var logoutUpdatedDialog:Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val views = inflater.inflate(R.layout.account, container, false)
        mContext = activity as Context

        val toggle1 = views.findViewById<ImageView>(R.id.toggle1)
        val toggle_off2 = views!!.findViewById<ImageView>(R.id.toggle_off2)

        toggle1.setOnClickListener {
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        }
        toggle_off2.setOnClickListener {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }

        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        tvLogout.setOnClickListener {
            logoutDailogMethod()
        }

        tvBusiness.setOnClickListener {
            startActivity(Intent(tvPassword.context, BusinessProfile::class.java))
        }

        tvPassword.setOnClickListener {
            startActivity(Intent(tvPassword.context, ChangePasswordActivity::class.java))
        }

        ivEditProfile.setOnClickListener {
            startActivity(Intent(tvPassword.context, EditProfileActivity::class.java))
        }

        tvManage.setOnClickListener {
            startActivity(Intent(tvPassword.context, ManageAddress::class.java))
        }

        tvHelp.setOnClickListener {
            startActivity(Intent(tvPassword.context, HelpActivity::class.java))
        }

        tvManagePayment.setOnClickListener {
            startActivity(Intent(tvPassword.context , ManagePaymentsActivity::class.java))
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) { }
    }

    private fun logoutDailogMethod() {
        logoutUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        logoutUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialog.setContentView(R.layout.logout_alert)

        logoutUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialog.setCancelable(true)
        logoutUpdatedDialog.setCanceledOnTouchOutside(false)
        logoutUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        logoutUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        logoutUpdatedDialog.btn_no.setOnClickListener {
            logoutUpdatedDialog.dismiss()
        }
        logoutUpdatedDialog.btn_yes.setOnClickListener {
            logoutUpdatedDialog.dismiss()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finishAffinity()
        }
        logoutUpdatedDialog.show()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommunicationListner) {
        }
        else {
            throw RuntimeException("Account frag not Attached")

        }
    }

}