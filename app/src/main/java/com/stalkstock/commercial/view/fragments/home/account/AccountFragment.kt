package com.stalkstock.commercial.view.fragments.home.account

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stalkstock.commercial.view.activities.BusinessProfile
import com.stalkstock.R
import com.stalkstock.advertiser.activities.*
import com.stalkstock.advertiser.model.NotificationResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.clearPrefrences
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.account.*
import kotlinx.android.synthetic.main.account.toggle1
import kotlinx.android.synthetic.main.account.toggle_off2
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.logout_alert.*
import okhttp3.RequestBody
import java.util.HashMap

class AccountFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    lateinit var mContext:Context
    lateinit var logoutUpdatedDialog:Dialog

    val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }
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
            notificationOnOffAPI("on")
        }
        toggle_off2.setOnClickListener {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
            notificationOnOffAPI("off")
        }

        return views
    }

    private fun notificationOnOffAPI(s: String) {
        val map = HashMap<String, String>()
        map["status"] = s
        viewModel.getNotification(requireActivity(), true, map)
        viewModel.mResponse.observe(requireActivity(),this)

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
            logoutApi()


        }

        logoutUpdatedDialog.show()
    }

    private fun logoutApi() {
        homeViewModel.logout(requireActivity(),true)
        homeViewModel.homeResponse.observe(requireActivity(),this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommunicationListner) {
        }
        else {
            throw RuntimeException("Account frag not Attached")

        }
    }

    override fun onResume() {
        super.onResume()
        val notify =  getPrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.notification, "")
        if (notify == "on"){
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        }
        else{
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }
        tvName.text = getPrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.firstName,"") +" " + getPrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.lastName,"")
        tvEmail.text = getPrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.email,"")
        tvNumber.text = getPrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.mobile,"")
        Glide.with(requireActivity()).load(getPrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.image,"")).into(civAccount)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is UserCommonModel){
                    val data = it.data
                    if (data.code == 200){
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finishAffinity()
                        clearPrefrences()
                    }
                }

                if (it.data is NotificationResponse){
                    val data: NotificationResponse = it.data

                    if (data.code == GlobalVariables.URL.code){

                        saveStatus(data)

                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireActivity(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun saveStatus(data: NotificationResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.notification, data.body.notification)
    }

}