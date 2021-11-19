package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.stalkstock.vender.adapter.AccpetAdapter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.Model.VendorBiddingListResponse
import com.stalkstock.vender.adapter.RequestAdapter
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.activity_bid_product.*
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import okhttp3.RequestBody
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class BidFragment : Fragment(), Observer<RestObservable>, RequestAdapter.RequestInterface{
    var mContext: Context? = null
    var requestAdapter: RequestAdapter? = null
    var accpetAdapter: AccpetAdapter? = null
    var arrayList=ArrayList<BidData>()
    // var bidrecyclerview: RecyclerView? = null
    var views: View? = null
    val viewModel: VendorViewModel by viewModels()

    var type="0"


    lateinit var mUtils: Util
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.activity_bid_product, container, false)

        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  editDialog()
        mUtils = Util()
        val bidproductbackarrow = views?.findViewById<ImageView>(R.id.bidproductbackarrow)
        bidproductbackarrow?.visibility = View.GONE
        // bidrecyclerview = views?.findViewById(R.id.bidrecyclerview)
        mContext = requireActivity()

        bidrecyclerview?.layoutManager = LinearLayoutManager(mContext)
        requestAdapter = RequestAdapter(mContext!!,arrayList,this)

        bidrecyclerview?.adapter = requestAdapter
        getBidList(type)

//        val backarrow = views?.findViewById<ImageView>(R.id.bidproductbackarrow)
//        val btnrequest = views?.findViewById<Button>(R.id.btnrequest)
//        val btnaccpet = views?.findViewById<Button>(R.id.btnaccpet)


//        backarrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });
        btnrequest.setOnClickListener {
            type="0"
            getBidList("0")
            btnrequest.background = resources.getDrawable(R.drawable.current_button)
            btnaccpet.background = resources.getDrawable(R.drawable.past_button2)
            btnrequest.setTextColor(resources.getColor(R.color.white))
            btnaccpet.setTextColor(resources.getColor(R.color.balck))

        }
        btnaccpet.setOnClickListener {
            type="1"
            getBidList("1")
            btnaccpet.background = resources.getDrawable(R.drawable.current_button)
            btnrequest.background = resources.getDrawable(R.drawable.past_button2)
            btnaccpet.setTextColor(resources.getColor(R.color.white))
            btnrequest.setTextColor(resources.getColor(R.color.balck))

        }
    }

    private fun editDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.biddetailsalertbox)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.submitbutton.setOnClickListener {
            if (dialog.edtSellingPrice.text.toString().isEmpty()) {
                dialog.edtSellingPrice.error = resources.getString(R.string.please_enter_sale_price)
            } else if (dialog.edtSellngDesc.text.toString().isEmpty()) {
                dialog.edtSellngDesc.error = resources.getString(R.string.please_enter_sale_terms)
            } else {
                val hashMap = HashMap<String, RequestBody>()
//                hashMap["bidId"] = mUtil.createPartFromString("bidId")
//                hashMap["amount"] = mUtil.createPartFromString(dialog.edtSellingPrice?.text.toString())
//                hashMap["description"] = mUtil.createPartFromString(dialog.edtSellngDesc?.text.toString())
//                viewModel.vendorAcceptBid(this, true, hashMap)
//                viewModel.mResponse.observe(this, this)

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
    private fun getBidList(type: String) {
        val hashMap = HashMap<String, RequestBody>()
        hashMap["type"] = mUtils.createPartFromString(type)
        //   hashMap["type"] = "0"    // 0=>new request 1=>accepted
        val mActivity = activity as BottomnavigationScreen
        viewModel.vendorBiddingList(mActivity, true, hashMap)
        viewModel.mResponse.observe(mActivity, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorBiddingListResponse) {
                    val mResponse: VendorBiddingListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        //Toast.makeText(requireContext(), mResponse.body.size.toString(), Toast.LENGTH_SHORT).show()
                        if(mResponse.body.size==0){

                            tvNoBid.visibility=View.VISIBLE
                            bidrecyclerview.visibility=View.GONE
                        }else{

                            tvNoBid.visibility=View.GONE
                            bidrecyclerview.visibility=View.VISIBLE
                            if(type=="0"){
                                arrayList.clear()
                                arrayList.addAll(it.data.body)
                                requestAdapter = RequestAdapter(mContext!!,arrayList,this)
                                bidrecyclerview.layoutManager = LinearLayoutManager(mContext)
                                bidrecyclerview.adapter = requestAdapter

                                //requestAdapter?.notifyDataSetChanged()
                            }else{
                                arrayList.clear()
                                arrayList.addAll(it.data.body)
                                accpetAdapter = AccpetAdapter(mContext!!,arrayList)
                                bidrecyclerview.layoutManager = LinearLayoutManager(mContext)
                                bidrecyclerview.adapter = accpetAdapter

                                //accpetAdapter?.notifyDataSetChanged()
                            }
                        }

                        Log.i("====",mResponse.message)

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

    override fun onResume() {
        super.onResume()
        getBidList(type)
    }

    override fun OnItemClick(position: Int, id: String) {
        val bidDetail= BidDetail()
        val bundle= Bundle()
        bundle.putString("bidId",id.toString())
        bidDetail.arguments= bundle
        fragmentManager?.beginTransaction()?.add(R.id.rl_container, bidDetail)?.addToBackStack(null)?.commit()
        //replaceFragment(bidDetail)
//        val intent = Intent(requireActivity(), BidDetail::class.java)
//        intent.putExtra("bidId",arrayList[position].id.toString())
//        mContext.startActivity(intent)
    }


}


