package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.consumer.adapter.CartAdapter
import com.stalkstock.consumer.fragment.CartFragment
import com.stalkstock.consumer.model.ModelCartData
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.Model.OrderItem
import com.stalkstock.vender.Model.VendorBiddingListResponse
import com.stalkstock.vender.ui.BidFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.bidprdouctaccpetlist.view.*
import kotlinx.android.synthetic.main.bidprdouctaccpetlist.view.biddate
import kotlinx.android.synthetic.main.bidprdouctaccpetlist.view.bidtime
import kotlinx.android.synthetic.main.bidprdouctaccpetlist.view.requestid
import kotlinx.android.synthetic.main.bidproductlist.view.*
import kotlinx.android.synthetic.main.item_view_bid_order_adapter.view.*
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_cart.view.name_text
import kotlin.collections.ArrayList

class BidOrderAdapter(
    var mContext: Context,
    var arrayList: ArrayList<OrderItem>
) :
    RecyclerView.Adapter<BidOrderAdapter.RecyclerViewHolder>() {

    var mUtil= Util()
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.item_view_bid_order_adapter, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.tvSNo.text= (position+1).toString()+"."
        holder.itemView.tvCategory.text= arrayList[position].product.categoryName
        holder.itemView.tvProduct.text= arrayList[position].product.name
        holder.itemView.tvQuantity.text= arrayList[position].qty.toString()
        holder.itemView.tvQuantityType.text= arrayList[position].product.measurementName
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
