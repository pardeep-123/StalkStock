package com.stalkstock.consumer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.PaymentActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.adapter.CartAdapter
import com.stalkstock.consumer.model.ModelAddToCart
import com.stalkstock.consumer.model.ModelCartData
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.fragment_cart.*
import okhttp3.RequestBody
import java.text.DecimalFormat
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment(), Observer<RestObservable> {
    private lateinit var cartAdapter: CartAdapter
    lateinit var tvChange: TextView
    var views: View? = null
    lateinit var btnPaymentMethod: Button
    lateinit var recyceler: RecyclerView
    var currentCartModel: ArrayList<ModelCartData.Body.CartData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_cart, container, false)
        btnPaymentMethod = views!!.findViewById(R.id.btnPaymentMethod)
        tvChange = views!!.findViewById(R.id.tvChange)
        btnPaymentMethod.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, PaymentActivity::class.java)
            startActivity(intent)
        })
        tvChange.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, ManageAddress::class.java)
            startActivity(intent)
        })
        recyceler = views!!.findViewById(R.id.recyclerCartItems)
        cartAdapter = CartAdapter(this, requireContext(), currentCartModel)
        recyceler.adapter = cartAdapter
        return views
    }

    override fun onResume() {
        super.onResume()
        getCartData()
    }

    private fun getCartData() {
        val mainConsumerActivity = activity as MainConsumerActivity
        viewModel.getUserCardDataAPI(mainConsumerActivity, true)
        viewModel.homeResponse.observe(mainConsumerActivity, this)

    }

    val viewModel: HomeViewModel by viewModels()
    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is ModelCartData) {
                    val mResponse: ModelCartData = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setDataCart(mResponse)
                    } else {
                        AppUtils.showErrorAlert(
                            requireActivity(),
                            mResponse.message.toString()
                        )
                    }
                }

                if (it.data is ModelAddToCart) {
                    val mResponse: ModelAddToCart = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        getCartData()
                    } else {
                        AppUtils.showErrorAlert(
                            requireActivity(),
                            mResponse.message.toString()
                        )
                    }
                }
                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        getCartData()
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    if (it.error!!.toString().contains("Data not found")) {
                        val intent = Intent(requireActivity(), MainConsumerActivity::class.java)
                        intent.putExtra("is_open", "4")
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()

                    } else
                        Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()
//

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setDataCart(mResponse: ModelCartData) {
        if (mResponse.body.shopDetail.deliveryType == 0) {
            ltPickup.visibility = View.VISIBLE
            ltDeliveryAddress.visibility = View.GONE
        } else {
            ltPickup.visibility = View.GONE
            ltDeliveryAddress.visibility = View.VISIBLE

            var addressType = ""
            if (mResponse.body.address.type.equals("1"))
                addressType = "Home"
            if (mResponse.body.address.type.equals("2"))
                addressType = "Work"
            if (mResponse.body.address.type.equals("3"))
                addressType = "Other"
            item_count.setText("Deliver to $addressType")
            addressInCart.setText(mResponse.body.address.geoLocation)
        }


        var totalItemValue = 0.0f
        for (i in mResponse.body.cartData) {
            val mrp = i.product.mrp.toFloat()
            val singleItemTotal = i.quantity * mrp
            totalItemValue += singleItemTotal
        }
        val format = DecimalFormat("##.##").format(totalItemValue)
        txtItemTotal.setText("$$format")
        val shopCharges = mResponse.body.shopDetail.shopCharges.toFloat()
        val deliveryCharges = mResponse.body.deliveryCharges.value.toFloat()
        txtMerchantChargesTitle.setText(mResponse.body.shopDetail.shopName + " Charges")
        txtMerchantCharges.setText("$$shopCharges")
        txtDeliveryCharges.setText("$$deliveryCharges")

        val subtotal = totalItemValue + shopCharges + deliveryCharges
        val subtotalFormat = DecimalFormat("##.##").format(subtotal)
        txtTotal.setText("$$subtotalFormat")
        txtGrandTotal.setText("$$subtotalFormat")

        img.loadImage(mResponse.body.shopDetail.shopLogo)
        kfc.setText(mResponse.body.shopDetail.shopName)
        shopLocation.setText(mResponse.body.shopDetail.geoLocation)
        currentCartModel.clear()
        currentCartModel.addAll(mResponse.body.cartData)
        cartAdapter.notifyDataSetChanged()
    }

    fun addToCartAPI(productId: String, qty: String) {
        val mainConsumerActivity = activity as MainConsumerActivity
        val hashMap = HashMap<String, RequestBody>()
        hashMap["productId"] = mainConsumerActivity.mUtils.createPartFromString(productId)
        hashMap["quantity"] = mainConsumerActivity.mUtils.createPartFromString(qty)
        viewModel.userAddUpdateToCartAPI(mainConsumerActivity, false, hashMap)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }
}