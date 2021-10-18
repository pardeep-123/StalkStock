package com.stalkstock.commercial.view.fragments.home.myorders

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.live.stalkstockcommercial.OpenActivity
import com.live.stalkstockcommercial.ui.models.ModelPojo
import com.stalkstock.commercial.view.activities.OrderDetailActivity
import com.stalkstock.commercial.view.adapters.MyOrdersListAdapter
import com.stalkstock.R
import kotlinx.android.synthetic.main.fragment_my_orders.*

class MyOrdersFragment : Fragment(), View.OnClickListener , MyOrdersListAdapter.OnMyOrdersRecyclerViewItemClickListner{
    var handler: Handler?=null
    var list:ArrayList<ModelPojo.MyOrdersListModel>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            createAdapterList()
    }

    private  fun createAdapterList(){
        if(list!=null){
            list!!.clear()
        }else{
            list= ArrayList()
        }
        list!!.add(ModelPojo.MyOrdersListModel(R.drawable.mc_img,"McDonald's","New York","USA","Beef Fresh Meet 250gm","01 May 2020","06:17 PM","$80.50","Pending"))
        list!!.add(ModelPojo.MyOrdersListModel(R.drawable.mkfc_image,"KFC","New York","USA","Creamy nachos, Maharaja mac","01 May 2020","06:17 PM","$36.00","Delivered"))
        rv_myOrders.adapter= MyOrdersListAdapter(requireActivity(), list!!, this@MyOrdersFragment)

        val dividerBetweenRecyclerViewItems = DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL)
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
    }

    override fun onDetach() {
        super.onDetach()
        list= null
        handler= null
        requireContext().cacheDir.deleteRecursively()
        Runtime.getRuntime().gc()


    }

}