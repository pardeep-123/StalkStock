package com.stalkstock.advertiser.activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.ModelHelpContent
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.toolbar.*

class HelpActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable> {
    val mContext: Context = this
    override fun getContentId(): Int {
        return R.layout.activity_help
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        tv_heading.text = "Help"
        iv_back.setOnClickListener(this)
        btn_ok.setOnClickListener(this)

        getHelpContent()
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getHelpContent() {
        viewModel.helpContentAPI(this, true)
        viewModel.homeResponse.observe(this, this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.btn_ok -> {
                /*if(MyApplication.instance.getString("usertype").equals("1")){
                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }else if(MyApplication.instance.getString("usertype").equals("2")){
                    val intent = Intent(mContext, MainCommercialActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else if (MyApplication.instance.getString("usertype").equals("5")) {
                    val intent = Intent(mContext, HomeActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }else{
                    val intent = Intent(mContext, MainConsumerActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }*/

                onBackPressed()

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelHelpContent) {
                    val mResponse: ModelHelpContent = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            txtHelp.setText(
                                Html.fromHtml(
                                    mResponse.body.content,
                                    Html.FROM_HTML_MODE_COMPACT
                                )
                            );
                        } else {
                            txtHelp.setText(
                                Html.fromHtml(
                                    mResponse.body.content
                                )
                            )
                        }

//                        AppUtils.showSuccessAlert(this, mResponse.message.toString())
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