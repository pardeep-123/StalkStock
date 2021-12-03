package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.Chat
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.Model.CommonResponseModel
import com.stalkstock.vender.Model.OrderItem
import com.stalkstock.vender.Model.VendorBidDetailResponse
import com.stalkstock.vender.adapter.BidOrderAdapter
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BidDetail : Fragment(), View.OnClickListener, Observer<RestObservable> {
    val viewModel: VendorViewModel by viewModels()
    var bidId = ""
    var mUtil = Util()
    var arrayList = ArrayList<OrderItem>()
    var bidOrderAdapter: BidOrderAdapter? = null
    var userId = 0
    var chatId = ""
    var userName = ""
    var userImage = ""
    var views: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.activity_bid_detail, container, false)

        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getIntentData()

        setOnClicks()
    }

    private fun getIntentData() {

        bidId = arguments?.getString("bidId").toString()
        getBidDetail(bidId)

    }

    private fun getBidDetail(bidId: String) {
        val hashMap = HashMap<String, RequestBody>()
        hashMap["bidId"] = mUtil.createPartFromString(bidId)

        //   hashMap["type"] = "0"    // 0=>new request 1=>accepted
        viewModel.vendorBiddingDetail(requireActivity(), true, hashMap)
        viewModel.mResponse.observe(viewLifecycleOwner, this)

    }

    private fun setOnClicks() {
        placebid_button.setOnClickListener(this)
        newchat.setOnClickListener(this)
        biddetails_backarrow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.newchat -> {
                val intent = Intent(requireContext(), Chat::class.java)
                intent.putExtra("screen_type", "bid")
                intent.putExtra("id", bidId.toString())
                intent.putExtra("userId", userId)
                intent.putExtra("chatId", chatId)
                intent.putExtra("userName", userName)
                intent.putExtra("userImage", userImage)
                intent.putExtra("paramName", "bidId")
                startActivity(intent)
            }

            R.id.biddetails_backarrow -> {
                activity?.onBackPressed()
            }

            R.id.placebid_button -> {
                editDialog()
            }
        }
    }


    fun placeBid(hashMap: HashMap<String, RequestBody>) {

    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                .commit()
            return true
        }
        return false
    }

    private fun editDialog() {
        val dialog = Dialog(requireContext(), R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.biddetailsalertbox)
        dialog.setCancelable(true)
        Objects.requireNonNull<Window>(dialog.window).setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)

        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialog.submitbutton.setOnClickListener {
            if (dialog.edtSellingPrice.text.toString().isEmpty()) {
                dialog.edtSellingPrice.requestFocus()
                dialog.edtSellingPrice.error = resources.getString(R.string.please_enter_sale_price)
            }else if (dialog.edtSellingPrice.text.toString()=="0") {
                dialog.edtSellingPrice.requestFocus()
                dialog.edtSellingPrice.error = "Price should be greater than 0"
            } else if (dialog.edtSellngDesc.text.toString().isEmpty()) {
                dialog.edtSellngDesc.requestFocus()
                dialog.edtSellngDesc.error = resources.getString(R.string.please_enter_sale_terms)
            } else {
                val hashMap = HashMap<String, RequestBody>()
                hashMap["bidId"] = mUtil.createPartFromString(bidId)
                hashMap["amount"] = mUtil.createPartFromString(dialog.edtSellingPrice?.text.toString())
                hashMap["description"] = mUtil.createPartFromString(dialog.edtSellngDesc?.text.toString())
                viewModel.vendorAcceptBid(requireActivity(), true, hashMap)

                bidamt.visibility = View.VISIBLE
                biddisc.visibility = View.VISIBLE
                placebid_button.tag = 1
                placebid_button.text = "Place Bid"
                if (placebid_button.text.toString() == "Place Bid") {
                    placebid_button.text = "Edit Bid"
                    it.tag = 1 //pause
                } else {
                    val status = it.tag as Int
                    if (status == 1) {
                        it.tag = 0 //pause
                    } else {
                        placebid_button.text = "Edit Bid"
                        it.tag = 1 //pause
                    }
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorBidDetailResponse) {
                    val mResponse: VendorBidDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setBidData(mResponse.body)

                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                } else if (it.data is CommonResponseModel) {
                    val mResponse: CommonResponseModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        Log.i("====", mResponse.message)
                        activity?.onBackPressed()
                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireContext(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(requireContext(), it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setBidData(body: BidData) {
        userId = body.commercialDetail.id
        chatId = body.chatId.toString()
        requestid.text = "Request Id: " + body.requestNo
        bidid.text = "Bid : " + body.bidCount

        arrayList.clear()
        arrayList.addAll(body.orderItems)
        bidOrderAdapter = BidOrderAdapter(requireContext(), arrayList)
        rvOrders.layoutManager = LinearLayoutManager(requireContext())
        rvOrders.adapter = bidOrderAdapter
//        tvCategory.text= body.orderItems[0].product.categoryName
//        tvProduct.text= body.orderItems[0].product.name
//        tvQty.text= body.orderItems[0].qty.toString()
//        tvMesurement.text= body.orderItems[0].product.measurementName
        biddate.text = AppUtils.changeDateFormat(
            body.createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat1,
            GlobalVariables.DATEFORMAT.DateTimeFormat2
        )
//        biddate.text=mUtil.toDate(body.createdAt,"MMM,dd,yyyy")
//        bidtime.text= mUtil.toDate(body.createdAt,"hh:mm")
        Glide.with(this).load(body.commercialDetail.image).into(bidimguser as CircleImageView)
        bidusername.text = body.commercialDetail.firstName + " " + body.commercialDetail.lastName
        userName = body.commercialDetail.firstName + " " + body.commercialDetail.lastName
        userImage = body.commercialDetail.image
    }
}