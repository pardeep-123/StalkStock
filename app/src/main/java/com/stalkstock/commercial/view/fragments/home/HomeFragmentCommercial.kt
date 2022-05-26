package com.stalkstock.commercial.view.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.commercial.view.activities.AddedProduct
import com.stalkstock.commercial.view.adapters.HomeAdapter

import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.advertiser.activities.NotificationListActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.CommunicationListner
import com.stalkstock.commercial.view.model.BidingListResponse

import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home_commercial.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragmentCommercial : Fragment(), View.OnClickListener, Observer<RestObservable> {
    //  var listner: CommunicationListner? = null
    private val homeModel: HomeViewModel by viewModels()
    var list: ArrayList<BidingListResponse.BodyX> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_commercial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // listner!!.getYourFragmentActive(1)
        btnAddNew.setOnClickListener(this)
        ivNotification.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnAddNew -> {
                startActivity(Intent(context, AddedProduct::class.java))
            }
            R.id.ivNotification -> {
                startActivity(Intent(context, NotificationFirstActivity::class.java))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommunicationListner) {
            //listner = context
        } else {
            throw RuntimeException("home frag not Attached")
        }
    }

    override fun onDetach() {
        super.onDetach()
        // listner = null
    }


    data class HomeData(
        var id: String = "", var bid: String = "", var type: String = "",
        var colorCode: Int = R.color.orange_colour
    )

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is BidingListResponse) {
                    val mResponse: BidingListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        list.clear()
                        list.addAll(it.data.body)

                        rvHome.adapter = HomeAdapter(list)

                        if (list.isNotEmpty()) {
                            tvNoRequest.visibility = View.GONE
                            rvHome.visibility = View.VISIBLE
                        } else {
                            tvNoRequest.visibility = View.VISIBLE
                            rvHome.visibility = View.GONE
                        }

                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireActivity(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getBidingListApi()
    }

    private fun getBidingListApi() {
        val map = HashMap<String, String>()
        map["currencyType"] =Currency.getInstance(Locale.getDefault()).toString()
        homeModel.getBidingList(requireActivity(), true, map)
        homeModel.homeResponse.observe(this, this)
    }


}