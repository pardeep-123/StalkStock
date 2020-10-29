package com.live.stalkstockcommercial.ui.view.fragments.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.live.stalkstockcommercial.ui.paymnet.ManagePayment
import com.stalkstock.R
import com.stalkstock.advertiser.activities.BusinessProfileActivity
import com.stalkstock.advertiser.activities.ChangePasswordActivity
import com.stalkstock.advertiser.activities.EditProfileActivity
import com.stalkstock.advertiser.activities.HelpActivity
import com.stalkstock.commercial.view.activities.CommunicationListner
import kotlinx.android.synthetic.main.account.*

class AccountFragment : Fragment(), View.OnClickListener {
    var listner: CommunicationListner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listner!!.getYourFragmentActive(4)
    }

    private fun init() {

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
            startActivity(Intent(tvPassword.context, ManagePayment::class.java))
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommunicationListner) {
            listner = context
        } else {
            throw RuntimeException("Account frag not Attached")

        }
    }

    override fun onDetach() {
        super.onDetach()
        listner = null
    }

}