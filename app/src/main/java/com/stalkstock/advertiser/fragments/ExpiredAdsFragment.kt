package com.stalkstock.advertiser.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.adapters.ExpiredAdsAdapter
import com.stalkstock.advertiser.model.BusinessAdsList
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import kotlinx.android.synthetic.main.fragment_expired_ads.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ExpiredAdsFragment : Fragment(), Observer<RestObservable> {

    lateinit var v:View
    lateinit var mContext: Context

    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_expired_ads, container, false)
        mContext = activity as Context

        return v
    }

    private fun getExpiredAdsList() {
        fun createPartFromString(string: String?): RequestBody {
            return RequestBody.create(
                MultipartBody.FORM, string
            )
        }
        val map = HashMap<String, RequestBody>()
        map["type"] = createPartFromString("2")
        viewModel.getAdsList(requireActivity(),true,map)
        viewModel.mResponse.observe(requireActivity(),this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is BusinessAdsList){
                    val data = it.data
                    if (data.code == 200){
                        val adsList: List<BusinessAdsList.Body> = data.body
                        setAdapter(adsList)
                    }
                }
            }

            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireActivity(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setAdapter(adsList: List<BusinessAdsList.Body>) {
        val adapter = ExpiredAdsAdapter(mContext,adsList)
        v.rv_expired.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        v.rv_expired.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getExpiredAdsList()
    }
}