package com.stalkstock.consumer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.ModelSignupUser
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup_consumer.*
import kotlinx.android.synthetic.main.activity_signup_consumer.emailEdittext
import okhttp3.RequestBody
import java.util.*

class SignupConsumerActivity : BaseActivity(), Observer<RestObservable> {
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    var firstimage = ""
    var context: SignupConsumerActivity? = null
    override fun getContentId(): Int {
        return R.layout.activity_signup_consumer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        clicks()
    }

    private fun clicks() {
        img!!.setOnClickListener {
            mAlbumFiles = ArrayList()
            mAlbumFiles.clear()
            selectImage(img, "1")
        }
        btn_signup!!.setOnClickListener {
            if (validations())
                signupUserAPI()
        }
        iv_back!!.setOnClickListener { finish() }
        tv_signin!!.setOnClickListener { onBackPressed() }
    }

    val viewModel: HomeViewModel by viewModels()

    private fun signupUserAPI() {
        val map = HashMap<String, RequestBody>()
        map["first_name"] = mUtils.createPartFromString(edtConsumerFirstname.text.toString())
        map["last_name"] = mUtils.createPartFromString(edtConsumerLastname.text.toString())
        map["email"] = mUtils.createPartFromString(emailEdittext.text.toString())
        map["mobile"] = mUtils.createPartFromString(edtConsumerPhone.text.toString())
        map["password"] = mUtils.createPartFromString(edtConsumerPassword.text.toString())
        viewModel.getusersignupApi(this, true, map, firstimage, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    private fun validations(): Boolean {
        if (firstimage == "") {
            AppUtils.showErrorAlert(this, "Please select image")
            return false
        } else if (edtConsumerFirstname.text.toString().trim().isEmpty()) {
            edtConsumerFirstname.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter first name")
            return false
        } else if (edtConsumerLastname.text.toString().trim().isEmpty()) {
            edtConsumerLastname.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter last name")
            return false
        } else if (emailEdittext.text.toString().trim().isEmpty()) {
            emailEdittext.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter email")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.getText().toString()).matches()) {
            emailEdittext.requestFocus()
            emailEdittext.setError(resources.getString(R.string.please_enter_valid_email))
            return false
        } else if (edtConsumerPhone.text.toString().trim().isEmpty()) {
            edtConsumerPhone.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter mobile number")
            return false
        } else if (edtConsumerPhone.text.toString().length< 10) {
            edtConsumerPassword.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter correct mobile number")
            return false
        }  else if (edtConsumerPassword.text.toString().trim().isEmpty()) {
            edtConsumerPassword.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter password")
            return false
        }else if (edtConsumerPassword.text.toString().length<6) {
            edtConsumerPassword.requestFocus()
            AppUtils.showErrorAlert(this, "Password should contain at least 6 characters")
            return false
        } else if (edtConsumerRePassword.text.toString().trim().isEmpty()) {
            edtConsumerRePassword.requestFocus()
            AppUtils.showErrorAlert(this, "Please re-enter password")
            return false
        } else if (edtConsumerRePassword.text.toString()
                .trim() != edtConsumerPassword.text.toString().trim()
        ) {
            edtConsumerRePassword.requestFocus()
            AppUtils.showErrorAlert(this, "Confirm password should be same as password")
            return false
        } else {
            return true
        }
    }

    private fun callMethod() {}
    private fun selectImage(ivProduct: ImageView?, mediType: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this@SignupConsumerActivity).load(result[0].path).into(ivProduct!!)
                if (mediType == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel { }
            .start()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelSignupUser) {
                    val mResponse: ModelSignupUser = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                        moveToConsumerHome()
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

    private fun moveToConsumerHome() {
        startActivity(Intent(this, MainConsumerActivity::class.java))
        finishAffinity()
    }

    private fun setData(mResponse: ModelSignupUser) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "1")
        savePrefrence(GlobalVariables.SHARED_PREF_USER.AUTH_KEY, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.token, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.verified, mResponse.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.status, mResponse.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.notification, mResponse.body.notification)
        savePrefrence(
            GlobalVariables.SHARED_PREF_USER.remember_token,
            mResponse.body.remember_token
        )
        savePrefrence(GlobalVariables.SHARED_PREF_USER.created, mResponse.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.updated, mResponse.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.createdAt, mResponse.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.updatedAt, mResponse.body.updatedAt)
    }
}