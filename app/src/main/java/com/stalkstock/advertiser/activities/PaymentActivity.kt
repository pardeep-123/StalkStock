package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.ThanksActivity
import com.stalkstock.consumer.model.*
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.toolbar.*

class PaymentActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    val mContext: Context = this
    var click = 0
    var mMap: HashMap<String, String>? = null
    var mObject: PlaceOrderModel? = null
    val viewModel: HomeViewModel by viewModels()
    var mPaymentType = "1"  // 0=>Wallet 1=>Card 2=>paypal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_payment)

        tv_heading.text = "Payment"
        iv_back.setOnClickListener(this)
        btn_checkout.setOnClickListener(this)
        btn_preview.setOnClickListener(this)
        firstclick.setOnClickListener(this)
        secondclick.setOnClickListener(this)

        if (intent.hasExtra("orderdata")) {
//            mMap = intent.getSerializableExtra("orderdata") as HashMap<String, String>
            mObject = intent.getSerializableExtra("orderdata") as PlaceOrderModel
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.btn_checkout -> {
                if (click == 0) {
                    click = 1;
                    btn_checkout.setText("Pay Now")
                } else {
                    if (MyApplication.instance.getString("usertype").equals("1")) {
                        if (mObject != null)
                            placeOrderApi()
                        /* val intent = Intent(mContext, ThanksActivity::class.java)
                         startActivity(intent)*/
                    } else {
                        val intent = Intent(mContext, ThankyouActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
            R.id.btn_preview -> {
                val intent = Intent(mContext, AddNewCardActivity::class.java)
                startActivity(intent)
            }
            R.id.firstclick -> {
                mPaymentType = "1"
                oneone.setImageResource(R.drawable.radio_fill)
                onetwo.setImageResource(R.drawable.radio_circle)
            }
            R.id.secondclick -> {
                mPaymentType = "2"
                oneone.setImageResource(R.drawable.radio_circle)
                onetwo.setImageResource(R.drawable.radio_fill)
            }
        }
    }

    private fun placeOrderApi() {
        mObject!!.paymentMethod = mPaymentType
        viewModel.placeOrderApi(this, true, mObject!!)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderPlaceResponse) {
                    val mResponse: OrderPlaceResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        val intent = Intent(mContext, ThanksActivity::class.java)
                        startActivity(intent)
                    } else {
                        AppUtils.showErrorAlert(
                            this,
                            mResponse.message.toString()
                        )
                    }
                }

            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("Data not found")) {
                        val intent = Intent(this, MainConsumerActivity::class.java)
                        intent.putExtra("is_open", "4")
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()

                    } else
                        Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()
//

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }
}