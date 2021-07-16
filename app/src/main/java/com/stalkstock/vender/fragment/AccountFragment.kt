package com.stalkstock.vender.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.advertiser.activities.ChangePasswordActivity
import com.stalkstock.advertiser.activities.HelpActivity
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.clearPrefrences
import com.stalkstock.vender.Model.VendorProfileDetail
import com.stalkstock.vender.ui.*
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.account_fragment.*
import okhttp3.RequestBody
import java.util.HashMap

class AccountFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    private var from = ""
    var dialog: Dialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.account_fragment, null)
        val imageView = view.findViewById<ImageView>(R.id.profileediticon)
        val business = view.findViewById<LinearLayout>(R.id.businessprofile)
        val addproduct = view.findViewById<LinearLayout>(R.id.addproductaccounts)
        val bidProduct = view.findViewById<LinearLayout>(R.id.bidproduct)
        val subscription = view.findViewById<LinearLayout>(R.id.subscription)
        val payment = view.findViewById<LinearLayout>(R.id.payments)
        val changepassword = view.findViewById<LinearLayout>(R.id.chnagepassword)
        val help = view.findViewById<LinearLayout>(R.id.help)
        val logout = view.findViewById<LinearLayout>(R.id.ll_1)
        val toggle1 = view.findViewById<ImageView>(R.id.toggle1)
        val toggle_off2 = view.findViewById<ImageView>(R.id.toggle_off2)
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
        imageView.setOnClickListener(this)
        business.setOnClickListener(this)
        addproduct.setOnClickListener(this)
        bidProduct.setOnClickListener(this)
        subscription.setOnClickListener(this)
        payment.setOnClickListener(this)
        changepassword.setOnClickListener(this)
        help.setOnClickListener(this)
        logout.setOnClickListener(this)
        return view
    }

    val viewModel: HomeViewModel by viewModels()

    private fun notificationOnOffAPI(s: String) {
        val map = HashMap<String, RequestBody>()
        val mainConsumerActivity = activity as BottomnavigationScreen
        map.put("status", mainConsumerActivity.mUtils.createPartFromString(s))
        viewModel.notification_on_offAPI(mainConsumerActivity, true, map)
        viewModel.homeResponse.observe(mainConsumerActivity, this)

        from = "notification"

    }

    override fun onResume() {
        super.onResume()
        getprofileAPI()
    }

    private fun getprofileAPI() {
        val map = HashMap<String, String>()
        val mainConsumerActivity = activity as BottomnavigationScreen
        viewModel.getProfileDetailVendor(mainConsumerActivity, true, map)
        viewModel.homeResponse.observe(mainConsumerActivity, this)

    }


    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.profileediticon -> {
                val intent = Intent(activity, EditProfile::class.java)
                startActivity(intent)
            }
            R.id.businessprofile -> {
                val intent2 = Intent(activity, BussinessProfile::class.java)
                startActivity(intent2)
            }
            R.id.addproductaccounts -> {
                val intent3 = Intent(activity, SelectCategory::class.java)
                startActivity(intent3)
            }
            R.id.bidproduct -> {
                val intent4 = Intent(activity, BidProduct::class.java)
                startActivity(intent4)
            }
            R.id.subscription -> {
                val intent5 = Intent(activity, Subscription::class.java)
                startActivity(intent5)
            }
            R.id.payments -> {
                val intent6 = Intent(activity, PaymentAccounts::class.java)
                startActivity(intent6)
            }
            R.id.chnagepassword -> {
//                val intent7 = Intent(activity, ChangePassword::class.java)
                val intent7 = Intent(activity, ChangePasswordActivity::class.java)
                startActivity(intent7)
            }
            R.id.help -> {
                val intent = Intent(activity, HelpActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_1 -> {
                //showDialog()

                /* LayoutInflater inflate= LayoutInflater.from(getActivity());
                View v= inflate.inflate(R.layout.logout_alert_box,null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(getActivity()).create();
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                deleteDialog.setView(v);
                final Button btnyes= v.findViewById(R.id.logout_yesbtn);
                final Button buttonno=v.findViewById(R.id.logout_nobtn);
                btnyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnyes.setBackground(getResources().getDrawable(R.drawable.logoutshape));
                        buttonno.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        btnyes.setTextColor(getResources().getColor(R.color.white));
                        buttonno.setTextColor(getResources().getColor(R.color.balck));

                        deleteDialog.dismiss();
                    }
                });
                buttonno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonno.setBackground(getResources().getDrawable(R.drawable.current_button));
                        btnyes.setBackground(getResources().getDrawable(R.drawable.past_button));
                        buttonno.setTextColor(getResources().getColor(R.color.white));
                        btnyes.setTextColor(getResources().getColor(R.color.balck));
                        Intent intent9 = new Intent(getActivity(), LoginScreen.class);
                        startActivity(intent9);
                        requireActivity().finish();
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();*/
                val logoutUpdatedDialog2 = Dialog(requireContext())
                logoutUpdatedDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
                logoutUpdatedDialog2.setContentView(R.layout.logout_alert_box)
                logoutUpdatedDialog2.window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                logoutUpdatedDialog2.setCancelable(true)
                logoutUpdatedDialog2.setCanceledOnTouchOutside(false)
                logoutUpdatedDialog2.window!!.setGravity(Gravity.CENTER)
                logoutUpdatedDialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val btnyes = logoutUpdatedDialog2.findViewById<Button>(R.id.logout_yesbtn)
                val buttonno = logoutUpdatedDialog2.findViewById<Button>(R.id.logout_nobtn)
                btnyes.setOnClickListener {
                    btnyes.background = resources.getDrawable(R.drawable.logoutshape)
                    buttonno.background = resources.getDrawable(R.drawable.buttonshape)
                    btnyes.setTextColor(resources.getColor(R.color.white))
                    buttonno.setTextColor(resources.getColor(R.color.balck))
                    logoutUpdatedDialog2.dismiss()
                }
                buttonno.setOnClickListener {
                    buttonno.background = resources.getDrawable(R.drawable.current_button)
                    btnyes.background = resources.getDrawable(R.drawable.past_button)
                    buttonno.setTextColor(resources.getColor(R.color.white))
                    btnyes.setTextColor(resources.getColor(R.color.balck))
                    logoutAPI()
                    logoutUpdatedDialog2.dismiss()
                }
                logoutUpdatedDialog2.show()
            }
        }
    } //    private void showDialog() {

    private fun logoutAPI() {
        val mainConsumerActivity = activity as BottomnavigationScreen
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
                if (it.data is VendorProfileDetail) {
                    val mResponse: VendorProfileDetail = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)

                    } else {
//                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setData(mResponse: VendorProfileDetail) {
        imguser.loadImage(mResponse.body.vendorDetail.image)
        profileusename.setText(mResponse.body.vendorDetail.firstName + " " + mResponse.body.vendorDetail.lastName)
        emaildID.setText(mResponse.body.email)
        if (mResponse.body.notification.equals("on")) {
            toggle_off2.visibility = View.VISIBLE
            toggle1.visibility = View.GONE
        } else {
            toggle_off2.visibility = View.GONE
            toggle1.visibility = View.VISIBLE
        }

    }
}