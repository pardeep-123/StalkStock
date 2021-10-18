package com.stalkstock.advertiser.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.*
import com.stalkstock.advertiser.model.AdvertiserProfileDetailResponse
import com.stalkstock.advertiser.model.NotificationResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.consumer.model.UserLoginResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.clearPrefrences
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.logout_alert.*
import kotlinx.android.synthetic.main.toolbar2.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap
import android.content.SharedPreferences




class AccountFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {

    lateinit var v: View
    var click = ""
    lateinit var mContext:Context
    lateinit var logoutUpdatedDialog:Dialog


    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_account, container, false)
        mContext = activity as Context
        v.tv_heading.text = "Account"

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_business_profile.setOnClickListener(this)
        tv_manage.setOnClickListener(this)
        tv_changepass.setOnClickListener(this)
       tv_help.setOnClickListener(this)
       tv_logout.setOnClickListener(this)
        lin_editProfile.setOnClickListener(this)

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
    }

    private fun notificationOnOffAPI(s: String) {

            val map = HashMap<String, String>()
        map["status"] = s
        viewModel.getNotification(requireActivity(), true, map)
        viewModel.mResponse.observe(requireActivity(),this)

        }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_business_profile -> {
                val intent = Intent(activity, BusinessProfileActivity::class.java)
                startActivity(intent)
            }

            R.id.lin_editProfile -> {
                val intent = Intent(activity, EditProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_manage -> {
                val intent = Intent(activity, ManagePaymentsActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_changepass -> {
                val intent = Intent(activity, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_help -> {
                val intent = Intent(activity, HelpActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_logout -> {
                logoutDialogMethod()
            }
        }
    }
    private fun logoutDialogMethod() {
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
                    val data: NotificationResponse= it.data

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

        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.notification, data.body.notification)


    }

    override fun onResume() {
        super.onResume()

        val notify =  getPrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.notification, "")
        if (notify == "on"){
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        }
        else{
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }
        v.tvUserName.text = getPrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.firstName,"")
        v.tvUserEmailId.text = getPrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.email,"")
        v.tvUserPhone.text = getPrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.mobile,"")
        Glide.with(requireActivity()).load(getPrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.image,"")).into(v.ivUserProfile)
    }
}