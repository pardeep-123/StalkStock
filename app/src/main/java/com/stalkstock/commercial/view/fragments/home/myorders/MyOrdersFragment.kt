package com.stalkstock.commercial.view.fragments.home.myorders

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.live.stalkstockcommercial.OpenActivity
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.OrderDetailActivity
import com.stalkstock.commercial.view.adapters.MyOrdersListAdapter
import com.stalkstock.commercial.view.model.MyOrdersList
import com.stalkstock.consumer.model.OrderListModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_my_orders.*
import java.util.*
import kotlin.collections.ArrayList

class MyOrdersFragment : Fragment(), View.OnClickListener , Observer<RestObservable>,MyOrdersListAdapter.OnMyOrdersRecyclerViewItemClickListner{
    var handler: Handler?=null
    var list = ArrayList<OrderListModel.Body>()
    val viewModel: HomeViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myOrderApi()

//            createAdapterList()
    }

    private fun myOrderApi() {

        if (reset) {
            currentOffset = 0
//            currentModel.clear()
        }
        val hashMap = HashMap<String, String>()
        hashMap.put("offset", currentOffset.toString())
        hashMap.put("limit", "10")
        viewModel.getOrderListAPI(requireActivity(), true, hashMap)
        viewModel.homeResponse.observe(requireActivity(), this)

    }

 /*   private  fun createAdapterList(){
        if(list!=null){
            list!!.clear()
        }
        else{
            list= ArrayList()
        }

        list!!.add(ModelPojo.MyOrdersListModel(R.drawable.mc_img,"McDonald's","New York","USA","Beef Fresh Meet 250gm","01 May 2020","06:17 PM","$80.50","Pending"))
        list!!.add(ModelPojo.MyOrdersListModel(R.drawable.mkfc_image,"KFC","New York","USA","Creamy nachos, Maharaja mac","01 May 2020","06:17 PM","$36.00","Delivered"))

        rv_myOrders.adapter= MyOrdersListAdapter(requireActivity(), list!!, this@MyOrdersFragment)

        val dividerBetweenRecyclerViewItems = DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL)
        rv_myOrders.addItemDecoration(dividerBetweenRecyclerViewItems)
    }*/

    override fun onClick(p0: View?) {
        when(p0!!.id){
        }
    }

    override fun onMyOrdersItemClickListner(
        list: ArrayList<OrderListModel.Body>,
        position: Int
    ) {
        requireContext().OpenActivity(OrderDetailActivity::class.java){
            putString("fragment",position.toString())
            putString("orderId",list[position].id.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        handler= null
        requireContext().cacheDir.deleteRecursively()
        Runtime.getRuntime().gc()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderListModel) {
                    val mResponse: OrderListModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if(mResponse.body.size==0){
                            tvNoOrders.visibility=View.VISIBLE
                            rv_myOrders.visibility=View.GONE
                        }else{
                            tvNoOrders.visibility=View.GONE
                            rv_myOrders.visibility=View.VISIBLE
                            currentOffset += 10
                            list.addAll(mResponse.body)
                            /*       list.add(MyOrdersList(mResponse.body[i].orderVendor.shopName,mResponse.body[i].orderVendor.ShopAddress,"USA",mResponse.body[i].orderItems[i].product.name,mResponse.body[i].createdAt,"06:17 PM",
                                       mResponse.body[i].total,mResponse.body[i].orderStatus))*/
                            Log.i("lita",list.toString())


                            rv_myOrders.adapter= MyOrdersListAdapter(requireActivity(), list!!, this@MyOrdersFragment)

                            val dividerBetweenRecyclerViewItems = DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL)
                            rv_myOrders.addItemDecoration(dividerBetweenRecyclerViewItems)
                        }

                    } else {
                        AppUtils.showErrorAlert(requireActivity(), mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(requireActivity(), it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }



}