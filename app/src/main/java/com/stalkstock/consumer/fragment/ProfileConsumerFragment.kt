package com.stalkstock.consumer.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.R
import com.stalkstock.advertiser.activities.ChangePasswordActivity
import com.stalkstock.advertiser.activities.HelpActivity
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.advertiser.activities.ManagePaymentsActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.EditprofileConsumerActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.model.ModelGetProfileDetail
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.custom.TitiliumBoldButton
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.clearPrefrences
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.fragment_account_consumer.*
import okhttp3.RequestBody
import java.util.*


class ProfileConsumerFragment : Fragment(), Observer<RestObservable> {

    private var from = ""
    lateinit var edi_icon: ImageView
    lateinit var profile: RelativeLayout
    lateinit var tv_changepass: TextView
    lateinit var tv_help: TextView
    lateinit var tv_manage: TextView
    lateinit var tv_business_profile: TextView
    lateinit var tv_logout: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views = inflater.inflate(R.layout.fragment_account_consumer, container, false)
        profile = views!!.findViewById(R.id.profile)
        tv_changepass = views.findViewById(R.id.tv_changepass)
        tv_logout = views.findViewById(R.id.tv_logout)
        edi_icon = views.findViewById(R.id.ivEdit)
        tv_help = views.findViewById(R.id.tv_help)
        tv_manage = views.findViewById(R.id.tv_manage)
        tv_business_profile = views.findViewById(R.id.tv_business_profile)
        profile.setOnClickListener {
            val intent = Intent(activity, EditprofileConsumerActivity::class.java)
            startActivity(intent)
        }
        tv_help.setOnClickListener {
            val intent = Intent(activity, HelpActivity::class.java)
            startActivity(intent)
        }
        tv_changepass.setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        tv_logout.setOnClickListener { LogoutAlert() }
        tv_manage.setOnClickListener {
            val intent = Intent(activity, ManagePaymentsActivity::class.java)
            startActivity(intent)
        }
        tv_business_profile.setOnClickListener {
            val intent = Intent(activity, ManageAddress::class.java)
            startActivity(intent)
        }
        val toggle1 = views.findViewById<ImageView>(R.id.toggle1)
        val toggle_off2 = views.findViewById<ImageView>(R.id.toggle_off2)
        toggle1.setOnClickListener {
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
            notificationOnOffAPI("on")
        }
        toggle_off2.setOnClickListener {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
            notificationOnOffAPI("off")
        }
        return views
    }

    val viewModel: HomeViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        getprofileAPI()
    }

    private fun getprofileAPI() {
        val map = HashMap<String, String>()
        val mainConsumerActivity = activity as MainConsumerActivity
        viewModel.getProfileDetail(mainConsumerActivity, true, map)
        viewModel.homeResponse.observe(mainConsumerActivity, this)

    }

    private fun notificationOnOffAPI(s: String) {
        val map = HashMap<String, RequestBody>()
        val mainConsumerActivity = activity as MainConsumerActivity
        map["status"] = mainConsumerActivity.mUtils.createPartFromString(s)
        viewModel.notification_on_offAPI(mainConsumerActivity, true, map)
        viewModel.homeResponse.observe(mainConsumerActivity, this)

        from = "notification"

    }

    fun LogoutAlert() {
        val dialogSuccessful = Dialog(requireActivity(), R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.logout_alert)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull(dialogSuccessful.window)
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)
        val btn_no: TitiliumBoldButton = dialogSuccessful.findViewById(R.id.btn_no)
        val btn_yes: TitiliumBoldButton = dialogSuccessful.findViewById(R.id.btn_yes)
        btn_no.setOnClickListener { dialogSuccessful.dismiss() }
        btn_yes.setOnClickListener {

            logoutAPI()
            dialogSuccessful.dismiss()
        }
        dialogSuccessful.show()
    }

    private fun logoutAPI() {
        val mainConsumerActivity = activity as MainConsumerActivity
        viewModel.logout(mainConsumerActivity, true)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
        from = "logout"

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if (from == "logout") {
                            val intent = Intent(activity, LoginActivity::class.java)
                            startActivity(intent)
                            requireActivity().finishAffinity()
                            clearPrefrences()
                        } else
                            AppUtils.showSuccessAlert(
                                requireActivity(),
                                mResponse.message
                            )
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                }
                if (it.data is ModelGetProfileDetail) {
                    val mResponse: ModelGetProfileDetail = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                    }
                    else {
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: ModelGetProfileDetail) {
        (imgUserProfile as ImageView).loadImage(mResponse.body.userDetail.image)
        txtUserEmail.text = mResponse.body.email
        txtUserPhone.text = mResponse.body.mobile
        txtUserName.text = mResponse.body.userDetail.first_name + " " + mResponse.body.userDetail.last_name
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "1")
        savePrefrence(GlobalVariables.SHARED_PREF_USER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.notification, mResponse.body.notification)
        if (mResponse.body.notification == "on") {
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        }
        else {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }
    }
}