package com.stalkstock.consumer.activities

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.ModelGetProfileDetail
import com.stalkstock.consumer.model.ModelUpdateProfile
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_bussiness_profile.*
import kotlinx.android.synthetic.main.activity_editprofile.*
import okhttp3.RequestBody
import java.util.*

class EditprofileConsumerActivity : BaseActivity(), Observer<RestObservable> {
    private var from = ""
    private val mAlbumFiles = ArrayList<AlbumFile>()
    var firstimage = ""
    var context: EditprofileConsumerActivity? = null
    lateinit var btn_update_profile: Button
    lateinit var image: ImageView
    lateinit var back_arrow: ImageView
    override fun getContentId(): Int {
        return R.layout.activity_editprofile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        btn_update_profile = findViewById(R.id.btn_update_profile)
        image = findViewById(R.id.image)
        back_arrow = findViewById(R.id.back_arrow)
        back_arrow.setOnClickListener(View.OnClickListener { finish() })
        btn_update_profile.setOnClickListener(View.OnClickListener {
            if (validations()) {
                updateProfileAPI()
            }
        })
        image.setOnClickListener(View.OnClickListener { selectImage(image, "1") })
        getprofileAPI()
    }

    private fun validations(): Boolean {
        if (edtFirstName.text.toString().trim().isEmpty()) {
            edtFirstName.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter first name")
            return false
        } else if (edtLastName.text.toString().trim().isEmpty()) {
            edtLastName.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter last name")
            return false
        } else return true
    }

    private fun updateProfileAPI() {
        val map = HashMap<String, RequestBody>()
        map.put("first_name", mUtils.createPartFromString(edtFirstName.text.toString()))
        map.put("last_name", mUtils.createPartFromString(edtLastName.text.toString()))
        from = "edit"
        viewModel.editUserProfileDetail(this, true, map, firstimage, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    override fun onResume() {
        super.onResume()
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getprofileAPI() {
        val map = HashMap<String, String>()
        viewModel.getProfileDetail(this, true, map)
        from = "getProfile"
        viewModel.homeResponse.observe(this, this)

    }

    private fun selectImage(ivProduct: ImageView?, mediType: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this@EditprofileConsumerActivity).load(result[0].path).into(ivProduct!!)
                if (mediType == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel { }
            .start()
    }

    fun openStartInfoApp() {
        val dialogSuccessful = Dialog(Objects.requireNonNull(this), R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.update_successfully_alert2)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull(dialogSuccessful.window)
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)
        val btn_ok = dialogSuccessful.findViewById<Button>(R.id.btn_ok)
        btn_ok.setOnClickListener {
            dialogSuccessful.dismiss()
            finish()
        }
        dialogSuccessful.show()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelUpdateProfile) {
                    val mResponse: ModelUpdateProfile = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        openStartInfoApp()

//                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
                if (it.data is ModelGetProfileDetail) {
                    val mResponse: ModelGetProfileDetail = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if (from.equals("edit"))
                            openStartInfoApp()

                        setData(mResponse)

                    } else {
//                        AppUtils.showErrorAlert(this, mResponse.message.toString())
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

    private fun setData(mResponse: ModelGetProfileDetail) {
        Glide.with(this).load(mResponse.body.userDetail.image).placeholder(R.drawable.camera_green).into(image)

       // image.loadImage(mResponse.body.userDetail.image)
        edtFirstName.setText(mResponse.body.userDetail.first_name)
        edtLastName.setText(mResponse.body.userDetail.last_name)
        edtMobile.setText(mResponse.body.mobile)
        emailEdittext.setText(mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "1")
        savePrefrence(GlobalVariables.SHARED_PREF_USER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.notification, mResponse.body.notification)
    }

}