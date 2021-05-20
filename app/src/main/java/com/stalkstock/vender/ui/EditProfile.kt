package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.ModelGetProfileDetail
import com.stalkstock.consumer.model.ModelUpdateProfile
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.vender.Model.UpdateVendorProfileModel
import com.stalkstock.vender.Model.VendorProfileDetail
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_profile2.*
import okhttp3.RequestBody
import java.util.*

class EditProfile : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    private var from = ""
    private var mAlbumFiles = ArrayList<AlbumFile?>()
    var firstimage = ""
    var context: Context? = null
    lateinit var opencamera: ImageView
    var setimage: ImageView? = null
    override fun getContentId(): Int {
        return R.layout.activity_edit_profile2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageView = findViewById<ImageView>(R.id.editprofile_backarrow)
        val button = findViewById<Button>(R.id.editprofile_savebtn)
        setimage = findViewById(R.id.editprofile_imageset)
        opencamera = findViewById(R.id.editprofile_camera)
        opencamera.setOnClickListener(this)
        imageView.setOnClickListener { onBackPressed() }
        button.setOnClickListener {
            if (validations())
                updateProfileAPI()
        }
        CommonMethods.hideKeyboard(this, opencamera)

        getprofileAPI()
    }

    private fun updateProfileAPI() {
        val map = HashMap<String, RequestBody>()
        map.put("firstName", mUtils.createPartFromString(editprofile_name.text.toString()))
        map.put("lastName", mUtils.createPartFromString(editprofile_last_name.text.toString()))
        map.put("shopAddress", mUtils.createPartFromString(editprofile_address.text.toString()))
        from = "edit"
        viewModel.editVendorProfileDetail(this, true, map, firstimage, mUtils)
        viewModel.homeResponse.observe(this, this)

    }

    private fun validations(): Boolean {
        if (editprofile_name.text.toString().trim().isEmpty()) {
            editprofile_name.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter first name")
            return false
        } else if (editprofile_last_name.text.toString().trim().isEmpty()) {
            editprofile_last_name.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter last name")
            return false
        } else if (editprofile_address.text.toString().trim().isEmpty()) {
            editprofile_address.requestFocus()
            AppUtils.showErrorAlert(this, "Please enter last name")
            return false
        } else {
            return true
        }
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getprofileAPI() {
        val map = HashMap<String, String>()
        from = "profile"
        viewModel.getProfileDetailVendor(this, true, map)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onClick(view: View) {
        askCameraPermissons()
    }

    private fun askCameraPermissons() {
        mAlbumFiles = ArrayList()
        mAlbumFiles.clear()
        selectImage(setimage, "1")
    }

    private fun selectImage(setimage: ImageView?, s: String) {
        Album.image(this)
            .singleChoice()
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .camera(true)
            .columnCount(4)
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this@EditProfile).load(result[0].path).into(setimage!!)
                if (s == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel { }
            .start()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
/*
                if (it.data is ModelUpdateProfile) {
                    val mResponse: ModelUpdateProfile = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        openStartInfoApp()
//                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
*/

                if (it.data is VendorProfileDetail) {
                    val mResponse: VendorProfileDetail = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if (from.equals("edit"))
                            openStartInfoApp()
                        setData(mResponse)

                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

                if (it.data is UpdateVendorProfileModel) {
                    val mResponse: UpdateVendorProfileModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        openStartInfoApp()
//                        setData(mResponse)

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

    private fun openStartInfoApp() {
        val logoutUpdatedDialog2 = Dialog(this@EditProfile)
        logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialog2.setContentView(R.layout.editprofilealertbox)
        logoutUpdatedDialog2.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialog2.setCancelable(true)
        logoutUpdatedDialog2.setCanceledOnTouchOutside(false)
        logoutUpdatedDialog2.window!!.setGravity(Gravity.CENTER)
        logoutUpdatedDialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btncontinue = logoutUpdatedDialog2.findViewById<Button>(R.id.edit_donebtn)
        btncontinue.setOnClickListener {
            onBackPressed()
            //                      context.startActivity(new Intent(EditProfile.this, AccountFragment.class));
            logoutUpdatedDialog2.dismiss()
        }
        logoutUpdatedDialog2.show()

    }

    private fun setData(mResponse: VendorProfileDetail) {
        editprofile_imageset.loadImage(mResponse.body.vendorDetail.image)
        editprofile_name.setText(mResponse.body.vendorDetail.firstName)
        editprofile_last_name.setText(mResponse.body.vendorDetail.lastName)
        editprofile_email.setText(mResponse.body.email)
        edtNumber.setText(mResponse.body.mobile)
        editprofile_address.setText(mResponse.body.vendorDetail.shopAddress)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, mResponse.body.role.toString())
        savePrefrence(GlobalVariables.SHARED_PREF_USER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.notification, mResponse.body.notification)

    }
}