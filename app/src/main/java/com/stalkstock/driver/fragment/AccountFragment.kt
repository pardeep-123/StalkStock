package com.stalkstock.driver.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.R
import com.stalkstock.advertiser.activities.*
import com.stalkstock.driver.DriverInformationActivity
import kotlinx.android.synthetic.main.fragment_account2.*
import kotlinx.android.synthetic.main.logout_alert.*


class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_help.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), HelpActivity::class.java))
        }
        tv_notification.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), Notification_firstActivity::class.java))
        }
        lin_editProfile.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        tv_document.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), DriverInformationActivity::class.java))
        }
        tv_manage_address.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ManageAddress::class.java))
        }


        tv_logout.setOnClickListener {
            logoutDailogMethod()
        }

        tv_changepass.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }


        val toggle1 =
            view.findViewById<ImageView>(R.id.toggle1)
        val toggle_off2 =
            view.findViewById<ImageView>(R.id.toggle_off2)

        toggle1.setOnClickListener {
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        }
        toggle_off2.setOnClickListener {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }
    }


    private fun logoutDailogMethod() {
       val  logoutUpdatedDialog = Dialog(requireContext(), R.style.Theme_Dialog)
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
}
