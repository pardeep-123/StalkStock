package com.stalkstock.advertiser.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_successfully_alert.*
import okhttp3.RequestBody

class ChangePasswordActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    lateinit var successfulUpdatedDialog: Dialog
    val mContext: Context = this
    override fun getContentId(): Int {
        return R.layout.activity_change_password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        tv_heading.text = "Change Password"
        iv_back.setOnClickListener(this)
        btn_update_pass.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.btn_update_pass -> {
                if (validations())
                    changePasswordAPI()
//                    updateDailogMethod()
            }
        }
    }

    val viewModel: HomeViewModel by viewModels()

    private fun changePasswordAPI() {
        val hashMap = HashMap<String, RequestBody>()
        hashMap["oldPassword"] = mUtils.createPartFromString(passwordEdittext.text.toString())
        hashMap["newPassword"] = mUtils.createPartFromString(newpasswordEdittext.text.toString())
        viewModel.changePasswordAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)
    }

    private fun validations(): Boolean {
        if (passwordEdittext.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter old password")
            passwordEdittext.requestFocus()
            return false
        } else if (newpasswordEdittext.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, "Please enter new password")
            newpasswordEdittext.requestFocus()
            return false
        } else if (confpasswordEdittext.text.toString() != newpasswordEdittext.text.toString()) {
            AppUtils.showErrorAlert(this, "Password mismatch")
            confpasswordEdittext.requestFocus()
            return false
        } else {
            return true
        }
    }

    private fun updateDailogMethod() {
        successfulUpdatedDialog = Dialog(mContext, R.style.Theme_Dialog)
        successfulUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successfulUpdatedDialog.setContentView(R.layout.update_successfully_alert)

        successfulUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        successfulUpdatedDialog.tv_msg.text = "Your password has been successfully updated"
        successfulUpdatedDialog.setCancelable(true)
        successfulUpdatedDialog.setCanceledOnTouchOutside(false)
        successfulUpdatedDialog.window!!.setGravity(Gravity.CENTER)

        successfulUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        successfulUpdatedDialog.btn_ok.setOnClickListener {
            successfulUpdatedDialog.dismiss()
            finish()
        }

        successfulUpdatedDialog.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                            //Do something after 100ms
                        }, 2000)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }


}