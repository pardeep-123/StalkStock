package com.stalkstock.vender.ui

import android.content.Context
import com.stalkstock.vender.adapter.AccpetAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.activity_bid_product.*
import okhttp3.RequestBody
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class BidFragment : Fragment(), Observer<RestObservable> {
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

        mUtils = Util()
        val bidproductbackarrow = views?.findViewById<ImageView>(R.id.bidproductbackarrow)
        bidproductbackarrow?.visibility = View.GONE
        // bidrecyclerview = views?.findViewById(R.id.bidrecyclerview)
        mContext = requireActivity()
        requestAdapter = RequestAdapter(mContext!!,arrayList)
        bidrecyclerview?.layoutManager = LinearLayoutManager(mContext)
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
                                requestAdapter = RequestAdapter(mContext!!,arrayList)
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


}


