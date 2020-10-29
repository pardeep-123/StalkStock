package com.stalkstock.commercial.view.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.live.stalkstockcommercial.ui.view.fragments.home.HomeAdapter
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationListActivity
import com.stalkstock.commercial.view.activities.AddedProduct
import com.stalkstock.commercial.view.activities.CommunicationListner
import kotlinx.android.synthetic.main.fragment_home_commercial.*

class HomeFragmentCommercial : Fragment(), View.OnClickListener {
    var listner: CommunicationListner? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_commercial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        listner!!.getYourFragmentActive(1)

        btnAddNew.setOnClickListener {
            startActivity(Intent(context, AddedProduct::class.java))
        }

        ivNotification.setOnClickListener {
            startActivity(Intent(context, NotificationListActivity::class.java))
        }

    }


    var list: ArrayList<HomeData> = ArrayList()
    private fun init() {

        list.clear()
        list.add(HomeData("", "8", "Bid Processing", R.color.orange_colour))
        list.add(HomeData("", "10", "Bid Received", R.color.orange_colour))
        list.add(HomeData("", "10", "Pay Now", R.color.orange_colour))
        list.add(HomeData("", "10", "Pay Now", R.color.orange_colour))
        list.add(HomeData("", "10", "Pay Now", R.color.orange_colour))


        rvHome.adapter = HomeAdapter(list)


    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CommunicationListner) {
            listner = context
        } else {
            throw RuntimeException("home frag not Attached")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listner = null
    }


    data class HomeData(
        var id: String = "", var bid: String = "", var type: String = "",
        var colorCode: Int = R.color.orange_colour
    )


}