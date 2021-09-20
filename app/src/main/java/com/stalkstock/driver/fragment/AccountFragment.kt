package com.stalkstock.driver.fragment

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.advertiser.activities.*
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.driver.DriverInformationActivity
import com.stalkstock.driver.HomeActivity
import com.stalkstock.driver.models.DriverProfileDetailResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.clearPrefrences
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.fragment_account2.*
import kotlinx.android.synthetic.main.fragment_account2.lin_editProfile
import kotlinx.android.synthetic.main.fragment_account2.toggle1
import kotlinx.android.synthetic.main.fragment_account2.toggle_off2
import kotlinx.android.synthetic.main.fragment_account2.tv_changepass
import kotlinx.android.synthetic.main.fragment_account2.tv_help
import kotlinx.android.synthetic.main.fragment_account2.tv_logout
import kotlinx.android.synthetic.main.logout_alert.*
import okhttp3.RequestBody
import java.util.*


class AccountFragment : Fragment(), Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()
    private lateinit var mHomeDriverActivity : HomeActivity
    private var from = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mHomeDriverActivity = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel.mResponse.observe(mHomeDriverActivity, this)
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
            notificationOnOffAPI("on")
        }
        toggle_off2.setOnClickListener {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
            notificationOnOffAPI("off")
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
            logoutAPI()

        }

        logoutUpdatedDialog.show()
    }

    override fun onResume() {
        super.onResume()
        getprofileAPI()
    }

    private fun getprofileAPI() {
        val map = HashMap<String, String>()
        viewModel.getProfileDetail(mHomeDriverActivity, true, map)
    }

    private fun notificationOnOffAPI(s: String) {
        from = "notification"
        val map = HashMap<String, RequestBody>()
        map.put("status", mHomeDriverActivity.mUtils.createPartFromString(s))
        viewModel.notification_on_offAPI(mHomeDriverActivity, true, map)
    }

    private fun logoutAPI() {
        viewModel.logout(mHomeDriverActivity, true)
        viewModel.mResponse.observe(mHomeDriverActivity, this)
        from = "logout"

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is DriverProfileDetailResponse) {
                    val mResponse: DriverProfileDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                    } else {
//                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if (from.equals("logout")) {
                            val intent = Intent(activity, LoginActivity::class.java)
                            // intent.putExtra("is_open","1");
                            startActivity(intent)
                            requireActivity().finishAffinity()
                            clearPrefrences()
                        } else
                            AppUtils.showSuccessAlert(
                                requireActivity(),
                                mResponse.message.toString()
                            )
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(mHomeDriverActivity, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mHomeDriverActivity, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: DriverProfileDetailResponse) {
        image.loadImage(mResponse.body.driverDetail.image)
        tvName.setText(mResponse.body.driverDetail.firstName+" "+mResponse.body.driverDetail.lastName)
        tvMobile.setText(mResponse.body.mobile)
        tvEmail.setText(mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "2")
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.notification, mResponse.body.notification)
        if (mResponse.body.notification.equals("on")) {
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        } else {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }
    }
}
