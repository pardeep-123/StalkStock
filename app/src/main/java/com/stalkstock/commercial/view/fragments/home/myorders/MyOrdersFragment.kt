package com.live.stalkstockcommercial.ui.view.fragments.myorders

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.live.stalkstockcommercial.OpenActivity
import com.live.stalkstockcommercial.ui.models.ModelPojo
import com.live.stalkstockcommercial.ui.view.activities.OrderDetailActivity
import com.live.stalkstockcommercial.ui.view.adapters.myorders.MyOrdersListAdapter
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.CommunicationListner
import kotlinx.android.synthetic.main.fragment_my_orders.*
import java.lang.RuntimeException

class MyOrdersFragment : Fragment(), View.OnClickListener , MyOrdersListAdapter.OnMyOrdersRecyclerViewItemClickListner{
    var listner: CommunicationListner?=null
    var handler: Handler?=null
    var list:ArrayList<ModelPojo.MyOrdersListModel>?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listner!!.getYourFragmentActive(2)
        handler= Handler(Looper.myLooper()!!)
        handler!!.postDelayed({
            createAdapterList()
        },60)
    }
    private  fun init(){

    }
    private  fun createAdapterList(){
        if(list!=null){
            list!!.clear()
        }else{
            list= ArrayList()
        }
        list!!.add(ModelPojo.MyOrdersListModel(R.drawable.mc_img,"McDonald's","New York","USA","Beef Fresh Meet 250gm","01 May 2020","06:17 PM","$80.50","Pending"))
        list!!.add(ModelPojo.MyOrdersListModel(R.drawable.mkfc_image,"KFC","New York","USA","Creamy nachos, Maharaja mac","01 May 2020","06:17 PM","$36.00","Delivered"))
        rv_myOrders.adapter= MyOrdersListAdapter(requireContext(), list!!, this@MyOrdersFragment)

        val dividerBetweenRecyclerViewItems = DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
        rv_myOrders.addItemDecoration(dividerBetweenRecyclerViewItems)


    }

    override fun onClick(p0: View?) {
        when(p0!!.id){


        }
    }

    override fun onMyOrdersItemClickListner(
        list: ArrayList<ModelPojo.MyOrdersListModel>,
        position: Int
    ) {

        requireContext().OpenActivity(OrderDetailActivity::class.java){
            putString("fragment",position.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CommunicationListner){
            listner= context as CommunicationListner
        }else{
            throw RuntimeException("Orders Frag not Attched")

        }
    }

    override fun onDetach() {
        super.onDetach()
        listner= null
        list= null
        handler= null
        requireContext().cacheDir.deleteRecursively()
        Runtime.getRuntime().gc()


    }

}